package ua.foggger.config;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.foggger.wrapper.IAnnotationProcessor;
import ua.foggger.wrapper.interactor.IElementInteractor;
import ua.foggger.wrapper.interactor.Interactors;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class WrappedElementsSettings {

    private static Logger LOGGER = LoggerFactory.getLogger(WrappedElementsSettings.class);

    private String platform;
    private long interactionTimeout;
    private IElementInteractor defaultElementInteractor;
    private Supplier<WebDriver> driverSupplier;
    private Map<Class, IAnnotationProcessor> annotationProcessorMap;

    //default constructor for hiding access from other modules
    WrappedElementsSettings() {
        annotationProcessorMap = new HashMap<>();
    }

    public void setPlatform(String platform) {
        LOGGER.atTrace().setMessage("Setting platform '{}'").addArgument(platform).log();
        this.platform = platform;
    }

    public String getPlatform() {
        return platform;
    }

    public void setInteractionTimeout(long interactionTimeout) {
        LOGGER.atTrace().setMessage("Setting global element interaction timeout to {} seconds").addArgument(interactionTimeout).log();
        this.interactionTimeout = interactionTimeout;
    }

    public long getInteractionTimeout() {
        return interactionTimeout;
    }

    public Supplier<WebDriver> getDriverSupplier() {
        LOGGER.atTrace().setMessage("Setting web driver supplier function").log();
        return driverSupplier;
    }

    public void setDriverSupplier(Supplier<WebDriver> driverSupplier) {
        this.driverSupplier = driverSupplier;
    }

    public IElementInteractor getDefaultElementInteractor() {
        return defaultElementInteractor;
    }

    public void setDefaultElementInteractor(IElementInteractor defaultElementInteractor) {
        LOGGER.atTrace().setMessage("Setting element interactor {}").addArgument(defaultElementInteractor.name()).log();
        this.defaultElementInteractor = defaultElementInteractor;
    }

    public Map<Class, IAnnotationProcessor> getAnnotationProcessors() {
        return annotationProcessorMap;
    }

    public void addWrapperAnnotationProcessor(Class clazz, IAnnotationProcessor annotationProcessor) {
        LOGGER.atTrace().setMessage("Setting annotation processor for {} as {}").addArgument(clazz).addArgument(annotationProcessor).log();
        annotationProcessorMap.put(clazz, annotationProcessor);
    }

    public Map<String, IElementInteractor> getRegisteredInteractors() {
        return Interactors.getInteractorsMap();
    }
}
