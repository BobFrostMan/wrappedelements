package ua.foggger.driver;

import org.openqa.selenium.WebDriver;

import java.util.function.Supplier;

public class ThreadSafeDriverProvider implements IDriverProvider {

    private static final ThreadLocal<WebDriver> DRIVERS_MAP = new ThreadLocal<>();

    private Supplier<WebDriver> supplier;

    /**
     * Creates web driver (if not created) and returns it's object.
     *
     * @return WebDriver boject
     */
    @Override
    public WebDriver provide() {
        if (DRIVERS_MAP.get() == null) {
            DRIVERS_MAP.set(supplier.get());
        }
        return DRIVERS_MAP.get();
    }

    @Override
    public void setSupplier(Supplier<WebDriver> driverSupplier) {
        supplier = driverSupplier;
    }
}
