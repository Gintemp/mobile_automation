package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchPageObject extends MainPageObject {
    private static final String
            SEARCH_INIT_ELEMENT = "org.wikipedia:id/search_container",
            SEARCH_INPUT = "org.wikipedia:id/search_src_text",
            SEARCH_RESULT = "org.wikipedia:id/page_list_item_container",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']",
            SEARCH_RESULT_TITLE = "org.wikipedia:id/page_list_item_container";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /* TEMPLATE METHODS */
    private static String getResultSearchElement(String subString) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", subString);
    }
    /* TEMPLATES METHODS END */

    public void initSearchInput() {
        this.waitForElementAndClick(By.id(SEARCH_INIT_ELEMENT), "Cannot find and click search init element", 5);
        this.waitForElementPresent(By.id(SEARCH_INIT_ELEMENT), "Cannot find search input after clicking search init element");
    }

    public String getSearchInputText() {
        return this.waitForElementAndGetAttribute(By.id(SEARCH_INPUT), "text",
                "Cannot find input element and get text attribute", 5);
    }

    public void typeSearchLine(String searchLine) {
        this.waitForElementAndSendKeys(By.id(SEARCH_INPUT), searchLine, "Cannot find and click search init element", 5);
    }

    public void waitForAnySearchResult() {
        this.waitForElementPresent(By.id(SEARCH_RESULT), "Cannot find search result");
    }

    public void clickByArticleWithSubstring(String subString) {
        String searchResultXpath = getResultSearchElement(subString);
        this.waitForElementAndClick(By.xpath(searchResultXpath),
                "Cannot find and click search result with substring " + subString, 10);
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Found search cancel button", 5);
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find and click search cancel button", 5);
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find anything by the request",
                15
        );

        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(By.xpath(SEARCH_EMPTY_RESULT_ELEMENT), "Cannot find empty result element", 15);
    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT), "We supposed not find any results");
    }

    public List<WebElement> getListOfSearchResults() {
        return driver.findElements(By.id(SEARCH_RESULT_ELEMENT));
    }

    public List<String> getListOfTitlesInResults() {
        List<String> titleList = new ArrayList<>();
        List<WebElement> elements = getListOfSearchResults();
        for (WebElement e : elements) {
            WebElement resultTitle = e.findElement(By.id(SEARCH_RESULT_TITLE));
            String titleText = resultTitle.getAttribute("text");
            titleList.add(titleText);
        }

        return titleList;
    }
}
