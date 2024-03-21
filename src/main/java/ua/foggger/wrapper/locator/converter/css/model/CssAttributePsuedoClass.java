package ua.foggger.wrapper.locator.converter.css.model;

public class CssAttributePsuedoClass extends CssAttribute {

    private CssPsuedoClassType pseudoClassType;
    private String element;
    private String parenthesisExpression;

    public CssAttributePsuedoClass(CssPsuedoClassType psuedoClassTypeIn, String elementIn, String parenthesisExpressionIn) {
        super(null, null, (CssAttributeValueType) null);
        pseudoClassType = psuedoClassTypeIn;
        element = elementIn;
        parenthesisExpression = parenthesisExpressionIn;
    }

    public String getXPath() {
        return pseudoClassType.getXpath(element, parenthesisExpression);

    }

    public CssPsuedoClassType getCssPsuedoClassType() {
        return pseudoClassType;
    }

    @Override
    public String toString() {
        return "Psuedo Class = " + pseudoClassType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof CssAttributePsuedoClass)) {
            return false;
        }
        CssAttributePsuedoClass obj = (CssAttributePsuedoClass) o;
        if (this.parenthesisExpression == null) {
            if (obj.parenthesisExpression != null) {
                return false;
            }
        } else if (!this.parenthesisExpression.equals(obj.parenthesisExpression)) {
            return false;
        }
        return this.pseudoClassType.equals(obj.pseudoClassType);
    }

}
