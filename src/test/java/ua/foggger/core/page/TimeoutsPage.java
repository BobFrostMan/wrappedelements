package ua.foggger.core.page;

import ua.foggger.annotation.WebElement;
import ua.foggger.wrapper.element.WrappedElement;
import ua.foggger.wrapper.element.impl.ClickableElement;
import ua.foggger.wrapper.page.IPage;

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
