package ua.foggger.wrapper.block;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;
import ua.foggger.annotation.AndroidComponent;
import ua.foggger.annotation.AnnotatedMethodMeta;
import ua.foggger.annotation.IOSComponent;
import ua.foggger.annotation.WebComponent;
import ua.foggger.common.IKnowPlatforms;
import ua.foggger.config.SettingsProvider;
import ua.foggger.wrapper.element.IElementAnnotationProcessor;
import ua.foggger.wrapper.page.ElementNameResolver;
import ua.foggger.wrapper.page.LocatorResolver;

import java.lang.reflect.Method;

public class WrappedBlockAnnotationProcessor implements IElementAnnotationProcessor, SettingsProvider, IKnowPlatforms {

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
        AnnotatedMethodMeta meta = parseAnnotatedMeta(method, args);
        if (meta == null) {
            return null;
        }
        if (parentBlockMeta != null) {
            parentBlockMeta.setName(resolveName(parentBlockMeta, meta.getName(), method, args));
            parentBlockMeta.setLocator(resolveLocator(parentBlockMeta, meta.getValue(), method, args));
        }
        return element;
    }

    @Override
    public <T> WrappedBlockMeta parseWrappedBlockMeta(T element, Method method, Object[] args) {
        AnnotatedMethodMeta meta = parseAnnotatedMeta(method, args);
        if (meta == null) {
            return null;
        }
        WrappedBlockMeta parentBlockMeta = new WrappedBlockMeta();
        parentBlockMeta.setName(resolveName(null, meta.getName(), method, args));
        parentBlockMeta.setLocator(resolveLocator(null, meta.getValue(), method, args));
        return parentBlockMeta;
    }

    /**
     * Gathers all required information from method to AnnotatedMethodMeta object
     *
     * @param method method that should produce wrapped element
     * @param args   method arguments
     * @return AnnotatedMethodMeta information object
     */
    public AnnotatedMethodMeta parseAnnotatedMeta(Method method, Object[] args) {
        String platform = getSettings().getPlatform();
        //TODO: Convert to map with platform related converters for increased flexibility
        switch (platform) {
            case WEB:
                return parseWebAnnotatedMeta(method, args);
            case ANDROID:
                return parseAndroidAnnotatedMeta(method, args);
            case IOS:
                return parseIOSWebAnnotatedMeta(method, args);
            default:
                throw new IllegalArgumentException("Platform '" + platform + "' is not supported yet!");
        }
    }

    public By resolveLocator(final WrappedBlockMeta parentBlockMeta, String locatorValue, Method method, Object[] args) {
        By locator = locatorResolver.resolveLocator(locatorValue, method, args);
        return parentBlockMeta != null ? new ByChained(parentBlockMeta.getLocator(), locator) : locator;
    }

    public String resolveName(final WrappedBlockMeta parentBlockMeta, String elementName, Method method, Object[] args) {
        if (elementName != null && !elementName.isBlank()) {
            return elementName;
        }
        String page = elementNameResolver.prettify(method.getDeclaringClass().getSimpleName());
        String name = elementNameResolver.prettify(method.getName());
        if (parentBlockMeta != null) {
            name += " from " + parentBlockMeta.getName();
        } else {
            name += " on " + page;
        }
        return name;
    }

    private AnnotatedMethodMeta parseWebAnnotatedMeta(Method method, Object[] args) {
        WebComponent annotation = method.getAnnotation(WebComponent.class);
        if (annotation == null) {
            return null;
        }

        AnnotatedMethodMeta meta = new AnnotatedMethodMeta();
        meta.setName(annotation.name());
        meta.setValue(annotation.value());
        meta.setMethod(method);
        meta.setArgs(args);
        return meta;
    }

    private AnnotatedMethodMeta parseAndroidAnnotatedMeta(Method method, Object[] args) {
        AndroidComponent annotation = method.getAnnotation(AndroidComponent.class);
        if (annotation == null) {
            return null;
        }

        AnnotatedMethodMeta meta = new AnnotatedMethodMeta();
        meta.setName(annotation.name());
        meta.setValue(annotation.value());
        meta.setMethod(method);
        meta.setArgs(args);
        return meta;
    }

    private AnnotatedMethodMeta parseIOSWebAnnotatedMeta(Method method, Object[] args) {
        IOSComponent annotation = method.getAnnotation(IOSComponent.class);
        if (annotation == null) {
            return null;
        }

        AnnotatedMethodMeta meta = new AnnotatedMethodMeta();
        meta.setName(annotation.name());
        meta.setValue(annotation.value());
        meta.setMethod(method);
        meta.setArgs(args);
        return meta;
    }

}
