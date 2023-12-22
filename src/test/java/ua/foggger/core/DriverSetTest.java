package ua.foggger.core;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ua.foggger.WrappedElements;

public class DriverSetTest extends BaseTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNoDriverSupplier() {
        WrappedElements.config().driverCreator(null);
    }

    @Test
    public void testDriverSupplier() {
        WrappedElements.config().driverCreator(() -> null);
    }

    @AfterClass
    public void cleanUp() {
        WrappedElements.config().driverCreator(() -> null);
    }

}
