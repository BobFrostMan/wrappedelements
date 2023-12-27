package ua.foggger.driver;

import org.openqa.selenium.WebDriver;

/**
 * Provides driver object across framework
 */
public class DriverProvider {

    private static IWebDriverProvider driverProvider;

    /**
     * Returns WebDriver object that is managed by IDriverProvider and created by driver supplier function
     *
     * @return WebDriver object
     */
    public static WebDriver get() {
        return driverProvider.get();
    }

    /**
     * Sets Driver provider (that manages driver existence)
     *
     * @param provider IDriverProvide function
     */
    public static void setDriverProvider(IWebDriverProvider provider) {
        driverProvider = provider;
    }

}
