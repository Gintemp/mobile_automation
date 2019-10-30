package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SearchPageObject extends MainPageObject {
    private static final String
            SEARCH_INIT_ELEMENT = "id:org.wikipedia:id/search_container",
            SEARCH_INPUT = "id:org.wikipedia:id/search_src_text",
            SEARCH_RESULT = "id:org.wikipedia:id/page_list_item_container",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_BY_SUBSTRING_ARTICLE_AND_DESCRIPTION_TPL =
                    "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{ARTICLE}']/../*[@text='{DESCRIPTION}']",
            SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']",
            SEARCH_RESULT_TITLE = "id:org.wikipedia:id/page_list_item_container";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /* TEMPLATE METHODS */
    private static String getResultSearchElement(String subString) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", subString);
    }

    private static String getResultSearchElement(String article, String desc) {
        return SEARCH_RESULT_BY_SUBSTRING_ARTICLE_AND_DESCRIPTION_TPL
                .replace("{ARTICLE}", article)
                .replace("{DESCRIPTION}", desc);
    }
    /* TEMPLATES METHODS END */

    public void initSearchInput() {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element");
    }

    public String getSearchInputText() {
        return this.waitForElementAndGetAttribute(SEARCH_INPUT, "text",
                "Cannot find input element and get text attribute", 5);
    }

    public void typeSearchLine(String searchLine) {
        this.waitForElementAndSendKeys(SEARCH_INPUT, searchLine, "Cannot find and click search init element", 5);
    }

    public void waitForAnySearchResult() {
        this.waitForElementPresent(SEARCH_RESULT, "Cannot find search result");
    }

    public void clickByArticleWithSubstring(String subString) {
        String searchResultXpath = getResultSearchElement(subString);
        this.waitForElementAndClick(searchResultXpath,
                "Cannot find and click search result with substring " + subString, 10);
    }

    public void clickByArticleWithDescriptionBySubstring(String article, String desc) {
        String searchResultXpath = getResultSearchElement(article, desc);
        this.waitForElementAndClick(searchResultXpath,
                "Cannot find and click search result with article " + article +
                        " and description " + desc, 10);
    }

    public void waitForElementByTitleAndDescription(String title, String description)
    {
        String elementXpath = getResultSearchElement(title, description);
        waitForElementPresent(elementXpath,
                "Cannot find element with article " + title +
                        " and description " + description, 10);
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Found search cancel button", 5);
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 5);
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15
        );

        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result element", 15);
    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed not find any results");
    }

    public List<WebElement> getListOfSearchResults() {
        return driver.findElements(By.id(SEARCH_RESULT_ELEMENT.split(Pattern.quote(":"), 2)[1]));
    }

    public List<String> getListOfTitlesInResults() {
        List<String> titleList = new ArrayList<>();
        List<WebElement> elements = getListOfSearchResults();
        for (WebElement e : elements) {
            WebElement resultTitle = e.findElement(By.id(SEARCH_RESULT_TITLE.split(Pattern.quote(":"), 2)[1]));
            String titleText = resultTitle.getAttribute("text");
            titleList.add(titleText);
        }

        return titleList;
    }
}
