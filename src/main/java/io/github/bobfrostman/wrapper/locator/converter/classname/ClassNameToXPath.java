package io.github.bobfrostman.wrapper.locator.converter.classname;

import io.github.bobfrostman.wrapper.locator.converter.LocatorConverter;

public class ClassNameToXPath implements LocatorConverter {

    @Override
    public String convertToXpath(String locator) {
        return "//*[@class='" + locator + "']";
    }

}
