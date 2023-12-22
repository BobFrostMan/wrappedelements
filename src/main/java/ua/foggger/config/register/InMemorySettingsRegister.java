package ua.foggger.config.register;

import org.openqa.selenium.WebDriver;
import ua.foggger.elements.detection.IElementInteractor;

import java.util.function.Supplier;

public class InMemorySettingsRegister implements SettingsRegister {

    private WrappedElementsSettings wrappedElementsSettings;

    public InMemorySettingsRegister() {
        wrappedElementsSettings = new WrappedElementsSettings();
    }

    /**
     * Returns WrappedElementSettings object, received somehow (from in-memory, remote server).
     *
     * @return WrappedElementSettings object
     */
    @Override
    public WrappedElementsSettings get() {
        return wrappedElementsSettings;
    }

    /**
     * Sets default driver creation function.
     * For instance () -> new ChromeDriver();
     *
     * @param driverSupplier driver creation function as Supplier<WebDriver>
     */
    @Override
    public void driverCreator(Supplier<WebDriver> driverSupplier) {
        wrappedElementsSettings.setDriverSupplier(driverSupplier);
    }

    /**
     * Sets default element interaction function. Function defines how to interact with element (wait for visibility, or clickability before clicks or getting attributes).
     *
     * @param elementInteractor element interaction function
     */
    @Override
    public void defaultElementInteractor(IElementInteractor elementInteractor) {
        wrappedElementsSettings.setElementDetection(elementInteractor);
    }
}
