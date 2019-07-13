package com.webtest.page;

import com.google.common.base.Function;
import com.webtest.utils.ConfigParser;
import com.webtest.utils.DriverProvider;
import com.webtest.utils.PageWaits;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @author anusha
 */
public class BasePage {

    protected static final Logger LOG = Logger.getLogger(BasePage.class);
    protected String baseURL;
    protected WebDriver driver;

    public enum Sections {PRICING, ACCOUNT, PRODUCT, COMPANY, HELP}

    @FindBy(how = How.CSS, using = ".iUZsMP > span[title='Revolut']")
    private WebElement page_title;

    @FindBy(how = How.XPATH, using = ".gHiZch > a[href='/our-pricing-plans']")
    private WebElement pricing;

    @FindAll(value = {@FindBy(how = How.CSS, using = ".gHiZch > div")})
    private List<WebElement> section_list;

    public BasePage() {
        driver = DriverProvider.getDriver();
        baseURL = ConfigParser.getConfig().getBase_url();
    }

    public void navigate() {
        driver.get(baseURL);
        waitForURL(baseURL, PageWaits.DEFAULT_PAGE_LOADING_TIMEOUT);
        waitForElement(page_title, PageWaits.DEFAULT_PAGE_LOADING_TIMEOUT);
    }

    public void navigate(Sections sections) {
        navigate();
        PageWaits.wait(PageWaits.DEFAULT_SMALL_WAIT);
        waitForElements(section_list, PageWaits.DEFAULT_PAGE_LOADING_TIMEOUT);
        switch (sections) {
            case PRICING:
                pricing.click();
                break;
            case ACCOUNT:
                section_list.get(0).click();
                break;
            case PRODUCT:
                section_list.get(1).click();
                break;
            case COMPANY:
                section_list.get(2).click();
                break;
            case HELP:
                section_list.get(3).click();
                break;
            default:
                LOG.error("help selection not supported!");
        }
    }


    protected void waitForURL(String url, long timeOutSeconds) {
        waitForPageLoad(timeOutSeconds);

        int timer = 0;
        while (timer < timeOutSeconds && !driver.getCurrentUrl().contains(url)) {
            timer += 5;
            PageWaits.wait(PageWaits.DEFAULT_SMALL_WAIT);
        }
    }

    protected void waitForPageLoad(long timeOutSeconds) {
        new WebDriverWait(driver, timeOutSeconds).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

    }

    protected void waitForElement(WebElement element, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until((WebDriver drv) -> {
            try {
                return element.isDisplayed();
            } catch (WebDriverException ex) {
                return false;
            }
        });

    }

    public void clickWhenReady(WebElement element, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    protected void waitForElements(List<WebElement> elements, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until((WebDriver drv) -> {
            try {
                return ExpectedConditions.visibilityOfAllElements(elements);
            } catch (WebDriverException ex) {
                return false;
            }
        });
    }

//    protected void switchHandles() {
//        LOG.info("Switching to the next tabs on the browser...");
//        Set<String> handles = driver.getWindowHandles();
//        String currentHandle = driver.getWindowHandle();
//        for (String handle : handles) {
//            if (!handle.equals(currentHandle)) {
//                driver.switchTo().window(handle);
//            }
//        }
//    }

     void switchHandles(Long timeOutSeconds) {
        LOG.info("Switching to the next tab on the browser...");
        Set<String> handles = driver.getWindowHandles();
        final Iterator iterator = handles.iterator();
        Object lastHandle = iterator.next();
        while (iterator.hasNext()) {
            lastHandle = iterator.next();
        }
        driver.switchTo().window(lastHandle.toString());
        waitForPageLoad(timeOutSeconds);
        LOG.info("Switched to new tab with URL : " + driver.getCurrentUrl());
    }


    protected void waitUntilText(WebElement element, String text, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until((WebDriver drv) -> {
            try {
                return ExpectedConditions.textToBePresentInElementValue(element, text);
            } catch (WebDriverException ex) {
                return false;
            }
        });
    }
}
