package ua.foggger.config.provider;

import ua.foggger.InternalSettingsProvider;
import ua.foggger.config.register.WrappedElementsSettings;

/**
 * Provides configurations internally for WrappedElements framework
 */
public interface SettingsProvider {

    default WrappedElementsSettings getSettings() {
        return InternalSettingsProvider.getSettings();
    }

}
