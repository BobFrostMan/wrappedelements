package io.github.bobfrostman.ui.component;

import io.github.bobfrostman.annotation.WebComponent;
import io.github.bobfrostman.wrapper.block.WrappedComponent;

public interface EmptyHolderComponent extends WrappedComponent {


    @WebComponent(value = "//li")
    EmptyComponent innerComponent();
}
