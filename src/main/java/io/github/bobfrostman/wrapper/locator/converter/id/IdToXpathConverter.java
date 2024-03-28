package io.github.bobfrostman.wrapper.locator.converter.id;

import io.github.bobfrostman.wrapper.locator.converter.LocatorConverter;

public class IdToXpathConverter implements LocatorConverter {

    @Override
    public String convertToXpath(String locator) {
        return "//*[@id='" + locator + "']";
    }

}
