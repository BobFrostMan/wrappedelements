package ua.foggger.wrapper.element.visible;

import ua.foggger.annotation.WebElement;
import ua.foggger.wrapper.element.IElementAnnotationProcessor;
import ua.foggger.wrapper.element.interactor.Interactors;
import ua.foggger.wrapper.page.ElementNameResolver;
import ua.foggger.wrapper.page.LocatorResolver;

import java.lang.reflect.Method;

public class VisibleElementAnnotationProcessor implements IElementAnnotationProcessor {

    private LocatorResolver locatorResolver;
    private ElementNameResolver elementNameResolver;

    public VisibleElementAnnotationProcessor() {
        locatorResolver = new LocatorResolver();
        elementNameResolver = new ElementNameResolver();
    }

    @Override
    public <T> Object setValuesFromAnnotation(T element, Method method, Object[] args) {
        WebElement annotation = method.getAnnotation(WebElement.class);
        VisibleElement visibleElement = (VisibleElement) element;
        String name = "".equals(annotation.name()) ? elementNameResolver.resolve(method) : annotation.name();
        visibleElement.setName(name);
        visibleElement.setInteractor(Interactors.getRegisteredInteractor(annotation.waitUntil()));
        visibleElement.setLocator(locatorResolver.resolveLocator(annotation.value(), method, args));
        visibleElement.setTimeoutInSeconds(annotation.timeout());
        return element;
    }
}
