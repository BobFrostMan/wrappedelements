package ua.foggger.core.component;

import ua.foggger.annotation.Parameter;
import ua.foggger.annotation.WebElement;
import ua.foggger.wrapper.block.WrappedComponent;
import ua.foggger.wrapper.element.WrappedElement;

public interface EmptyComponent extends WrappedComponent {

    String solo = "solo";

    default String solo(){
        return solo;
    }

    @WebElement(value = "//li/a[contains(text(), '${first_name} ${ last_name }')]")
    WrappedElement elementWithNamedParameter(@Parameter("last_name") String lastName, @Parameter("first_name") String firstName);

}
