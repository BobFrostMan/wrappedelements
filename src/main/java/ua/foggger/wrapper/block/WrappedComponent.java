package ua.foggger.wrapper.block;

import org.openqa.selenium.WebDriver;
import ua.foggger.driver.DriverProvider;

public interface WrappedComponent {

    default WebDriver driver() {
        return DriverProvider.get();
    }
}
