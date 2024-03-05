package ua.foggger.wrapper.locator.converter.name;

import ua.foggger.wrapper.locator.converter.LocatorConverter;

public class NameToXpathConverter implements LocatorConverter {

    @Override
    public String convertToXpath(String locator) {
        return "//*[@name='" + locator + "']";
    }

}
