package ua.foggger.wrapper.element.visible;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import ua.foggger.driver.DriverProvider;
import ua.foggger.wrapper.element.IElementInteractor;
import ua.foggger.wrapper.element.WrappedElement;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class VisibleElement implements WrappedElement {

    protected String name;
    protected By locator;
    protected IElementInteractor interactor;
    protected int timeoutInSeconds;
    private WebElement innerElement;

    public VisibleElement() {

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
        boolean isReady = waitFor(() -> interactor.isReadyForInteraction(methodName, locator, DriverProvider.get()), timeoutInSeconds);
        if (isReady) {
            innerElement = driver().findElement(locator);
            return innerElement;
        } else {
            throw new RuntimeException("Element '" + name + "' not found in DOM by locator: '" + locator + "'!");
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
