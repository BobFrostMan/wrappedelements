package ua.foggger.core.page;

import ua.foggger.annotation.*;
import ua.foggger.core.component.EmptyHolderComponent;
import ua.foggger.wrapper.element.WrappedElement;
import ua.foggger.wrapper.element.impl.ClickableElement;
import ua.foggger.wrapper.page.IPage;

import java.util.ArrayList;
import java.util.List;

public interface MultiPlatformPage extends IPage {

    @WebElement(value = "linkText= someLinkText", name = "Web element")
    @AndroidElement(value = "//span/a", name = "Android element")
    @IOSElement(value = "id=just_id", name = "IOS element")
    ClickableElement multiplatformElement();

    @WebElement(value = "//div[text() = '%s']|//span[contains(text(), '%s')]", name = "Web element")
    @AndroidElement(value = "//android.TextView[text() = '%s']|//android.Button[contains(text(), '%s')]", name = "Android element")
    @IOSElement(value = "//ios[text() = '%s']|//ios2[contains(text(), '%s')]", name = "IOS element")
    WrappedElement elementWithFewPlaceHoldersAndOneParameter(String text);

    @WebElement(value = "//div[text() = '%d']|//span[contains(text(), '%d')]", name = "Web element")
    @AndroidElement(value = "//android.TextView[text() = '%d']|//android.Button[contains(text(), '%d')]", name = "Android element")
    @IOSElement(value = "//div[text() = '%d']|//span[contains(text(), '%d')]", name = "IOS element")
    WrappedElement elementWithFewDigitPlaceHoldersAndOneParameter(int number);

    @WebElement(value = "//li/a[contains(text(), '${first_name} ${ last_name }')]", name = "Web element")
    @AndroidElement(value = "//android.TextView/*[contains(text(), '${first_name} ${ last_name }')]", name = "Android element")
    @IOSElement(value = "//li/a[contains(text(), '${first_name} ${ last_name }')]", name = "IOS element")
    WrappedElement elementWithNamedParameter(@Parameter("last_name") String lastName, @Parameter("first_name") String firstName);

    @WebElement(value = "//li/a[contains(text(), '${name}')]", name = "Web element", timeout = 15)
    @AndroidElement(value = "//android.TextView/*[contains(text(), '${name}')]", name = "Android element", waitUntil = UNTIL_VISIBLE, timeout = 15)
    @IOSElement(value = "//li/a[contains(text(), '${name}')]", name = "IOS element", waitUntil = VERTICAL_SCROLL_UNTIL_VISIBLE, timeout = 20)
    default List<ClickableElement> listWithNamedParameter(@Parameter("name") String text) {
        ClickableElement element = new ClickableElement();
        List<ClickableElement> list = new ArrayList<>();
        list.add(element);
        return list;
    }

    @WebElement(value = "//li/a[contains(text(), '${name}')]", name = "Web element", timeout = 15)
    @AndroidElement(value = "//android.TextView/*[contains(text(), '${name}')]", name = "Android element", waitUntil = UNTIL_VISIBLE, timeout = 15)
    @IOSElement(value = "//li/a[contains(text(), '${name}')]", name = "IOS element", waitUntil = VERTICAL_SCROLL_UNTIL_VISIBLE, timeout = 20)
    default List<ClickableElement> listWithNamedParameter() {
        ClickableElement element = new ClickableElement();
        List<ClickableElement> list = new ArrayList<>();
        list.add(element);
        return list;
    }

    default String androidName() {
        return "Android element";
    }

    default String iosName() {
        return "IOS element";
    }

    default String webName() {
        return "Web element";
    }

    @WebComponent("//webcomponent")
    @AndroidComponent("//android.TextView")
    @IOSComponent("ios")
    EmptyHolderComponent holder();

}
