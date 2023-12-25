package ua.foggger.config;

import org.openqa.selenium.WebDriver;
import ua.foggger.elements.interactor.IElementInteractor;

import java.util.function.Supplier;

public class WrappedElementsSettings {

    private long interactionTimeout;
    private IElementInteractor elementDetection;
    private Supplier<WebDriver> driverSupplier;

    //default constructor for hiding access from other modules
    WrappedElementsSettings() {

    }

    public void setInteractionTimeout(long interactionTimeout) {
        this.interactionTimeout = interactionTimeout;
    }

    public long getInteractionTimeout() {
        return interactionTimeout;
    }

    public Supplier<WebDriver> getDriverSupplier() {
        return driverSupplier;
    }

    public void setDriverSupplier(Supplier<WebDriver> driverSupplier) {
        this.driverSupplier = driverSupplier;
    }

    public IElementInteractor getElementDetection() {
        return elementDetection;
    }

    public void setElementDetection(IElementInteractor elementDetection) {
        this.elementDetection = elementDetection;
    }
}
