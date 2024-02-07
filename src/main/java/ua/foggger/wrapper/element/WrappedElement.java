package ua.foggger.wrapper.element;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.interactions.Locatable;
import ua.foggger.common.ICanWait;
import ua.foggger.driver.DriverProvider;

import java.util.concurrent.TimeUnit;

public interface WrappedElement extends WebElement, Locatable, WrapsElement, ICanWait {

    String getName();

    default WebDriver driver() {
        return DriverProvider.get();
    }

    /**
     * Sends keys one by one with delay specified in millis
     *
     * @param string      string to send
     * @param delayMillis delay in millis before each key send
     */
    default void sendKeys(String string, long delayMillis) {
        CharSequence charSequence = string.subSequence(0, string.length() + 1);
        for (int i = 0; i <= string.length(); i++) {
            sendKeys(charSequence.subSequence(i, i + 1));
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException ignored) {
                //do nothing
            }
        }
    }

}
