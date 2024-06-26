package io.github.bobfrostman.ui.page;

import io.github.bobfrostman.annotation.WebElement;
import io.github.bobfrostman.wrapper.element.WrappedElement;
import io.github.bobfrostman.wrapper.element.impl.ClickableElement;
import io.github.bobfrostman.wrapper.page.IPage;
import io.github.bobfrostman.common.IKnowInteractors;

import java.util.ArrayList;
import java.util.List;

public interface BreakingBadPage extends IPage {

    @WebElement(value = "//p")
    WrappedElement heisenberg();

    @WebElement(value = "//p[contains(text(), '%s')]", name = "My name is Vova!")
    WrappedElement randomPerson(String text);

    @WebElement(value = "//p[2]")
    default WrappedElement jessy() {
        ClickableElement clickableElement = new ClickableElement("What's up bi......h!");
        return clickableElement;
    }

    @WebElement(value = "//p[3]", name = "Saul Goodman")
    default WrappedElement saulGoodman() {
        return new ClickableElement("I'm a lawyer");
    }

    @WebElement(value = "//combo", waitUntil = IKnowInteractors.VERTICAL_SCROLL_UNTIL_VISIBLE, timeout = 15)
    WrappedElement combo();

    @WebElement(value = "//p", waitUntil = IKnowInteractors.UNTIL_VISIBLE)
    List<WrappedElement> heisenbergs();

    //Since hobbits not from breaking bad, they won't be annotated
    List<ClickableElement> hobbits();

    ClickableElement frodo();


    @WebElement(value = "//combo", waitUntil = IKnowInteractors.UNTIL_VISIBLE)
    default List<ClickableElement> combos() {
        List<ClickableElement> someComboRelatedGuys = new ArrayList<>();
        someComboRelatedGuys.add(new ClickableElement("Guy 1"));
        someComboRelatedGuys.add(new ClickableElement("Guy 2"));
        return someComboRelatedGuys;
    }


}
