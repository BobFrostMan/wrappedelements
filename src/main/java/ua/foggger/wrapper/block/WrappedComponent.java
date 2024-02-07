package ua.foggger.wrapper.block;

import org.openqa.selenium.WebDriver;
import ua.foggger.driver.DriverProvider;
import ua.foggger.common.IKnowInteractors;

public interface WrappedComponent extends IKnowInteractors {

    default WebDriver driver() {
        return DriverProvider.get();
    }
}
