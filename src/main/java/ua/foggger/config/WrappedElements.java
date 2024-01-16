package ua.foggger.config;

import org.openqa.selenium.WebDriver;
import ua.foggger.config.manager.DefaultSettingsManagerImpl;
import ua.foggger.config.manager.SettingsManager;
import ua.foggger.config.repo.InMemorySettingsRepository;
import ua.foggger.config.repo.SettingsRepository;
import ua.foggger.driver.DriverProvider;
import ua.foggger.driver.IWebDriverProvider;
import ua.foggger.driver.ThreadSafeWebDriverManager;
import ua.foggger.wrapper.element.IWrappedElement;
import ua.foggger.wrapper.element.clickable.ClickableElement;
import ua.foggger.wrapper.element.clickable.ClickableElementAnnotationProcessor;
import ua.foggger.wrapper.element.interactor.Interactors;
import ua.foggger.wrapper.element.visible.VisibleElement;
import ua.foggger.wrapper.element.visible.VisibleElementAnnotationProcessor;
import ua.foggger.wrapper.page.IPage;
import ua.foggger.wrapper.page.PageInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * General configurations and framework entry point.
 * Before any interactions, config().driverCreator() should be invoked.
 */
public final class WrappedElements {

    static final SettingsManager settingsManager;
    private static final SettingsRepository settingsRepository;
    private static final WrappedElementsConfig wrappedElementsConfig;

    static {
        settingsRepository = new InMemorySettingsRepository();
        settingsManager = new DefaultSettingsManagerImpl(settingsRepository);
        wrappedElementsConfig = new WrappedElementsConfig(settingsManager);
        IWebDriverProvider driverProvider = new ThreadSafeWebDriverManager();
        DriverProvider.setDriverProvider(driverProvider);

        WrappedElementsSettings wrappedElementsSettings = new WrappedElementsSettings();
        wrappedElementsSettings.setElementInteractor(Interactors.getRegisteredInteractor(Interactors.STANDARD));
        wrappedElementsSettings.addWrapperAnnotationProcessor(IWrappedElement.class, new ClickableElementAnnotationProcessor());
        wrappedElementsSettings.addWrapperAnnotationProcessor(ClickableElement.class, new ClickableElementAnnotationProcessor());
        wrappedElementsSettings.addWrapperAnnotationProcessor(VisibleElement.class, new VisibleElementAnnotationProcessor());

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
        if (settingsManager.get().getDriverSupplier() == null) {
            throw new IllegalArgumentException("You need to specify function that creates webdriver using WrappedElements.config().driverCreator() function!");
        }
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz, IPage.class}, new PageInvocationHandler());
    }

    public static WebDriver getDriver() {
        return DriverProvider.get();
    }

}
