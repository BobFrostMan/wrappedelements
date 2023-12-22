package ua.foggger.page;

import ua.foggger.driver.DriverStorage;
import ua.foggger.elements.detection.Interactors;

public interface IPage {

    String UNTIL_VISIBLE = Interactors.UNTIL_VISIBLE;
    String UNTIL_CLICKABLE = Interactors.UNTIL_CLICKABLE;
    String VERTICAL_SCROLL_UNTIL_VISIBLE = Interactors.VERTICAL_SCROLL_UNTIL_VISIBLE;
    String STANDARD = Interactors.STANDARD;
    String NO_WAIT = Interactors.NO_WAIT;

    default void get(String url) {
        DriverStorage.get().get(url);
    }
}
