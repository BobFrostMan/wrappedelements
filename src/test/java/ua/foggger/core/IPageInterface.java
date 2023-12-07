package ua.foggger.core;

import ua.foggger.annotation.WebElement;
import ua.foggger.elements.ClickableElement;
import ua.foggger.elements.IClickableElement;
import ua.foggger.page.IPage;

public interface IPageInterface extends IPage {

    @WebElement(value = "//p")
    IClickableElement heisenberg();

    @WebElement(value = "//p[contains(text(), '%s')]", name = "My name is Vova!")
    IClickableElement randomPerson(String text);

    @WebElement(value = "//p[contains(text(), '%s')]")
    default IClickableElement jessy() {
        ClickableElement clickableElement = new ClickableElement();
        clickableElement.setName("What's up bi......h!");
        return clickableElement;
    }

}
