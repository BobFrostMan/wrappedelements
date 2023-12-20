package ua.foggger.config;

import ua.foggger.driver.DriverStorage;

public class SettingsProvider {

    private static WrappedElementsSettings wrappedElementsSettings;

    public static WrappedElementsSettings provide() {
        return wrappedElementsSettings;
    }

    public static void registerSettings(WrappedElementsSettings settings) {
        wrappedElementsSettings = settings;
        DriverStorage.setDriverSupplier(settings.getDriverSupplier());
    }
}
