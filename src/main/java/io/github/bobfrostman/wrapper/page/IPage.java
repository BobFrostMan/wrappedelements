package io.github.bobfrostman.wrapper.page;

import io.github.bobfrostman.common.IKnowInteractors;
import org.openqa.selenium.WebDriver;
import io.github.bobfrostman.driver.DriverProvider;

public interface IPage extends IKnowInteractors {

    default WebDriver driver() {
        return DriverProvider.get();
    }

}
