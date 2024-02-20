package ua.foggger.config.manager;

import org.openqa.selenium.WebDriver;
import ua.foggger.config.WrappedElementsSettings;
import ua.foggger.wrapper.IAnnotationProcessor;
import ua.foggger.wrapper.element.WrappedElement;
import ua.foggger.wrapper.interactor.IElementInteractor;

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

}
