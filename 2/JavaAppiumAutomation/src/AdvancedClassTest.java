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
        By secondArticle = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='"+ secondArticleTitle +"']");
        By articleList = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text, 'Skyrim')]");
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

        //first article
        waitForElementAndClick(searchInputInactiveId);
        waitForElementAndSendKeys(searchInputInProcessId, searchedString);
        waitForElementPresent(searchResultId);

        List<WebElement> articlesList = getDriver().findElements(searchResultId);
        Assert.assertNotEquals("Amount of articles equal 0. Searched string: " + searchedString,
                articlesList.size(), 0);
        waitForElementAndClick(firstArticle);
        waitForElementAndClick(moreOptionsButtonId, "Could not find More Options button", 5);
        waitForElementAndClick(addToReadingListOptionText, "Could not find Add to list option", 5);

        WebElement gotItButton = getDriver().findElement(gotItButtonId);
        if (gotItButton.isDisplayed()) {
            waitForElementAndClick(gotItButtonId, "Could not find GOT IT button.", 5);
        } else {
            waitForElementAndClick(createListButton, "Could not find Create new list button.", 5);
        }
        waitForElementAndClear(newListTitleInputId, "Could not find input field of new title", 5);
        waitForElementAndSendKeys(newListTitleInputId, listTitle);
        waitForElementAndClick(confirmNewListTitleButton);
        waitForElementAndClick(crossButton, "Cannot close article by X link", 5);

        //second article
        waitForElementAndClick(searchInputInactiveId);
        waitForElementAndSendKeys(searchInputInProcessId, searchedString);
        waitForElementPresent(searchResultId);
        waitForElementAndClick(secondArticle);
        waitForElementAndClick(moreOptionsButtonId, "Could not find More Options button", 5);
        waitForElementAndClick(addToReadingListOptionText, "Could not find Add to list option", 5);
        waitForElementAndClick(listTitleXpath);
        waitForElementAndClick(crossButton, "Cannot close article by X link", 5);

        //open My Lists
        waitForElementAndClick(myListsTab);
        waitForElementAndClick(listTitleXpath);
        List<WebElement> listOfArticles = getDriver().findElements(articleList);
        Assert.assertEquals("Amount of articles not equal 2", 2, listOfArticles.size());
        swipeElementToLeft(firstArticle, "Cannot delete article " + firstArticle.toString());
        listOfArticles = getDriver().findElements(articleList);
        Assert.assertEquals("Amount of articles not equal 1 after delete", 1, listOfArticles.size());
        waitForElementAndClick(secondArticle);
        // waitForElementAndGetAttribute("title")
    }
}
