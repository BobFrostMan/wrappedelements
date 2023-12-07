package ua.foggger.core;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.foggger.page.WrappedPage;

public class SmokeTest {

    private IPageInterface page;

    @BeforeClass
    public void setUp() {
        page = WrappedPage.create(IPageInterface.class);
    }

    @Test
    public void testElementsWrappersPresent() {
        Assert.assertEquals(page.heisenberg().getName(), "heisenberg");
        Assert.assertEquals(page.jessy().getName(), "What's up bi......h!");
        Assert.assertEquals(page.randomPerson("rand").getName(), "My name is Vova!");
    }

}
