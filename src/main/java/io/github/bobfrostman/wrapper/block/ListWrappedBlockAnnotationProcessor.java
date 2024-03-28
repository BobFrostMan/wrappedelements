package io.github.bobfrostman.wrapper.block;

import io.github.bobfrostman.common.IHaveReflectionAccess;
import io.github.bobfrostman.wrapper.locator.converter.XPathConverter;
import io.github.bobfrostman.wrapper.page.WrappedBlockInvocationHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.bobfrostman.annotation.AnnotatedMethodMeta;
import io.github.bobfrostman.driver.DriverProvider;
import io.github.bobfrostman.wrapper.IAnnotationProcessor;
import io.github.bobfrostman.wrapper.interactor.Interactors;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListWrappedBlockAnnotationProcessor extends AbstractBlockProcessor implements IAnnotationProcessor, IHaveReflectionAccess {

    private static Logger LOGGER = LoggerFactory.getLogger(ListWrappedBlockAnnotationProcessor.class);

    private WrappedBlockMeta parentBlockMeta;
    private IAnnotationProcessor listElementsProcessor;
    private List<Object> elementsList;

    public ListWrappedBlockAnnotationProcessor(WrappedBlockMeta wrappedBlockMeta, IAnnotationProcessor listElementsProcessor, List<Object> elementsList) {
        this.parentBlockMeta = wrappedBlockMeta;
        this.listElementsProcessor = listElementsProcessor;
        this.elementsList = elementsList;
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

        if (parentBlockMeta != null) {
            parentBlockMeta.setName(resolveName(parentBlockMeta, meta.getName(), method, args));
            parentBlockMeta.setLocator(resolveLocator(parentBlockMeta, meta.getValue(), method, args));
        }
        return resolveElement(parentBlockMeta, meta, element);
    }

    /**
     * Returns wrapped block metadata or null if element is not a WrappedComponent
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
        WrappedBlockMeta wrappedBlockMeta = new WrappedBlockMeta();
        wrappedBlockMeta.setName(resolveName(parentBlockMeta, meta.getName(), method, args));
        wrappedBlockMeta.setLocator(resolveLocator(parentBlockMeta, meta.getValue(), method, args));
        return wrappedBlockMeta;
    }

    /**
     * Fulfills element wrapper object with data from WrappedBlockMeta and AnnotatedMethodMeta objects.
     *
     * @param parentBlockMeta         parent block meta info object (can be null)
     * @param annotatedMethodMetaInfo wrapped element method meta information
     * @param element                 element wrapper object
     * @return wrapped element with fulfilled fields
     */
    @SuppressWarnings({"unchecked", "unused"})
    public <T> T resolveElement(WrappedBlockMeta parentBlockMeta, AnnotatedMethodMeta annotatedMethodMetaInfo, T element) {
        Class<?> elClazz = getElementClass(annotatedMethodMetaInfo.getMethod());
        if (elementsList.size() == 0) {
            List<org.openqa.selenium.WebElement> listElements;
            if (parentBlockMeta == null) {
                listElements = DriverProvider.get().findElements(annotatedMethodMetaInfo.getResolvedLocator());
            } else {
                listElements = DriverProvider.get().findElements(parentBlockMeta.getLocator());
            }
            if (listElements.size() > 0) {
                for (int i = 0; i < listElements.size(); i++) {
                    /**
                     * Invoke listElementsProcessor.setValuesFromAnnotation on child elements will modify parentBlockMeta object.
                     * As result with for each element in this list, parentBlockMeta locator will get another wrap of new ByChained, and locator won't be valid.
                     * To avoid such wrap, and save proper xpath need to pass a copy of this object if it's not null
                     */
                    WrappedBlockMeta parentForElementMeta = null;
                    if (parentBlockMeta != null) {
                        parentForElementMeta = new WrappedBlockMeta();
                        parentForElementMeta.setName(parentBlockMeta.getName());
                        parentForElementMeta.setLocator(parentBlockMeta.getLocator());
                    }
                    WebElement webElement = listElements.get(i);
                    WrappedComponent component = wrapComponent(parentForElementMeta, i, elClazz, annotatedMethodMetaInfo);
                    listElementsProcessor.setValuesFromAnnotation(parentForElementMeta, component, annotatedMethodMetaInfo.getMethod(), annotatedMethodMetaInfo.getArgs());
                    elementsList.add(component);
                }
            }
            return (T) elementsList;
        } else {
            throw new UnsupportedOperationException("Default list element implementation s not supported");
        }
    }

    private Class<?> getElementClass(Method method) {
        Type actualType = ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
        try {
            return Class.forName(actualType.getTypeName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private WrappedComponent wrapComponent(WrappedBlockMeta parentBlockMeta, int elementIndex, Class clazz, AnnotatedMethodMeta annotatedMethodMetaInfo) {
        WrappedBlockMeta blockMeta = new WrappedBlockMeta();
        if (parentBlockMeta == null) {
            blockMeta.setName(annotatedMethodMetaInfo.getResolvedName() + " (" + elementIndex + " component in list)");
            By xpath = getXpathLocatorForListItem(annotatedMethodMetaInfo.getResolvedLocator(), elementIndex + 1);
            blockMeta.setLocator(xpath);
        } else {
            blockMeta.setName(parentBlockMeta.getName() + " (" + elementIndex + " component in list)");
            //FIXME: Handle list of items in list of items or parent locator
            By xpath = getXpathLocatorForListItem(parentBlockMeta.getLocator(), elementIndex + 1);
            blockMeta.setLocator(xpath);
        }
        return (WrappedComponent) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz, WrappedComponent.class},
                new WrappedBlockInvocationHandler(blockMeta));
    }

    /**
     * Returns exact By.xpath locator for list items, based on provided locator and element index
     *
     * @param locator      by locator object
     * @param elementIndex list item index
     * @return By.xpath for specified element and index
     */
    private By getXpathLocatorForListItem(By locator, int elementIndex) {
        LOGGER.atTrace().setMessage("Converting {} to xpath for {} element").addArgument(locator).addArgument(elementIndex).log();
        List<By> locators = new ArrayList<>();
        putAsSeparateLocators(locator, locators);
        XPathConverter converter = new XPathConverter();
        StringBuilder builder = new StringBuilder();
        for (By by : locators) {
            builder.append(converter.convert(by));
        }
        String xpath = builder.toString();
        By result = By.xpath("(" + xpath + ")[" + elementIndex + "]");
        LOGGER.atTrace().setMessage("Converted xpath is {}").addArgument(result).log();
        return result;
    }

    /**
     * Fills result list with By locators extracted from provided locator.
     * Mainly extracts ByChained locator to list of By objects
     *
     * @param locator locator to extract
     * @param result  list to fill with locators
     */
    private void putAsSeparateLocators(By locator, final List<By> result) {
        if (locator instanceof ByChained) {
            By[] bys = (By[]) getFieldValue(locator, "bys");
            for (By by : bys) {
                putAsSeparateLocators(by, result);
            }
        } else {
            result.add(locator);
        }
    }
}