package io.github.bobfrostman.config.repo;

import io.github.bobfrostman.config.WrappedElementsSettings;

public class InMemorySettingsRepository implements SettingsRepository {

    private static WrappedElementsSettings settings;

    /**
     * Get settings object from repository
     *
     * @return existing settings object from repository
     */
    @Override
    public WrappedElementsSettings get() {
        return settings;
    }

    /**
     * Save settings object to repository
     *
     * @param wrappedElementsSettings object
     */
    @Override
    public void save(WrappedElementsSettings wrappedElementsSettings) {
        settings = wrappedElementsSettings;
    }
}
