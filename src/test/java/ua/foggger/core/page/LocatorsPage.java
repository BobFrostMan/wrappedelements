package ua.foggger.core.page;

import ua.foggger.annotation.Parameter;
import ua.foggger.annotation.WebElement;
import ua.foggger.wrapper.element.WrappedElement;
import ua.foggger.wrapper.element.impl.ClickableElement;
import ua.foggger.wrapper.page.IPage;

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

    @WebElement(value = "//li/a[contains(text(), '${name}", waitUntil = UNTIL_CLICKABLE, timeout = 15)
    List<WrappedElement> listWithNamedParameter(@Parameter("name") String text);
}
