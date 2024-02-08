package ua.foggger.tests.multiplatform;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.foggger.BaseTest;
import ua.foggger.common.IKnowPlatforms;
import ua.foggger.config.WrappedElements;
import ua.foggger.ui.page.MultiPlatformPage;

public class MultiPlatformComponentsTest extends BaseTest {

    private MultiPlatformPage page;

    @BeforeClass
    public void setUp() {
        super.setUp();
        page = WrappedElements.initPage(MultiPlatformPage.class);
    }

    @Test
    public void WebComponentLocatorsShouldBePickedUp() {
        WrappedElements.config().setPlatform(IKnowPlatforms.WEB);
        Assert.assertEquals(getLocator(page.holder().innerComponent().elementWithNamedParameter("web", "31")),
                new ByChained(new ByChained(By.xpath("//webcomponent"), By.xpath("//li")), By.xpath("//li/a[contains(text(), '31 web')]")));
    }

    @Test
    public void IOSElementsInComponentShouldBeNullIfNoPlatformAnnotation() {
        WrappedElements.config().setPlatform(IKnowPlatforms.IOS);
        Assert.assertNull(page.holder().innerComponent().elementWithNamedParameter("IOS", "31"));
    }

    @Test
    public void AndroidElementsInComponentShouldBeNullIfNoPlatformAnnotation() {
        WrappedElements.config().setPlatform(IKnowPlatforms.ANDROID);
        Assert.assertNull(page.holder().innerComponent().elementWithNamedParameter("Android", "31"));
    }

    @AfterClass
    public void tearDown() {
        WrappedElements.config().setPlatform(IKnowPlatforms.WEB);
    }

}
