# wrappedelements
A convenient framework wrapper for pages and selenium webelements, that reduces usual waiter, and initialization routine. Just get rid of boilerplate code in UI test automation frameworks.

# Features
## Clear and simple Page object classes
Imagine page object class as an interface! That makes page object classes clear and avoids additional code.
WrappedElements framework will generate the exact implementation for you.
```
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
```  
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
```
@WebElement(value = "//combo", waitUntil = UNTIL_CLICKABLE, timeout = 15)
WrappedElement combo();
```
Interactor - is the entity that describes the behavior with web element. 
You can specify it in page object class using @WebElement 'waitUntil' value. 

Available Interactor values:
- UNTIL_VISIBLE (waits for element to be displayed before physical interaction)
- UNTIL_CLICKABLE (waits for element to be displayed and be enabled)
- VERTICAL_SCROLL_UNTIL_VISIBLE (scrolls down until the element will be visible in the view port)

## Easy element's interaction setup
You can easily create you own custom Decorators for specific webelements that you'll face during test automation.

Let's say you need MyAwesomeButton implementation to be used by WrappedElements framework.
1. Create **MyAweSomeButton** class (implement ClickableElement):
```
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
```
public class ButtonDecorator implements IElementDecorator {
...
    @Override
    public <T> Object setValuesFromAnnotation(T element, Method method, Object[] args) {
        ButtonElement annotation = (ButtonElement) method.getAnnotation(getAnnotationClass());
        String name = "".equals(annotation.value()) ? elementNameResolver.resolve(method) : annotation.value();
        MyAwesomeButton button = (MyAwesomeButton) element;
        button.setName(name);
        ...
        return element;
    }

    @Override
    public Class<? extends Annotation> getAnnotationClass() {
        return ButtonElement.class;
    }
}
```
3. Create your annotation (or use WrappedElements @WebElement annotation):
```
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ButtonElement {
    String value();
    //other awesome fields, that you may need for your awesome web element
}
```
4. Register your decorator for your class in WrappedElements framework:
```
WrappedElements.config().registerDecorator(MyAwesomeButton.class, new ButtonDecorator());
```
5. Use it! 
Declare your object page object layer:
```
public interface InventoryPage extends IPage {

    @ButtonElement("//*[@class='inventory_item']//button")
    MyAwesomeButton addToCartButton();

}
```
Test class
```
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

## Easy element's locator definition for different platforms
In progress

## Dynamic locators support
WrappedElements supports dynamical element locators, we know that sometimes you need some specific element that may be found only dynamically.

Declaration example:
```
@WebElement(value = "//div[text() = '%s']|//span[contains(text(), '%s')]")
ClickableElement elementWithText(String text);

@WebElement(value = "//div[text() = '%d']|//span[contains(text(), '%d')]")
ClickableElement elementWithDigits(int number);

@WebElement(value = "//li/a[contains(text(), '${first_name} ${ last_name }')]")
ClickableElement elementWithNamedParameter(@Parameter("last_name") String lastName, @Parameter("first_name") String firstName);
```
Usage example:
```
String name = page.elementWithDigits(0).getText();
String lastName = page.elementWithDigits(1).getText();
String someOtherDynamicalText = page.elementWithNamedParameter(lastName, name).getText();
elementWithText(someOtherDynamicalText).click();
```

## List elements support
In progress

# Getting started
TODO