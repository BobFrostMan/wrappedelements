package io.github.bobfrostman.config;

import io.github.bobfrostman.common.IKnowInteractors;
import io.github.bobfrostman.wrapper.element.WrappedElement;
import io.github.bobfrostman.wrapper.element.impl.ClickableElement;
import io.github.bobfrostman.wrapper.interactor.*;
import io.github.bobfrostman.wrapper.page.IPage;
import io.github.bobfrostman.wrapper.page.PageInvocationHandler;
import org.openqa.selenium.WebDriver;
import io.github.bobfrostman.common.IKnowPlatforms;
import io.github.bobfrostman.config.manager.DefaultSettingsManagerImpl;
import io.github.bobfrostman.config.manager.SettingsManager;
import io.github.bobfrostman.config.repo.InMemorySettingsRepository;
import io.github.bobfrostman.config.repo.SettingsRepository;
import io.github.bobfrostman.driver.DriverProvider;
import io.github.bobfrostman.driver.IWebDriverProvider;
import io.github.bobfrostman.driver.ThreadSafeWebDriverManager;
import io.github.bobfrostman.wrapper.block.WrappedBlockAnnotationProcessor;
import io.github.bobfrostman.wrapper.block.WrappedComponent;
import io.github.bobfrostman.wrapper.element.impl.ClickableElementAnnotationProcessor;
import io.github.bobfrostman.wrapper.interactor.*;

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
        Interactors.registerInteractor(new NoActionInteractor());
        Interactors.registerInteractor(new UntilVisibleInteractor());
        Interactors.registerInteractor(new UntilClickableInteractor());
        Interactors.registerInteractor(new VerticalScrollUntilVisibleInteractor());

        WrappedElementsSettings wrappedElementsSettings = new WrappedElementsSettings();
        wrappedElementsSettings.setPlatform(IKnowPlatforms.WEB);
        wrappedElementsSettings.setDefaultElementInteractor(Interactors.getRegisteredInteractor(IKnowInteractors.UNTIL_CLICKABLE));
        wrappedElementsSettings.addWrapperAnnotationProcessor(WrappedElement.class, new ClickableElementAnnotationProcessor());
        wrappedElementsSettings.addWrapperAnnotationProcessor(ClickableElement.class, new ClickableElementAnnotationProcessor());
        wrappedElementsSettings.addWrapperAnnotationProcessor(WrappedComponent.class, new WrappedBlockAnnotationProcessor());

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
