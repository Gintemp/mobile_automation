import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FirstClassTest extends BaseTestClass {

    @Test
    public void findSearchText() {
        String searchedString = "Search…";
        String searchInputInactiveId = "org.wikipedia:id/search_container";
        String searchInputInProcessId = "org.wikipedia:id/search_src_text";

        waitForElementAndClick(
                By.id(searchInputInactiveId),
                "Element " + searchInputInactiveId + " not found",
                5
        );
        WebElement element = waitForElementPresent(By.id(searchInputInProcessId));
        Assert.assertEquals(
                "Expected default text in search field is not equal " + searchedString,
                searchedString,
                element.getAttribute("text"));
    }

    @Test
    public void searchTextAndClearResult() {
        String searchedString = "Java";
        String searchInputInactiveId = "org.wikipedia:id/search_container";
        String searchInputInProcessId = "org.wikipedia:id/search_src_text";
        String searchResultId = "org.wikipedia:id/page_list_item_container";
        String cancelSearchId = "org.wikipedia:id/search_close_btn";

        waitForElementAndClick(By.id(searchInputInactiveId));
        waitForElementAndSendKeys(By.id(searchInputInProcessId), searchedString);
        waitForElementPresent(By.id(searchResultId));

        List<WebElement> articlesList = getDriver().findElements(By.id(searchResultId));
        Assert.assertNotEquals(
                "Amount of articles equal 0. Searched string: " + searchedString, articlesList.size(), 0);

        waitForElementAndClick(By.id(cancelSearchId));
        waitForElementNotPresent(By.id(searchResultId));

        List<WebElement> emptyArticlesList = getDriver().findElements(By.id(searchResultId));
        Assert.assertEquals(
                "Amount of articles not equal 0.", emptyArticlesList.size(), 0);
    }

    @Test
    public void searchTextInResult() {
        String searchedString = "Java";
        String searchInputInactiveId = "org.wikipedia:id/search_container";
        String searchInputInProcessId = "org.wikipedia:id/search_src_text";
        String searchResultId = "org.wikipedia:id/page_list_item_container";
        String resultTitleId = "org.wikipedia:id/page_list_item_title";

        waitForElementAndClick(By.id(searchInputInactiveId));
        waitForElementAndSendKeys(By.id(searchInputInProcessId), searchedString);
        waitForElementPresent(By.id(searchResultId));

        List<WebElement> articlesList = getDriver().findElements(By.id(searchResultId));
        Assert.assertNotEquals(
                "Amount of articles equal 0. Searched string: " + searchedString, articlesList.size(), 0);

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

        Assert.assertFalse(errorString.toString(), isAsserted);
    }
}
