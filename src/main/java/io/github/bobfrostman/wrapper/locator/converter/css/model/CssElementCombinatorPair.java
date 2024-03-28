package io.github.bobfrostman.wrapper.locator.converter.css.model;

public class CssElementCombinatorPair {

    private CssCombinatorType combinatorType;
    private CssElementAttributes cssElementAttributes;

    public CssElementCombinatorPair(CssCombinatorType combinatorTypeIn, CssElementAttributes cssElemAttributes) {
        this.combinatorType = combinatorTypeIn;
        this.cssElementAttributes = cssElemAttributes;
    }

    public CssCombinatorType getCombinatorType() {
        return combinatorType;
    }

    public CssElementAttributes getCssElementAttributes() {
        return cssElementAttributes;
    }

    @Override
    public String toString() {
        return "(Combinator=" + this.getCombinatorType() + ", " + this.cssElementAttributes + ")";
    }

    @Override
    public boolean equals(Object cssElementCombinatorPair) {
        if (cssElementCombinatorPair == null) {
            return false;
        }
        return this.toString().equals(cssElementCombinatorPair.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
