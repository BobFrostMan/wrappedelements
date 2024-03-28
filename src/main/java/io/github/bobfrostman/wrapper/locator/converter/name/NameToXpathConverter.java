package io.github.bobfrostman.wrapper.locator.converter.name;

import io.github.bobfrostman.wrapper.locator.converter.LocatorConverter;

public class NameToXpathConverter implements LocatorConverter {

    @Override
    public String convertToXpath(String locator) {
        return "//*[@name='" + locator + "']";
    }

}
