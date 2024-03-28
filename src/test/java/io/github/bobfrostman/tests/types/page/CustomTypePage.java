package io.github.bobfrostman.tests.types.page;

import io.github.bobfrostman.annotation.WebComponent;
import io.github.bobfrostman.annotation.WebElement;
import io.github.bobfrostman.tests.types.annotations.CustomEmptyElement;
import io.github.bobfrostman.tests.types.element.EmptyElement;
import io.github.bobfrostman.ui.component.EmptyComponent;
import io.github.bobfrostman.ui.component.EmptyHolderComponent;
import io.github.bobfrostman.wrapper.element.WrappedElement;
import io.github.bobfrostman.wrapper.page.IPage;

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
