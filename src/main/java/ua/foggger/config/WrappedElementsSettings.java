package ua.foggger.config;

import org.openqa.selenium.WebDriver;
import ua.foggger.elements.decorator.IElementDecorator;
import ua.foggger.elements.interactor.IElementInteractor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class WrappedElementsSettings {

    private long interactionTimeout;
    //TODO: rename element interaction
    private IElementInteractor elementDetection;
    private Supplier<WebDriver> driverSupplier;
    private Map<Class, IElementDecorator> decoratorMap;

    //default constructor for hiding access from other modules
    WrappedElementsSettings() {
        decoratorMap = new HashMap<>();
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

    public Map<Class, IElementDecorator> getDecorators() {
        return decoratorMap;
    }

    public void addDecorator(Class clazz, IElementDecorator decorator) {
        decoratorMap.put(clazz, decorator);
    }
}
