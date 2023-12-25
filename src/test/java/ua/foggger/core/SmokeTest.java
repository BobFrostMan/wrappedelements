package ua.foggger.core;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.foggger.config.WrappedElements;
import ua.foggger.core.page.IPageInterface;
import ua.foggger.elements.interactor.Interactors;

public class SmokeTest extends BaseTest {

    private IPageInterface page;

    @BeforeClass
    public void setUp() {
        super.setUp();
        page = WrappedElements.initPage(IPageInterface.class);
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
        Assert.assertNotNull(getDetection(page.heisenberg()));
        Assert.assertNotNull(getDetection(page.randomPerson("rand")));
        Assert.assertNotNull(getDetection(page.jessy()));
        Assert.assertEquals(getDetection(page.combo()).name(), Interactors.UNTIL_CLICKABLE);
    }

    @Test
    public void timeoutIsSet() {
        Assert.assertNotNull(getTimeout(page.heisenberg()));
        Assert.assertNotNull(getTimeout(page.randomPerson("rand")));
        Assert.assertNotNull(getTimeout(page.jessy()));
        Assert.assertNotNull(getDetection(page.combo()));
    }

}
