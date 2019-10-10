import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AdvancedClassTest extends BaseTestClass {
    @Test
    public void saveAndDeleteArticle() {
        String searchedString = "Skyrim";
        By firstArticle = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='The Elder Scrolls V: Skyrim']");
        String secondArticleTitle = "The Elder Scrolls V: Skyrim – Dawnguard";
        By secondArticle = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + secondArticleTitle + "']");
        By articleList = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text, 'The Elder Scrolls V')]");
        String listTitle = "Tests for Nords";
        By listTitleXpath = By.xpath("//*[@text='" + listTitle + "']");
        By searchInputInactiveId = By.xpath("//*[@resource-id='org.wikipedia:id/search_container']");
        By searchInputInProcessId = By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']");
        By searchResultId = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']");
        By moreOptionsButtonId = By.xpath("//android.widget.ImageView[@content-desc='More options']");
        By addToReadingListOptionText = By.xpath("//*[@text='Add to reading list']");
        By gotItButtonId = By.xpath("//*[@resource-id='org.wikipedia:id/onboarding_button']");
        By newListTitleInputId = By.xpath("//*[@resource-id='org.wikipedia:id/text_input']");
        By confirmNewListTitleButton = By.xpath("//*[@text='OK']");
        By crossButton = By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']");
        By myListsTab = By.xpath("//android.widget.FrameLayout[@content-desc='My lists']");
        By createListButton = By.xpath("//*[@resource-id='org.wikipedia:id/create_button']");
        By articleTitle = By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']");

        //first article
        waitForElementAndClick(searchInputInactiveId);
        waitForElementAndSendKeys(searchInputInProcessId, searchedString);
        waitForElementPresent(searchResultId);

        List<WebElement> articlesList = getDriver().findElements(searchResultId);
        Assert.assertNotEquals("Amount of articles equal 0. Searched string: " + searchedString,
                articlesList.size(), 0);
        waitForElementAndClick(firstArticle, "Could not find first article", 10);
        waitForElementPresent(articleTitle);
        waitForElementAndClick(moreOptionsButtonId, "Could not find More Options button", 10);
        waitForElementAndClick(addToReadingListOptionText, "Could not find Add to list option", 10);

        if (checkElementVisibility(gotItButtonId)) {
            waitForElementAndClick(gotItButtonId, "Could not find GOT IT button.", 5);
        } else {
            waitForElementAndClick(createListButton, "Could not find Create new list button.", 5);
        }
        waitForElementAndClear(newListTitleInputId, "Could not find input field of new title", 5);
        waitForElementAndSendKeys(newListTitleInputId, listTitle);
        waitForElementAndClick(confirmNewListTitleButton);
        waitForElementAndClick(crossButton, "Cannot close article by X link", 10);

        //second article
        waitForElementAndClick(searchInputInactiveId);
        waitForElementAndSendKeys(searchInputInProcessId, searchedString);
        waitForElementPresent(searchResultId);
        waitForElementAndClick(secondArticle, "Could not find second article", 10);
        waitForElementPresent(articleTitle);
        waitForElementAndClick(moreOptionsButtonId, "Could not find More Options button", 10);
        waitForElementAndClick(addToReadingListOptionText, "Could not find Add to list option", 10);
        waitForElementAndClick(listTitleXpath);
        waitForElementAndClick(crossButton, "Cannot close article by X link", 10);

        //open My Lists
        waitForElementAndClick(myListsTab);
        waitForElementAndClick(listTitleXpath);
        swipeElementToLeft(firstArticle, "Cannot delete article " + firstArticle.toString());
        waitForElementNotPresent(firstArticle, "Could not delete first article", 10);
        waitForElementAndClick(secondArticle);
        String attrValue = waitForElementAndGetAttribute(articleTitle, "text",
                "Could not find article by xpath " + articleTitle.toString(), 5);
        Assert.assertEquals("Expected title string is not equal to real", secondArticleTitle, attrValue);
    }
}
