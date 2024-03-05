package ua.foggger.wrapper.locator.converter.linktext;

import ua.foggger.wrapper.locator.converter.LocatorConverter;

public class LinkTextToXpathConverter implements LocatorConverter {

    @Override
    public String convertToXpath(String locator) {
        return "//*[@href='" + locator + "']";
    }

}
