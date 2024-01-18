package ua.foggger.wrapper.element;

import ua.foggger.helper.IHaveReflectionAccess;

import java.lang.reflect.Method;

/**
 * Element annotation processor used for passing parameters from annotation to element wrapper object.
 */
public interface IElementAnnotationProcessor extends IHaveReflectionAccess {

    /**
     * Sets values from annotation to web element wrapper.
     *
     * @param element web element wrapper
     * @param method  annotated method that will produce web element
     * @param args    annotated method arguments
     * @param <T>     any web element wrapper
     * @return web element wrapper
     */
    <T> Object setValuesFromAnnotation(T element, Method method, Object[] args);
}
