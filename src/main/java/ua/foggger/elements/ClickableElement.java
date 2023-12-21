package ua.foggger.elements;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import ua.foggger.driver.DriverStorage;
import ua.foggger.elements.detection.IElementDetection;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

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
        boolean isReady = waitFor(() -> detection.isReadyForInteraction(methodName, locator, DriverStorage.get()), timeoutInSeconds);
        if (isReady) {
            return driver().findElement(locator);
        } else {
            throw new RuntimeException("Element " + this + " not found in DOM!");
        }
    }

    /**
     * Waits for function to return true with timeout.
     * Returns result of last function execution.
     *
     * @param func       - wait function to execute
     * @param timeOutSec - execution timeout (in seconds)
     * @return true if wait was successful
     */
    protected Boolean waitFor(Callable<Boolean> func, int timeOutSec) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + TimeUnit.SECONDS.toMillis(timeOutSec);
        while (System.currentTimeMillis() <= endTime) {
            try {
                Boolean res = func.call();
                if (res) {
                    return res;
                }
            } catch (Throwable e) {
                //do nothing
            }
        }
        return false;
    }
}
