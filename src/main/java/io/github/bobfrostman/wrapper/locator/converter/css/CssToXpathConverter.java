package io.github.bobfrostman.wrapper.locator.converter.css;

import io.github.bobfrostman.wrapper.locator.converter.LocatorConverter;
import io.github.bobfrostman.wrapper.locator.converter.css.exception.LocatorConversionException;
import io.github.bobfrostman.wrapper.locator.converter.css.parsers.CssElementCombinatorPairsToXpath;

public class CssToXpathConverter implements LocatorConverter {

    private final CssElementCombinatorPairsToXpath combinatorPairsToXpath;

    public CssToXpathConverter() {
        combinatorPairsToXpath = new CssElementCombinatorPairsToXpath();
    }

    @Override
    public String convertToXpath(String locator) {
        try {
            return combinatorPairsToXpath.convertCssSelectorStringToXpathString(locator);
        } catch (LocatorConversionException e) {
            throw new RuntimeException(e);
        }
    }

}
