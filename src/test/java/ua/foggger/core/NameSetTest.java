package ua.foggger.core;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.foggger.page.WrappedPage;

/**
 * Check that element name properly set for different usecases.
 */
public class NameSetTest {

    private IPageInterface page;

    @BeforeClass
    public void setUp() {
        page = WrappedPage.create(IPageInterface.class);
    }

    @Test
    public void nameIsSetFromMethodName() {
        Assert.assertEquals(page.heisenberg().getName(), "heisenberg");
    }

    @Test
    public void nameIsSetFromAnnotation() {
        Assert.assertEquals(page.randomPerson("rand").getName(), "My name is Vova!");
    }

    @Test
    public void nameIsSetIfBasedOnMethodManeIfMethodIsDefault() {
        Assert.assertEquals(page.jessy().getName(), "jessy");
    }

    @Test
    public void nameIsSetIfBasedOnAnnotationIfIfMethodIsDefault() {
        Assert.assertEquals(page.saulGoodman().getName(), "Saul Goodman");
    }

}
