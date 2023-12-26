package ua.foggger.page;

import org.openqa.selenium.By;
import ua.foggger.annotation.WebElement;
import ua.foggger.elements.ClickableElement;
import ua.foggger.elements.IClickableElement;
import ua.foggger.elements.interactor.Interactors;
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
public class PageInvocationHandler implements InvocationHandler, IHaveReflectionAccess {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //TODO: Will we have different annotations for different element types? Like dropdowns or something?
        //TODO: How to avoid new objects creation on each method invocation? Shouldn't be so much objects
        WebElement webElAnnotation = method.getAnnotation(WebElement.class);
        if (webElAnnotation != null) {
            Class<?> clazz = method.getReturnType();
            if (IClickableElement.class.isAssignableFrom(clazz)) {
                if (clazz.isInterface()) {
                    if (method.isDefault()) {
                        //TODO: What to do with default implementations?
                        IClickableElement element = (IClickableElement) invokeDefaultMethodImpl(proxy, method, args);
                        return setValuesFromAnnotations(element, method, webElAnnotation, args);
                    } else {
                        //Create default exact implementation
                        IClickableElement element = ClickableElement.class.getConstructor().newInstance();
                        return setValuesFromAnnotations(element, method, webElAnnotation, args);
                    }
                } else {
                    //Create exact implementation
                    IClickableElement element = (IClickableElement) clazz.getConstructor().newInstance();
                    return setValuesFromAnnotations(element, method, webElAnnotation, args);
                }
            }
            if (List.class.isAssignableFrom(clazz)) {
                //TODO: Add list wrapper
            }
        }
        return invokeDefaultMethodImpl(proxy, method, args);
    }

    //TODO: How to read parameter names and replace it
    private String resolvePlaceholders(String target, Object[] args) {
        if (args == null) {
            return target;
        }
        if (args.length == 1 && target.contains("%")) {
            if (target.contains("%s")) {
                return target.replaceAll("%s", String.valueOf(args[0]));
            }
            if (target.contains("%d")) {
                return target.replaceAll("%d", String.valueOf(args[0]));
            }
        }
        //TODO: handle named parameters from annotation here
        return target;
    }

    private By formLocator(String locator, Object[] args) {
        locator = resolvePlaceholders(locator, args);
        if (isLocatorTypeDefined(locator)) {
            return getDefinedTypeLocator(locator);
        }
        if (isXPath(locator)) {
            return new By.ByXPath(locator);
        }
        if (isCSSSelector(locator)) {
            return new By.ByCssSelector(locator);
        }
        return new By.ByXPath(String.format("//%s|//*[@id='%s']|//*[@name='%s']|//*[@class='%s'])", locator, locator, locator, locator));
    }

    private static boolean isXPath(String locator) {
        return locator.matches("^//(.+)|^\\./(.+)|^(\\(\\/\\/.+)");
    }

    private static boolean isCSSSelector(String locator) {
        return locator.startsWith(".") || locator.startsWith("#") || locator.contains(">");
    }

    private static boolean isLocatorTypeDefined(String locator) {
        return locator.startsWith("id=") ||
                locator.startsWith("tag=") ||
                locator.startsWith("name=") ||
                locator.startsWith("class=") ||
                locator.startsWith("xpath=") ||
                locator.startsWith("css=") ||
                locator.startsWith("text=") ||
                locator.startsWith("linkText=");
    }

    private static By getDefinedTypeLocator(String locator) {
        if (locator.startsWith("id=")) {
            return By.id(locator.replace("id=", "").trim());
        }
        if (locator.startsWith("tag=")) {
            return By.tagName(locator.replace("tag=", "").trim());
        }
        if (locator.startsWith("name=")) {
            return By.name(locator.replace("name=", "").trim());
        }
        if (locator.startsWith("class=")) {
            return By.className(locator.replace("class=", "").trim());
        }
        if (locator.startsWith("xpath=")) {
            return By.xpath(locator.replace("xpath=", "").trim());
        }
        if (locator.startsWith("css=")) {
            return By.cssSelector(locator.replace("css=", "").trim());
        }
        return new By.ByLinkText(locator.replace("linkText=", "").trim());
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
    }

    private <T> Object setValuesFromAnnotations(T element, Method method, WebElement webElementAnnotation, Object[] args) {
        String name = "".equals(webElementAnnotation.name()) ? method.getName() : webElementAnnotation.name();
        setFieldValue(element, "name", name);
        setFieldValue(element, "detection", Interactors.getRegisteredDetection(webElementAnnotation.waitUntil()));
        setFieldValue(element, "locator", formLocator(webElementAnnotation.value(), args));
        setFieldValue(element, "timeoutInSeconds", webElementAnnotation.during());
        return element;
    }
}
