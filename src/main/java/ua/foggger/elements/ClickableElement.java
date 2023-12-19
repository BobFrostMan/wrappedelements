package ua.foggger.elements;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
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
    private int timeoutInSeconds;
    private WebElement innerElement;

    public ClickableElement() {

    }

    public ClickableElement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void click() {
        getWrappedElement().click();
    }

    @Override
    public void submit() {
        getWrappedElement().submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        getWrappedElement().sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        getWrappedElement().clear();
    }

    @Override
    public String getTagName() {
        return getWrappedElement().getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return getWrappedElement().getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return getWrappedElement().isSelected();
    }

    @Override
    public boolean isEnabled() {
        return getWrappedElement().isEnabled();
    }

    @Override
    public String getText() {
        return getWrappedElement().getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return getWrappedElement().findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return getWrappedElement().findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        return getWrappedElement().isDisplayed();
    }

    @Override
    public Point getLocation() {
        return getWrappedElement().getLocation();
    }

    @Override
    public Dimension getSize() {
        return getWrappedElement().getSize();
    }

    @Override
    public Rectangle getRect() {
        return getWrappedElement().getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        return getWrappedElement().getCssValue(propertyName);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return getWrappedElement().getScreenshotAs(target);
    }

    @Override
    public WebElement getWrappedElement() {
        return innerElement;
    }

    @Override
    public Coordinates getCoordinates() {
        return ((Locatable)getWrappedElement()).getCoordinates();
    }
}
