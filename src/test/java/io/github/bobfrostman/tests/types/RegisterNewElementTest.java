package io.github.bobfrostman.tests.types;

import io.github.bobfrostman.BaseTest;
import io.github.bobfrostman.config.WrappedElements;
import io.github.bobfrostman.tests.types.page.CustomTypePage;
import io.github.bobfrostman.ui.driver.DummyWebDriver;
import io.github.bobfrostman.wrapper.IAnnotationProcessor;
import io.github.bobfrostman.wrapper.block.WrappedBlockMeta;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bobfrostman.tests.types.element.EmptyElement;

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
