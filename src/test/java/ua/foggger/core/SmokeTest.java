package ua.foggger.core;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.foggger.elements.detection.Detections;
import ua.foggger.page.WrappedPage;

public class SmokeTest extends BaseTest {

    private IPageInterface page;

    @BeforeClass
    public void setUp() {
        page = WrappedPage.create(IPageInterface.class);
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
        Assert.assertEquals(getDetection(page.combo()).name(), Detections.UNTIL_CLICKABLE);
    }

    @Test
    public void timeoutIsSet() {
        Assert.assertNotNull(getTimeout(page.heisenberg()));
        Assert.assertNotNull(getTimeout(page.randomPerson("rand")));
        Assert.assertNotNull(getTimeout(page.jessy()));
        Assert.assertNotNull(getDetection(page.combo()));
    }

}
