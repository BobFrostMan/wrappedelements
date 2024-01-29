package ua.foggger.core;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.foggger.BaseTest;
import ua.foggger.config.WrappedElements;
import ua.foggger.core.page.BreakingBadPage;
import ua.foggger.wrapper.interactor.Interactors;

/**
 * Check that element locator properly set for different usecases.
 */
public class InteractionParsingTest extends BaseTest {

    private BreakingBadPage page;

    @BeforeClass
    public void setUp() {
        super.setUp();
        page = WrappedElements.initPage(BreakingBadPage.class);
    }

    @Test
    public void shouldPickUpDefaultInteractor() {
        Assert.assertEquals(getInteraction(page.heisenberg()).name(), Interactors.STANDARD);
    }

    @Test
    public void shouldPickUpCustomInteractor() {
        Assert.assertEquals(getInteraction(page.combo()).name(), Interactors.UNTIL_CLICKABLE);
    }
}
