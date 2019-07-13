package com.webtest.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverProvider {

    private static WebDriver driver = null;

    //TODO : https://www.toolsqa.com/selenium-webdriver/how-to-use-geckodriver/

    //TODO :  https://ftp.mozilla.org/pub/firefox/releases/59.0/mac/en-GB/

    //TODO : https://firefox-source-docs.mozilla.org/testing/geckodriver/geckodriver/Support.html

    private static String geckoPath = "/Users/anusha/setup/geckodriver";

    private DriverProvider() {
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            driver = getFireFoxInstance();
        }
        return driver;
    }

    private static WebDriver getFireFoxInstance() {
        WebDriver driver = null;
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        System.setProperty("webdriver.gecko.driver", geckoPath);

        int attempt = 0;
        while (driver == null && attempt++ < 5) {
            driver = new FirefoxDriver(capabilities);
        }
        driver.manage().window().maximize();
        return driver;
    }

}
