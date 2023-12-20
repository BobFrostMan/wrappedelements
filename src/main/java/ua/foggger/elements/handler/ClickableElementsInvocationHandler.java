package ua.foggger.elements.handler;

import org.openqa.selenium.By;
import ua.foggger.driver.DriverStorage;
import ua.foggger.elements.ClickableElement;
import ua.foggger.elements.detection.IElementDetection;
import ua.foggger.helper.ICanWait;
import ua.foggger.helper.IHaveReflectionAccess;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ClickableElementsInvocationHandler implements InvocationHandler, IHaveReflectionAccess, ICanWait {

    //TODO: do we need to make elements invocation handler or it's better to handle via internal mechanism
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ClickableElement element = (ClickableElement) proxy;
        IElementDetection detection = (IElementDetection) getFieldValue(element, "detection");
        By locator = (By) getFieldValue(element, "locator");
        int timeoutSeconds = (Integer) getFieldValue(element, "timeoutInSeconds");
        boolean isReady = waitFor(() -> detection.isReadyForInteraction(method.getName(), locator, DriverStorage.get()), timeoutSeconds);
        //TODO: Redesign invocation conditions
        if (isReady) {
            return method.invoke(args);
        } else {
            throw new RuntimeException("Element '" + element.getName() + "' with locator '" + getFieldValue(element, "locator") + "' can't be detected!");
        }
    }
}
