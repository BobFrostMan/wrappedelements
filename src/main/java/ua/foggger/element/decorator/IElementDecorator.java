package ua.foggger.element.decorator;

import ua.foggger.helper.IHaveReflectionAccess;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Element decorator used for passing parameters from annotation to element wrapper.
 */
public interface IElementDecorator extends IHaveReflectionAccess {

    /**
     * Sets values from annotation to web element wrapper.
     *
     * @param element web element wrapper
     * @param method annotated method that will produce web element
     * @param args annotated method arguments
     * @param <T> any web element wrapper
     * @return web element wrapper
     */
    <T> Object setValuesFromAnnotation(T element, Method method, Object[] args);

    /**
     * Annotation that used to mark web element wrapper
     * @return annotation clazz
     */
    Class<? extends Annotation> getAnnotationClass();
}
