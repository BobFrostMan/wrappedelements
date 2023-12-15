package ua.foggger.core;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.foggger.page.WrappedPage;

/**
 * Check that element locator properly set for different usecases.
 */
public class LocatorsParsingTest {

    private IPageInterface page;

    @BeforeClass
    public void setUp() {
        page = WrappedPage.create(IPageInterface.class);
    }

    @Test
    public void locatorIsDefinedAsXpath() {
        Assert.assertEquals(page.heisenberg().getLocator(), By.xpath("//p"));
    }

    @Test
    public void locatorIsDefinedAsCSS() {
        Assert.assertEquals(page.randomTuco("salamanka").getLocator(), By.cssSelector("p > tuco_salamanka"));
    }

}
