package ua.foggger.wrapper.element.visible;

import org.openqa.selenium.support.pagefactory.ByChained;
import ua.foggger.annotation.WebElement;
import ua.foggger.wrapper.block.WrappedBlockMeta;
import ua.foggger.wrapper.element.IElementAnnotationProcessor;
import ua.foggger.wrapper.interactor.Interactors;
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

    /**
     * Sets values from annotation to web element wrapper to child elements.
     *
     * @param parentBlockMeta parent block meta information object (can be null)
     * @param element         web element wrapper
     * @param method          annotated method that will produce web element
     * @param args            annotated method arguments
     * @return web element wrapper
     */
    @Override
    public <T> Object setValuesFromAnnotation(WrappedBlockMeta parentBlockMeta, T element, Method method, Object[] args) {
        WebElement annotation = method.getAnnotation(WebElement.class);
        VisibleElement visibleElement = (VisibleElement) element;
        String name = "".equals(annotation.name()) ? elementNameResolver.resolve(method) : annotation.name();
        visibleElement.setName(name);
        visibleElement.setInteractor(Interactors.getRegisteredInteractor(annotation.waitUntil()));
        if (parentBlockMeta != null) {
            visibleElement.setLocator(new ByChained(parentBlockMeta.getLocator(), locatorResolver.resolveLocator(annotation.value(), method, args)));
        } else {
            visibleElement.setLocator(locatorResolver.resolveLocator(annotation.value(), method, args));
        }
        visibleElement.setTimeoutInSeconds(annotation.timeout());
        return element;
    }
}
