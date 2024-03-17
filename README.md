# wrappedelements

A convenient framework wrapper for pages and selenium webelements, that reduces usual waiter, and initialization routine. It will help you to get rid of boilerplate code in UI test automation frameworks.

Inspired by [Burning wave](https://www.burningwave.org/getstarted/).

Special thanks to [Tyrrz](https://github.com/Tyrrrz) for Terms of use, and [Sam Rosental](https://github.com/sam-rosenthal) for opensource [css to xpath converter](https://github.com/sam-rosenthal/java-cssSelector-to-xpath).

[![Status](https://img.shields.io/badge/status-active-47c219.svg)](https://github.com/Tyrrrz/.github/blob/master/docs/project-status.md)
[![Made in Ukraine](https://img.shields.io/badge/made_in-ukraine-ffd700.svg?labelColor=0057b7)](https://tyrrrz.me/ukraine)
[![Fuck Russia](https://img.shields.io/badge/fuck-russia-e4181c.svg?labelColor=000000)](https://twitter.com/tyrrrz/status/1495972128977571848)

## Terms of use
By using this project or its source code, for any purpose and in any shape or form, you grant your **implicit agreement** to all the following statements:

- You **condemn Russia and its military aggression against Ukraine**
- You **recognize that Russia is an occupant that unlawfully invaded a sovereign state**
- You **support Ukraine's territorial integrity, including its claims over temporarily occupied territories of Crimea and Donbas**
- You **reject false narratives perpetuated by Russian state propaganda**
Glory to Ukraine!

## Features
- Clear and simple Page object classes
- Easy element interaction condition definition on Page object level 
- Increased element reusability for different platforms (Web, iOS, Android) 
- Easy element waiters and timeouts configuration on Page object level
- Dynamic locators support 
- No element synchronization code on test level as result clear code on test level
- Extendable custom element configuration and customization system 
- Automatic web driver recreation if it's dead or quit
- Your own custom blocks will be highly reusable

## Setup
### Maven
Add wrappedelements and selenium java dependency to your pom file:
```maven
<dependency>
    <groupId>ua.foggger</groupId>
    <artifactId>wrappedelements</artifactId>
    <version>${wrappedelements.version}</version>
</dependency>
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>${selenium.version}</version>
</dependency>
```
## Getting started
1. Add wrappedelements dependencies to your project (see Setup section)
2. Create your own pageobject entities as interfaces (extend the IPage interface)
```java
public interface LoginPage extends IPage {

    @WebElement(value = "//form//input")
    ClickableElement loginInput();

    @WebElement(value = "(//form//input)[2]")
    ClickableElement passwordInput();

    @WebElement(value = "//input[@type='submit']")
    ClickableElement loginButton();

} 
```
3. Register selenium webdriver instance to be used by wrappedelements framework.
To do that provide a driver creation function, and init your page with wrappedelements:
```java
    @BeforeClass
    public void setUp() {
        WrappedElements.config()
                .driverCreator(() -> {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.setImplicitWaitTimeout(Duration.ofSeconds(10));
                    return new ChromeDriver();
                });
        loginPage = WrappedElements.initPage(LoginPage.class);
    }
```
4. Enjoy writing your clean tests without any explicit waits!
```java
public class TestNGSmokeTest {

    private LoginPage loginPage;

    @BeforeClass
    public void setUp() {
        WrappedElements.config()
                .driverCreator(() -> {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.setImplicitWaitTimeout(Duration.ofSeconds(10));
                    return new ChromeDriver();
                });

        loginPage = WrappedElements.initPage(LoginPage.class);
    }

    @BeforeMethod
    public void beforeMethod() {
        loginPage.driver().get("https://www.saucedemo.com/");
    }

    @Test
    public void simpleUIInteractionTest() {
        loginPage.loginInput().sendKeys("standard_user");
        loginPage.passwordInput().sendKeys("secret_sauce");
        loginPage.loginButton().click();

        Assert.assertEquals(loginPage.driver().getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
    }

    @AfterMethod
    public void tearDown() {
        if (loginPage.driver() != null) {
            loginPage.driver().quit();
        }
    }
}
```

## Features overview

## Clear and simple Page object classes
Imagine page object model description as an interface! 
That makes page object classes clear and avoids additional code.
```java
import ua.foggger.annotation.WebElement;
import ua.foggger.element.clickable.ClickableElement;
import ua.foggger.page.IPage;

public interface LoginPage extends IPage {

    @WebElement(value = "//form//input")
    ClickableElement loginInput();

    @WebElement(value = "(//form//input)[2]")
    ClickableElement passwordInput();

    @WebElement(value = "//input[@type='submit']")
    ClickableElement loginButton();

}
```
#### Question: How can I create instance of my page object interface?
Quite simple:
```java
LoginPage loginPage = WrappedElements.initPage(LoginPage.class);
```

## No waiters on test level that leaves code clean and clear
All element's interaction waiters encapsulated on page object layer. No additional waiters in element interaction code, - enjoy the usage:)
```java  
@Test
public void simpleUIInteractionTest() {
    loginPage.loginInput().sendKeys("standard_user");
    loginPage.passwordInput().sendKeys("secret_sauce");
    loginPage.loginButton().click();
    ...
}
```
By default wrapped elements will wait for all element become clickable before any active interactions (click, sendKeys, etc).
It is possible because of 'Interactor' mechanism, that allows you to configure waits for elements directly on page object layer. 

#### Question: How can I set some different waiter before elements interaction?
Feel free to use 'waitUntil' value of @WebElement annotation.

For instance: 
You need to wait for element to become just visible (and may not be clickable) on some ui element called 'heisenberg'.
```java
@WebElement(value = "//div[@class='heisenberg']", waitUntil = UNTIL_VISIBLE, timeout = 15)
ClickableElement heisenberg();
```
Any time you perform any kind of physical interaction (click, sendKeys, etc.), on heisenberg() element, it will wait for heisenberg() to become visible first, with 15 seconds timeout.

Available values are next:
- IMMEDIATELY (no waiters applied)
- UNTIL_VISIBLE (waits for element to be displayed before physical interaction)
- UNTIL_CLICKABLE (waits for element to be displayed and be enabled)
- VERTICAL_SCROLL_UNTIL_VISIBLE (scrolls down until the element will be visible in the view port)

#### Question: What if I want to set some custom waiter for all elements by default?
To do that:
1. Implement your own Interactor class by implementing IElementInteractor interface and register it in WrappedElements framework:
```java
import ua.foggger.wrapper.interactor.IElementInteractor;

public class WaitUntilMyCustomConditionsMet implements IElementInteractor {

    @Override
    public String name() {
        return "custom_wait";
    }
    
    Override
    public boolean isReadyForInteraction(String methodName, By by, WebDriver webDriver) {
        //my interaction logic that says if method name is "click" or "sendKeys" or anything else, then wait for my custom conditions
    }
}
```
2. Register you interactor in WrappedElements class before your tests run with code:
```java
    WrappedElements.config().defaultElementInteractor(new WaitUntilMyCustomConditionsMet());
```
That's it. Your cutom waiter will be applied by default for all elements that doesn't have 'waitUntil' value specified explicitly.

#### Question: What if I want to set some custom waiter for element?
You can create you own waiter and use it with wrappedelements.
It is possible by implementing your IElementInteractor interface.
Interactor - is the entity that describes the behavior with web element.

To do that: 
1. Implement your own Interactor class by implementing IElementInteractor interface and register it in WrappedElements framework:
```java
import ua.foggger.wrapper.interactor.IElementInteractor;

public class WaitUntilMyCustomConditionsMet implements IElementInteractor {

    @Override
    public String name() {
        return "custom_wait";
    }
    
    Override
    public boolean isReadyForInteraction(String methodName, By by, WebDriver webDriver) {
        //my interaction logic that says if method name is "click" or "sendKeys" or anything else, then wait for my custom conditions
    }
}
```
2. Register you interactor in WrappedElements class before your tests run with code:
```java
    WrappedElements.config().registerInteractor(new WaitUntilMyCustomConditionsMet());
```
3. Now you can specify it in page object class using @WebElement 'waitUntil' value.
```java
    @WebElement(value = "//div[@class='heisenberg']", waitUntil = "custom_wait")
    ClickableElement heisenberg();
```

## Easy element's locator definition for different platforms
It's quite common case - run ui mobile tests on different platforms. 
Enjoy running same tests with different locators for different platforms without changing test logic:

Page example:
```java
    @WebElement(value = "linkText=someLinkText", name = "Web element")
    @AndroidElement(value = "//span/a", name = "Android element")
    @IOSElement(value = "just_some_ios_element_id", name = "IOS element")
    ClickableElement multiplatformElement();
```
To run tests on android just invoke:
```java
    WrappedElements.config().setPlatform(IKnowPlatforms.ANDROID);
```
And element locators will be picked up from @AndroidElement annotation. 
Specify IOS platform, and framework will run tests with locators in  @IOSElement

## Dynamic locators support
WrappedElements supports dynamical element locators, we know that sometimes you need some specific element that may be found only dynamically.

Declaration example:
```java
    @WebElement(value = "//div[text() = '%s']|//span[contains(text(), '%s')]")
    ClickableElement elementWithText(String text);
    
    @WebElement(value = "//div[text() = '%d']|//span[contains(text(), '%d')]")
    ClickableElement elementWithDigits(int number);
    
    @WebElement(value = "//li/a[contains(text(), '${first_name} ${ last_name }')]")
    ClickableElement elementWithNamedParameter(@Parameter("last_name") String lastName, @Parameter("first_name") String firstName);
```
Usage example:
```java
String name = page.elementWithDigits(0).getText();
String lastName = page.elementWithDigits(1).getText();
String someOtherDynamicalText = page.elementWithNamedParameter(lastName, name).getText();
elementWithText(someOtherDynamicalText).click();
```
## Components design
It's quite common case when you have some component on the page that duplicated on different page, and may contain elements that duplicates from page to page.
For instance: in your header you have a basket button that contains from basket element button and products count label.

To create BasketButton component you need:
1. Extend WrappedComponent interface:
```java
public interface BasketButton extends WrappedComponent {

    @WebElement(value = ".//span[@class=\"shopping_cart_badge\"]", waitUntil = IMMEDIATELY)
    ClickableElement notificationsCountLabel();

    @WebElement(value = ".//a[@class=\"shopping_cart_link\"]", waitUntil = VERTICAL_SCROLL_UNTIL_VISIBLE)
    ClickableElement basketButton();

}
```
2. Specify locator for your basket button on the page use @WebComponent annotation
```java
public interface Header extends WrappedComponent {

    @WebComponent("//div[@class='basket_button_locator']")
    BasketButton basket();
    
}
```
In such way your Wrappedelelements will search basketButton() and notificationsCountLabel() by relative locator from from BasketButton component.

Such approach allows you to reuse component with different locators on different pages.
WrappedComponents also supports @IOSComponent and @AndroidComponent annotations, so you can define same component with different locators for different platforms

## List elements support
Common case when you need to find a list of components or elements on the page, Wrappedelements also supports such functionality

```java
import ua.foggger.wrapper.element.impl.ClickableElement;

public interface InventoryPage extends IPage {
    @WebComponent("//*[@class='inventory_item']")
    List<InventoryItem> inventoryItems();

    @WebElement(".inventory_item")
    List<ClickableElement> inventoryItems();
}
```
Note that it's recommended to use xpath locator for list of components.
## Built-in elements interaction logger
Wrappedelements as a build-in slf4-api logging interface, so you can see elements interaction's logs by adding different logging libraries to your dependencies:
For instance to use logback logging just add next dependencies to your pom.xml
```
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-core</artifactId>
    <version>${your_logback_version}</version>
</dependency>
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>${your_logback_version}</version>
</dependency>
```

## Feedback 
I'm glad to hear any feedback from you via [Facebook](https://www.facebook.com/maksym.zaverukha.37) or Telegram (@foggger)