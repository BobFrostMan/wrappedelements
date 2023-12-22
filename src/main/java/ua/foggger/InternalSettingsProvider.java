package ua.foggger;

import ua.foggger.config.register.WrappedElementsSettings;

public final class InternalSettingsProvider {

    private static WrappedElementsSettings settings;

    private InternalSettingsProvider() {
        throw new UnsupportedOperationException("No way! You can not create SettingsProvider!");
    }

    static void setSettings(WrappedElementsSettings s) {
        settings = s;
    }

    public static WrappedElementsSettings getSettings() {
        return settings;
    }


}
