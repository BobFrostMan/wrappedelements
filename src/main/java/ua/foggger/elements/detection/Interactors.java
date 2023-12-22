package ua.foggger.elements.detection;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

public class Interactors {

    public static final String UNTIL_VISIBLE = "untilVisible";
    public static final String UNTIL_CLICKABLE = "untilClickable";
    public static final String VERTICAL_SCROLL_UNTIL_VISIBLE = "verticalScrollUntilVisible";
    public static final String STANDARD = "standard";
    //TODO: do we need no wait action?
    public static final String NO_WAIT = "noWait";

    private static final Map<String, IElementInteractor> detectionsMap = new HashMap<>();

    static {
        try {
            registerDetection(untilVisible());
            registerDetection(untilClickable());
            registerDetection(verticalScrollUntilVisible());
            registerDetection(standard());
            registerDetection(nothing());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void registerDetection(IElementInteractor elementDetection) {
        detectionsMap.put(elementDetection.name(), elementDetection);
    }

    public static IElementInteractor getRegisteredDetection(String detectionName) {
        return detectionsMap.get(detectionName);
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
                return true;
            }
        };
    }

    private static IElementInteractor nothing() {
        return new IElementInteractor() {
            @Override
            public String name() {
                return NO_WAIT;
            }

            @Override
            public boolean isReadyForInteraction(By by, WebDriver webDriver) {
                return true;
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

}
