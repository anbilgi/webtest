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

    /**
     * Test to verify shortcut for Latest page
     * Test first clicks on Top page just to make sure that it's not on latest page already
     * Test keys in shortcut for Latest and verifies text on the latest page
     */
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

    /**
     * Test to verify shortcut for Home page
     * Test first clicks on Top page just to make sure that it's not on  Homepage already
     * Test keys in shortcut for home and verifies text and the URL of the home page
     */
    @Test
    public void test_HomePage_shortcut() {
        LOG.info("Click on Top");
        keyboardShortcuts.clickOnShortcut(KeyboardShortcutsPage.Top);
        PageWaits.wait(PageWaits.DEFAULT_SMALL_WAIT);

        //Verify go to Homepage from Top page
        keyboardShortcuts.clickOnShortcut(KeyboardShortcutsPage.home);
        Assert.assertTrue(keyboardShortcuts.getcurrentURL().contains("https://community.revolut.com"), "Current URL : " + driver.getCurrentUrl());
        List<String> homepage_categories = keyboardShortcuts.verifyHomePageCategories();
        Assert.assertTrue(homepage_categories.contains("Announcements"));
        Assert.assertTrue(homepage_categories.contains("Bugs"));
    }

    /**
     * Test to verify shortcut for Hamburger menu
     * Test keys in shortcut for Hamburger and verifies text on Hamburger menu
     */
    @Test
    public void test_Hamburger_shortcut() {
        LOG.info("Click on Top");
        keyboardShortcuts.clickOnShortcut(KeyboardShortcutsPage.Top);
        PageWaits.wait(PageWaits.DEFAULT_SMALL_WAIT);

        //Verify go to Home from Top
        keyboardShortcuts.clickOnShortcut(KeyboardShortcutsPage.hamburger);
        List<String> hamburger_labels = keyboardShortcuts.verifyHamburgerLabels();
        LOG.info(hamburger_labels);
        Assert.assertTrue(hamburger_labels.contains("Latest"));
        Assert.assertTrue(hamburger_labels.contains("Badges"));
        Assert.assertTrue(hamburger_labels.contains("Groups"));
        Assert.assertTrue(hamburger_labels.contains("Top"));
        Assert.assertTrue(hamburger_labels.contains("Users"));
    }

    /**
     * Test to check navigation shortcuts - Back page &  up and down scroll
     * Test lands on homepage, goes to Top where it keys in back shortcut, to check it takes the user back to homepage
     *
     * Test goes through the list of all latest post by keying in the 'down' shortcut
     * And it goes through the same list of post in reverse order by keying in the 'up' shortcut
     */
    @Test
    public void test_NavigationShortcuts() {
        LOG.info("Click on Top");
        keyboardShortcuts.clickOnShortcut(KeyboardShortcutsPage.Top);
        PageWaits.wait(PageWaits.DEFAULT_SMALL_WAIT);

        LOG.info("Go back to categories page and verify categories page");
        keyboardShortcuts.clickOnShortcut(KeyboardShortcutsPage.back);

        Assert.assertTrue(keyboardShortcuts.getcurrentURL().contains("https://community.revolut.com"), "Current URL : " + driver.getCurrentUrl());
        List<String> homepage_categories = keyboardShortcuts.verifyHomePageCategories();
        Assert.assertTrue(homepage_categories.contains("Announcements"));
        Assert.assertTrue(homepage_categories.contains("Bugs"));

        List<String> all_posts = keyboardShortcuts.getListOfLatestPost();

        LOG.info("Go through list of Latest post by clicking on shortcut -> down");
        for (String post : all_posts) {
            LOG.info("Clicking on posts : " + post);
            keyboardShortcuts.clickOnShortcut(KeyboardShortcutsPage.down);
            Assert.assertEquals(keyboardShortcuts.getActiveElement(), post);
        }

        LOG.info("Go through all posts backwards by clicking on shortcut -> up");
        for (int i = all_posts.size() - 2; i > 0; i--) {
            String post = all_posts.get(i);
            LOG.info("Clicking on posts backwards: " + post);
            keyboardShortcuts.clickOnShortcut(KeyboardShortcutsPage.up);
            Assert.assertEquals(keyboardShortcuts.getActiveElement(), post);
        }
    }
}
