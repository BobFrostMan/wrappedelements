package ua.foggger.wrapper.block;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ua.foggger.annotation.AnnotatedMethodMeta;
import ua.foggger.common.IHaveReflectionAccess;
import ua.foggger.driver.DriverProvider;
import ua.foggger.wrapper.IAnnotationProcessor;
import ua.foggger.wrapper.interactor.Interactors;
import ua.foggger.wrapper.page.WrappedBlockInvocationHandler;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;

public class ListWrappedBlockAnnotationProcessor extends AbstractBlockProcessor implements IAnnotationProcessor, IHaveReflectionAccess {

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
                    WebElement webElement = listElements.get(i);
                    WrappedComponent component = wrapComponent(parentBlockMeta, i, elClazz, annotatedMethodMetaInfo);
                    listElementsProcessor.setValuesFromAnnotation(parentBlockMeta, component, annotatedMethodMetaInfo.getMethod(), annotatedMethodMetaInfo.getArgs());
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

    //TODO: refactor resolve locator with index
    private By getXpathLocatorForListItem(By locator, int elementIndex) {
        String xpath = locator.toString().replace("By.xpath: ", "");
        return By.xpath("(" + xpath + ")[" + elementIndex + "]");
    }
}