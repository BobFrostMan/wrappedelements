package ua.foggger.core.page;

import ua.foggger.annotation.WebElement;
import ua.foggger.element.IWrappedElement;
import ua.foggger.element.clickable.ClickableElement;
import ua.foggger.page.IPage;

public interface TimeoutsPage extends IPage {

    @WebElement(value = "//p", during = 15)
    IWrappedElement customTimeout();

    @WebElement(value = "(//form//input)[2]")
    IWrappedElement defaultTimeout();

    @WebElement(value = "//p[2]", during = 15)
    default IWrappedElement defaultTimeoutDefaultImpl() {
        return new ClickableElement("Default impl element name");
    }
}
