package ua.foggger.core.page;

import ua.foggger.annotation.WebElement;
import ua.foggger.wrapper.element.IWrappedElement;
import ua.foggger.wrapper.element.clickable.ClickableElement;
import ua.foggger.wrapper.page.IPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface BreakingBadPage extends IPage {

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

    @WebElement(value = "//p", waitUntil = UNTIL_VISIBLE)
    List<IWrappedElement> heisenbergs();

    @WebElement(value = "//combo", waitUntil = UNTIL_VISIBLE)
    default List<ClickableElement> combos() {
        List<ClickableElement> someComboRelatedGuys = new ArrayList<>();
        someComboRelatedGuys.add(new ClickableElement("Guy 1"));
        someComboRelatedGuys.add(new ClickableElement("Guy 2"));
        return someComboRelatedGuys;
    }


}
