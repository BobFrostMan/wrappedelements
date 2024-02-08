package ua.foggger.tests.types.page;

import ua.foggger.annotation.WebComponent;
import ua.foggger.annotation.WebElement;
import ua.foggger.ui.component.EmptyComponent;
import ua.foggger.ui.component.EmptyHolderComponent;
import ua.foggger.tests.types.annotations.CustomEmptyElement;
import ua.foggger.tests.types.element.EmptyElement;
import ua.foggger.wrapper.element.WrappedElement;
import ua.foggger.wrapper.page.IPage;

import java.util.List;

public interface CustomTypePage extends IPage {

    @CustomEmptyElement(locator = "//div")
    EmptyElement emptyElement();

    @CustomEmptyElement(locator = "(//div|//span)[2]//label(text=\"%s\")")
    EmptyElement emptyElementWithParameter(String text);

    @CustomEmptyElement(locator = ".h1")
    default EmptyElement emptyElementWithDefaultImpl() {
        return null;
    }

    @WebElement("//li")
    List<WrappedElement> items();

    @WebComponent("//div")
    EmptyComponent element();

    @WebComponent("//div")
    EmptyHolderComponent holderComponent();
}
