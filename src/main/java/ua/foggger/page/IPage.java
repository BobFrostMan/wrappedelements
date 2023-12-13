package ua.foggger.page;

import org.openqa.selenium.WebDriver;
import ua.foggger.elements.detection.Detections;

public interface IPage {

    String UNTIL_VISIBLE = Detections.UNTIL_VISIBLE;
    String UNTIL_CLICKABLE = Detections.UNTIL_CLICKABLE;
    String VERTICAL_SCROLL_UNTIL_VISIBLE = Detections.VERTICAL_SCROLL_UNTIL_VISIBLE;
    String STANDARD = Detections.STANDARD;

    WebDriver getWebDriver();

}
