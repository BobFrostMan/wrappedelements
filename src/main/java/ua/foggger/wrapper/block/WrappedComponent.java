package ua.foggger.wrapper.block;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ua.foggger.common.IKnowInteractors;
import ua.foggger.driver.DriverProvider;

import java.util.concurrent.atomic.AtomicReference;

public interface WrappedComponent extends IKnowInteractors {

    default WebDriver driver() {
        return DriverProvider.get();
    }

}
