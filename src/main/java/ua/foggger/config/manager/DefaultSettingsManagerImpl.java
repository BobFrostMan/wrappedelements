package ua.foggger.config.manager;

import org.openqa.selenium.WebDriver;
import ua.foggger.config.WrappedElementsSettings;
import ua.foggger.config.repo.SettingsRepository;
import ua.foggger.wrapper.IAnnotationProcessor;
import ua.foggger.wrapper.element.WrappedElement;
import ua.foggger.wrapper.interactor.IElementInteractor;
import ua.foggger.wrapper.interactor.Interactors;

import java.util.function.Supplier;

public class DefaultSettingsManagerImpl implements SettingsManager {

    private SettingsRepository settingsRepository;

    public DefaultSettingsManagerImpl(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    /**
     * Returns WrappedElementSettings object, received somehow (from in-memory, remote server).
     *
     * @return WrappedElementSettings object
     */
    @Override
    public WrappedElementsSettings get() {
        return settingsRepository.get();
    }

    /**
     * Sets default driver creation function.
     * For instance () -> new ChromeDriver();
     *
     * @param driverSupplier driver creation function as Supplier<WebDriver>
     */
    @Override
    public void setDefaultDriverCreator(Supplier<WebDriver> driverSupplier) {
        WrappedElementsSettings settings = settingsRepository.get();
        settings.setDriverSupplier(driverSupplier);
        settingsRepository.save(settings);
    }

    /**
     * Sets default element interaction function. Function defines how to interact with element (wait for visibility, or clickability before clicks or getting attributes).
     *
     * @param elementInteractor element interaction function
     */
    @Override
    public void setDefaultElementInteractor(IElementInteractor elementInteractor) {
        WrappedElementsSettings settings = settingsRepository.get();
        settings.setDefaultElementInteractor(elementInteractor);
        settingsRepository.save(settings);
    }

    /**
     * Adds clickable element custom decorator
     *
     * @param clazz            class that will be handled
     * @param elementDecorator decorator that will be used for clazz decoration
     */
    @Override
    public void addElementDecorator(Class<? extends WrappedElement> clazz, IAnnotationProcessor elementDecorator) {
        WrappedElementsSettings settings = settingsRepository.get();
        settings.addWrapperAnnotationProcessor(clazz, elementDecorator);
        settingsRepository.save(settings);
    }

    /**
     * Sets platform name to use
     *
     * @param platformName platform name as String
     */
    @Override
    public void setPlatform(String platformName) {
        WrappedElementsSettings settings = settingsRepository.get();
        settings.setPlatform(platformName);
        settingsRepository.save(settings);
    }

    /**
     * Registers custom IElementInteractor in WrappedElements
     *
     * @param elementInteractor interactor to be used for as value for waitUntil
     */
    @Override
    public void registerElementInteractor(IElementInteractor elementInteractor) {
        Interactors.registerInteractor(elementInteractor);
    }
}
