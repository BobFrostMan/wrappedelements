package ua.foggger.elements.decorator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface IElementDecorator {

    <T> Object setValuesFromAnnotation(T element, Method method, Object[] args);

    Class<? extends Annotation> getAnnotationClass();
}
