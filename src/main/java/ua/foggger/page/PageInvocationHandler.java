package ua.foggger.page;

import org.openqa.selenium.By;
import ua.foggger.annotation.WebElement;
import ua.foggger.elements.ClickableElement;
import ua.foggger.elements.IClickableElement;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PageInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Method invoke, should return clickable element
        WebElement webElAnnotation = method.getAnnotation(WebElement.class);
        if (webElAnnotation != null) {
            Class<?> clazz = method.getReturnType();
            //TODO: Add list wrapper
            if (IClickableElement.class.isAssignableFrom(clazz)) {
                if (clazz.isInterface()) {
                    if (method.isDefault()) {
                        final float version = Float.parseFloat(System.getProperty("java.class.version"));
                        if (version <= 52) {
                            final Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class);
                            constructor.setAccessible(true);

                            final Class<?> clazz2 = method.getDeclaringClass();
                            return constructor.newInstance(clazz2)
                                    .in(clazz2)
                                    .unreflectSpecial(method, clazz2)
                                    .bindTo(proxy)
                                    .invokeWithArguments(args);
                        } else {
                            return MethodHandles.lookup()
                                    .findSpecial(
                                            method.getDeclaringClass(),
                                            method.getName(),
                                            MethodType.methodType(method.getReturnType(), new Class[0]),
                                            method.getDeclaringClass()
                                    ).bindTo(proxy)
                                    .invokeWithArguments(args);
                        }
                    } else {
                        // Create instance based on args
                        ClickableElement element = ClickableElement.class.getConstructor().newInstance();
                        String name = "".equals(webElAnnotation.name()) ? method.getName() : webElAnnotation.name();
                        element.setName(name);
                        //String locator = webElAnnotation.value();
                        //element.setLocator();
                        //TODO: form proper By locator based on
                        //TODO: need to set InvocationHandler here for fields
                        return (IClickableElement) element;
                    }
                } else {
                    IClickableElement clickableElement = (IClickableElement) clazz.getConstructor().newInstance();
                    String name = "".equals(webElAnnotation.name()) ? method.getName() : webElAnnotation.name();
                    clickableElement.setName(name);
                    //String locator = webElAnnotation.value();
                    //element.setLocator();
                    //TODO: form proper By locator based on
                    //TODO: need to set InvocationHandler here for fields
                    return (IClickableElement) clickableElement;
                }
            }
        }
        // Temp null return
        return null;
    }

    private By locatorFromString(String locator) {
        if (isXPath(locator)) {
            return new By.ByXPath(locator);
        }
        if (isCSSSelector(locator)) {
            return new By.ByCssSelector(locator);
        }
        if (isLinkText(locator)) {
            return new By.ByLinkText(locator);
        }
        if (isID(locator)) {
            return new By.ById(locator);
        }
        return new By.ByTagName(locator);
    }

    private static boolean isXPath(String locator) {
        return locator.matches("^//(.+)|^\\./(.+)");
    }

    private static boolean isCSSSelector(String locator) {
        return locator.matches("^([a-zA-Z]+)?(#[\\w-]+)?(\\.[\\w-]+)*$");
    }

    //TODO: link text should be specified directly
    private static boolean isLinkText(String locator) {
        return locator.matches("^link=[\\s\\S]+$");
    }

    //TODO: id should be specified directly
    private static boolean isID(String locator) {
        return locator.matches("^id=[\\w-]+$");
    }

}
