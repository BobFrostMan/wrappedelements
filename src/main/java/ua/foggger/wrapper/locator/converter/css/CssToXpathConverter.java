package ua.foggger.wrapper.locator.converter.css;

import ua.foggger.wrapper.locator.converter.LocatorConverter;
import ua.foggger.wrapper.locator.converter.css.exception.LocatorConversionException;
import ua.foggger.wrapper.locator.converter.css.parsers.CssElementCombinatorPairsToXpath;

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
