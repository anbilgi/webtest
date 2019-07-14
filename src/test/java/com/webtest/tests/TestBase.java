package com.webtest.tests;

import com.webtest.utils.DriverProvider;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;

public class TestBase {
    WebDriver driver = DriverProvider.getDriver();

    @AfterClass(alwaysRun = true)
    protected void close() {
        DriverProvider.CloseAllOtherHandles();
    }

    @AfterSuite
    protected void shutdown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
