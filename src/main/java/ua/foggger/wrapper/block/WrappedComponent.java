package ua.foggger.wrapper.block;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ua.foggger.driver.DriverProvider;

import java.util.concurrent.atomic.AtomicReference;

public interface WrappedComponent {

    AtomicReference<By> rootLocator = new AtomicReference<>();

    AtomicReference<String> name = new AtomicReference<>();

    default void setRootElementLocator(By locator) {
        rootLocator.set(locator);
    }

    default void setName(String nameStr) {
        name.set(nameStr);
    }

    default WebDriver driver() {
        return DriverProvider.get();
    }

    default String getName() {
        return name.get();
    }

    default By rootLocator() {
        return rootLocator.get();
    }


}
