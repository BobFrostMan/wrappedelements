package ua.foggger.core.page;

import ua.foggger.annotation.WebElement;
import ua.foggger.element.IWrappedElement;
import ua.foggger.element.clickable.ClickableElement;
import ua.foggger.page.IPage;

public interface IBreakingBadPage extends IPage {

    @WebElement(value = "//p")
    IWrappedElement heisenberg();

    @WebElement(value = "//p[contains(text(), '%s')]", name = "My name is Vova!")
    IWrappedElement randomPerson(String text);

    @WebElement(value = "//p[2]")
    default IWrappedElement jessy() {
        ClickableElement clickableElement = new ClickableElement("What's up bi......h!");
        return clickableElement;
    }

    @WebElement(value = "//p[3]", name = "Saul Goodman")
    default IWrappedElement saulGoodman() {
        return new ClickableElement("I'm a lawyer");
    }

    @WebElement(value = "//combo", waitUntil = UNTIL_CLICKABLE, timeout = 15)
    IWrappedElement combo();

}
