package io.github.bobfrostman.wrapper.block;

import io.github.bobfrostman.common.IKnowInteractors;
import io.github.bobfrostman.driver.DriverProvider;
import org.openqa.selenium.WebDriver;

public interface WrappedComponent extends IKnowInteractors {

    default WebDriver driver() {
        return DriverProvider.get();
    }

}
