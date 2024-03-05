package ua.foggger.wrapper.locator.converter.classname;

import ua.foggger.wrapper.locator.converter.LocatorConverter;

public class ClassNameToXPath implements LocatorConverter {

    @Override
    public String convertToXpath(String locator) {
        return "//*[@class='" + locator + "']";
    }

}
