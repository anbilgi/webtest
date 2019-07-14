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

public class ChangeCountryPage extends BasePage {

    public ChangeCountryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //CSS
    @FindBy(how = How.CSS, using = "a div.CountryLabel__StyledText-sc-1fnsayw-0")
    private WebElement change_country_link;

    @FindAll(value = {@FindBy(how = How.CSS, using = ".styles__StyledCountryText-sc-1fldlh2-5")})
    private List<WebElement> all_countries;

    @FindAll(value = {@FindBy(how = How.CSS, using = ".rvl-Heading-Heading")})
    private List<WebElement> all_headings;


    public void navigateTo() {
        navigate();
        PageWaits.wait(PageWaits.DEFAULT_SMALL_WAIT);
        waitForElement(change_country_link, PageWaits.DEFAULT_PAGE_LOADING_TIMEOUT);
    }


    public boolean changeCountryTo(String newCountry) {
        boolean found = false;
        change_country_link.click();
        PageWaits.wait(PageWaits.DEFAULT_SMALL_WAIT);
        waitForElements(all_countries, PageWaits.DEFAULT_PAGE_LOADING_TIMEOUT);
        for (WebElement e : all_countries) {
            if (e.getText().contains(newCountry)) {
                e.click();
                found = true;
            }
        }
        return found;
    }

    public String getNewCountry() {
        waitForElement(change_country_link, PageWaits.DEFAULT_PAGE_LOADING_TIMEOUT);
        return change_country_link.getText();
    }

    public List<String> getAllHeadings() {
        List<String> headingList = new ArrayList<>();
        waitForElements(all_headings, PageWaits.DEFAULT_WINDOW_APPEAR_TIMEOUT);
        for (WebElement e : all_headings) {
            headingList.add(e.getText());
        }
        return headingList;
    }
}
