package ua.foggger.common;

import java.lang.reflect.Field;

/**
 * Provides access to fields using reflection
 */
public interface IHaveReflectionAccess {

    default void setFieldValue(Object element, String fieldName, Object fieldValue) {
        Field field = null;
        try {
            field = element.getClass()
                    .getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(element, fieldValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    default Object getFieldValue(Object element, String fieldName) {
        Field field = null;
        try {
            field = element.getClass()
                    .getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(element);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
