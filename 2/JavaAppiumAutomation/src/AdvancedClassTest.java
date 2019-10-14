import lib.CoreTestCase;
import lib.ui.MainPageObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AdvancedClassTest extends CoreTestCase {
    private lib.ui.MainPageObject MainPageObject;
    protected void setUp() throws Exception
    {
        super.setUp();
        MainPageObject = new MainPageObject(driver);
    }

    @Before
    public void preparations()
    {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    @Test
    public void testSaveAndDeleteArticle() {
        String searchedString = "Skyrim";
        By firstArticle = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='The Elder Scrolls V: Skyrim']");
        String secondArticleTitle = "The Elder Scrolls V: Skyrim – Dawnguard";
        By secondArticle = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + secondArticleTitle + "']");
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
        MainPageObject.waitForElementAndClick(searchInputInactiveId);
        MainPageObject.waitForElementAndSendKeys(searchInputInProcessId, searchedString);
        MainPageObject.waitForElementPresent(searchResultId);

        List<WebElement> articlesList = driver.findElements(searchResultId);
        Assert.assertNotEquals("Amount of articles equal 0. Searched string: " + searchedString,
                articlesList.size(), 0);
        MainPageObject.waitForElementAndClick(firstArticle, "Could not find first article", 10);
        MainPageObject.waitForElementPresent(articleTitle);
        MainPageObject.waitForElementAndClick(moreOptionsButtonId, "Could not find More Options button", 10);
        MainPageObject.waitForElementAndClick(addToReadingListOptionText, "Could not find Add to list option", 10);

        if (MainPageObject.checkElementVisibility(gotItButtonId)) {
            MainPageObject.waitForElementAndClick(gotItButtonId, "Could not find GOT IT button.", 5);
        } else {
            MainPageObject.waitForElementAndClick(createListButton, "Could not find Create new list button.", 5);
        }
        MainPageObject.waitForElementAndClear(newListTitleInputId, "Could not find input field of new title", 5);
        MainPageObject.waitForElementAndSendKeys(newListTitleInputId, listTitle);
        MainPageObject.waitForElementAndClick(confirmNewListTitleButton);
        MainPageObject.waitForElementAndClick(crossButton, "Cannot close article by X link", 10);

        //second article
        MainPageObject.waitForElementAndClick(searchInputInactiveId);
        MainPageObject.waitForElementAndSendKeys(searchInputInProcessId, searchedString);
        MainPageObject.waitForElementPresent(searchResultId);
        MainPageObject.waitForElementAndClick(secondArticle, "Could not find second article", 10);
        MainPageObject.waitForElementPresent(articleTitle);
        MainPageObject.waitForElementAndClick(moreOptionsButtonId, "Could not find More Options button", 10);
        MainPageObject.waitForElementAndClick(addToReadingListOptionText, "Could not find Add to list option", 10);
        MainPageObject.waitForElementAndClick(listTitleXpath);
        MainPageObject.waitForElementAndClick(crossButton, "Cannot close article by X link", 10);

        //open My Lists
        MainPageObject.waitForElementAndClick(myListsTab);
        MainPageObject.waitForElementAndClick(listTitleXpath);
        MainPageObject.swipeElementToLeft(firstArticle, "Cannot delete article " + firstArticle.toString());
        MainPageObject.waitForElementNotPresent(firstArticle, "Could not delete first article", 10);
        MainPageObject.waitForElementAndClick(secondArticle);
        String attrValue = MainPageObject.waitForElementAndGetAttribute(articleTitle, "text",
                "Could not find article by xpath " + articleTitle.toString(), 5);
        Assert.assertEquals("Expected title string is not equal to real", secondArticleTitle, attrValue);
    }

    @Test
    public void testAssertElementPresent()
    {
        String searchedString = "Skyrim";
        By articleInSearch = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='The Elder Scrolls V: Skyrim']");
        By articleTitle = By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']");
        By searchInputInactiveId = By.xpath("//*[@resource-id='org.wikipedia:id/search_container']");
        By searchInputInProcessId = By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']");
        By searchResultId = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']");

        MainPageObject.waitForElementAndClick(searchInputInactiveId);
        MainPageObject.waitForElementAndSendKeys(searchInputInProcessId, searchedString);
        MainPageObject.waitForElementPresent(searchResultId);
        MainPageObject.waitForElementAndClick(articleInSearch,
                "Could not find searched article: " +articleInSearch.toString() , 10);
        WebElement element;
        try {
            element = driver.findElement(articleTitle);
            Assert.assertTrue("Title of article not found", element.isDisplayed());
        }catch (NoSuchElementException e)
        {
            Assert.fail("Title of article was not found");
        }
    }
}
