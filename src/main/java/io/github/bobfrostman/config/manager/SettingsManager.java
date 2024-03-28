package io.github.bobfrostman.config.manager;

import io.github.bobfrostman.config.WrappedElementsSettings;
import io.github.bobfrostman.wrapper.element.WrappedElement;
import io.github.bobfrostman.wrapper.interactor.IElementInteractor;
import org.openqa.selenium.WebDriver;
import io.github.bobfrostman.wrapper.IAnnotationProcessor;

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
     * @param driverSupplier driver creation function as Supplier of WebDriver
     */
    void setDefaultDriverCreator(Supplier<WebDriver> driverSupplier);

    /**
     * Sets default element interaction function. Function defines how to interact with element (wait for visibility, or clickability before clicks or getting attributes).
     *
     * @param elementInteractor element interaction function
     */
    void setDefaultElementInteractor(IElementInteractor elementInteractor);

    /**
     * Adds clickable element custom decorator
     *
     * @param clazz            class that will be handled
     * @param elementDecorator decorator that will be used for clazz decoration
     */
    void addElementDecorator(Class<? extends WrappedElement> clazz, IAnnotationProcessor elementDecorator);

    /**
     * Sets platform name to use
     *
     * @param platformName platform name as String
     */
    void setPlatform(String platformName);

    /**
     * Registers custom IElementInteractor in WrappedElements
     *
     * @param elementInteractor interactor to be used for as value for waitUntil
     */
    void registerElementInteractor(IElementInteractor elementInteractor);

}
