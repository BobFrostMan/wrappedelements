package ua.foggger.elements;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Coordinates;
import ua.foggger.elements.detection.IElementDetection;

import java.util.List;

/**
 * WebElement wrapper that provides next additional functionality for webElement:
 * - Scroll element into view before click;
 * - Checks elements visibility before interaction;
 * - Element visibility criteria can be specified explicitly with setVisibilityCondition(Function<WebDriver, Boolean> visibilityCondition);
 * - Preforms protection from StaleElementReferenceException | NoSuchElementException | ElementClickInterceptedException that occurs before http requests sending to selenium server;
 * - Introduce getLocator() method that returns By object, for some specific interactions.
 */
public class ClickableElement implements IClickableElement {

    private String name;
    private By locator;
    private IElementDetection detection;

    public ClickableElement() {

    }

    public ClickableElement(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public By getLocator() {
        return locator;
    }

    @Override
    public String getDetectionName() {
        return detection.name();
    }

    @Override
    public void click() {

    }

    @Override
    public void submit() {

    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {

    }

    @Override
    public void clear() {

    }

    @Override
    public String getTagName() {
        return null;
    }

    @Override
    public String getAttribute(String name) {
        return null;
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public List<WebElement> findElements(By by) {
        return null;
    }

    @Override
    public WebElement findElement(By by) {
        return null;
    }

    @Override
    public boolean isDisplayed() {
        return false;
    }

    @Override
    public Point getLocation() {
        return null;
    }

    @Override
    public Dimension getSize() {
        return null;
    }

    @Override
    public Rectangle getRect() {
        return null;
    }

    @Override
    public String getCssValue(String propertyName) {
        return null;
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return null;
    }

    @Override
    public WebElement getWrappedElement() {
        return null;
    }

    @Override
    public Coordinates getCoordinates() {
        return null;
    }
}
