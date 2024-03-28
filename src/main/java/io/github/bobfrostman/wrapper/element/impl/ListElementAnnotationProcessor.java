package io.github.bobfrostman.wrapper.element.impl;

import io.github.bobfrostman.annotation.AnnotatedMethodMeta;
import io.github.bobfrostman.common.IHaveReflectionAccess;
import io.github.bobfrostman.driver.DriverProvider;
import io.github.bobfrostman.wrapper.IAnnotationProcessor;
import io.github.bobfrostman.wrapper.block.WrappedBlockMeta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListElementAnnotationProcessor extends AbstractElementProcessor implements IHaveReflectionAccess {

    private WrappedBlockMeta wrappedBlockMeta;
    private IAnnotationProcessor listElementsProcessor;
    private List<Object> elementsList;

    public ListElementAnnotationProcessor(WrappedBlockMeta wrappedBlockMeta, IAnnotationProcessor listElementsProcessor, List<Object> elementsList) {
        this.wrappedBlockMeta = wrappedBlockMeta;
        this.listElementsProcessor = listElementsProcessor;
        this.elementsList = elementsList;
    }

    /**
     * Fulfills element wrapper object with data from WrappedBlockMeta and AnnotatedMethodMeta objects.
     *
     * @param annotatedMethodMetaInfo wrapped element method meta information
     * @param element                 element wrapper object
     * @return wrapped element with fulfilled fields
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T resolveElement(AnnotatedMethodMeta annotatedMethodMetaInfo, T element) {
        if (elementsList.size() == 0) {
            Class clazz = getListElementClass(annotatedMethodMetaInfo.getMethod());
            List<org.openqa.selenium.WebElement> listElements = DriverProvider.get().findElements(annotatedMethodMetaInfo.getResolvedLocator());
            if (listElements.size() > 0) {
                for (int i = 0; i < listElements.size(); i++) {
                    org.openqa.selenium.WebElement webElement = listElements.get(i);
                    elementsList.add(wrapListElement(webElement, i, clazz, annotatedMethodMetaInfo));
                }
            }
            return (T) elementsList;
        } else {
            List<Object> result = new ArrayList<>();
            for (int i = 0; i < elementsList.size(); i++) {
                ClickableElement clickableElement = (ClickableElement) elementsList.get(i);
                listElementsProcessor.setValuesFromAnnotation(wrappedBlockMeta, clickableElement, annotatedMethodMetaInfo.getMethod(), annotatedMethodMetaInfo.getArgs());
                clickableElement.setName(clickableElement.getName() + " (list item number [" + i + "])");
                result.add(clickableElement);
            }
            return (T) result;
        }
    }

    private Class getListElementClass(Method method) {
        try {
            Type actualType = ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
            return Class.forName(actualType.getTypeName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T wrapListElement(org.openqa.selenium.WebElement webElement, int elementIndex, Class clazz, AnnotatedMethodMeta annotatedMethodMetaInfo) {
        try {
            if (ClickableElement.class.isAssignableFrom(clazz)) {
                Object clickableElementObject = clazz.getConstructor().newInstance();
                ClickableElement clickableElement = (ClickableElement) clickableElementObject;
                setFieldValue(clickableElement, "innerElement", webElement);
                clickableElement.setDetectionFunction((name) -> clickableElement.getWrappedElement());
                listElementsProcessor.setValuesFromAnnotation(wrappedBlockMeta, clickableElement, annotatedMethodMetaInfo.getMethod(), annotatedMethodMetaInfo.getArgs());
                clickableElement.setName(clickableElement.getName() + " (list item number [" + elementIndex + "])");
                return (T) clickableElementObject;
            } else {
                throw new UnsupportedOperationException("Blocks are not supported yet!");
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e.getCause());
        }
    }


}
