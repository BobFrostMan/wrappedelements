package ua.foggger.wrapper.block;

import org.openqa.selenium.support.pagefactory.ByChained;
import ua.foggger.annotation.WebComponent;
import ua.foggger.wrapper.element.IElementAnnotationProcessor;
import ua.foggger.wrapper.page.ElementNameResolver;
import ua.foggger.wrapper.page.LocatorResolver;

import java.lang.reflect.Method;
import java.util.Arrays;

public class WrappedBlockAnnotationProcessor implements IElementAnnotationProcessor {

    private final LocatorResolver locatorResolver;
    private final ElementNameResolver elementNameResolver;

    public WrappedBlockAnnotationProcessor() {
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
        WebComponent annotation = method.getAnnotation(WebComponent.class);
        if (annotation == null) {
            return null;
        }
        if (parentBlockMeta != null) {
            String name = "".equals(annotation.name()) ? elementNameResolver.resolve(method) : annotation.name();
            parentBlockMeta.setName(name);
            parentBlockMeta.setBlockIdentifier(method.getReturnType() + "_" + method.getName() + "_" + Arrays.toString(method.getParameterTypes()));
            parentBlockMeta.setLocator(new ByChained(parentBlockMeta.getLocator(), locatorResolver.resolveLocator(annotation.value(), method, args)));
        }
        return element;
    }

    @Override
    public <T> WrappedBlockMeta parseWrappedBlockMeta(T element, Method method, Object[] args) {
        WebComponent annotation = method.getAnnotation(WebComponent.class);
        if (annotation == null) {
            return null;
        }
        WrappedBlockMeta parentBlockMeta = new WrappedBlockMeta();
        String name = "".equals(annotation.name()) ? elementNameResolver.resolve(method) : annotation.name();
        parentBlockMeta.setName(name);
        parentBlockMeta.setBlockIdentifier(method.getReturnType() + "_" + method.getName() + "_" + Arrays.toString(method.getParameterTypes()));
        parentBlockMeta.setLocator(locatorResolver.resolveLocator(annotation.value(), method, args));
        return parentBlockMeta;
    }


}
