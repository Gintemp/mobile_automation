package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;

abstract public class ArticlePageObject extends MainPageObject {
    protected static String
            TITLE,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            CREATE_NEW_FOLDER_BUTTON,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON,
            EXISTING_LIST;

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(TITLE, "Cannot find title article on page!", 15);
    }

    public WebElement waitForTitleElementOnIOS(String articleTitle) {
        return this.waitForElementPresent(TITLE.replace("{TITLE}", articleTitle), "Cannot find title article on page!", 15);
    }

    public String getArticleTitleOnIOS(String articleTitle) {
        WebElement titleElement = waitForTitleElementOnIOS(articleTitle);
        return titleElement.getAttribute("name");
    }

    public String getArticleTitle() {
        WebElement titleElement = waitForTitleElement();
        if (Platform.getInstance().isAndroid())
            return titleElement.getAttribute("text");
        else
            return titleElement.getAttribute("name");
    }

    public void swipeToFooter() {
        if (Platform.getInstance().isAndroid())
            this.swipeUpToFindElement(FOOTER_ELEMENT, "Cannot find the end of article", 40);
        else
            this.swipeUpTillElementAppear(FOOTER_ELEMENT, "Cannot find the end of article", 40);
    }

    public void addArticleToMyList(String nameOfFolder) {
        this.waitForElementAndClick(OPTIONS_BUTTON, "Could not find More Options button", 10);
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Could not find Add to list option", 10);

        if (this.checkElementVisibility(ADD_TO_MY_LIST_OVERLAY)) {
            this.waitForElementAndClick(ADD_TO_MY_LIST_OVERLAY, "Could not find GOT IT button.", 15);
            this.waitForElementAndClear(MY_LIST_NAME_INPUT, "Could not find input field of new title", 15);
            this.waitForElementAndSendKeys(MY_LIST_NAME_INPUT, nameOfFolder, "Cannot put text to articles folder input", 15);
            this.waitForElementAndClick(MY_LIST_OK_BUTTON, "Cannot press OK button", 15);
        } else {
            String existedList = EXISTING_LIST.replace("{LIST_TITLE}", nameOfFolder);
            if (this.checkElementVisibility(existedList)) {
                this.waitForElementAndClick(existedList, "Could not click existed list", 15);
            } else {
                this.waitForElementAndClick(CREATE_NEW_FOLDER_BUTTON, "Could not find Create new list button.", 15);
                this.waitForElementAndClear(MY_LIST_NAME_INPUT, "Could not find input field of new title", 15);
                this.waitForElementAndSendKeys(MY_LIST_NAME_INPUT, nameOfFolder, "Cannot put text to articles folder input", 15);
                this.waitForElementAndClick(MY_LIST_OK_BUTTON, "Cannot press OK button", 15);
            }
        }
    }

    public void addArticlesToMySaved()
    {
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list\n", 10);
    }

    public void closeArticle() {
        this.waitForElementAndClick(CLOSE_ARTICLE_BUTTON, "Cannot close article by X link", 5);
    }

    public void tapArticle() {
        driver.tap(1, this.waitForTitleElement(), 1);
    }

    public void assertTitlePresent() {
        assertElementPresent(TITLE, "Cannot find title in article");
    }
}
