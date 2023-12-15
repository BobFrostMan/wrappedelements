package ua.foggger.config;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ua.foggger.elements.detection.Detections;
import ua.foggger.elements.detection.IElementDetection;

/**
 * General configurations entry point
 */
public final class WrappedElements {

    private static final WrappedElementsSettings settings = new WrappedElementsSettings();

    static {
        settings.setElementDetection(new IElementDetection() {
            @Override
            public String name() {
                return Detections.STANDARD;
            }

            @Override
            public boolean isReadyForInteraction(By by, WebDriver webDriver) {
                return true;
            }
        });
        settings.setInteractionTimeout(30);
    }

    public static WrappedElementsSettings settings() {
        return settings;
    }

}
