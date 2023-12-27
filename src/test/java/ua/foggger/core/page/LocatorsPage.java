package ua.foggger.core.page;

import ua.foggger.annotation.Parameter;
import ua.foggger.annotation.WebElement;
import ua.foggger.elements.ClickableElement;
import ua.foggger.elements.IClickableElement;
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
    IClickableElement elementWithIdAutoDetect();

    @WebElement(value = "//p")
    IClickableElement heisenberg();

    @WebElement(value = "(//form//input)[2]")
    IClickableElement heisenberg2();

    @WebElement(value = "p > tuco_%s")
    IClickableElement randomTuco(String text);

    @WebElement(value = "//div[text() = '%s']|//span[contains(text(), '%s')]")
    IClickableElement elementWithFewPlaceHoldersAndOneParameter(String text);

    @WebElement(value = "//div[text() = '%d']|//span[contains(text(), '%d')]")
    IClickableElement elementWithFewDigitPlaceHoldersAndOneParameter(int number);

    @WebElement(value = "//li/a[contains(text(), '${first_name} ${ last_name }')]")
    IClickableElement elementWithNamedParameter(@Parameter("last_name") String lastName, @Parameter("first_name") String firstName);
}
