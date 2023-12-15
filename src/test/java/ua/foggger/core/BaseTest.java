package ua.foggger.core;

import ua.foggger.elements.IClickableElement;
import ua.foggger.elements.detection.IElementDetection;
import ua.foggger.helper.IHaveReflectionAccess;

public abstract class BaseTest implements IHaveReflectionAccess {

    protected Object getLocator(IClickableElement element) {
        return getFieldValue(element, "locator");
    }

    protected IElementDetection getDetection(IClickableElement element) {
        return (IElementDetection) getFieldValue(element, "detection");
    }

    protected Object getTimeout(IClickableElement element) {
        return getFieldValue(element, "timeoutInSeconds");
    }
}
