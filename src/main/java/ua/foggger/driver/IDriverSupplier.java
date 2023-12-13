package ua.foggger.driver;

import org.openqa.selenium.WebDriver;

public interface IDriverSupplier {

    WebDriver get();

    void init();

}
