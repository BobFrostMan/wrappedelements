package ua.foggger.core;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.foggger.page.WrappedPage;

/**
 * Check that element locator properly set for different usecases.
 */
public class LocatorsParsingTest extends BaseTest {

    private IPageInterface page;

    @BeforeClass
    public void setUp() {
        page = WrappedPage.create(IPageInterface.class);
    }

    @Test
    public void locatorIsDefinedAsXpath() {
        Assert.assertEquals(getLocator(page.heisenberg()), By.xpath("//p"));
    }

    @Test
    public void locatorIsDefinedAsCSS() {
        Assert.assertEquals(getLocator(page.randomTuco("salamanka")), By.cssSelector("p > tuco_salamanka"));
    }

}
