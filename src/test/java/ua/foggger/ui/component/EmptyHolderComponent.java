package ua.foggger.ui.component;

import ua.foggger.annotation.WebComponent;
import ua.foggger.wrapper.block.WrappedComponent;

public interface EmptyHolderComponent extends WrappedComponent {


    @WebComponent(value = "//li")
    EmptyComponent innerComponent();
}
