package ua.foggger.types.page;

import ua.foggger.page.IPage;
import ua.foggger.types.annotations.CustomEmptyElement;
import ua.foggger.types.element.EmptyElement;

public interface CustomTypePage extends IPage {

    @CustomEmptyElement(locator = "//div")
    EmptyElement emptyElement();

    @CustomEmptyElement(locator = "(//div|//span)[2]//label(text=\"%s\")")
    EmptyElement emptyElementWithParameter(String text);

    @CustomEmptyElement(locator = ".h1")
    default EmptyElement emptyElementWithDefaultImpl() {
        return new EmptyElement();
    }

}
