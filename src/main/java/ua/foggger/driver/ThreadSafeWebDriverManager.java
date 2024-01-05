package ua.foggger.driver;

import org.openqa.selenium.NoSuchSessionException;
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
        if (!isAlive(DRIVERS_MAP.get())) {
            DRIVERS_MAP.set(getSettings().getDriverSupplier().get());
        }
        return DRIVERS_MAP.get();
    }

    private boolean isAlive(WebDriver driver) {
        if (driver == null) {
            return false;
        }
        try {
            driver.getTitle();
            return true;
        } catch (NoSuchSessionException e) {
            return false;
        }
    }
}
