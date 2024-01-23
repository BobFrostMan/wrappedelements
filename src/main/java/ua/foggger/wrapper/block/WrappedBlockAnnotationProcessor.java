package ua.foggger.wrapper.block;

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
     * Sets values from annotation to web component wrapper.
     *
     * @param element web element wrapper
     * @param method  annotated method that will produce web element
     * @param args    annotated method arguments
     * @return web element wrapper
     */
    @Override
    public <T> Object setValuesFromAnnotation(T element, Method method, Object[] args) {
        WebComponent annotation = method.getAnnotation(WebComponent.class);
        if (annotation == null) {
            return null;
        }
        String name = "".equals(annotation.name()) ? elementNameResolver.resolve(method) : annotation.name();
        //FIXME: block doesn't work currently
        WrappedBlockMeta blockMeta = getContextMeta(method.getReturnType() + "_" + method.getName() + "_" + Arrays.toString(method.getParameterTypes()));
        if (blockMeta == null) {
            blockMeta = new WrappedBlockMeta();
            blockMeta.setBlockIdentifier(method.getReturnType() + "_" + method.getName() + "_" + Arrays.toString(method.getParameterTypes()));
            blockMeta.setLocator(locatorResolver.resolveLocator(annotation.value(), method, args));
            blockMeta.setName(name);
            setContextMeta(blockMeta);
        }
        WrappedComponent wrappedComponent = (WrappedComponent) element;
        //TODO: cannot set values to proxy object itself, however child elements is possible to handle
        //wrappedComponent.setRootElementLocator(locatorResolver.resolveLocator(annotation.value(), method, args));
        //wrappedComponent.setName(name);
        return element;
    }
}
