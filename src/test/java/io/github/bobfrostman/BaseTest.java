package io.github.bobfrostman;

import io.github.bobfrostman.common.IHaveReflectionAccess;
import io.github.bobfrostman.common.IKnowInteractors;
import io.github.bobfrostman.wrapper.element.WrappedElement;
import org.testng.annotations.BeforeClass;
import io.github.bobfrostman.config.WrappedElements;
import io.github.bobfrostman.ui.driver.DummyWebDriver;
import io.github.bobfrostman.wrapper.interactor.IElementInteractor;

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
