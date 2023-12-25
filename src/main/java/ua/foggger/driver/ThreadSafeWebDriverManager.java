package ua.foggger.driver;

import org.openqa.selenium.WebDriver;

public class ThreadSafeWebDriverManager implements IWebDriverProvider {

    private static final ThreadLocal<WebDriver> DRIVERS_MAP = new ThreadLocal<>();

    /**
     * Creates web driver (if not created) and returns it's object.
     *
     * @return WebDriver object
     */
    @Override
    public WebDriver get() {
        if (DRIVERS_MAP.get() == null) {
            DRIVERS_MAP.set(getSettings().getDriverSupplier().get());
        }
        return DRIVERS_MAP.get();
    }
}
