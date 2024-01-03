package ua.foggger.page;

import ua.foggger.config.SettingsProvider;
import ua.foggger.elements.ClickableElement;
import ua.foggger.elements.IClickableElement;
import ua.foggger.elements.decorator.IElementDecorator;
import ua.foggger.helper.IHaveReflectionAccess;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;


/**
 * InvocationHandler for interfaces that extends IPage
 */
public class PageInvocationHandler implements InvocationHandler, IHaveReflectionAccess, SettingsProvider {

    //TODO: support components
    //TODO: support class fields
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //TODO: Will we have different annotations for different element types? Like dropdowns or something?
        //TODO: How to avoid new objects creation on each method invocation? Shouldn't be so much objects

        Class<?> clazz = method.getReturnType();
        IElementDecorator decorator = getSettings().getDecorators().get(clazz);
        if (decorator != null) {
            if (clazz.isInterface()) {
                if (method.isDefault()) {
                    //TODO: What to do with default implementations?
                    IClickableElement element = (IClickableElement) invokeDefaultMethodImpl(proxy, method, args);
                    return decorator.setValuesFromAnnotation(element, method, args);
                } else {
                    //TODO: Add opportunity to provide any element types not only that one that implements IClickableElement
                    //Create default exact implementation
                    IClickableElement element = ClickableElement.class.getConstructor().newInstance();
                    return decorator.setValuesFromAnnotation(element, method, args);
                }
            } else {
                //TODO: Add opportunity to provide any element types not only that one that implements IClickableElement
                //Create exact implementation
                IClickableElement element = (IClickableElement) clazz.getConstructor().newInstance();
                return decorator.setValuesFromAnnotation(element, method, args);
            }
        }
        if (List.class.isAssignableFrom(clazz)) {
            //TODO: Add list wrapper
        }
        return invokeDefaultMethodImpl(proxy, method, args);
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
