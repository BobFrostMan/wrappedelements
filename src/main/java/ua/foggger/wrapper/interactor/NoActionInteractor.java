package ua.foggger.wrapper.interactor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Interaction with web elements will be without any additional logic, (usual web driver usage)
 */
public class NoActionInteractor implements IElementInteractor {

    /**
     * Returns name of this element detection implementation
     *
     * @return name as String
     */
    @Override
    public String name() {
        return "no_action";
    }

    /**
     * Returns true if element is ready for interaction.
     *
     * @param by        element By locator
     * @param webDriver web driver object
     * @return true if element is ready for interaction
     */
    @Override
    public boolean isReadyForInteraction(By by, WebDriver webDriver) {
        return true;
    }
}
