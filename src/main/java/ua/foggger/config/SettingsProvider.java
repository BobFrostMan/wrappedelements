package ua.foggger.config;

/**
 * Provides configurations internally for WrappedElements framework
 */
public interface SettingsProvider {

    /**
     * Returns existing settings object
     *
     * @return settings object
     */
    default WrappedElementsSettings getSettings() {
        return WrappedElements.settingsManager.get();
    }

}
