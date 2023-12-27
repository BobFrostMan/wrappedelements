package ua.foggger.core;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.foggger.config.WrappedElements;
import ua.foggger.core.page.LocatorsPage;

/**
 * Check that element locator properly set for different usecases.
 */
public class LocatorsParsingTest extends BaseTest {

    private LocatorsPage page;

    @BeforeClass
    public void setUp() {
        super.setUp();
        page = WrappedElements.initPage(LocatorsPage.class);
    }

    @Test
    public void locatorIsAutoDefinedAsXpath() {
        Assert.assertEquals(getLocator(page.heisenberg()), By.xpath("//p"));
        Assert.assertEquals(getLocator(page.heisenberg2()), By.xpath("(//form//input)[2]"));
    }

    @Test
    public void locatorIsAutoDefinedAsCSS() {
        Assert.assertEquals(getLocator(page.randomTuco("salamanka")), By.cssSelector("p > tuco_salamanka"));
    }

    @Test
    public void locatorIsWithFewPlaceHoldersAndOneParameterXpath() {
        Assert.assertEquals(getLocator(page.elementWithFewPlaceHoldersAndOneParameter("text")), By.xpath("//div[text() = 'text']|//span[contains(text(), 'text')]"));
        Assert.assertEquals(getLocator(page.elementWithFewDigitPlaceHoldersAndOneParameter(5)), By.xpath("//div[text() = '5']|//span[contains(text(), '5')]"));
    }

    @Test
    public void locatorIsWithNamedParameters() {
        Assert.assertEquals(getLocator(page.elementWithNamedParameter("Salamanka", "Tuco")), By.xpath("//li/a[contains(text(), 'Tuco Salamanka')]"));
    }

    @Test
    public void locatorIsAutoDefined() {
        String locator = "id";
        String expectedLocatorValue = String.format("//%s|//*[@id='%s']|//*[@name='%s']|//*[@class='%s'])", locator, locator, locator, locator);
        Assert.assertEquals(getLocator(page.elementWithIdAutoDetect()), By.xpath(expectedLocatorValue));
    }

    @Test
    public void locatorIsDefinedByType() {
        Assert.assertEquals(getLocator(page.elementSpecifiedById()), By.id("someId"));
        Assert.assertEquals(getLocator(page.elementSpecifiedByName()), By.name("someName"));
        Assert.assertEquals(getLocator(page.elementSpecifiedByTagName()), By.tagName("p-table"));
        Assert.assertEquals(getLocator(page.elementSpecifiedByClass()), By.className("someClass"));
        Assert.assertEquals(getLocator(page.elementSpecifiedByCss()), By.cssSelector("p > p"));
        Assert.assertEquals(getLocator(page.elementSpecifiedByLinkText()), By.linkText("someLinkText"));
    }

}
