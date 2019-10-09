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
        By secondArticle = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='The Elder Scrolls V: Skyrim – Dawnguard']");
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
            waitForElementAndClick(gotItButtonId);
        } else {
            //ToDo: create new list
        }
        waitForElementAndClear(newListTitleInputId, "Could not find input field of new title", 5);
        waitForElementAndSendKeys(newListTitleInputId, listTitle);
        waitForElementAndClick(confirmNewListTitleButton);
        waitForElementAndClick(crossButton, "Cannot close article by X link", 5);
        waitForElementAndClick(myListsTab);
        waitForElementAndClick(listTitleXpath);

        //ToDo: add second article
        swipeElementToLeft(firstArticle, "Cannot delete article " + firstArticle.toString());
    }

}
