package ua.foggger.driver;

import org.openqa.selenium.WebDriver;

import java.util.function.Supplier;

public class DriverStorage {

    private static IDriverProvider driverProvider = new ThreadSafeDriverProvider();

    /**
     * Returns WebDriver object that is managed by IDriverProvider and created by driver supplier function
     *
     * @return WebDriver object
     */
    public static WebDriver get() {
        return driverProvider.provide();
    }

    /**
     * Sets Driver provider (that manages driver existence)
     *
     * @param provider IDriverProvide function
     */
    public static void setDriverProvider(IDriverProvider provider) {
        driverProvider = provider;
    }

    /**
     * Sets Driver supplier function (a function that creates WebDriver object).
     *
     * @param supplier Supplier<WebDriver> object
     */
    public static void setDriverSupplier(Supplier<WebDriver> supplier) {
        driverProvider.setSupplier(supplier);
    }

}
