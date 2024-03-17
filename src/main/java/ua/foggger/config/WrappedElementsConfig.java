package ua.foggger.config;

import org.openqa.selenium.WebDriver;
import ua.foggger.config.manager.SettingsManager;
import ua.foggger.wrapper.IAnnotationProcessor;
import ua.foggger.wrapper.element.WrappedElement;
import ua.foggger.wrapper.interactor.IElementInteractor;

import java.util.function.Supplier;

/**
 * Represents WrappedElements configuration for user interface (external)
 */
public class WrappedElementsConfig {

    private final SettingsManager settingsManager;

    WrappedElementsConfig(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    /**
     * Sets default driver creation function.
     * For instance () -> new ChromeDriver();
     *
     * @param driverSupplier driver creation function as Supplier<WebDriver>
     * @return this
     */

    public WrappedElementsConfig driverCreator(Supplier<WebDriver> driverSupplier) {
        settingsManager.setDefaultDriverCreator(driverSupplier);
        return this;
    }

    /**
     * Sets default element interaction function.
     * Function defines how to interact with element (wait for visibility, or clickability before clicks or getting attributes).
     *
     * @param elementInteractor element interaction function
     * @return this
     */
    public WrappedElementsConfig defaultElementInteractor(IElementInteractor elementInteractor) {
        settingsManager.setDefaultElementInteractor(elementInteractor);
        return this;
    }

    /**
     * Registers custom element annotation processor
     *
     * @param clazz               class that will be handled
     * @param annotationProcessor annotation processor that will be used for specified element clazz
     * @return this
     */
    public WrappedElementsConfig registerAnnotationProcessor(Class<? extends WrappedElement> clazz, IAnnotationProcessor annotationProcessor) {
        settingsManager.addElementDecorator(clazz, annotationProcessor);
        return this;
    }

    /**
     * Sets platform to use. Element annotations will be selected according to specified platform.
     * Example:
     * <pre>{@code
     *      //initialization code:
     *      WrappedElements.config().setPlatform("ios")
     *
     *      //Page file
     *      public interface SomePage extends IPage {
     *          @WebElement("//locator-web")
     *          @AndroidElement("//locator-android")
     *          @IOSElement("//locator-ios")
     *          ClickableElement someElement();
     *      }
     *
     *      //Usage in test code:
     *      someElement().click();
     * }</pre>
     * Such usage will cause webdriver to search for element with locator "//locator-ios", that is very useful for mirror like mobile applications for different platforms.
     *
     * @param platformName platform name as String
     * @return this
     */
    public WrappedElementsConfig setPlatform(String platformName) {
        settingsManager.setPlatform(platformName);
        return this;
    }

    /**
     * Registers custom interactor class to be used in waitUntil values
     *
     * @param interactor custom element interactor to register
     * @return this
     */
    public WrappedElementsConfig registerInteractor(IElementInteractor interactor) {
        settingsManager.registerElementInteractor(interactor);
        return this;
    }
}
