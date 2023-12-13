package ua.foggger.config;

import org.openqa.selenium.WebDriver;
import ua.foggger.elements.detection.IElementDetection;

import java.util.function.Supplier;

public class WrappedElementsSettings {

    private long interactionTimeout;
    private IElementDetection elementDetection;
    private Supplier<WebDriver> driverSupplier;

    void setInteractionTimeout(long interactionTimeout) {
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

    public IElementDetection getElementDetection() {
        return elementDetection;
    }

    public void setElementDetection(IElementDetection elementDetection) {
        this.elementDetection = elementDetection;
    }
}
