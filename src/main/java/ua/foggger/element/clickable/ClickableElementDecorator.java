package ua.foggger.element.clickable;

import ua.foggger.annotation.WebElement;
import ua.foggger.element.decorator.IElementDecorator;
import ua.foggger.element.interactor.Interactors;
import ua.foggger.page.ElementNameResolver;
import ua.foggger.page.LocatorResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ClickableElementDecorator implements IElementDecorator {

    private LocatorResolver locatorResolver;
    private ElementNameResolver elementNameResolver;

    public ClickableElementDecorator() {
        locatorResolver = new LocatorResolver();
        elementNameResolver = new ElementNameResolver();
    }

    //TODO: change interface for easier reading
    @Override
    public <T> Object setValuesFromAnnotation(T element, Method method, Object[] args) {
        WebElement annotation = (WebElement) method.getAnnotation(getAnnotationClass());
        ClickableElement clickableElement = (ClickableElement) element;
        String name = "".equals(annotation.name()) ? elementNameResolver.resolve(method) : annotation.name();
        clickableElement.setName(name);
        clickableElement.setDetection(Interactors.getRegisteredDetection(annotation.waitUntil()));
        clickableElement.setLocator(locatorResolver.resolveLocator(annotation.value(), method, args));
        clickableElement.setTimeoutInSeconds(annotation.timeout());
        return element;
    }

    @Override
    public Class<? extends Annotation> getAnnotationClass() {
        return WebElement.class;
    }
}
