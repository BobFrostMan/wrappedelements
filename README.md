# wrappedelements
A convenient framework wrapper for pages and selenium webelements, that reduces usual waiter, and initialization routine. Just get rid of boilerplate code in UI test automation frameworks.

# Features
## Clear and simple Page object classes
```
import ua.foggger.annotation.WebElement;
import ua.foggger.elements.ClickableElement;
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
## Easy element's interaction setup
In progress 

## Easy element's locator definition for different platforms
In progress

## Dynamic locators support
In progress 

## List elements support
In progress

# Getting started
TODO