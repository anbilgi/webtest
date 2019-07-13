package com.webtest.page;

import com.webtest.utils.PageWaits;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class HelpPage extends BasePage {

    public enum HELP_SELECTION {COMMUNITY, HELP_CENTER}

    private final static String community_url = "https://community.revolut.com";

    public HelpPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Page objects for Help
    @FindBy(how = How.XPATH, using = "//div[@class='styles__StyledDropdownTrigger-uh23xh-0 eFGfOE']//span[contains(text(),'Help')]")
    private WebElement help_selection;

    @FindBy(how = How.CSS, using = ".eWQRKR > a[href='https://community.revolut.com/']")
    private WebElement community;

    @FindBy(how = How.CSS, using = ".eWQRKR > a[href='/help']")
    private WebElement help_center;

    @FindBy(how = How.CSS, using = "a[href='/search']")
    private WebElement search;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Sign Up')]")
    private WebElement sign_up;

    @FindBy(how = How.CSS, using = "input#search-term")
    private WebElement search_term;


    //Intermediate search results CSS
    @FindAll(value = {@FindBy(how = How.CSS, using = "a.search-link")})
    private List<WebElement> all_links;

    @FindBy(how = How.CSS, using = "h3 a")
    private WebElement blog_link;


    //Blog Post CSS
    @FindBy(how = How.CSS, using = "h1.post-full-title")
    private WebElement post_full_title;

    @FindAll(value = {@FindBy(how = How.CSS, using = ".post-full-content > h3")})
    private List<WebElement> all_headings;

    @FindAll(value = {@FindBy(how = How.CSS, using = ".post-full-content > p")})
    private List<WebElement> all_texts;

    public void navigate(HELP_SELECTION selection) {
        navigate(Sections.HELP);
        PageWaits.wait(PageWaits.DEFAULT_SMALL_WAIT);
        waitForElement(community, PageWaits.DEFAULT_PAGE_LOADING_TIMEOUT);
        switch (selection) {
            case COMMUNITY:
                community.click();
                switchHandles(PageWaits.DEFAULT_PAGE_LOADING_TIMEOUT);
                waitForURL(community_url, PageWaits.DEFAULT_PAGE_LOADING_TIMEOUT);
                break;
            case HELP_CENTER:
                help_center.click();
                break;
            default:
                LOG.error("help selection not supported!");
        }
    }

    public void searchOnCommunity(String text) {
        waitForElement(sign_up, PageWaits.DEFAULT_PAGE_LOADING_TIMEOUT);
        clickWhenReady(search, PageWaits.DEFAULT_ELEMENT_APPEAR_TIMEOUT);
        waitForElement(search_term, PageWaits.DEFAULT_ELEMENT_APPEAR_TIMEOUT);
        search_term.click();
        search_term.clear();
        search_term.sendKeys(text);
        PageWaits.wait(PageWaits.DEFAULT_SMALL_WAIT);
        waitForElements(all_links, PageWaits.DEFAULT_PAGE_LOADING_TIMEOUT);
        clickWhenReady(all_links.get(0), PageWaits.DEFAULT_WINDOW_APPEAR_TIMEOUT);
        waitForPageLoad(PageWaits.DEFAULT_PAGE_LOADING_TIMEOUT);
    }

    public void clickOnFirstLink() {
        waitForElement(blog_link, PageWaits.DEFAULT_WINDOW_APPEAR_TIMEOUT);
        blog_link.click();
        waitForPageLoad(PageWaits.DEFAULT_PAGE_LOADING_TIMEOUT);
    }

    public void switchToBlogTab() {
        switchHandles(PageWaits.DEFAULT_PAGE_LOADING_TIMEOUT);
        LOG.info(driver.getCurrentUrl());
        waitForPageLoad(PageWaits.DEFAULT_PAGE_LOADING_TIMEOUT);
        waitForElement(post_full_title, PageWaits.DEFAULT_WINDOW_APPEAR_TIMEOUT);
    }

    public String getBlogTitle() {
        waitForElement(post_full_title, PageWaits.DEFAULT_WINDOW_APPEAR_TIMEOUT);
        return post_full_title.getText();
    }

    public List<String> getBlogPostHeading() {
        waitForElements(all_headings, PageWaits.DEFAULT_WINDOW_APPEAR_TIMEOUT);
        List<String> all_headings_texts = new ArrayList<>();
        for (WebElement e : all_headings) {
            all_headings_texts.add(e.getText());
        }
        return all_headings_texts;
    }
}
