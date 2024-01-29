package ua.foggger.wrapper.interactor;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.util.HashMap;
import java.util.Map;

/**
 * Class contains default element interaction behaviors used for @WebElement annotation handling.
 *
 * @see ua.foggger.annotation.WebElement
 */
public final class Interactors {

    public static final String UNTIL_VISIBLE = "untilVisible";
    public static final String UNTIL_CLICKABLE = "untilClickable";
    public static final String VERTICAL_SCROLL_UNTIL_VISIBLE = "verticalScrollUntilVisible";
    public static final String STANDARD = "standard";
    public static final String IMMEDIATELY = "immediately";

    private static final Map<String, IElementInteractor> interactorsMap = new HashMap<>();

    private Interactors() {

    }

    static {
        try {
            registerInteractor(untilVisible());
            registerInteractor(untilClickable());
            registerInteractor(verticalScrollUntilVisible());
            registerInteractor(standard());
            registerInteractor(immediately());
        } catch (Throwable t) {
            throw new RuntimeException(t.getCause());
        }
    }

    /**
     * Register additional behavior
     *
     * @param elementInteractor object that defines element interaction behavior
     */
    public static void registerInteractor(IElementInteractor elementInteractor) {
        interactorsMap.put(elementInteractor.name(), elementInteractor);
    }

    public static IElementInteractor getRegisteredInteractor(String elementInteractorName) {
        return interactorsMap.get(elementInteractorName);
    }

    private static IElementInteractor untilVisible() {
        return new IElementInteractor() {
            @Override
            public String name() {
                return UNTIL_VISIBLE;
            }

            @Override
            public boolean isReadyForInteraction(By by, WebDriver webDriver) {
                return true;
            }
        };
    }

    private static IElementInteractor untilClickable() {
        return new IElementInteractor() {
            @Override
            public String name() {
                return UNTIL_CLICKABLE;
            }

            @Override
            public boolean isReadyForInteraction(By by, WebDriver webDriver) {
                return true;
            }
        };
    }

    private static IElementInteractor verticalScrollUntilVisible() {
        return new IElementInteractor() {
            @Override
            public String name() {
                return VERTICAL_SCROLL_UNTIL_VISIBLE;
            }

            @Override
            public boolean isReadyForInteraction(By by, WebDriver webDriver) {
                try {
                    ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView({block: \"center\", behavior: \"smooth\"})", webDriver.findElement(by));
                    return (Boolean) ((JavascriptExecutor) webDriver).executeScript(
                            "var elem = arguments[0],                 " +
                                    "  box = elem.getBoundingClientRect(),    " +
                                    "  cx = box.left + box.width / 2,         " +
                                    "  cy = box.top + box.height / 2,         " +
                                    "  e = document.elementFromPoint(cx, cy); " +
                                    "for (; e; e = e.parentElement) {         " +
                                    "  if (e === elem)                        " +
                                    "    return true;                         " +
                                    "}                                        " +
                                    "return false;                            "
                            , webDriver.findElement(by));
                } catch (WebDriverException ex) {
                    return false;
                }
            }
        };
    }

    private static IElementInteractor standard() {
        return new IElementInteractor() {
            @Override
            public String name() {
                return Interactors.STANDARD;
            }

            @Override
            public boolean isReadyForInteraction(By by, WebDriver webDriver) {
                return (Boolean) ((JavascriptExecutor) webDriver).executeScript(
                        "var elem = arguments[0],                 " +
                                "  box = elem.getBoundingClientRect(),    " +
                                "  cx = box.left + box.width / 2,         " +
                                "  cy = box.top + box.height / 2,         " +
                                "  e = document.elementFromPoint(cx, cy); " +
                                "for (; e; e = e.parentElement) {         " +
                                "  if (e === elem)                        " +
                                "    return true;                         " +
                                "}                                        " +
                                "return false;                            "
                        , webDriver.findElement(by));
            }

            @Override
            public boolean isReadyForInteraction(String methodName, By by, WebDriver webDriver) {
                switch (methodName) {
                    case "click":
                    case "submit":
                    case "sendKeys":
                    case "clear":
                    case "isSelected":
                    case "isEnabled":
                    case "getText":
                    case "findElements":
                    case "findElement":
                    case "isDisplayed":
                        return isReadyForInteraction(by, webDriver);
                    default:
//                    case "getAttribute":
//                    case "getTagName":
//                    case "getLocation":
//                    case "getSize":
//                    case "getRect":
//                    case "getCssValue":
//                    case "getScreenshotAs":
                        return true;
                }
            }
        };
    }

    private static IElementInteractor immediately() {
        return new IElementInteractor() {
            @Override
            public String name() {
                return IMMEDIATELY;
            }

            @Override
            public boolean isReadyForInteraction(By by, WebDriver webDriver) {
                return true;
            }
        };
    }

}
