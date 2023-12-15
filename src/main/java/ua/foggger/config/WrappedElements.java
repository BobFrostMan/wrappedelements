package ua.foggger.config;

/**
 * General configurations entry point
 */
public final class WrappedElements {

    private static final WrappedElementsSettings settings = new WrappedElementsSettings();

    public static WrappedElementsSettings settings() {
        return settings;
    }

}
