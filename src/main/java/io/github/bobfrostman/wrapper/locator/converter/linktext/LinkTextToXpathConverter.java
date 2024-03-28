package io.github.bobfrostman.wrapper.locator.converter.linktext;

import io.github.bobfrostman.wrapper.locator.converter.LocatorConverter;

public class LinkTextToXpathConverter implements LocatorConverter {

    @Override
    public String convertToXpath(String locator) {
        return "//*[@href='" + locator + "']";
    }

}
