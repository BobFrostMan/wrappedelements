package io.github.bobfrostman.ui.page;

import io.github.bobfrostman.annotation.Parameter;
import io.github.bobfrostman.annotation.WebElement;
import io.github.bobfrostman.wrapper.element.WrappedElement;
import io.github.bobfrostman.wrapper.element.impl.ClickableElement;
import io.github.bobfrostman.wrapper.page.IPage;
import io.github.bobfrostman.common.IKnowInteractors;

import java.util.List;

public interface LocatorsPage extends IPage {

    @WebElement("id=someId")
    ClickableElement elementSpecifiedById();

    @WebElement("css=p > p")
    ClickableElement elementSpecifiedByCss();

    @WebElement("name=someName")
    ClickableElement elementSpecifiedByName();

    @WebElement("class=someClass")
    ClickableElement elementSpecifiedByClass();

    @WebElement("tag=p-table")
    ClickableElement elementSpecifiedByTagName();

    @WebElement("linkText= someLinkText")
    ClickableElement elementSpecifiedByLinkText();

    @WebElement("id")
    WrappedElement elementWithIdAutoDetect();

    @WebElement(value = "//p")
    WrappedElement heisenberg();

    @WebElement(value = "(//form//input)[2]")
    WrappedElement heisenberg2();

    @WebElement(value = "p > tuco_%s")
    WrappedElement randomTuco(String text);

    @WebElement(value = "//div[text() = '%s']|//span[contains(text(), '%s')]")
    WrappedElement elementWithFewPlaceHoldersAndOneParameter(String text);

    @WebElement(value = "//div[text() = '%d']|//span[contains(text(), '%d')]")
    WrappedElement elementWithFewDigitPlaceHoldersAndOneParameter(int number);

    @WebElement(value = "//li/a[contains(text(), '${first_name} ${ last_name }')]")
    WrappedElement elementWithNamedParameter(@Parameter("last_name") String lastName, @Parameter("first_name") String firstName);

    @WebElement(value = "//li/a[contains(text(), '${name}", waitUntil = IKnowInteractors.UNTIL_CLICKABLE, timeout = 15)
    List<WrappedElement> listWithNamedParameter(@Parameter("name") String text);
}
