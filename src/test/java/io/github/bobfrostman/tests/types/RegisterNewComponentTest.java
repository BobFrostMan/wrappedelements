package io.github.bobfrostman.tests.types;

import io.github.bobfrostman.BaseTest;
import io.github.bobfrostman.config.WrappedElements;
import io.github.bobfrostman.tests.types.page.CustomTypePage;
import io.github.bobfrostman.ui.driver.DummyWebDriver;
import io.github.bobfrostman.wrapper.IAnnotationProcessor;
import io.github.bobfrostman.wrapper.block.WrappedBlockMeta;
import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bobfrostman.tests.types.element.EmptyElement;

import java.lang.reflect.Method;

public class RegisterNewComponentTest extends BaseTest {

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
    public void registerNewComponentWithParameterInLocatorTest() {
        Assert.assertEquals(page.element().solo(), "solo");
        Assert.assertEquals(getLocator(page.element().elementWithNamedParameter("last", "name")),
                new ByChained(By.xpath("//div"), By.xpath("//li/a[contains(text(), 'name last')]")));
    }

    @Test
    public void registerNewComponentInsideComponentWithParameterInLocatorTest() {
        Assert.assertEquals(getLocator(page.holderComponent().innerComponent().elementWithNamedParameter("last", "name")),
                new ByChained(new ByChained(By.xpath("//div"), By.xpath("//li")), By.xpath("//li/a[contains(text(), 'name last')]")));
    }

    @AfterClass
    public void cleanUp() {
        WrappedElements.config().driverCreator(DummyWebDriver::new);
    }

}
