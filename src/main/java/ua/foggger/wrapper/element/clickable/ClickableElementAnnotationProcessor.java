package ua.foggger.wrapper.element.clickable;

import ua.foggger.annotation.WebElement;
import ua.foggger.wrapper.element.IElementAnnotationProcessor;
import ua.foggger.wrapper.element.interactor.Interactors;
import ua.foggger.wrapper.page.ElementNameResolver;
import ua.foggger.wrapper.page.LocatorResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ClickableElementAnnotationProcessor implements IElementAnnotationProcessor {

    private LocatorResolver locatorResolver;
    private ElementNameResolver elementNameResolver;

    public ClickableElementAnnotationProcessor() {
        locatorResolver = new LocatorResolver();
        elementNameResolver = new ElementNameResolver();
    }

    @Override
    public <T> Object setValuesFromAnnotation(T element, Method method, Object[] args) {
        WebElement annotation = method.getAnnotation(WebElement.class);
        ClickableElement clickableElement = (ClickableElement) element;
        String name = "".equals(annotation.name()) ? elementNameResolver.resolve(method) : annotation.name();
        clickableElement.setName(name);
        clickableElement.setInteractor(Interactors.getRegisteredInteractor(annotation.waitUntil()));
        clickableElement.setLocator(locatorResolver.resolveLocator(annotation.value(), method, args));
        clickableElement.setTimeoutInSeconds(annotation.timeout());
        return element;
    }

    @Override
    public boolean isSupportAnnotation(Class<? extends Annotation> annotationClass) {
        return WebElement.class.equals(annotationClass);
    }
}
