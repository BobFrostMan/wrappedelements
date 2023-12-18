package ua.foggger.driver;

import org.openqa.selenium.WebDriver;

import java.util.function.Supplier;

/**
 * Implements functionality for managing webdriver providence
 */
public interface IDriverProvider {

    /**
     * Creates web driver (if not created) and returns it.
     *
     * @return WebDriver object
     */
    WebDriver provide();

    /**
     * Set Supplier<WebDriver> function that defines WebDriver object creation
     *
     * @param driverSupplier Supplier<WebDriver>
     */
    void setSupplier(Supplier<WebDriver> driverSupplier);

}
