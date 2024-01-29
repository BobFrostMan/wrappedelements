package ua.foggger.wrapper.block;

import org.openqa.selenium.WebDriver;
import ua.foggger.driver.DriverProvider;
import ua.foggger.wrapper.interactor.KnowsInteractors;

public interface WrappedComponent extends KnowsInteractors {

    default WebDriver driver() {
        return DriverProvider.get();
    }
}
