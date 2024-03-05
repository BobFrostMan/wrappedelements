package ua.foggger.wrapper.locator.converter.tagname;

import ua.foggger.wrapper.locator.converter.LocatorConverter;

public class TagToXPath implements LocatorConverter {

    @Override
    public String convertToXpath(String locator) {
        return "//" + locator;
    }

}
