package ua.foggger.core;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.foggger.BaseTest;
import ua.foggger.config.WrappedElements;
import ua.foggger.core.page.IBreakingBadPage;

/**
 * Check that element name properly set for different usecases.
 */
public class NameSetTest extends BaseTest {

    private IBreakingBadPage page;

    @BeforeClass
    public void setUp() {
        super.setUp();
        page = WrappedElements.initPage(IBreakingBadPage.class);
    }

    @Test
    public void nameIsSetFromMethodName() {
        Assert.assertEquals(page.heisenberg().getName(), "Heisenberg on Breaking bad page");
    }

    @Test
    public void nameIsSetFromAnnotation() {
        Assert.assertEquals(page.randomPerson("rand").getName(), "My name is Vova!");
    }

    @Test
    public void nameIsSetIfBasedOnMethodManeIfMethodIsDefault() {
        Assert.assertEquals(page.jessy().getName(), "Jessy on Breaking bad page");
    }

    @Test
    public void nameIsSetIfBasedOnAnnotationIfIfMethodIsDefault() {
        Assert.assertEquals(page.saulGoodman().getName(), "Saul Goodman");
    }

}
