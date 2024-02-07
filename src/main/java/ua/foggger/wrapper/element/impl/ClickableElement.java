package ua.foggger.wrapper.element.impl;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import ua.foggger.driver.DriverProvider;
import ua.foggger.wrapper.element.WrappedElement;
import ua.foggger.wrapper.interactor.IElementInteractor;

import java.util.List;
import java.util.function.Function;

/**
 * WebElement wrapper that provides next additional functionality for webElement:
 * - Scroll element into view before click;
 * - Checks elements visibility before interaction;
 * - Element visibility criteria can be specified explicitly with setVisibilityCondition(Function<WebDriver, Boolean> visibilityCondition);
 * - Preforms protection from StaleElementReferenceException | NoSuchElementException | ElementClickInterceptedException that occurs before http requests sending to selenium server;
 * - Introduce getLocator() method that returns By object, for some specific interactions.
 */
public class ClickableElement implements WrappedElement {

    protected String name;
    protected By locator;
    protected IElementInteractor interactor;
    protected int timeoutInSeconds;
    private WebElement innerElement;

    private Function<String, WebElement> detectionFunction;

    public ClickableElement() {
        detectionFunction = (methodName) -> {
            boolean isReady = waitFor(() -> interactor.isReadyForInteraction(methodName, locator, DriverProvider.get()), timeoutInSeconds);
            if (isReady) {
                innerElement = driver().findElement(locator);
                return innerElement;
            } else {
                throw new RuntimeException("Element '" + name + "' not found in DOM by locator: '" + locator + "'!");
            }
        };
    }

    void setDetectionFunction(Function<String, WebElement> detectionFunction) {
        this.detectionFunction = detectionFunction;
    }

    void setName(String name) {
        this.name = name;
    }

    void setLocator(By locator) {
        this.locator = locator;
    }

    void setInteractor(IElementInteractor interactor) {
        this.interactor = interactor;
    }

    void setTimeoutInSeconds(int timeoutInSeconds) {
        this.timeoutInSeconds = timeoutInSeconds;
    }

    public ClickableElement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void click() {
        detectElement("click").click();
    }

    @Override
    public void submit() {
        detectElement("submit").submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        detectElement("sendKeys").sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        detectElement("clear").clear();
    }

    @Override
    public String getTagName() {
        return detectElement("getTagName").getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return detectElement("getAttribute").getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return detectElement("isSelected").isSelected();
    }

    @Override
    public boolean isEnabled() {
        return detectElement("isEnabled").isEnabled();
    }

    @Override
    public String getText() {
        return detectElement("getText").getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return detectElement("findElements").findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return detectElement("findElement").findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        return detectElement("isDisplayed").isDisplayed();
    }

    @Override
    public Point getLocation() {
        return detectElement("getLocation").getLocation();
    }

    @Override
    public Dimension getSize() {
        return detectElement("getSize").getSize();
    }

    @Override
    public Rectangle getRect() {
        return detectElement("getRect").getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        return detectElement("getCssValue").getCssValue(propertyName);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return detectElement("getScreenshotAs").getScreenshotAs(target);
    }

    @Override
    public WebElement getWrappedElement() {
        return innerElement;
    }

    @Override
    public Coordinates getCoordinates() {
        return ((Locatable) getWrappedElement()).getCoordinates();
    }

    protected WebElement detectElement(String methodName) {
        return detectionFunction.apply(methodName);
    }
}
