package ua.foggger.wrapper.element;

import ua.foggger.annotation.WebElement;
import ua.foggger.helper.IHaveReflectionAccess;

import java.lang.annotation.Annotation;
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

    /**
     * Returns true if current processor supports specified annotation class, otherwise false.
     *
     * @param annotationClass annotation to test
     * @return true or false
     */
    default boolean isSupportAnnotation(Class<? extends Annotation> annotationClass) {
        return WebElement.class.equals(annotationClass);
    }
}
