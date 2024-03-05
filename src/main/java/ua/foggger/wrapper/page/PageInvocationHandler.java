package ua.foggger.wrapper.page;

import ua.foggger.common.IHaveReflectionAccess;
import ua.foggger.config.SettingsProvider;
import ua.foggger.wrapper.IAnnotationProcessor;
import ua.foggger.wrapper.block.ListWrappedBlockAnnotationProcessor;
import ua.foggger.wrapper.block.WrappedBlockMeta;
import ua.foggger.wrapper.block.WrappedComponent;
import ua.foggger.wrapper.element.WrappedElement;
import ua.foggger.wrapper.element.impl.ClickableElement;
import ua.foggger.wrapper.element.impl.ListElementAnnotationProcessor;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;


/**
 * InvocationHandler for interfaces that extends IPage
 */
public class PageInvocationHandler implements InvocationHandler, IHaveReflectionAccess, SettingsProvider {

    //TODO: How to avoid new objects creation on each method invocation? Shouldn't be so much objects or it's fine?
    //TODO: each time reset the values from annotations is another drawback
    @SuppressWarnings({"unchecked"})
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> clazz = method.getReturnType();
        if (WrappedComponent.class.isAssignableFrom(clazz)) {
            IAnnotationProcessor annotationProcessor = getSettings().getAnnotationProcessors().get(clazz);
            if (annotationProcessor == null) {
                annotationProcessor = getSettings().getAnnotationProcessors().get(WrappedComponent.class);
            }
            WrappedBlockMeta wrappedBlockMeta = annotationProcessor.parseWrappedBlockMeta(proxy, method, args);
            Object obj = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz, WrappedComponent.class}, new WrappedBlockInvocationHandler(wrappedBlockMeta));
            annotationProcessor.setValuesFromAnnotation(null, obj, method, args);
            return obj;
        }

        if (List.class.isAssignableFrom(clazz)) {
            Type actualType = ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
            IAnnotationProcessor annotationProcessor = getSettings().getAnnotationProcessors().get(Class.forName(actualType.getTypeName()));
            if (WrappedComponent.class.isAssignableFrom(Class.forName(actualType.getTypeName()))) {
                //TODO:
                //throw new UnsupportedOperationException("List of components is not supported yet!");

                Object listToWrap = new ArrayList<>();
                if (method.isDefault()) {
                    listToWrap = invokeDefaultMethodImpl(proxy, method, args);
                }
                if (annotationProcessor == null) {
                    annotationProcessor = getSettings().getAnnotationProcessors().get(WrappedComponent.class);
                }
                ListWrappedBlockAnnotationProcessor listBlockAnnotationProcessor = new ListWrappedBlockAnnotationProcessor(null, annotationProcessor, (List<Object>) listToWrap);
                listBlockAnnotationProcessor.setValuesFromAnnotation(null, listToWrap, method, args);
                return listToWrap;

            }
            Object listToWrap = new ArrayList<>();
            if (method.isDefault()) {
                listToWrap = invokeDefaultMethodImpl(proxy, method, args);
            }
            ListElementAnnotationProcessor listElementProcessorWrapper = new ListElementAnnotationProcessor(null, annotationProcessor, (List<Object>) listToWrap);
            listElementProcessorWrapper.setValuesFromAnnotation(null, listToWrap, method, args);
            return listToWrap;
        }

        IAnnotationProcessor annotationProcessor = getSettings().getAnnotationProcessors().get(clazz);
        if (annotationProcessor != null) {
            //user wants to use abstract element
            if (clazz.isInterface()) {
                WrappedElement element = method.isDefault()
                        ? (WrappedElement) invokeDefaultMethodImpl(proxy, method, args)
                        : ClickableElement.class.getConstructor().newInstance();
                return annotationProcessor.setValuesFromAnnotation(null, element, method, args);
            } else {
                //Create exact implementation
                return annotationProcessor.setValuesFromAnnotation(null, clazz.getConstructor().newInstance(), method, args);
            }
        }

        //TODO: some annotation value to just invoke default impl without attempts to set annotations
        //no annotationProcessor - do nothing with method just invoke if it has default implementation
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
            return MethodHandles.lookup()
                    .findSpecial(method.getDeclaringClass(), method.getName(), MethodType.methodType(method.getReturnType(), method.getParameterTypes()), method.getDeclaringClass())
                    .bindTo(proxy).invokeWithArguments(args);
        }
    }
}
