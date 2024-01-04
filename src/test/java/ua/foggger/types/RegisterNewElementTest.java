package ua.foggger.types;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.foggger.BaseTest;
import ua.foggger.config.WrappedElements;
import ua.foggger.core.driver.DummyWebDriver;
import ua.foggger.element.decorator.IElementDecorator;
import ua.foggger.types.annotations.CustomEmptyElement;
import ua.foggger.types.element.EmptyElement;
import ua.foggger.types.page.CustomTypePage;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class RegisterNewElementTest extends BaseTest {

    private CustomTypePage page;

    @BeforeClass
    public void setUp() {
        super.setUp();
        WrappedElements.config().registerDecorator(EmptyElement.class, new IElementDecorator() {
            @Override
            public <T> Object setValuesFromAnnotation(T element, Method method, Object[] args) {
                setFieldValue(element, "name", "Not Empty!");
                return element;
            }

            @Override
            public Class<? extends Annotation> getAnnotationClass() {
                return CustomEmptyElement.class;
            }
        });
    }

    @Test
    public void registerNewEmptyElementTest() {
        page = WrappedElements.initPage(CustomTypePage.class);
        Assert.assertEquals(page.emptyElement().getName(), "Not Empty!");
        Assert.assertEquals(page.emptyElement().getClass(), EmptyElement.class);
    }

    @AfterClass
    public void cleanUp() {
        WrappedElements.config().driverCreator(DummyWebDriver::new);
    }

}
