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
import java.lang.reflect.Parameter;
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

    private String resolvePlaceholders(String target, Method method, Object[] args) {
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

        for (int i = 0; i < method.getParameters().length; i++) {
            Parameter parameter = method.getParameters()[i];
            ua.foggger.annotation.Parameter annotation = parameter.getAnnotation(ua.foggger.annotation.Parameter.class);
            target = target.replaceAll("\\$\\{\\s?" + annotation.value() + "\\s?\\}", String.valueOf(args[i]));
        }
        return target;
    }

    private By formLocator(String locator, Method method, Object[] args) {
        locator = resolvePlaceholders(locator, method, args);
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
        String name = "".equals(webElementAnnotation.name()) ? defaultElementName(method) : webElementAnnotation.name();
        setFieldValue(element, "name", name);
        setFieldValue(element, "detection", Interactors.getRegisteredDetection(webElementAnnotation.waitUntil()));
        setFieldValue(element, "locator", formLocator(webElementAnnotation.value(), method, args));
        setFieldValue(element, "timeoutInSeconds", webElementAnnotation.during());
        return element;
    }

    private static String prettify(String s) {
        String str = s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
        String[] words = str.split(" ");
        StringBuilder builder = new StringBuilder();
        String capitalized = words[0].substring(0, 1).toUpperCase() + words[0].substring(1);
        builder.append(capitalized);
        for (int i = 1; i < words.length; i++) {
            builder.append(" ");
            builder.append(words[i].toLowerCase());
        }
        return builder.toString();
    }

    private static <T> String defaultElementName(Method method) {
        String className = method.getDeclaringClass().getSimpleName();
        if (className.startsWith("I")) {
            className = className.substring(1);
        }
        String page = prettify(className);
        String elementName = prettify(method.getName());
        return elementName + " on " + page;
    }
}
