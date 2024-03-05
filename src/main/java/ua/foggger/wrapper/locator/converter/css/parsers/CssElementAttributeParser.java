package ua.foggger.wrapper.locator.converter.css.parsers;

import ua.foggger.wrapper.locator.converter.css.exception.LocatorConversionException;
import ua.foggger.wrapper.locator.converter.css.model.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CssElementAttributeParser {

    public static final String ERROR_INVALID_ATTRIBUTE_VALUE = "Invalid attribute value";
    public static final String ERROR_INVALID_ELEMENT_AND_OR_ATTRIBUTES = "Invalid element and/or attributes";
    public static final String ERROR_QUOTATIONS_MISMATCHED = "Quotations mismatched";

    private static final String QUOTES_RE = "([\"\'])";
    private static final String ATTRIBUTE_VALUE_RE = "([-_.#a-zA-Z0-9:\\/ ]+)";
    private static final String ATTRIBUTE_VALUE_RE_NO_SPACES = "([-_.#a-zA-Z0-9:\\/]+)";
    private static final String ATTRIBUTE_TYPE_RE = createElementAttributeNameRegularExpression();
    private static final String ELEMENT_ATTRIBUTE_NAME_RE = "(-?[_a-zA-Z]+[_a-zA-Z0-9-]*)";
    private static final String STARTING_ELEMENT_RE = "^(" + ELEMENT_ATTRIBUTE_NAME_RE + "|([*]))?";
    public static final String PSUEDO_RE = "(:[a-z][a-z\\-]*([(][^)]+[)])?)";
    private static final String ATTRIBUTE_RE = "(" + PSUEDO_RE + "|(\\[" + "\\s*" + ELEMENT_ATTRIBUTE_NAME_RE + "\\s*" + ATTRIBUTE_TYPE_RE + "\\s*" + "((" + QUOTES_RE + ATTRIBUTE_VALUE_RE + QUOTES_RE + ")|(" + ATTRIBUTE_VALUE_RE_NO_SPACES + "))?" + "\\s*" + "\\]))";


    private static String createElementAttributeNameRegularExpression() {
        StringBuilder builder = new StringBuilder();
        for (CssAttributeValueType type : CssAttributeValueType.values()) {
            if (builder.length() == 0) {
                builder.append("((");
            } else {
                builder.append(")|(");
            }
            builder.append("\\").append(type.getEqualStringName());
        }
        builder.append("))?");
        return builder.toString();
    }

    public void checkValid(String elementWithAttributesString) {
        int reIndexAttributeValueType = 9;
        int reIndexAttributeValue = 16;
        int reIndexStartingQuote = reIndexAttributeValue + 2;
        int reIndexEndingQuote = reIndexStartingQuote + 2;
        Pattern cssElementAtributePattern = Pattern.compile(STARTING_ELEMENT_RE + ATTRIBUTE_RE + "*$");
        Matcher match = cssElementAtributePattern.matcher(elementWithAttributesString);
        if (!match.find()) {
            throw new LocatorConversionException(ERROR_INVALID_ELEMENT_AND_OR_ATTRIBUTES);
        }
        boolean cssAttributeValueTypeExists = match.group(reIndexAttributeValueType) != null;
        boolean cssAttributeValueExists = match.group(reIndexAttributeValue) != null;
        if ((cssAttributeValueTypeExists && !cssAttributeValueExists) || (!cssAttributeValueTypeExists && cssAttributeValueExists)) {
            throw new LocatorConversionException(ERROR_INVALID_ATTRIBUTE_VALUE);
        }

        String startQuote = match.group(reIndexStartingQuote);
        String endQuote = match.group(reIndexEndingQuote);
        boolean startQuoteExists = startQuote != null;
        //note the only way startQuote could be null is that there no attribute value
        if (startQuoteExists && !(startQuote.equals(endQuote))) {
            throw new LocatorConversionException(ERROR_QUOTATIONS_MISMATCHED);
        }
    }

    public CssElementAttributes createElementAttribute(String elementWithAttributesString) throws LocatorConversionException {
        int rePseudoClass = 2;
        int reIndexAttributeName = 5;
        int reIndexAttributeType = reIndexAttributeName + 1;
        int reIndexAttributeValueWithQuotes = 14;
        int reIndexAttributeValueWithinQuotes = reIndexAttributeValueWithQuotes + 2;
        int reIndexAttributeValueWithoutQuotes = reIndexAttributeValueWithinQuotes + 2;

        checkValid(elementWithAttributesString);
        Pattern startingCssElementAtributePattern = Pattern.compile(STARTING_ELEMENT_RE);
        Matcher match = startingCssElementAtributePattern.matcher(elementWithAttributesString);
        List<CssAttribute> attributeList = new ArrayList<>();

        String element = null;
        if (match.find()) {
            String possibleElement = match.group();
            if (!possibleElement.isEmpty()) {
                element = possibleElement;
            }
        }
        Pattern restOfCssElementAtributePattern = Pattern.compile(ATTRIBUTE_RE);
        match = restOfCssElementAtributePattern.matcher(elementWithAttributesString);


        while (match.find()) {
            String pseudoClass = match.group(rePseudoClass);
            if (pseudoClass != null) {
                CssPsuedoClassType psuedoClassType;
                String parenthesisExpression = null;
                try {
                    Pattern pseudoClassWithParenthesisExpression = Pattern.compile("(:[a-z][a-z\\-]*)(\\()([^)]+)(\\))");
                    Matcher pseudoClassWithParenthesisExpressionMatch = pseudoClassWithParenthesisExpression.matcher(pseudoClass);
                    if (pseudoClassWithParenthesisExpressionMatch.find()) {
                        parenthesisExpression = pseudoClassWithParenthesisExpressionMatch.group(3).replaceAll(CssSelectorStringSplitter.NTH_OF_TYPE_PLACEHOLDER, "+");
                        pseudoClass = pseudoClassWithParenthesisExpressionMatch.group(1);
                    }

                    psuedoClassType = CssPsuedoClassType.psuedoClassTypeString(pseudoClass, element, parenthesisExpression);
                } catch (IllegalArgumentException e) {
                    String output = pseudoClass;
                    if (parenthesisExpression != null) {
                        output = pseudoClass + "(" + parenthesisExpression + ")";
                    }
                    throw new LocatorConversionException(String.format("Unable to convert \"%s\". A converter for CSS Seletor Pseudo-Classes has not been implement at this time. TODO: A future capability.", output));
                }
                attributeList.add(new CssAttributePsuedoClass(psuedoClassType, element, parenthesisExpression));
            } else {
                boolean attributeValueHasQuotes = match.group(reIndexAttributeValueWithQuotes) != null;
                attributeList.add(new CssAttribute(
                        match.group(reIndexAttributeName),
                        match.group(attributeValueHasQuotes ? reIndexAttributeValueWithinQuotes : reIndexAttributeValueWithoutQuotes),
                        match.group(reIndexAttributeType)));
            }
        }
        attributeList = cleanUpAttributes(attributeList);
        return new CssElementAttributes(element, attributeList);
    }

    public List<CssAttribute> cleanUpAttributes(List<CssAttribute> attributeList) {
        //Sets will guarantee no duplicate attributes and hashlinkset preserves order
        LinkedHashSet<CssAttribute> attributeSet = new LinkedHashSet<>(attributeList);
        cleanUpChildOfType(attributeSet, CssPsuedoClassType.FIRST_CHILD, CssPsuedoClassType.ONLY_CHILD);
        cleanUpChildOfType(attributeSet, CssPsuedoClassType.FIRST_OF_TYPE, CssPsuedoClassType.FIRST_CHILD);
        cleanUpChildOfType(attributeSet, CssPsuedoClassType.FIRST_OF_TYPE, CssPsuedoClassType.ONLY_CHILD);

        cleanUpChildOfType(attributeSet, CssPsuedoClassType.LAST_CHILD, CssPsuedoClassType.ONLY_CHILD);
        cleanUpChildOfType(attributeSet, CssPsuedoClassType.LAST_OF_TYPE, CssPsuedoClassType.LAST_CHILD);
        cleanUpChildOfType(attributeSet, CssPsuedoClassType.LAST_OF_TYPE, CssPsuedoClassType.ONLY_CHILD);

        cleanUpChildOfType(attributeSet, CssPsuedoClassType.FIRST_OF_TYPE, CssPsuedoClassType.ONLY_OF_TYPE);
        cleanUpChildOfType(attributeSet, CssPsuedoClassType.LAST_OF_TYPE, CssPsuedoClassType.ONLY_OF_TYPE);

        cleanUpChildOfType(attributeSet, CssPsuedoClassType.ONLY_OF_TYPE, CssPsuedoClassType.ONLY_CHILD);

        return new ArrayList<>(attributeSet);
    }

    private void cleanUpChildOfType(LinkedHashSet<CssAttribute> attributeSet, CssPsuedoClassType candidateToRemove, CssPsuedoClassType reasonToRemove) {
        CssAttributePsuedoClass foundCandidateToRemove = null;
        CssAttributePsuedoClass foundReasonToRemove = null;
        for (CssAttribute attribute : attributeSet) {
            if (attribute instanceof CssAttributePsuedoClass cssAttributePsuedoClass) {
                if (cssAttributePsuedoClass.getCssPsuedoClassType().equals(candidateToRemove)) {
                    foundCandidateToRemove = cssAttributePsuedoClass;
                } else if (cssAttributePsuedoClass.getCssPsuedoClassType().equals(reasonToRemove)) {
                    foundReasonToRemove = cssAttributePsuedoClass;
                }
                if (foundCandidateToRemove != null && foundReasonToRemove != null) {
                    break;
                }
            }
        }
        if (foundCandidateToRemove != null && foundReasonToRemove != null) {
            attributeSet.remove(foundCandidateToRemove);
        }
    }

}
	


