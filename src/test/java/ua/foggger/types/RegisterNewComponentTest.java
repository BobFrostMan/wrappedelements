package ua.foggger.types;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.foggger.BaseTest;
import ua.foggger.config.WrappedElements;
import ua.foggger.core.driver.DummyWebDriver;
import ua.foggger.types.element.EmptyElement;
import ua.foggger.types.page.CustomTypePage;
import ua.foggger.wrapper.element.IElementAnnotationProcessor;

import java.lang.reflect.Method;

public class RegisterNewComponentTest extends BaseTest {

    private CustomTypePage page;

    @BeforeClass
    public void setUp() {
        super.setUp();
        WrappedElements.config().registerDecorator(EmptyElement.class, new IElementAnnotationProcessor() {
            @Override
            public <T> Object setValuesFromAnnotation(T element, Method method, Object[] args) {
                setFieldValue(element, "name", "Not Empty!");
                return element;
            }
        });
        page = WrappedElements.initPage(CustomTypePage.class);
    }

    @Test
    public void registerNewComponentWithParameterInLocatorTest() {
        Assert.assertEquals(page.element().solo(), "solo");
        Assert.assertEquals(getLocator(page.element().elementWithNamedParameter("last", "name")), "");
    }

    @AfterClass
    public void cleanUp() {
        WrappedElements.config().driverCreator(DummyWebDriver::new);
    }

}
