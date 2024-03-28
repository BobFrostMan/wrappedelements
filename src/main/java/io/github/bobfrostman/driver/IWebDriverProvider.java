package io.github.bobfrostman.driver;

import org.openqa.selenium.WebDriver;
import io.github.bobfrostman.config.SettingsProvider;

/**
 * Implements functionality for managing webdriver providence
 */
public interface IWebDriverProvider extends SettingsProvider {

    /**
     * Creates web driver (if not created) and returns it.
     *
     * @return WebDriver object
     */
    WebDriver get();

}
