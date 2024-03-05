package ua.foggger.wrapper.locator.converter.text;

import ua.foggger.wrapper.locator.converter.LocatorConverter;

public class TextToXpathConverter implements LocatorConverter {

    @Override
    public String convertToXpath(String locator) {
        return "//*[text()='" + locator + "']";
    }

}
