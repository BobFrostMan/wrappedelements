package ua.foggger.config.repo;

import ua.foggger.config.WrappedElementsSettings;

/**
 * Provides WrappedElementsSettings interaction
 */
public interface SettingsRepository {

    /**
     * Get settings object from repository
     *
     * @return existing settings object from repository
     */
    WrappedElementsSettings get();

    /**
     * Save settings object to repository
     *
     * @param wrappedElementsSettings object
     */
    void save(WrappedElementsSettings wrappedElementsSettings);

}
