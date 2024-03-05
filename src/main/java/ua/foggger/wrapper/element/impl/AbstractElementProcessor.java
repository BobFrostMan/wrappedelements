package ua.foggger.wrapper.element.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;
import ua.foggger.annotation.AndroidElement;
import ua.foggger.annotation.AnnotatedMethodMeta;
import ua.foggger.annotation.IOSElement;
import ua.foggger.annotation.WebElement;
import ua.foggger.common.IKnowPlatforms;
import ua.foggger.config.SettingsProvider;
import ua.foggger.wrapper.IAnnotationProcessor;
import ua.foggger.wrapper.block.WrappedBlockMeta;
import ua.foggger.wrapper.interactor.Interactors;
import ua.foggger.wrapper.locator.converter.XPathConverter;
import ua.foggger.wrapper.page.ElementNameResolver;
import ua.foggger.wrapper.page.LocatorResolver;

import java.lang.reflect.Method;

public abstract class AbstractElementProcessor implements IAnnotationProcessor, IKnowPlatforms, SettingsProvider {

    protected final LocatorResolver locatorResolver;
    protected final ElementNameResolver elementNameResolver;

    public AbstractElementProcessor() {
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
        By resolvedLocator = resolveLocator(parentBlockMeta, meta.getValue(), method, args);
        String resolvedName = resolveName(parentBlockMeta, meta.getName(), method, args);
        meta.setResolvedName(resolvedName);
        meta.setResolvedLocator(resolvedLocator);
        meta.setResolvedInteractor(Interactors.getRegisteredInteractor(meta.getWaitUntil()));

        return resolveElement(meta, element);
    }

    /**
     * Fulfills element wrapper object with data from WrappedBlockMeta and AnnotatedMethodMeta objects.
     *
     * @param annotatedMethodMetaInfo wrapped element method meta information
     * @param element                 element wrapper object
     * @param <T>                     any type that extends ClickableElement
     * @return wrapped element with fulfilled fields
     */
    public abstract <T> T resolveElement(final AnnotatedMethodMeta annotatedMethodMetaInfo, T element);

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
        WebElement annotation = method.getAnnotation(WebElement.class);
        if (annotation == null) {
            return null;
        }

        AnnotatedMethodMeta meta = new AnnotatedMethodMeta();
        meta.setName(annotation.name());
        meta.setValue(annotation.value());
        meta.setMethod(method);
        meta.setArgs(args);
        meta.setWaitUntil(annotation.waitUntil());
        meta.setTimeout(annotation.timeout());
        return meta;
    }

    private AnnotatedMethodMeta parseAndroidAnnotatedMeta(Method method, Object[] args) {
        AndroidElement annotation = method.getAnnotation(AndroidElement.class);
        if (annotation == null) {
            return null;
        }

        AnnotatedMethodMeta meta = new AnnotatedMethodMeta();
        meta.setName(annotation.name());
        meta.setValue(annotation.value());
        meta.setMethod(method);
        meta.setArgs(args);
        meta.setWaitUntil(annotation.waitUntil());
        meta.setTimeout(annotation.timeout());
        return meta;
    }

    private AnnotatedMethodMeta parseIOSWebAnnotatedMeta(Method method, Object[] args) {
        IOSElement annotation = method.getAnnotation(IOSElement.class);
        if (annotation == null) {
            return null;
        }

        AnnotatedMethodMeta meta = new AnnotatedMethodMeta();
        meta.setName(annotation.name());
        meta.setValue(annotation.value());
        meta.setMethod(method);
        meta.setArgs(args);
        meta.setWaitUntil(annotation.waitUntil());
        meta.setTimeout(annotation.timeout());
        return meta;
    }

}
