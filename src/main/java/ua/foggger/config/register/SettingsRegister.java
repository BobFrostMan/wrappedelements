package ua.foggger.config.register;

import org.openqa.selenium.WebDriver;
import ua.foggger.elements.detection.IElementInteractor;

import java.util.function.Supplier;

/**
 * Implements access to global framework settings object
 */
public interface SettingsRegister {

    /**
     * Returns WrappedElementSettings object, received somehow (from in-memory, remote server).
     *
     * @return WrappedElementSettings object
     */
    WrappedElementsSettings get();

    /**
     * Sets default driver creation function.
     * For instance () -> new ChromeDriver();
     *
     * @param driverSupplier driver creation function as Supplier<WebDriver>
     */
    void driverCreator(Supplier<WebDriver> driverSupplier);

    /**
     * Sets default element interaction function. Function defines how to interact with element (wait for visibility, or clickability before clicks or getting attributes).
     *
     * @param elementInteractor element interaction function
     */
    void defaultElementInteractor(IElementInteractor elementInteractor);

}
