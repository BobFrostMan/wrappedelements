package ua.foggger.wrapper.locator.converter.css.model;

import java.util.ArrayList;
import java.util.List;


public class CssElementAttributes {

    private String element;
    private List<CssAttribute> cssAttributeList;

    public CssElementAttributes(String elementIn, List<CssAttribute> cssAttributeListIn) {
        this.element = elementIn;
        this.cssAttributeList = new ArrayList<>(cssAttributeListIn);
    }

    public String getElement() {
        return element;
    }

    public List<CssAttribute> getCssAttributeList() {
        return cssAttributeList;
    }

    @Override
    public String toString() {
        return "Element=" + this.element + ", CssAttributeList=" + this.cssAttributeList;
    }

    @Override
    public boolean equals(Object cssElementAttributes) {
        if (cssElementAttributes == null) {
            return false;
        }
        return this.toString().equals(cssElementAttributes.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}