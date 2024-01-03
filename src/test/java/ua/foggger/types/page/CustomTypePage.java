package ua.foggger.types.page;

import ua.foggger.page.IPage;
import ua.foggger.types.annotations.CustomEmptyElement;
import ua.foggger.types.element.EmptyElement;

public interface CustomTypePage extends IPage {

    @CustomEmptyElement
    EmptyElement emptyElement();

    @CustomEmptyElement
    EmptyElement emptyElementWithParameter(String text);

    @CustomEmptyElement
    default EmptyElement emptyElementWithDefaultImpl() {
        return new EmptyElement();
    }

}
