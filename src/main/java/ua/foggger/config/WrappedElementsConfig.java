package ua.foggger.config;

import org.openqa.selenium.WebDriver;
import ua.foggger.config.manager.SettingsManager;
import ua.foggger.elements.detection.IElementInteractor;

import java.util.function.Supplier;

/**
 * Represents WrappedElements configuration for user interface (external)
 */
public class WrappedElementsConfig {

    private SettingsManager settingsManager;

    WrappedElementsConfig(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    /**
     * Sets default driver creation function.
     * For instance () -> new ChromeDriver();
     *
     * @param driverSupplier driver creation function as Supplier<WebDriver>
     * @return this
     */

    public WrappedElementsConfig driverCreator(Supplier<WebDriver> driverSupplier) {
        settingsManager.setDefaultDriverCreator(driverSupplier);
        return this;
    }

    /**
     * Sets default element interaction function.
     * Function defines how to interact with element (wait for visibility, or clickability before clicks or getting attributes).
     *
     * @param elementInteractor element interaction function
     * @return this
     */
    public WrappedElementsConfig defaultElementInteractor(IElementInteractor elementInteractor) {
        settingsManager.setDefaultElementInteractor(elementInteractor);
        return this;
    }
}
