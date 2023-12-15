package ua.foggger.elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.interactions.Locatable;
import ua.foggger.driver.DriverSupplier;

public interface IClickableElement extends WebElement, Locatable, WrapsElement {

    String getName();

    default WebDriver driver() {
        return DriverSupplier.supply();
    }

}
