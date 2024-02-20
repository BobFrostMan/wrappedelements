package ua.foggger.wrapper.block;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ua.foggger.common.IKnowInteractors;
import ua.foggger.driver.DriverProvider;

import java.util.concurrent.atomic.AtomicReference;

public interface WrappedComponent extends IKnowInteractors {

    AtomicReference<WebElement> rootWebElement = new AtomicReference<>();

    default WebDriver driver() {
        return DriverProvider.get();
    }

    default WebElement rootElement() {
        return rootWebElement.get();
    }

    default void setRootElement(WebElement webElement) {
        rootWebElement.set(webElement);
    }

}
