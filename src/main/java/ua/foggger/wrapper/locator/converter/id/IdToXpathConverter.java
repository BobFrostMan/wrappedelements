package ua.foggger.wrapper.locator.converter.id;

import ua.foggger.wrapper.locator.converter.LocatorConverter;

public class IdToXpathConverter implements LocatorConverter {

    @Override
    public String convertToXpath(String locator) {
        return "//*[@id='" + locator + "']";
    }

}
