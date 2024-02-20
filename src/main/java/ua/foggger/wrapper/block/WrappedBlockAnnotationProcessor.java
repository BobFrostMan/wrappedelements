package ua.foggger.wrapper.block;

import ua.foggger.wrapper.page.ElementNameResolver;
import ua.foggger.wrapper.page.LocatorResolver;

public class WrappedBlockAnnotationProcessor extends AbstractBlockProcessor {


    private final LocatorResolver locatorResolver;
    private final ElementNameResolver elementNameResolver;

    public WrappedBlockAnnotationProcessor() {
        locatorResolver = new LocatorResolver();
        elementNameResolver = new ElementNameResolver();
    }
}
