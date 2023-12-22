package ua.foggger.config;

import org.openqa.selenium.WebDriver;
import ua.foggger.config.register.SettingsRegister;
import ua.foggger.elements.detection.IElementInteractor;

import java.util.function.Supplier;

/**
 * Represents WrappedElements configuration for user interface (external)
 */
public class WrappedElementsConfig {

    private SettingsRegister settingsRegister;

    public WrappedElementsConfig(SettingsRegister settingsRegister) {
        this.settingsRegister = settingsRegister;
    }

    /**
     * Sets default driver creation function.
     * For instance () -> new ChromeDriver();
     *
     * @param driverSupplier driver creation function as Supplier<WebDriver>
     * @return this
     */

    public WrappedElementsConfig driverCreator(Supplier<WebDriver> driverSupplier) {
        settingsRegister.driverCreator(driverSupplier);
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
        settingsRegister.defaultElementInteractor(elementInteractor);
        return this;
    }
}
