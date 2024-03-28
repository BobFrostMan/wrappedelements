package io.github.bobfrostman.tests.core;

import io.github.bobfrostman.BaseTest;
import io.github.bobfrostman.config.WrappedElements;
import io.github.bobfrostman.driver.DriverProvider;
import io.github.bobfrostman.ui.driver.DummyWebDriver;
import io.github.bobfrostman.wrapper.page.IPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

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
