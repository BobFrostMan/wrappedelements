# wrappedelements
Inspired by [Burning wave](https://www.burningwave.org/getstarted/).

Special thanks to [Tyrrz](https://github.com/Tyrrrz) for Terms of use, and [Sam Rosental](https://github.com/sam-rosenthal) for opensource [css to xpath converter](https://github.com/sam-rosenthal/java-cssSelector-to-xpath).

[![Status](https://img.shields.io/badge/status-active-47c219.svg)](https://github.com/Tyrrrz/.github/blob/master/docs/project-status.md)
[![Made in Ukraine](https://img.shields.io/badge/made_in-ukraine-ffd700.svg?labelColor=0057b7)](https://tyrrrz.me/ukraine)
[![Fuck Russia](https://img.shields.io/badge/fuck-russia-e4181c.svg?labelColor=000000)](https://twitter.com/tyrrrz/status/1495972128977571848)

A convenient framework wrapper for pages and selenium webelements, that reduces usual waiter, and initialization routine. It will help you to get rid of boilerplate code in UI test automation frameworks.

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

## Getting started
### Maven
1. Add wrappedelements and selenium java dependency to your pom file:
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
2. Create your own pageobject entities as interfaces (extend the IPage interface)
```java
public interface LoginPage extends IPage {

    @WebElement(value = "//form//input", name = "Login input on login page")
    ClickableElement loginInput();

    @WebElement(value = "(//form//input)[2]", name = "Password input on login page")
    ClickableElement passwordInput();

    @WebElement(value = "//input[@type='submit']", name = "Login button")
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
                })
                .registerAnnotationProcessor(Button.class, new ButtonDecorator());

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

## Quick overview
TODO

## Features overview
## Clear and simple Page object classes
Imagine page object class as an interface! That makes page object classes clear and avoids additional code.
WrappedElements framework will generate the exact implementation for you.
```java
import ua.foggger.annotation.WebElement;
import ua.foggger.element.clickable.ClickableElement;
import ua.foggger.page.IPage;

public interface LoginPage extends IPage {

    @WebElement(value = "//form//input", name = "Login input on login page")
    ClickableElement loginInput();

    @WebElement(value = "(//form//input)[2]", name = "Password input on login page")
    ClickableElement passwordInput();

    @WebElement(value = "//input[@type='submit']", name = "Login button")
    ClickableElement loginButton();

}
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
It is possible because of 'Interactor' mechanism, that allows you to configure waits for elements directly on page object layer.

Example:

Any time you perform any kind of physical interaction (click,sendKeys, etc.), on combo() element, it will wait for combo() to become clickable first, with 15 seconds timeout.
```java
@WebElement(value = "//combo", waitUntil = UNTIL_CLICKABLE, timeout = 15)
WrappedElement combo();
```
Interactor - is the entity that describes the behavior with web element. 
You can specify it in page object class using @WebElement 'waitUntil' value. 

Available Interactor values:
- UNTIL_VISIBLE (waits for element to be displayed before physical interaction)
- UNTIL_CLICKABLE (waits for element to be displayed and be enabled)
- VERTICAL_SCROLL_UNTIL_VISIBLE (scrolls down until the element will be visible in the view port)

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

## Easy element's interaction setup
You can easily create you own custom Decorators for specific webelements that you'll face during test automation.

Let's say you need MyAwesomeButton implementation to be used by WrappedElements framework.
1. Create **MyAweSomeButton** class (implement ClickableElement):
```java
public class MyAwesomeButton extends ClickableElement {

    void setName(String name) {
        this.name = name;
    }

    void setLocator(By by) {
        this.locator = by;
    }

    void setTimeoutInSeconds(int timeoutInSeconds) {
        this.timeoutInSeconds = timeoutInSeconds;
    }

    void setInteractor(IElementInteractor interactor){
        this.detection = interactor;
    }
    
    //some awesome button logic there
}
```
2. Create **MyAweSomeButtonDecorator** (implement IElementDecorator) that pass values from annotation to MyAweSomeButton object:
```java
public class ButtonDecorator implements IElementDecorator {

    @Override
    public <T> Object setValuesFromAnnotation(T element, Method method, Object[] args) {
        ButtonElement annotation = (ButtonElement) method.getAnnotation(getAnnotationClass());
        String name = "".equals(annotation.value()) ? elementNameResolver.resolve(method) : annotation.value();
        MyAwesomeButton button = (MyAwesomeButton) element;
        button.setName(name);
        //do something else with element
        return element;
    }

    @Override
    public Class<? extends Annotation> getAnnotationClass() {
        return ButtonElement.class;
    }
}
```
3. Create your annotation (or use WrappedElements @WebElement annotation):
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ButtonElement {
    String value();
    //other awesome fields, that you may need for your awesome web element
}
```
4. Register your decorator for your class in WrappedElements framework:
```java
WrappedElements.config().registerDecorator(MyAwesomeButton.class, new ButtonDecorator());
```
5. Use it! 
Declare your object page object layer:
```java
public interface InventoryPage extends IPage {

    @ButtonElement("//*[@class='inventory_item']//button")
    MyAwesomeButton addToCartButton();

}
```
Test class
```java
public class SmokeTest {
    private InventoryPage inventoryPage;

    @BeforeClass
    public void setUp() {
        WrappedElements.config()
                .driverCreator(() -> createChromeDriver())
                .registerDecorator(MyAwesomeButton.class, new ButtonDecorator());

        inventoryPage = WrappedElements.initPage(InventoryPage.class);
    }

    @Test
    public void someAwesomeTest() {
        //some logic before
        inventoryPage.addToCartButton().click();
        //some logic after
    }
    //some other test methods
}
```

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

## List elements support
In progress

## More documentation
TODO
## Videos guides
TODO