package ua.foggger.tests.core;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ua.foggger.BaseTest;
import ua.foggger.config.WrappedElements;
import ua.foggger.driver.DriverProvider;
import ua.foggger.ui.driver.DummyWebDriver;
import ua.foggger.wrapper.page.IPage;

public class DriverSetTest extends BaseTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNoDriverSupplier() {
        WrappedElements.config().driverCreator(null);
        WrappedElements.initPage(IPage.class);
    }

    @Test
    public void testDriverSupplier() {
        WrappedElements.config().driverCreator(DummyWebDriver::new);
        Assert.assertNotNull(DriverProvider.get());
    }

    @AfterClass
    public void cleanUp() {
        WrappedElements.config().driverCreator(DummyWebDriver::new);
    }

}
