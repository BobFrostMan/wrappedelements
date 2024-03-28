package io.github.bobfrostman.wrapper.block;

import io.github.bobfrostman.wrapper.page.ElementNameResolver;
import io.github.bobfrostman.wrapper.page.LocatorResolver;

public class WrappedBlockAnnotationProcessor extends AbstractBlockProcessor {


    private final LocatorResolver locatorResolver;
    private final ElementNameResolver elementNameResolver;

    public WrappedBlockAnnotationProcessor() {
        locatorResolver = new LocatorResolver();
        elementNameResolver = new ElementNameResolver();
    }
}
