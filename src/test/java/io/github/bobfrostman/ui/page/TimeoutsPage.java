package io.github.bobfrostman.ui.page;

import io.github.bobfrostman.annotation.WebElement;
import io.github.bobfrostman.wrapper.element.WrappedElement;
import io.github.bobfrostman.wrapper.element.impl.ClickableElement;
import io.github.bobfrostman.wrapper.page.IPage;

public interface TimeoutsPage extends IPage {

    @WebElement(value = "//p", timeout = 15)
    WrappedElement customTimeout();

    @WebElement(value = "(//form//input)[2]")
    WrappedElement defaultTimeout();

    @WebElement(value = "//p[2]", timeout = 15)
    default WrappedElement defaultTimeoutDefaultImpl() {
        return new ClickableElement("Default impl element name");
    }
}
