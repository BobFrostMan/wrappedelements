package ua.foggger.config.manager;

import org.openqa.selenium.WebDriver;
import ua.foggger.config.WrappedElementsSettings;
import ua.foggger.elements.interactor.IElementInteractor;

import java.util.function.Supplier;


/**
 * Manages global framework settings objects
 */
public interface SettingsManager {

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
    void setDefaultDriverCreator(Supplier<WebDriver> driverSupplier);

    /**
     * Sets default element interaction function. Function defines how to interact with element (wait for visibility, or clickability before clicks or getting attributes).
     *
     * @param elementInteractor element interaction function
     */
    void setDefaultElementInteractor(IElementInteractor elementInteractor);

}
