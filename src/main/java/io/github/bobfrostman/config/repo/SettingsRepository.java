package io.github.bobfrostman.config.repo;

import io.github.bobfrostman.config.WrappedElementsSettings;

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
