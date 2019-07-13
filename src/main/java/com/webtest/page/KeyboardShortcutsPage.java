package com.webtest.page;

import com.webtest.utils.PageWaits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * @author anusha
 */
public class KeyboardShortcutsPage extends BasePage {

    public KeyboardShortcutsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getcurrentURL() {
        waitForPageLoad(PageWaits.DEFAULT_PAGE_LOADING_TIMEOUT);
        return driver.getCurrentUrl();
    }

    public static String home = "gh";
    public static String latest = "gl";
    public static String categories = "gc";
    public static String Top = "gt";

    public static String hamburger = "=";


    public void clickOnShortcut(String shortcut) {
        LOG.info("Clicking on shortcut...");
        driver.findElement(By.cssSelector("body")).sendKeys(shortcut);
        waitForPageLoad(PageWaits.DEFAULT_PAGE_LOADING_TIMEOUT);
        PageWaits.wait(PageWaits.DEFAULT_SMALL_WAIT);
    }

    //Home Page CSS elements
    @FindAll(value = {@FindBy(how = How.CSS, using = "tbody > tr td .category-name")})
    private List<WebElement> category_names;

    public List<String> verifyHomePageCategories() {
        List<String> categories = new ArrayList<>();
        waitForPageLoad(PageWaits.DEFAULT_PAGE_LOADING_TIMEOUT);
        waitForElements(category_names, PageWaits.DEFAULT_PAGE_LOADING_TIMEOUT);
        for (WebElement e : category_names) {
            categories.add(e.getText());
        }
        return categories;
    }

    //Latest page CSS
    @FindBy(how = How.CSS, using = "a[href='/latest']")
    private WebElement latest_link;

    @FindAll(value = {@FindBy(how = How.CSS, using = ".topic-list  tr th")})
    private List<WebElement> links_on_latest;


    public List<String> getLinksOnLatest() {
        waitForElement(latest_link, PageWaits.DEFAULT_PAGE_LOADING_TIMEOUT);
        waitForElements(links_on_latest, PageWaits.DEFAULT_WINDOW_APPEAR_TIMEOUT);
        List<String> links = new ArrayList<>();
        for (WebElement e : links_on_latest) {
            links.add(e.getText());
        }
        return links;
    }

}
