package ua.foggger.config.manager;

import org.openqa.selenium.WebDriver;
import ua.foggger.config.WrappedElementsSettings;
import ua.foggger.config.repo.SettingsRepository;
import ua.foggger.element.IWrappedElement;
import ua.foggger.element.decorator.IElementDecorator;
import ua.foggger.element.interactor.IElementInteractor;

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
        settings.setElementInteractor(elementInteractor);
        settingsRepository.save(settings);
    }

    /**
     * Adds clickable element custom decorator
     *
     * @param clazz            class that will be handled
     * @param elementDecorator decorator that will be used for clazz decoration
     */
    @Override
    public void addElementDecorator(Class<? extends IWrappedElement> clazz, IElementDecorator elementDecorator) {
        WrappedElementsSettings settings = settingsRepository.get();
        settings.addDecorator(clazz, elementDecorator);
        settingsRepository.save(settings);
    }
}
