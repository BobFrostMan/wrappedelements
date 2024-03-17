package ua.foggger.wrapper.interactor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Class contains default element interaction behaviors used for @WebElement annotation handling.
 *
 * @see ua.foggger.annotation.WebElement
 */
public final class Interactors {

    private static final Map<String, IElementInteractor> interactorsMap = new HashMap<>();

    private Interactors() {

    }

    /**
     * Register additional behavior
     *
     * @param elementInteractor object that defines element interaction behavior
     */
    public static void registerInteractor(IElementInteractor elementInteractor) {
        interactorsMap.put(elementInteractor.name(), elementInteractor);
    }

    /**
     * Returns registered Interactor
     *
     * @return elementInteractor object that defines element interaction behavior
     */
    public static IElementInteractor getRegisteredInteractor(String elementInteractorName) {
        return interactorsMap.get(elementInteractorName);
    }

    /**
     * Returns unmodifiable copy of all registered interactors map where key is interactor's name, and value is IElementInteractor object itself
     */
    public static Map<String, IElementInteractor> getInteractorsMap() {
        Map<String, IElementInteractor> map = Collections.unmodifiableMap(interactorsMap);
        return map;
    }

}
