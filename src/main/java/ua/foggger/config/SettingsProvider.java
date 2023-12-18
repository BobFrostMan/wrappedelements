package ua.foggger.config;

public class SettingsProvider {

    private static WrappedElementsSettings wrappedElementsSettings;

    public static WrappedElementsSettings provide() {
        return wrappedElementsSettings;
    }

    public static void registerSettings(WrappedElementsSettings settings) {
        wrappedElementsSettings = settings;
    }
}
