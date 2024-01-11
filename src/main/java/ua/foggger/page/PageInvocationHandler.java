package ua.foggger.page;

import ua.foggger.config.SettingsProvider;
import ua.foggger.element.IWrappedElement;
import ua.foggger.element.clickable.ClickableElement;
import ua.foggger.element.decorator.IElementDecorator;
import ua.foggger.helper.IHaveReflectionAccess;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.*;
import java.util.List;


/**
 * InvocationHandler for interfaces that extends IPage
 */
public class PageInvocationHandler implements InvocationHandler, IHaveReflectionAccess, SettingsProvider {

    //TODO: support components
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //TODO: How to avoid new objects creation on each method invocation? Shouldn't be so much objects or it's fine?
        //TODO: each time reset the values from annotations is another drawback
        Class<?> clazz = method.getReturnType();
        if (clazz.isAssignableFrom(List.class)) {
            Type actualType = ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
            //TODO: add list types handling

        }
        //TODO: handle collections here
        IElementDecorator decorator = getSettings().getDecorators().get(clazz);
        if (decorator != null) {
            //user wants to use abstract element
            if (clazz.isInterface()) {
                IWrappedElement element = method.isDefault()
                        ? (IWrappedElement) invokeDefaultMethodImpl(proxy, method, args)
                        : ClickableElement.class.getConstructor().newInstance();
                return decorator.setValuesFromAnnotation(element, method, args);
            } else {
                //Create exact implementation
                return decorator.setValuesFromAnnotation(clazz.getConstructor().newInstance(), method, args);
            }
        }
        //no decorator - do nothing with method just invoke if it has default implementation
        if (method.isDefault()) {
            return invokeDefaultMethodImpl(proxy, method, args);
        } else {
            //Execution shouldn't get here
            throw new UnsupportedOperationException("Seems like you didn't implemented IElementDecorator according to defined type!");
        }
    }

    /**
     * Invokes default implementation of specified method using reflection, and returns result object.
     *
     * @param proxy  proxy object
     * @param method method to be invoked
     * @param args   method arguments
     * @return object created by methods, default implementation.
     */
    private Object invokeDefaultMethodImpl(Object proxy, Method method, Object[] args) throws Throwable {
        final float version = Float.parseFloat(System.getProperty("java.class.version"));
        if (version <= 52) {
            final Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class);
            constructor.setAccessible(true);

            final Class<?> clazz2 = method.getDeclaringClass();
            return constructor.newInstance(clazz2).in(clazz2).unreflectSpecial(method, clazz2).bindTo(proxy).invokeWithArguments(args);
        } else {
            return MethodHandles.lookup().findSpecial(method.getDeclaringClass(), method.getName(), MethodType.methodType(method.getReturnType(), new Class[0]), method.getDeclaringClass()).bindTo(proxy).invokeWithArguments(args);
        }
    }
}
