package ua.foggger.driver;

import org.openqa.selenium.WebDriver;

public class DriverSupplier {

    private static IDriverSupplier supplier = new DefaultDriverSupplier();

    public static void initSupplier(IDriverSupplier driverSupplier) {
        supplier = driverSupplier;
    }

    public static WebDriver supply() {
        return supplier.get();
    }

}
