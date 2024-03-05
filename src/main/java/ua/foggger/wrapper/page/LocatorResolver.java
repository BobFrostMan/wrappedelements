package ua.foggger.wrapper.page;

import org.openqa.selenium.By;
import ua.foggger.wrapper.locator.converter.XPathConverter;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Main purpose is to resolve locator with placeholders to proper By object
 */
public class LocatorResolver {

    /**
     * Resolves locatorString as By object for specified method using arguments to resolve placeholders in locator string.
     * For example:
     * <pre>
     *  @WebElement(value = "//div[text()='%s']|//span[contains(text(), '%s')]")
     *  ClickableElement elementWithFewPlaceHoldersAndOneParameter(String text);
     * </pre>
     * Invocation:
     * <pre>
     *     page.elementWithFewPlaceHoldersAndOneParameter("someText")
     * </pre>
     * Output value will be object:
     * By.xpath("//div[text()='someText']|//span[contains(text(), 'someText')])
     * <p>
     * Locator type resolution is actually automatic, but you can explicitly specify locator type using text prefix in @WebElement annotation.
     * Available prefixes:
     * "id="
     * "tag="
     * "name="
     * "class="
     * "xpath="
     * "css="
     * "linkText="
     *
     * @param locatorString web element locator as string
     * @param method        method that is used to define web element
     * @param args          arguments that of the method invocation
     * @return
     */
    public By resolveLocator(String locatorString, Method method, Object[] args) {
        String resolvedStringLocator = resolvePlaceholders(locatorString, method, args);
        return defineLocator(resolvedStringLocator);
    }

    public By resolveLocatorAsXpath(String locatorString, Method method, Object[] args) {
        String resolvedStringLocator = resolvePlaceholders(locatorString, method, args);
        return By.xpath(new XPathConverter().convert(defineLocator(resolvedStringLocator)));
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

    private By defineLocator(String locator) {
        if (isLocatorTypeDefined(locator)) {
            return getDefinedTypeLocator(locator);
        }
        if (isXPath(locator)) {
            return new By.ByXPath(locator);
        }
        if (isCSSSelector(locator)) {
            return new By.ByCssSelector(locator);
        }
        return new By.ByXPath(String.format("(//%s|//*[@id='%s']|//*[@name='%s']|//*[@class='%s'])", locator, locator, locator, locator));
    }

    private boolean isXPath(String locator) {
        return locator.matches("^//(.+)|^\\./(.+)|^(\\(\\/\\/.+)");
    }

    private boolean isCSSSelector(String locator) {
        return locator.startsWith(".") || locator.startsWith("#") || locator.contains(">");
    }

    private boolean isLocatorTypeDefined(String locator) {
        return locator.startsWith("id=") ||
                locator.startsWith("tag=") ||
                locator.startsWith("name=") ||
                locator.startsWith("class=") ||
                locator.startsWith("xpath=") ||
                locator.startsWith("css=") ||
                locator.startsWith("text=") ||
                locator.startsWith("linkText=");
    }

    private By getDefinedTypeLocator(String locator) {
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

}
