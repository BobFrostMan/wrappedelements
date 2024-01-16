package ua.foggger.core.page;

import ua.foggger.annotation.WebElement;
import ua.foggger.wrapper.element.IWrappedElement;
import ua.foggger.wrapper.element.clickable.ClickableElement;
import ua.foggger.wrapper.page.IPage;

public interface TimeoutsPage extends IPage {

    @WebElement(value = "//p", timeout = 15)
    IWrappedElement customTimeout();

    @WebElement(value = "(//form//input)[2]")
    IWrappedElement defaultTimeout();

    @WebElement(value = "//p[2]", timeout = 15)
    default IWrappedElement defaultTimeoutDefaultImpl() {
        return new ClickableElement("Default impl element name");
    }
}
