package ua.foggger.wrapper.interactor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Any active interactions will be performed after wait for element to become clickable.
 */
public class UntilClickableInteractor implements IElementInteractor {
    /**
     * Returns name of this element detection implementation
     *
     * @return name as String
     */
    @Override
    public String name() {
        return "until_clickable";
    }

    /**
     * Returns true if element is ready for interaction.
     *
     * @param by        element By locator
     * @param webDriver web driver object
     * @return true if element is ready for interaction
     */
    @Override
    public boolean isReadyForInteraction(By by, WebDriver webDriver) {
        WebElement el = webDriver.findElement(by);
        return el.isDisplayed() && el.isEnabled();
    }

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
    @Override
    public boolean isReadyForInteraction(String methodName, By by, WebDriver webDriver) {
        switch (methodName) {
            case "click":
            case "submit":
            case "sendKeys":
            case "clear":
            case "isSelected":
            case "findElements":
            case "findElement":
                return isReadyForInteraction(by, webDriver);
            default:
                //case "isEnabled":
                //case "getText":
                //case "isDisplayed":
                //case "getAttribute":
                //case "getTagName":
                //case "getLocation":
                //case "getSize":
                //case "getRect":
                //case "getCssValue":
                //case "getScreenshotAs":
                return true;
        }
    }
}
