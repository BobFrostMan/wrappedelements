package ua.foggger.tests.types;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.foggger.BaseTest;
import ua.foggger.config.WrappedElements;
import ua.foggger.tests.types.page.CustomTypePage;
import ua.foggger.ui.driver.DummyWebDriver;
import ua.foggger.tests.types.element.EmptyElement;
import ua.foggger.wrapper.block.WrappedBlockMeta;
import ua.foggger.wrapper.IAnnotationProcessor;

import java.lang.reflect.Method;

public class RegisterNewElementTest extends BaseTest {

    private CustomTypePage page;

    @BeforeClass
    public void setUp() {
        super.setUp();
        WrappedElements.config().registerAnnotationProcessor(EmptyElement.class, new IAnnotationProcessor() {
            @Override
            public <T> Object setValuesFromAnnotation(WrappedBlockMeta parentBlockMeta, T element, Method method, Object[] args) {
                setFieldValue(element, "name", "Not Empty!");
                return element;
            }
        });
        page = WrappedElements.initPage(CustomTypePage.class);
    }

    @Test
    public void registerNewEmptyElementTest() {
        Assert.assertEquals(page.emptyElement().getName(), "Not Empty!");
        Assert.assertEquals(page.emptyElement().getClass(), EmptyElement.class);
    }

    @Test
    public void registerNewEmptyElementWithDefaultImplTest() {
        Assert.assertEquals(page.emptyElementWithDefaultImpl().getClass(), EmptyElement.class);
        Assert.assertEquals(page.emptyElementWithDefaultImpl().getName(), "Not Empty!");
    }

    @Test
    public void registerNewEmptyElementWithParameterInLocatorTest() {
        Assert.assertEquals(page.emptyElementWithParameter("Some parameter").getName(), "Not Empty!");
        Assert.assertEquals(page.emptyElementWithParameter("Some parameter").getClass(), EmptyElement.class);
    }

    @AfterClass
    public void cleanUp() {
        WrappedElements.config().driverCreator(DummyWebDriver::new);
    }

}
