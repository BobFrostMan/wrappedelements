package ua.foggger.page;

import org.openqa.selenium.WebDriver;
import ua.foggger.config.SettingsProvider;
import ua.foggger.driver.DriverStorage;
import ua.foggger.elements.detection.Detections;

public interface IPage {

    String UNTIL_VISIBLE = Detections.UNTIL_VISIBLE;
    String UNTIL_CLICKABLE = Detections.UNTIL_CLICKABLE;
    String VERTICAL_SCROLL_UNTIL_VISIBLE = Detections.VERTICAL_SCROLL_UNTIL_VISIBLE;
    String STANDARD = Detections.STANDARD;

    WebDriver getWebDriver();

    default void get(String url) {
        DriverStorage.setDriverSupplier(SettingsProvider.provide().getDriverSupplier());
        DriverStorage.get().get(url);
    }
}
