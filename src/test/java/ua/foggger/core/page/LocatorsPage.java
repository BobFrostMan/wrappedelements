package ua.foggger.core.page;

import ua.foggger.annotation.Parameter;
import ua.foggger.annotation.WebElement;
import ua.foggger.element.IWrappedElement;
import ua.foggger.element.clickable.ClickableElement;
import ua.foggger.page.IPage;

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
    IWrappedElement elementWithIdAutoDetect();

    @WebElement(value = "//p")
    IWrappedElement heisenberg();

    @WebElement(value = "(//form//input)[2]")
    IWrappedElement heisenberg2();

    @WebElement(value = "p > tuco_%s")
    IWrappedElement randomTuco(String text);

    @WebElement(value = "//div[text() = '%s']|//span[contains(text(), '%s')]")
    IWrappedElement elementWithFewPlaceHoldersAndOneParameter(String text);

    @WebElement(value = "//div[text() = '%d']|//span[contains(text(), '%d')]")
    IWrappedElement elementWithFewDigitPlaceHoldersAndOneParameter(int number);

    @WebElement(value = "//li/a[contains(text(), '${first_name} ${ last_name }')]")
    IWrappedElement elementWithNamedParameter(@Parameter("last_name") String lastName, @Parameter("first_name") String firstName);
}
