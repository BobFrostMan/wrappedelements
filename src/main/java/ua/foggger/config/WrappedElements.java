package ua.foggger.config;

import ua.foggger.config.manager.DefaultSettingsManagerImpl;
import ua.foggger.config.manager.SettingsManager;
import ua.foggger.config.repo.InMemorySettingsRepository;
import ua.foggger.config.repo.SettingsRepository;
import ua.foggger.page.IPage;
import ua.foggger.page.PageInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * General configurations and framework entry point
 */
public final class WrappedElements {

    static final SettingsManager settingsManager;
    private static final SettingsRepository settingsRepository;
    private static final WrappedElementsConfig wrappedElementsConfig;

    static {
        settingsRepository = new InMemorySettingsRepository();
        settingsManager = new DefaultSettingsManagerImpl(settingsRepository);
        wrappedElementsConfig = new WrappedElementsConfig(settingsManager);

        WrappedElementsSettings wrappedElementsSettings = new WrappedElementsSettings();
        settingsRepository.save(wrappedElementsSettings);
    }

    private WrappedElements() {
        throw new UnsupportedOperationException("No way you can create WrappedElements");
    }

    public static WrappedElementsConfig config() {
        return wrappedElementsConfig;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T initPage(Class clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz, IPage.class}, new PageInvocationHandler());
    }

}
