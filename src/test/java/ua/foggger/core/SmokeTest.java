package ua.foggger.core;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.foggger.page.WrappedPage;

public class SmokeTest {

    private IPageInterface page;

    @BeforeClass
    public void setUp() {
        page = WrappedPage.create(IPageInterface.class);
    }

    @Test
    public void locatorIsSet() {
        Assert.assertNotNull(page.heisenberg().getLocator());
        Assert.assertNotNull(page.jessy().getLocator());
        Assert.assertNotNull(page.randomPerson("rand").getLocator());
    }

    @Test
    public void nameIsSet() {
        Assert.assertNotNull(page.heisenberg().getName());
        Assert.assertNotNull(page.randomPerson("rand").getName());
        Assert.assertNotNull(page.jessy().getName());
    }

}
