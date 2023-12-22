package ua.foggger;

import ua.foggger.config.WrappedElementsConfig;
import ua.foggger.config.register.InMemorySettingsRegister;
import ua.foggger.config.register.SettingsRegister;
import ua.foggger.page.IPage;
import ua.foggger.page.PageInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * General configurations entry point
 */
public final class WrappedElements {

    private static final SettingsRegister settingsRegister = new InMemorySettingsRegister();
    private static final WrappedElementsConfig wrappedElementsConfig = new WrappedElementsConfig(settingsRegister);

    static {
        InternalSettingsProvider.setSettings(settingsRegister.get());
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
