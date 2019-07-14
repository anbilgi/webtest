package com.webtest.tests;

import com.webtest.page.ChangeCountryPage;
import com.webtest.utils.DriverProvider;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestChangeCountry extends TestBase {

    public final static Logger LOG = Logger.getLogger(TestChangeCountry.class);
    WebDriver driver;
    ChangeCountryPage changeCountryPage;

    @BeforeClass
    private void init() {
        LOG.info("initializing test...");
        driver = DriverProvider.getDriver();
        changeCountryPage = new ChangeCountryPage(driver);

        LOG.info("Navigate to change country section..");
        changeCountryPage.navigate();
    }

    @Test
    public void test_changeCountryToUS() {
        LOG.info("Executing test_changeCountryToUS ");
        String new_country = "United States";
        String us_heading_text = "Revolut is coming to\n" +
                "the U.S.A";

        Assert.assertTrue(changeCountryPage.changeCountryTo(new_country));
        Assert.assertEquals(changeCountryPage.getNewCountry(), new_country);
        Assert.assertTrue(changeCountryPage.getAllHeadings().contains(us_heading_text), "heading : \n " + changeCountryPage.getAllHeadings());
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.revolut.com/en-US/");
    }

}
