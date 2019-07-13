package com.webtest.tests;

import com.webtest.page.HelpPage;
import com.webtest.utils.DriverProvider;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author anusha
 */
public class TestHelpSection {

    public final static Logger LOG = Logger.getLogger(TestHelpSection.class);
    WebDriver driver;
    HelpPage helpPage;

    @BeforeMethod
    private void init() {
        LOG.info("initializing test...");
        driver = DriverProvider.getDriver();
        helpPage = new HelpPage(driver);
        helpPage.navigate(HelpPage.HELP_SELECTION.COMMUNITY);
    }

    @Test
    public void test_BankingLicenceBlogPost() {
        String search_term = "We got a banking licence  ";

        LOG.info("Navigate to Community page and search for term : " + search_term);
        helpPage.searchOnCommunity(search_term);

        LOG.info("Verifying blog post");
        helpPage.clickOnFirstLink();

        LOG.info("Verifying the full blog page");
        helpPage.switchToBlogTab();
        Assert.assertTrue(helpPage.getBlogTitle().contains(search_term.trim()), "Blog title : " + helpPage.getBlogTitle());

        LOG.info("Verify the headings in the blog post");
        List<String> expected_headings = new ArrayList<>();
        expected_headings.add("Your money will be protected");
        expected_headings.add("Overdrafts and personal loans");
        expected_headings.add("Direct debits");
        expected_headings.add("In-house payment processing");
        expected_headings.add("When is it all kicking off");

        int index = 0;
        for (String s : helpPage.getBlogPostHeading()) {
            Assert.assertTrue(s.contains(expected_headings.get(index++)), "Actual heading is : " + s);
        }

    }

    @AfterMethod
    private void shutdown() {
        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.close();
            driver.quit();
            driver = null;
        }
    }
}
