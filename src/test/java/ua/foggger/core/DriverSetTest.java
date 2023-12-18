package ua.foggger.core;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ua.foggger.config.WrappedElements;

public class DriverSetTest extends BaseTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNoDriverSupplier() {
        WrappedElements.driverCreateFunction(null);
        WrappedElements.init();
    }

    @Test
    public void testDriverSupplier() {
        WrappedElements.driverCreateFunction(() -> null).init();
    }

    @AfterClass
    public void cleanUp() {
        WrappedElements.driverCreateFunction(() -> null).init();
    }

}
