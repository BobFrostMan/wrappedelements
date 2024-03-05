package ua.foggger.wrapper.locator.converter.css.model;

public class CssPsuedoClassNthChildToXpath extends CssPsuedoClassNthToXpath {

    public CssPsuedoClassNthChildToXpath(boolean lastIn) {
        super(lastIn);
    }

    @Override
    public String getNthToXpath(String element, String parenthesisExpression) {
        return super.getNthToXpath("*", parenthesisExpression);
    }

}
