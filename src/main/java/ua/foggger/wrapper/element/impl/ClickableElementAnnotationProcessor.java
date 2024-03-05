package ua.foggger.wrapper.element.impl;

import ua.foggger.annotation.AnnotatedMethodMeta;

public class ClickableElementAnnotationProcessor extends AbstractElementProcessor {

    public ClickableElementAnnotationProcessor() {
        super();
    }

    @SuppressWarnings("unchecked")
    public <T> T resolveElement(final AnnotatedMethodMeta annotatedMethodInfo, T element) {
        ClickableElement clickableElement = (ClickableElement) element;
        clickableElement.setName(annotatedMethodInfo.getResolvedName());
        clickableElement.setLocator(annotatedMethodInfo.getResolvedLocator());
        clickableElement.setInteractor(annotatedMethodInfo.getResolvedInteractor());
        clickableElement.setTimeoutInSeconds(annotatedMethodInfo.getTimeout());
        return (T) clickableElement;
    }
}
