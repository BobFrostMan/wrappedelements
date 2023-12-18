package ua.foggger.config;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ua.foggger.elements.detection.Detections;
import ua.foggger.elements.detection.IElementDetection;

import java.util.function.Supplier;

/**
 * General configurations entry point
 */
public final class WrappedElements {

    private static final WrappedElements wrappedElements;
    private static final WrappedElementsSettings settings;

    static {
        wrappedElements = new WrappedElements();
        settings = new WrappedElementsSettings();
        defaultElementsDetection(new IElementDetection() {
            @Override
            public String name() {
                return Detections.STANDARD;
            }

            @Override
            public boolean isReadyForInteraction(By by, WebDriver webDriver) {
                return true;
            }
        });
        withInteractionTimeout(30);
    }

    public static WrappedElements withInteractionTimeout(long interactionTimeout) {
        settings.setInteractionTimeout(interactionTimeout);
        return wrappedElements;
    }

    public static WrappedElements driverCreateFunction(Supplier<WebDriver> driverSupplier) {
        settings.setDriverSupplier(driverSupplier);
        return wrappedElements;
    }

    public static WrappedElements defaultElementsDetection(IElementDetection elementDetection) {
        settings.setElementDetection(elementDetection);
        return wrappedElements;
    }

    public static void init() {
        if (settings.getDriverSupplier() == null) {
            throw new IllegalArgumentException("WrappedElements framework need to know how to create webdriver. Please provide Supply<WebDriver> function using WrappedElements.driverCreateFunction(Supplier<WebDriver> driverSupplier)!");
        }
        SettingsProvider.registerSettings(settings);
    }

}
