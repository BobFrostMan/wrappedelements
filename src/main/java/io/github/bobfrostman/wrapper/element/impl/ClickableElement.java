package io.github.bobfrostman.wrapper.element.impl;

import io.github.bobfrostman.wrapper.element.WrappedElement;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.bobfrostman.wrapper.interactor.IElementInteractor;

import java.util.List;
import java.util.function.Function;

/**
 * WebElement wrapper that provides next additional functionality for webElement:
 */
public class ClickableElement implements WrappedElement {

    private static Logger LOGGER = LoggerFactory.getLogger(ClickableElement.class);

    protected String name;
    protected By locator;
    protected IElementInteractor interactor;
    protected int timeoutInSeconds;
    protected WebElement innerElement;

    protected Function<String, WebElement> detectionFunction;

    public ClickableElement() {
        detectionFunction = (methodName) -> {
            boolean isReady = waitFor(() -> interactor.isReadyForInteraction(methodName, locator, driver()), timeoutInSeconds);
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
        LOGGER.atDebug().setMessage("Click on {}").addArgument(name).log();
        LOGGER.atInfo().setMessage("Click on {}").addArgument(name).log();
        detectElement("click").click();
    }

    @Override
    public void submit() {
        LOGGER.atDebug().setMessage("Perform submit action on {}").addArgument(name).log();
        detectElement("submit").submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        LOGGER.atDebug().setMessage("Sending keys {} to {}").addArgument(keysToSend).addArgument(name).log();
        LOGGER.atInfo().setMessage("Typing text {} to {}").addArgument(keysToSend).addArgument(name).log();
        detectElement("sendKeys").sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        LOGGER.atDebug().setMessage("Perform clear action on {}").addArgument(name).log();
        LOGGER.atInfo().setMessage("Clear the {}").addArgument(name).log();
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
        LOGGER.atDebug().setMessage("Getting text from {}...").addArgument(name).log();
        String text = detectElement("getText").getText();
        LOGGER.atDebug().setMessage("Text received from {} is '{}'").addArgument(name).addArgument(text).log();
        return text;
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
        LOGGER.atDebug().setMessage("Getting css value from {}...").addArgument(name).log();
        String cssValue = detectElement("getCssValue").getCssValue(propertyName);
        LOGGER.atDebug().setMessage("Css {} value received from {} is {}").addArgument(propertyName).addArgument(name).addArgument(cssValue).log();
        return cssValue;
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        LOGGER.atDebug().setMessage("Making screenshot on target {}").addArgument(target).log();
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
