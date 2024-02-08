package ua.foggger.tests.core;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.foggger.BaseTest;
import ua.foggger.config.WrappedElements;
import ua.foggger.ui.page.BreakingBadPage;

/**
 * Check that element name properly set for different usecases.
 */
public class NameSetTest extends BaseTest {

    private BreakingBadPage page;

    @BeforeClass
    public void setUp() {
        super.setUp();
        page = WrappedElements.initPage(BreakingBadPage.class);
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

    @Test
    public void nameIsSetForListItems() {
        Assert.assertEquals(page.combos().size(), 2);
        Assert.assertEquals(page.combos().get(0).getName(), "Combos on Breaking bad page (list item number [0])");
        Assert.assertEquals(page.combos().get(1).getName(), "Combos on Breaking bad page (list item number [1])");
    }

}
