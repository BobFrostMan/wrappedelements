package ua.foggger.core;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.foggger.BaseTest;
import ua.foggger.config.WrappedElements;
import ua.foggger.core.page.BreakingBadPage;
import ua.foggger.wrapper.interactor.Interactors;

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
        Assert.assertEquals(getInteraction(page.combo()).name(), Interactors.UNTIL_CLICKABLE);
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
        Assert.assertNull(page.hobbits());
        Assert.assertNull(page.frodo());
    }

}
