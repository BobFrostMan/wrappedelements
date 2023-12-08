package ua.foggger.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.interactions.Locatable;

public interface IClickableElement extends WebElement, Locatable, WrapsElement {

    String getName();

    By getLocator();

}
