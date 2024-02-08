package ua.foggger.tests.multiplatform;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.foggger.BaseTest;
import ua.foggger.common.IKnowInteractors;
import ua.foggger.common.IKnowPlatforms;
import ua.foggger.config.WrappedElements;
import ua.foggger.ui.page.MultiPlatformPage;
import ua.foggger.wrapper.element.impl.ClickableElement;
import ua.foggger.wrapper.interactor.Interactors;

import java.util.List;

public class MultiPlatformElementsTest extends BaseTest {

    private MultiPlatformPage page;

    @BeforeClass
    public void setUp() {
        super.setUp();
        page = WrappedElements.initPage(MultiPlatformPage.class);
    }

    @Test
    public void WebLocatorsShouldBePickedUp() {
        WrappedElements.config().setPlatform(IKnowPlatforms.WEB);
        Assert.assertEquals(getLocator(page.multiplatformElement()), By.linkText("someLinkText"));
        Assert.assertEquals(getName(page.multiplatformElement()), page.webName());

        Assert.assertEquals(getLocator(page.elementWithFewDigitPlaceHoldersAndOneParameter(3)), By.xpath("//div[text() = '3']|//span[contains(text(), '3')]"));
        Assert.assertEquals(getName(page.elementWithFewDigitPlaceHoldersAndOneParameter(3)), page.webName());

        Assert.assertEquals(getLocator(page.elementWithFewPlaceHoldersAndOneParameter("Web")), By.xpath("//div[text() = 'Web']|//span[contains(text(), 'Web')]"));
        Assert.assertEquals(getName(page.elementWithFewPlaceHoldersAndOneParameter("Web")), page.webName());

        Assert.assertEquals(getLocator(page.elementWithNamedParameter("16", "Web")), By.xpath("//li/a[contains(text(), 'Web 16')]"));
        Assert.assertEquals(getName(page.elementWithNamedParameter("16", "Web")), page.webName());

        Assert.assertEquals(page.listWithNamedParameter().size(), 1);
        ClickableElement element = page.listWithNamedParameter().get(0);
        Assert.assertNotNull(element);
        Assert.assertEquals(getLocator(element), By.xpath("//li/a[contains(text(), '${name}')]"));
        Assert.assertEquals(getInteraction(element), Interactors.getRegisteredInteractor(IKnowInteractors.UNTIL_CLICKABLE));
        Assert.assertEquals(getTimeout(element), 15);

        List<ClickableElement> elementList = page.listWithNamedParameter("Web");
        Assert.assertNotNull(elementList);
        Assert.assertEquals(elementList.size(), 1);
        Assert.assertEquals(getLocator(elementList.get(0)), By.xpath("//li/a[contains(text(), 'Web')]"));
        Assert.assertEquals(getName(elementList.get(0)), "Web element (list item number [0])");
        Assert.assertEquals(getInteraction(elementList.get(0)), Interactors.getRegisteredInteractor(IKnowInteractors.UNTIL_CLICKABLE));
        Assert.assertEquals(getTimeout(elementList.get(0)), 15);
    }

    @Test
    public void IOSLocatorsShouldBePickedUp() {
        WrappedElements.config().setPlatform(IKnowPlatforms.IOS);
        Assert.assertEquals(getLocator(page.multiplatformElement()), By.id("just_id"));
        Assert.assertEquals(getName(page.multiplatformElement()), page.iosName());

        Assert.assertEquals(getLocator(page.elementWithFewDigitPlaceHoldersAndOneParameter(3)), By.xpath("//div[text() = '3']|//span[contains(text(), '3')]"));
        Assert.assertEquals(getName(page.elementWithFewDigitPlaceHoldersAndOneParameter(3)), page.iosName());

        Assert.assertEquals(getLocator(page.elementWithFewPlaceHoldersAndOneParameter("IOS")), By.xpath("//ios[text() = 'IOS']|//ios2[contains(text(), 'IOS')]"));
        Assert.assertEquals(getName(page.elementWithFewPlaceHoldersAndOneParameter("IOS")), page.iosName());

        Assert.assertEquals(getLocator(page.elementWithNamedParameter("16", "IOS")), By.xpath("//li/a[contains(text(), 'IOS 16')]"));
        Assert.assertEquals(getName(page.elementWithNamedParameter("16", "IOS")), page.iosName());

        Assert.assertEquals(page.listWithNamedParameter().size(), 1);
        ClickableElement element = page.listWithNamedParameter().get(0);
        Assert.assertNotNull(element);
        Assert.assertEquals(getLocator(element), By.xpath("//li/a[contains(text(), '${name}')]"));
        Assert.assertEquals(getInteraction(element), Interactors.getRegisteredInteractor(IKnowInteractors.VERTICAL_SCROLL_UNTIL_VISIBLE));
        Assert.assertEquals(getTimeout(element), 20);

        List<ClickableElement> elementList = page.listWithNamedParameter("IOS");
        Assert.assertNotNull(elementList);
        Assert.assertEquals(elementList.size(), 1);
        Assert.assertEquals(getLocator(elementList.get(0)), By.xpath("//li/a[contains(text(), 'IOS')]"));
        Assert.assertEquals(getName(elementList.get(0)), "IOS element (list item number [0])");
        Assert.assertEquals(getInteraction(elementList.get(0)), Interactors.getRegisteredInteractor(IKnowInteractors.VERTICAL_SCROLL_UNTIL_VISIBLE));
        Assert.assertEquals(getTimeout(elementList.get(0)), 20);
    }

    @Test
    public void AndroidLocatorsShouldBePickedUp() {
        WrappedElements.config().setPlatform(IKnowPlatforms.ANDROID);
        Assert.assertEquals(getLocator(page.multiplatformElement()), By.xpath("//span/a"));
        Assert.assertEquals(getName(page.multiplatformElement()), page.androidName());

        Assert.assertEquals(getLocator(page.elementWithFewDigitPlaceHoldersAndOneParameter(3)), By.xpath("//android.TextView[text() = '3']|//android.Button[contains(text(), '3')]"));
        Assert.assertEquals(getName(page.elementWithFewDigitPlaceHoldersAndOneParameter(3)), page.androidName());

        Assert.assertEquals(getLocator(page.elementWithFewPlaceHoldersAndOneParameter("Android")), By.xpath("//android.TextView[text() = 'Android']|//android.Button[contains(text(), 'Android')]"));
        Assert.assertEquals(getName(page.elementWithFewPlaceHoldersAndOneParameter("Android")), page.androidName());

        Assert.assertEquals(getLocator(page.elementWithNamedParameter("16", "Android")), By.xpath("//android.TextView/*[contains(text(), 'Android 16')]"));
        Assert.assertEquals(getName(page.elementWithNamedParameter("16", "Android")), page.androidName());

        Assert.assertEquals(page.listWithNamedParameter().size(), 1);
        ClickableElement element = page.listWithNamedParameter().get(0);
        Assert.assertNotNull(element);
        Assert.assertEquals(getLocator(element), By.xpath("//android.TextView/*[contains(text(), '${name}')]"));
        Assert.assertEquals(getInteraction(element), Interactors.getRegisteredInteractor(IKnowInteractors.UNTIL_VISIBLE));
        Assert.assertEquals(getTimeout(element), 15);

        List<ClickableElement> elementList = page.listWithNamedParameter("Android");
        Assert.assertNotNull(elementList);
        Assert.assertEquals(elementList.size(), 1);
        Assert.assertEquals(getLocator(elementList.get(0)), By.xpath("//android.TextView/*[contains(text(), 'Android')]"));
        Assert.assertEquals(getName(elementList.get(0)), "Android element (list item number [0])");
        Assert.assertEquals(getInteraction(elementList.get(0)), Interactors.getRegisteredInteractor(IKnowInteractors.UNTIL_VISIBLE));
        Assert.assertEquals(getTimeout(elementList.get(0)), 15);
    }

    @AfterClass
    public void tearDown() {
        WrappedElements.config().setPlatform(IKnowPlatforms.WEB);
    }

}
