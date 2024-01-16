package ua.foggger;

import org.testng.annotations.BeforeClass;
import ua.foggger.config.WrappedElements;
import ua.foggger.core.driver.DummyWebDriver;
import ua.foggger.wrapper.element.IWrappedElement;
import ua.foggger.wrapper.element.IElementInteractor;
import ua.foggger.helper.IHaveReflectionAccess;

public abstract class BaseTest implements IHaveReflectionAccess {

    protected Object getLocator(IWrappedElement element) {
        return getFieldValue(element, "locator");
    }

    protected IElementInteractor getInteraction(IWrappedElement element) {
        return (IElementInteractor) getFieldValue(element, "interactor");
    }

    protected Object getTimeout(IWrappedElement element) {
        return getFieldValue(element, "timeoutInSeconds");
    }

    @BeforeClass
    public void setUp() {
        WrappedElements.config().driverCreator(DummyWebDriver::new);
    }
}
