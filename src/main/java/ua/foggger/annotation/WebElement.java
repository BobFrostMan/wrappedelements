package ua.foggger.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * WebElement annotation that signalise that method should be wrapped with element.
 * Usage is next:
 * <pre>{@code
 *      public class SomePage implements IPage {
 *          @WebElement("/button", name = "My awesome button")
 *          IClickableElement awesomeButton();
 *      }
 * }</pre>
 * As result awesomeButton() will return found selenium web element.
 */
//TODO: should this support class target?
//TODO: should this support field target?
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WebElement {

    /**
     * Locator value as string. can be tag name, id, css, xpath.
     * Can be string pattern like "//label[contains(text(), '%s')]".
     * To handle this case you should define parameters in method, in this case, parameter value will be passed to locator
     *
     * @return locator value as String
     */
    String value();

    /**
     * Element name (mainly for logging purposes).
     *
     * @return element name as String
     */
    String name() default "";

}
