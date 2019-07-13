package com.webtest.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverProvider {

    private static WebDriver driver = null;

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
        setCapabilities(capabilities);
        System.setProperty("webdriver.gecko.driver",ConfigParser.getConfig().getGecko_path());

        int attempt = 0;
        while (driver == null && attempt++ < 5) {
            driver = new FirefoxDriver(capabilities);
        }
        driver.manage().window().maximize();
        return driver;
    }
    
    private static void setCapabilities(DesiredCapabilities capabilities){
        capabilities.setCapability("app.update.auto", false);
        capabilities.setCapability("app.update.enabled", false);
    }

    public static void CloseAllOtherHandles() {
        String originalHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }
        driver.switchTo().window(originalHandle);
    }

}
