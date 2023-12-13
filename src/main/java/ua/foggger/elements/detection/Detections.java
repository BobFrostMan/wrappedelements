package ua.foggger.elements.detection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ua.foggger.config.WrappedElements;

import java.util.HashMap;
import java.util.Map;

public class Detections {

    public static final String UNTIL_VISIBLE = "untilVisible";
    public static final String UNTIL_CLICKABLE = "untilClickable";
    public static final String VERTICAL_SCROLL_UNTIL_VISIBLE = "verticalScrollUntilVisible";
    public static final String STANDARD = "standard";

    private static final Map<String, IElementDetection> detectionsMap = new HashMap<>();

    static {
        registerDetection(untilVisible());
        registerDetection(untilClickable());
        registerDetection(verticalScrollUntilVisible());
        registerDetection(standard());
    }

    public static void registerDetection(IElementDetection elementDetection) {
        detectionsMap.put(elementDetection.name(), elementDetection);
    }

    public static IElementDetection getRegisteredDetection(String detectionName) {
        return detectionsMap.get(detectionName);
    }

    private static IElementDetection untilVisible() {
        return new IElementDetection() {
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

    private static IElementDetection untilClickable() {
        return new IElementDetection() {
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

    private static IElementDetection verticalScrollUntilVisible() {
        return new IElementDetection() {
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

    private static IElementDetection standard() {
        IElementDetection defaultDetection = WrappedElements.settings().getElementDetection();
        return defaultDetection != null
                ? defaultDetection
                : new IElementDetection() {
            @Override
            public String name() {
                return STANDARD;
            }

            @Override
            public boolean isReadyForInteraction(By by, WebDriver webDriver) {
                return true;
            }
        };
    }

}
