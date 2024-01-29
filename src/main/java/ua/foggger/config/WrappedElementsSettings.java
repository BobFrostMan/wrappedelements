package ua.foggger.config;

import org.openqa.selenium.WebDriver;
import ua.foggger.wrapper.element.IElementAnnotationProcessor;
import ua.foggger.wrapper.interactor.IElementInteractor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class WrappedElementsSettings {

    private long interactionTimeout;
    private IElementInteractor elementInteractor;
    private Supplier<WebDriver> driverSupplier;
    private Map<Class, IElementAnnotationProcessor> annotationProcessorMap;

    //default constructor for hiding access from other modules
    WrappedElementsSettings() {
        annotationProcessorMap = new HashMap<>();
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

    public IElementInteractor getElementInteractor() {
        return elementInteractor;
    }

    public void setElementInteractor(IElementInteractor elementInteractor) {
        this.elementInteractor = elementInteractor;
    }

    public Map<Class, IElementAnnotationProcessor> getAnnotationProcessors() {
        return annotationProcessorMap;
    }

    public void addWrapperAnnotationProcessor(Class clazz, IElementAnnotationProcessor decorator) {
        annotationProcessorMap.put(clazz, decorator);
    }
}
