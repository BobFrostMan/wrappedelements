package ua.foggger.core;

import ua.foggger.elements.IClickableElement;
import ua.foggger.elements.detection.IElementInteractor;
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
}
