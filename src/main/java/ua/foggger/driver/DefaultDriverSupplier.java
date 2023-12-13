package ua.foggger.driver;

import org.openqa.selenium.WebDriver;

public class DefaultDriverSupplier implements IDriverSupplier {

    private static final ThreadLocal<WebDriver> DRIVERS_MAP = new ThreadLocal<>();

    @Override
    public WebDriver get() {
        return DRIVERS_MAP.get();
    }

    @Override
    public void init() {
        DRIVERS_MAP.set(null);
    }
}
