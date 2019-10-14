package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {
    private static final String
        SEARCH_INIT_ELEMENT = "org.wikipedia:id/search_container",
        SEARCH_INPUT = "org.wikipedia:id/search_src_text",
        SEARCH_RESULT = "org.wikipedia:id/page_list_item_container",
        SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
        SEARCH_CANCEL = "org.wikipedia:id/search_close_btn";

    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }
    /* TEMPLATE METHODS */
    private static String getResultSearchElement(String subString)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", subString);
    }
    /* TEMPLATES METHODS END */

    public void initSearchInput()
    {
        this.waitForElementAndClick(By.id(SEARCH_INIT_ELEMENT), "Cannot find and click search init element", 5);
        this.waitForElementPresent(By.id(SEARCH_INIT_ELEMENT), "Cannot find search input after clicking search init element");
    }

    public String getSearchInputText()
    {
        return this.waitForElementAndGetAttribute(By.id(SEARCH_INPUT), "text",
                "Cannot find input element and get text attribute", 5);
    }

    public void typeSearchLine(String searchLine)
    {
        this.waitForElementAndSendKeys(By.id(SEARCH_INPUT), searchLine,"Cannot find and click search init element", 5);
    }

    public void waitForAnySearchResult()
    {
        this.waitForElementPresent(By.id(SEARCH_RESULT), "Cannot find search result");
    }

    public void waitForSearchResult(String subString)
    {
        String searchResultXpath = getResultSearchElement(subString);
        this.waitForElementPresent(By.xpath(searchResultXpath),
                "Cannot find search result with substring " + subString);
    }
}
