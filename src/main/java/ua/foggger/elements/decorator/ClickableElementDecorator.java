package ua.foggger.elements.decorator;

import ua.foggger.annotation.WebElement;
import ua.foggger.elements.interactor.Interactors;
import ua.foggger.helper.IHaveReflectionAccess;
import ua.foggger.page.ElementNameResolver;
import ua.foggger.page.LocatorResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ClickableElementDecorator implements IElementDecorator, IHaveReflectionAccess {

    private LocatorResolver locatorResolver;
    private ElementNameResolver elementNameResolver;

    public ClickableElementDecorator() {
        locatorResolver = new LocatorResolver();
        elementNameResolver = new ElementNameResolver();
    }

    @Override
    public <T> Object setValuesFromAnnotation(T element, Method method, Object[] args) {
        WebElement annotation = (WebElement) method.getAnnotation(getAnnotationClass());
        String name = "".equals(annotation.name()) ? elementNameResolver.resolve(method) : annotation.name();
        setFieldValue(element, "name", name);
        setFieldValue(element, "detection", Interactors.getRegisteredDetection(annotation.waitUntil()));
        setFieldValue(element, "locator", locatorResolver.resolveLocator(annotation.value(), method, args));
        setFieldValue(element, "timeoutInSeconds", annotation.during());
        return element;
    }

    @Override
    public Class<? extends Annotation> getAnnotationClass() {
        return WebElement.class;
    }
}
