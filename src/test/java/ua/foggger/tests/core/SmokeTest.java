package ua.foggger.tests.core;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.foggger.BaseTest;
import ua.foggger.config.WrappedElements;
import ua.foggger.ui.page.BreakingBadPage;
import ua.foggger.wrapper.interactor.Interactors;
import ua.foggger.wrapper.interactor.UntilVisibleInteractor;

import java.util.ArrayList;

public class SmokeTest extends BaseTest {

    private BreakingBadPage page;

    @BeforeClass
    public void setUp() {
        super.setUp();
        page = WrappedElements.initPage(BreakingBadPage.class);
    }

    @Test
    public void locatorIsSet() {
        Assert.assertNotNull(getLocator(page.heisenberg()));
        Assert.assertNotNull(getLocator(page.jessy()));
        Assert.assertNotNull(getLocator(page.randomPerson("rand")));
    }

    @Test
    public void nameIsSet() {
        Assert.assertNotNull(page.heisenberg().getName());
        Assert.assertNotNull(page.randomPerson("rand").getName());
        Assert.assertNotNull(page.jessy().getName());
    }

    @Test
    public void detectionIsSet() {
        Assert.assertNotNull(getInteraction(page.heisenberg()));
        Assert.assertNotNull(getInteraction(page.randomPerson("rand")));
        Assert.assertNotNull(getInteraction(page.jessy()));
        Assert.assertEquals(getInteraction(page.combo()).name(), VERTICAL_SCROLL_UNTIL_VISIBLE);
    }

    @Test
    public void timeoutIsSet() {
        Assert.assertNotNull(getTimeout(page.heisenberg()));
        Assert.assertNotNull(getTimeout(page.randomPerson("rand")));
        Assert.assertNotNull(getTimeout(page.jessy()));
        Assert.assertNotNull(getInteraction(page.combo()));
    }

    @Test
    public void notAnnotatedMethods() {
        Assert.assertEquals(page.hobbits(), new ArrayList<>());
        Assert.assertNull(page.frodo());
    }

    @Test
    public void defaultWaiterIsOverridden() {
        try {
            Assert.assertEquals(getInteraction(page.heisenberg()).getClass(), Interactors.getRegisteredInteractor(UNTIL_CLICKABLE).getClass());
            WrappedElements.config().defaultElementInteractor(new UntilVisibleInteractor());
            Assert.assertEquals(getInteraction(page.heisenberg()).getClass(), Interactors.getRegisteredInteractor(UNTIL_VISIBLE).getClass());
        } finally {
            WrappedElements.config().defaultElementInteractor(Interactors.getRegisteredInteractor(UNTIL_CLICKABLE));
        }
    }


}
