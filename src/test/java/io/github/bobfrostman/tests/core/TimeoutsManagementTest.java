package io.github.bobfrostman.tests.core;

import io.github.bobfrostman.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bobfrostman.config.WrappedElements;
import io.github.bobfrostman.ui.page.TimeoutsPage;

/**
 * Check that element locator properly set for different usecases.
 */
public class TimeoutsManagementTest extends BaseTest {

    private TimeoutsPage page;

    @BeforeClass
    public void setUp() {
        super.setUp();
        page = WrappedElements.initPage(TimeoutsPage.class);
    }

    @Test
    public void defaultTimeoutIsSet() {
        Assert.assertEquals(getTimeout(page.defaultTimeout()), 10);
    }

    @Test
    public void customTimeoutIsSet() {
        Assert.assertEquals(getTimeout(page.customTimeout()), 15);
    }

    @Test
    public void customTimeoutDefaultImplementationIsSet() {
        Assert.assertEquals(getTimeout(page.defaultTimeoutDefaultImpl()), 15);
    }

}
