import lib.CoreTestCase;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FirstClassTest extends CoreTestCase {
    private MainPageObject MainPageObject;

    protected void setUp() throws Exception {
        super.setUp();
        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testFindSearchText() {
        String searchedString = "Search…";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        assertEquals(
                "Expected default text in search field is not equal " + searchedString,
                searchedString,
                SearchPageObject.getSearchInputText());
    }

    @Test
    public void testSearchTextAndClearResult() {
        String searchedString = "Java";
        String searchInputInactiveId = "org.wikipedia:id/search_container";
        String searchInputInProcessId = "org.wikipedia:id/search_src_text";
        String searchResultId = "org.wikipedia:id/page_list_item_container";
        String cancelSearchId = "org.wikipedia:id/search_close_btn";

        MainPageObject.waitForElementAndClick(By.id(searchInputInactiveId));
        MainPageObject.waitForElementAndSendKeys(By.id(searchInputInProcessId), searchedString);
        MainPageObject.waitForElementPresent(By.id(searchResultId));

        List<WebElement> articlesList = driver.findElements(By.id(searchResultId));
        assertNotSame("Amount of articles equal 0. Searched string: " + searchedString, articlesList.size(), 0);

        MainPageObject.waitForElementAndClick(By.id(cancelSearchId));
        MainPageObject.waitForElementNotPresent(By.id(searchResultId));

        List<WebElement> emptyArticlesList = driver.findElements(By.id(searchResultId));
        assertEquals("Amount of articles not equal 0.", emptyArticlesList.size(), 0);
    }

    @Test
    public void testSearchTextInResult() {
        String searchedString = "Java";
        String searchInputInactiveId = "org.wikipedia:id/search_container";
        String searchInputInProcessId = "org.wikipedia:id/search_src_text";
        String searchResultId = "org.wikipedia:id/page_list_item_container";
        String resultTitleId = "org.wikipedia:id/page_list_item_title";

        MainPageObject.waitForElementAndClick(By.id(searchInputInactiveId));
        MainPageObject.waitForElementAndSendKeys(By.id(searchInputInProcessId), searchedString);
        MainPageObject.waitForElementPresent(By.id(searchResultId));

        List<WebElement> articlesList = driver.findElements(By.id(searchResultId));
        assertNotSame("Amount of articles equal 0. Searched string: " + searchedString, articlesList.size(), 0);

        boolean isAsserted = false;
        StringBuilder errorString = new StringBuilder();
        for (WebElement e : articlesList) {
            WebElement resultTitle = e.findElement(By.id(resultTitleId));
            String titleText = resultTitle.getAttribute("text");

            if (!titleText.toLowerCase().contains(searchedString.toLowerCase())) {
                isAsserted = true;
                errorString.append("String ")
                        .append(searchedString)
                        .append(" was not found in string: ")
                        .append(titleText).append("\n");
            }
        }

        assertFalse(errorString.toString(), isAsserted);
    }
}
