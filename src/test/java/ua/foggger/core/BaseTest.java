package ua.foggger.core;

import org.testng.annotations.BeforeClass;
import ua.foggger.config.WrappedElements;
import ua.foggger.core.driver.DummyWebDriver;
import ua.foggger.elements.IClickableElement;
import ua.foggger.elements.interactor.IElementInteractor;
import ua.foggger.helper.IHaveReflectionAccess;

public abstract class BaseTest implements IHaveReflectionAccess {

    protected Object getLocator(IClickableElement element) {
        return getFieldValue(element, "locator");
    }

    protected IElementInteractor getDetection(IClickableElement element) {
        return (IElementInteractor) getFieldValue(element, "detection");
    }

    protected Object getTimeout(IClickableElement element) {
        return getFieldValue(element, "timeoutInSeconds");
    }

    @BeforeClass
    public void setUp(){
        WrappedElements.config().driverCreator(DummyWebDriver::new);
    }
}
