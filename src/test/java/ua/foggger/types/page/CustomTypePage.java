package ua.foggger.types.page;

import ua.foggger.annotation.WebElement;
import ua.foggger.wrapper.element.IWrappedElement;
import ua.foggger.wrapper.page.IPage;
import ua.foggger.types.annotations.CustomEmptyElement;
import ua.foggger.types.element.EmptyElement;

import java.util.List;

public interface CustomTypePage extends IPage {

    @CustomEmptyElement(locator = "//div")
    EmptyElement emptyElement();

    @CustomEmptyElement(locator = "(//div|//span)[2]//label(text=\"%s\")")
    EmptyElement emptyElementWithParameter(String text);

    @CustomEmptyElement(locator = ".h1")
    default EmptyElement emptyElementWithDefaultImpl() {
        return new EmptyElement();
    }

    @WebElement("//li")
    List<IWrappedElement> items();

}
