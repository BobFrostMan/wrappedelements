package ua.foggger.wrapper.element.clickable;

import org.openqa.selenium.support.pagefactory.ByChained;
import ua.foggger.annotation.WebElement;
import ua.foggger.wrapper.block.WrappedBlockMeta;
import ua.foggger.wrapper.element.IElementAnnotationProcessor;
import ua.foggger.wrapper.element.interactor.Interactors;
import ua.foggger.wrapper.page.ElementNameResolver;
import ua.foggger.wrapper.page.LocatorResolver;

import java.lang.reflect.Method;

public class ClickableElementAnnotationProcessor implements IElementAnnotationProcessor {

    private final LocatorResolver locatorResolver;
    private final ElementNameResolver elementNameResolver;

    public ClickableElementAnnotationProcessor() {
        locatorResolver = new LocatorResolver();
        elementNameResolver = new ElementNameResolver();
    }

    /**
     * Sets values from annotation to web element wrapper to child elements.
     *
     * @param parentBlockMeta parent block meta information object (can be null)
     * @param element         web element wrapper
     * @param method          annotated method that will produce web element
     * @param args            annotated method arguments
     * @param <T>             any web element wrapper
     * @return web element wrapper
     */
    @Override
    public <T> Object setValuesFromAnnotation(WrappedBlockMeta parentBlockMeta, T element, Method method, Object[] args) {
        WebElement annotation = method.getAnnotation(WebElement.class);
        if (annotation == null) {
            return null;
        }
        ClickableElement clickableElement = (ClickableElement) element;
        String name = "".equals(annotation.name()) ? elementNameResolver.resolve(method) : annotation.name();
        clickableElement.setName(name);
        if (parentBlockMeta != null) {
            clickableElement.setLocator(new ByChained(parentBlockMeta.getLocator(), locatorResolver.resolveLocator(annotation.value(), method, args)));
        } else {
            clickableElement.setLocator(locatorResolver.resolveLocator(annotation.value(), method, args));
        }
        clickableElement.setInteractor(Interactors.getRegisteredInteractor(annotation.waitUntil()));
        clickableElement.setTimeoutInSeconds(annotation.timeout());
        return element;
    }
}
