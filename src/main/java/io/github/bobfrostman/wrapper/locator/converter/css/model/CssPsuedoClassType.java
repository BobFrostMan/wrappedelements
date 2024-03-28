package io.github.bobfrostman.wrapper.locator.converter.css.model;

import io.github.bobfrostman.wrapper.locator.converter.css.exception.LocatorConversionException;

public enum CssPsuedoClassType {

    EMPTY(":empty", (e, p) -> "[not(*) and .=\"\"]"),
    NTH_OF_TYPE(":nth-of-type", new CssPsuedoClassNthToXpath(false)),
    NTH_LAST_OF_TYPE(":nth-last-of-type", new CssPsuedoClassNthToXpath(true)),
    FIRST_OF_TYPE(":first-of-type", (e, p) -> NTH_OF_TYPE.getXpath(e, "1")),
    LAST_OF_TYPE(":last-of-type", (e, p) -> NTH_LAST_OF_TYPE.getXpath(e, "1")),
    ONLY_OF_TYPE(":only-of-type", (e, p) -> FIRST_OF_TYPE.getXpath(e, p) + LAST_OF_TYPE.getXpath(e, p)),
    NTH_CHILD(":nth-child", new CssPsuedoClassNthChildToXpath(false)),
    NTH_LAST_CHILD(":nth-last-child", new CssPsuedoClassNthChildToXpath(true)),
    FIRST_CHILD(":first-child", (e, p) -> NTH_CHILD.getXpath(e, "1")),
    LAST_CHILD(":last-child", (e, p) -> NTH_LAST_CHILD.getXpath(e, "1")),
    ONLY_CHILD(":only-child", (e, p) -> FIRST_CHILD.getXpath("", null) + LAST_CHILD.getXpath("", null));


    private String typeString;
    private CssPseudoClassToXpath toXpath;

    CssPsuedoClassType(String typeStringIn, CssPseudoClassToXpath toXpathIn) {
        this.typeString = typeStringIn;
        this.toXpath = toXpathIn;
    }

    public String getXpath(String element, String parenthesisExpression) {
        return toXpath.getXpath(element, parenthesisExpression);
    }

    public static CssPsuedoClassType psuedoClassTypeString(String unknownString, String element, String parenthesisExpression) {
        if (unknownString == null) {
            return null;
        }
        switch (unknownString) {
            case ":empty":
                return EMPTY;
            case ":first-of-type":
                return getOfType(FIRST_OF_TYPE, element);
            case ":last-of-type":
                return getOfType(LAST_OF_TYPE, element);
            case ":only-of-type":
                return getOfType(ONLY_OF_TYPE, element);
            case ":nth-of-type":
                return getOfType(NTH_OF_TYPE, element, parenthesisExpression);
            case ":nth-last-of-type":
                return getOfType(NTH_LAST_OF_TYPE, element, parenthesisExpression);
            case ":nth-child":
                return getOfType(NTH_CHILD, element, parenthesisExpression);
            case ":nth-last-child":
                return getOfType(NTH_LAST_CHILD, element, parenthesisExpression);
            case ":first-child":
                return FIRST_CHILD;
            case ":last-child":
                return LAST_CHILD;
            case ":only-child":
                return ONLY_CHILD;
            default:
                throw new IllegalArgumentException(unknownString);
        }
    }

    private static CssPsuedoClassType getOfType(CssPsuedoClassType ofType, String element) {
        if (element == null || element.equals("*")) {
            throw new LocatorConversionException("Unable to convert a CSS Selector with \"*\" or \"\" before psuedo class :first-of-type/:last-of-type/:only-of-type/:nth-of-type to a XPath");
        }
        return ofType;
    }

    private static CssPsuedoClassType getOfType(CssPsuedoClassType ofType, String element, String parenthesisExpression) {
        if (element == null || element.equals("*")) {
            throw new LocatorConversionException("Unable to convert a CSS Selector with \"*\" or \"\" before psuedo class :first-of-type/:last-of-type/:only-of-type/:nth-of-type to a XPath");
        } else {
            String positiveN = "^[+]?([0]*[1-9][0-9]*)?n([+-][0-9]+)?$";
            String negativeN = "^[-][0-9]*n[+]([0]*[1-9][0-9]*)$";
            String noN = "^[+]?([1-9][0-9]*)$";

            String nthOfTypeRe = "odd|even|" + positiveN + "|" + negativeN + "|" + noN;
            if (parenthesisExpression.matches(nthOfTypeRe)) {
                return ofType;
            } else {
                throw new LocatorConversionException(String.format("Unable to convert. %s is an invalid argument expression for :nth-of-type()", parenthesisExpression));
            }
        }
    }
}