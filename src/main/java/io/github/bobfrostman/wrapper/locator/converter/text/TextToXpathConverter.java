package io.github.bobfrostman.wrapper.locator.converter.text;

import io.github.bobfrostman.wrapper.locator.converter.LocatorConverter;

public class TextToXpathConverter implements LocatorConverter {

    @Override
    public String convertToXpath(String locator) {
        return "//*[text()='" + locator + "']";
    }

}
