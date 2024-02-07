package ua.foggger.wrapper.block;

import org.openqa.selenium.WebDriver;
import ua.foggger.common.IKnowInteractors;
import ua.foggger.driver.DriverProvider;

public interface WrappedComponent extends IKnowInteractors {

    default WebDriver driver() {
        return DriverProvider.get();
    }
}
