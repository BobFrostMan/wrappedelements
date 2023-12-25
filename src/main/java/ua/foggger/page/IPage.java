package ua.foggger.page;

import ua.foggger.driver.DriverProvider;
import ua.foggger.elements.interactor.Interactors;

public interface IPage {

    String UNTIL_VISIBLE = Interactors.UNTIL_VISIBLE;
    String UNTIL_CLICKABLE = Interactors.UNTIL_CLICKABLE;
    String VERTICAL_SCROLL_UNTIL_VISIBLE = Interactors.VERTICAL_SCROLL_UNTIL_VISIBLE;
    String STANDARD = Interactors.STANDARD;

    default void get(String url) {
        DriverProvider.get().get(url);
    }
}
