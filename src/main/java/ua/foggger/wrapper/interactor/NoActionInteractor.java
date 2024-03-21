package ua.foggger.wrapper.interactor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ua.foggger.common.IKnowInteractors;

/**
 * Interaction with web elements will be without any additional logic, (usual web driver usage)
 */
public class NoActionInteractor implements IElementInteractor, IKnowInteractors {

    /**
     * Returns name of this element detection implementation
     *
     * @return name as String
     */
    @Override
    public String name() {
        return IMMEDIATELY;
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
