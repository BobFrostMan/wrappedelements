package ua.foggger.wrapper.page;

import org.openqa.selenium.WebDriver;
import ua.foggger.common.IKnowInteractors;
import ua.foggger.driver.DriverProvider;

public interface IPage extends IKnowInteractors {

    default WebDriver driver() {
        return DriverProvider.get();
    }

}
