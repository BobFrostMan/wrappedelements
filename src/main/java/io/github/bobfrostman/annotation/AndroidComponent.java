package io.github.bobfrostman.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * WebComponent annotation that signalise that method should be wrapped with element.
 * Usage is next:
 * <pre>{@code
 *      public class SomePage implements IPage {
 *          @AndroidComponent(value = "//div[contains(@class, 'some_class')]", name = "My awesome button")
 *          WrappedBlock awesomeButton();
 *      }
 * }</pre>
 * As result awesomeButton() will return found selenium web element.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AndroidComponent {

    /**
     * Locator value as string. can be tag name, id, css, xpath.
     * It will be used as root locator for child elements automatically.
     * Can be string pattern like "//label[contains(text(), '%s')]".
     * To handle this case you should define parameters in method, in this case, parameter value will be passed to locator
     *
     * @return locator value as String
     */
    String value();

    /**
     * WebComponent name (mainly for logging purposes).
     *
     * @return component name as String
     */
    String name() default "";
}
