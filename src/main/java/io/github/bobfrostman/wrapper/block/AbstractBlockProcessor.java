package io.github.bobfrostman.wrapper.block;

import io.github.bobfrostman.wrapper.locator.converter.XPathConverter;
import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;
import io.github.bobfrostman.annotation.AndroidComponent;
import io.github.bobfrostman.annotation.AnnotatedMethodMeta;
import io.github.bobfrostman.annotation.IOSComponent;
import io.github.bobfrostman.annotation.WebComponent;
import io.github.bobfrostman.common.IKnowPlatforms;
import io.github.bobfrostman.config.SettingsProvider;
import io.github.bobfrostman.wrapper.IAnnotationProcessor;
import io.github.bobfrostman.wrapper.page.ElementNameResolver;
import io.github.bobfrostman.wrapper.page.LocatorResolver;

import java.lang.reflect.Method;

public abstract class AbstractBlockProcessor implements IAnnotationProcessor, SettingsProvider, IKnowPlatforms {

    protected final LocatorResolver locatorResolver;
    protected final ElementNameResolver elementNameResolver;

    public AbstractBlockProcessor() {
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

    /**
     * Returns parent wrapped block metadata (a component that is parent for current element)
     *
     * @param element web element wrapper
     * @param method  annotated method that will produce web element
     * @param args    annotated method arguments
     * @param <T>     any web element wrapper
     * @return web element wrapper
     */
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

    private By resolveLocatorAsXpath(final WrappedBlockMeta parentBlockMeta, String locatorValue, Method method, Object[] args) {
        XPathConverter xpathConverter = new XPathConverter();
        By locator = locatorResolver.resolveLocator(locatorValue, method, args);
        if (parentBlockMeta != null) {
            String child = xpathConverter.convert(locator);
            child = child.startsWith(".") ? child.substring(1) : child;
            child = child.startsWith("/") ? child : "//" + child;
            return By.xpath(xpathConverter.convert(parentBlockMeta.getLocator()) + child);
        } else {
            return By.xpath(xpathConverter.convert(locator));
        }
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
