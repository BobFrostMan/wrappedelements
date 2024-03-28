package io.github.bobfrostman.wrapper.locator.converter.tagname;

import io.github.bobfrostman.wrapper.locator.converter.LocatorConverter;

public class TagToXPath implements LocatorConverter {

    @Override
    public String convertToXpath(String locator) {
        return "//" + locator;
    }

}
