package ua.foggger.wrapper.interactor;

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

    public static IElementInteractor getRegisteredInteractor(String elementInteractorName) {
        return interactorsMap.get(elementInteractorName);
    }

}
