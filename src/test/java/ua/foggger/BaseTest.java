package ua.foggger;

import org.testng.annotations.BeforeClass;
import ua.foggger.config.WrappedElements;
import ua.foggger.core.driver.DummyWebDriver;
import ua.foggger.wrapper.element.WrappedElement;
import ua.foggger.wrapper.interactor.IElementInteractor;
import ua.foggger.common.IHaveReflectionAccess;
import ua.foggger.common.IKnowInteractors;

public abstract class BaseTest implements IHaveReflectionAccess, IKnowInteractors {

    protected Object getLocator(WrappedElement element) {
        return getFieldValue(element, "locator");
    }

    protected Object getName(WrappedElement element) {
        return getFieldValue(element, "name");
    }

    protected IElementInteractor getInteraction(WrappedElement element) {
        return (IElementInteractor) getFieldValue(element, "interactor");
    }

    protected Object getTimeout(WrappedElement element) {
        return getFieldValue(element, "timeoutInSeconds");
    }

    @BeforeClass
    public void setUp() {
        WrappedElements.config().driverCreator(DummyWebDriver::new);
    }
}
