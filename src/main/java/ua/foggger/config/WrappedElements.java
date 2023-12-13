package ua.foggger.config;

/**
 * General configurations entrypoint
 */
public class WrappedElements {

    private static WrappedElementsSettings settings = new WrappedElementsSettings();

    public static WrappedElementsSettings settings() {
        return settings;
    }

}
