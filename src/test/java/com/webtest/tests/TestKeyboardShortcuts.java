package com.webtest.tests;

import com.webtest.page.HelpPage;
import com.webtest.page.KeyboardShortcutsPage;
import com.webtest.utils.DriverProvider;
import com.webtest.utils.PageWaits;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;


/**
 * @author anusha
 */
public class TestKeyboardShortcuts extends TestBase {

    public final static Logger LOG = Logger.getLogger(TestKeyboardShortcuts.class);
    WebDriver driver;
    HelpPage helpPage;
    KeyboardShortcutsPage keyboardShortcuts;

    @BeforeClass
    private void init() {
        LOG.info("initializing test...");
        driver = DriverProvider.getDriver();
        helpPage = new HelpPage(driver);
        keyboardShortcuts = new KeyboardShortcutsPage(driver);

        LOG.info("Navigate to help section..");
        helpPage.navigate(HelpPage.HELP_SELECTION.COMMUNITY);
    }

    @Test
    public void test_Latest_shortcut() {
        LOG.info("Click on Top");
        keyboardShortcuts.clickOnShortcut(KeyboardShortcutsPage.Top);
        PageWaits.wait(PageWaits.DEFAULT_SMALL_WAIT);

        //Verify go to Latest from Top
        keyboardShortcuts.clickOnShortcut(KeyboardShortcutsPage.latest);
        List<String> links_on_Latest = keyboardShortcuts.getLinksOnLatest();
        Assert.assertTrue(links_on_Latest.contains("Topic"));
        Assert.assertTrue(links_on_Latest.contains("Replies"));
        Assert.assertTrue(links_on_Latest.contains("Views"));
        Assert.assertTrue(links_on_Latest.contains("Activity"));

        Assert.assertTrue(keyboardShortcuts.getcurrentURL().contains("https://community.revolut.com/latest"), "Current URL : " + driver.getCurrentUrl());
    }

    @Test
    public void test_HomePage_shortcut() {
        LOG.info("Click on Top");
        keyboardShortcuts.clickOnShortcut(KeyboardShortcutsPage.Top);
        PageWaits.wait(PageWaits.DEFAULT_SMALL_WAIT);

        //Verify go to Home from Top
        keyboardShortcuts.clickOnShortcut(KeyboardShortcutsPage.home);
        Assert.assertTrue(keyboardShortcuts.getcurrentURL().contains("https://community.revolut.com"), "Current URL : " + driver.getCurrentUrl());
        List<String> homepage_categories = keyboardShortcuts.verifyHomePageCategories();
        LOG.info(homepage_categories);
        Assert.assertTrue(homepage_categories.contains("Announcements"));
        Assert.assertTrue(homepage_categories.contains("Bugs"));
    }

}
