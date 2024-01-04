package ua.foggger.config;

import org.openqa.selenium.WebDriver;
import ua.foggger.config.manager.SettingsManager;
import ua.foggger.element.IWrappedElement;
import ua.foggger.element.decorator.IElementDecorator;
import ua.foggger.element.interactor.IElementInteractor;

import java.util.function.Supplier;

/**
 * Represents WrappedElements configuration for user interface (external)
 */
public class WrappedElementsConfig {

    private final SettingsManager settingsManager;

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

    /**
     * Registers clickable element custom decorator
     *
     * @param clazz     class that will be handled
     * @param decorator decorator that will be used for clazz decoration
     */
    public WrappedElementsConfig registerDecorator(Class<? extends IWrappedElement> clazz, IElementDecorator decorator) {
        settingsManager.addElementDecorator(clazz, decorator);
        return this;
    }
}
