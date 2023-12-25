package ua.foggger.core.page;

import ua.foggger.annotation.WebElement;
import ua.foggger.elements.ClickableElement;
import ua.foggger.elements.IClickableElement;
import ua.foggger.page.IPage;

public interface IPageInterface extends IPage {

    @WebElement(value = "//p")
    IClickableElement heisenberg();

    @WebElement(value = "(//form//input)[2]")
    IClickableElement heisenberg2();

    @WebElement(value = "//p[contains(text(), '%s')]", name = "My name is Vova!")
    IClickableElement randomPerson(String text);

    @WebElement(value = "//p[2]")
    default IClickableElement jessy() {
        ClickableElement clickableElement = new ClickableElement("What's up bi......h!");
        return clickableElement;
    }

    @WebElement(value = "//p[3]", name = "Saul Goodman")
    default IClickableElement saulGoodman() {
        return new ClickableElement("I'm a lawyer");
    }

    @WebElement(value = "p > tuco_%s")
    IClickableElement randomTuco(String text);

    @WebElement(value = "//combo", waitUntil = UNTIL_CLICKABLE)
    IClickableElement combo();

}
