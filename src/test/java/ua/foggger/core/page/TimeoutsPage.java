package ua.foggger.core.page;

import ua.foggger.annotation.WebElement;
import ua.foggger.elements.ClickableElement;
import ua.foggger.elements.IClickableElement;
import ua.foggger.page.IPage;

public interface TimeoutsPage extends IPage {

    @WebElement(value = "//p", during = 15)
    IClickableElement customTimeout();

    @WebElement(value = "(//form//input)[2]")
    IClickableElement defaultTimeout();

    @WebElement(value = "//p[2]", during = 15)
    default IClickableElement defaultTimeoutDefaultImpl() {
        return new ClickableElement("Default impl element name");
    }
}
