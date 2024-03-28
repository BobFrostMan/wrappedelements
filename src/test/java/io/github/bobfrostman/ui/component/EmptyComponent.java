package io.github.bobfrostman.ui.component;

import io.github.bobfrostman.annotation.Parameter;
import io.github.bobfrostman.annotation.WebElement;
import io.github.bobfrostman.wrapper.block.WrappedComponent;
import io.github.bobfrostman.wrapper.element.WrappedElement;

public interface EmptyComponent extends WrappedComponent {

    String solo = "solo";

    default String solo() {
        return solo;
    }

    @WebElement(value = "//li/a[contains(text(), '${first_name} ${ last_name }')]")
    WrappedElement elementWithNamedParameter(@Parameter("last_name") String lastName, @Parameter("first_name") String firstName);

}
