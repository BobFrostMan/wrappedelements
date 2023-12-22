package ua.foggger.elements.detection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Provides functionality to set condition for element to be ready for interactions
 */
public interface IElementInteractor {

    /**
     * Returns name of this element detection implementation
     *
     * @return name as String
     */
    String name();

    /**
     * Returns true if element is ready for interaction.
     *
     * @param by        element By locator
     * @param webDriver web driver object
     * @return true if element is ready for interaction
     */
    boolean isReadyForInteraction(By by, WebDriver webDriver);

    /**
     * Returns true if element is ready for interaction before specific method invocation.
     * There are some cases when you need to specify specific detection condition before specific element interaction.
     * For instance: you need to wait for element to be clickable before click on it, but not need to wait for element
     * to be clickable to get it's css attribute or class.
     *
     * @param methodName methodName of interaction
     * @param by         element By locator
     * @param webDriver  web driver object
     * @return true if element is ready for interaction
     */
    default boolean isReadyForInteraction(String methodName, By by, WebDriver webDriver) {
        return isReadyForInteraction(by, webDriver);
    }
}
