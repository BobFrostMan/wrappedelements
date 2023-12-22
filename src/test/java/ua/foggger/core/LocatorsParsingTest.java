package ua.foggger.core;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.foggger.WrappedElements;

/**
 * Check that element locator properly set for different usecases.
 */
public class LocatorsParsingTest extends BaseTest {

    private LocatorPageInterface page;

    @BeforeClass
    public void setUp() {
        page = WrappedElements.initPage(LocatorPageInterface.class);
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
