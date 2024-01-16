package ua.foggger.wrapper.block;

import org.openqa.selenium.WebDriver;
import ua.foggger.driver.DriverProvider;

public interface IWrappedBlock {

    String getName();

    default WebDriver driver() {
        return DriverProvider.get();
    }
}
