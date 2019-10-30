package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {
    private static final String
            TITLE = "id:org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "xpath://*[@text='View page in browser']",
            OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']",
            ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button",
            CREATE_NEW_FOLDER_BUTTON = "id:org.wikipedia:id/create_button",
            MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "xpath://*[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']",
            EXISTING_LIST = "xpath://*[@text='{LIST_TITLE}']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(TITLE, "Cannot find title article on page!", 15);
    }

    public String getArticleTitle() {
        WebElement titleElement = waitForTitleElement();
        return titleElement.getAttribute("text");
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(FOOTER_ELEMENT, "Cannot find the end of article", 20);
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

    public void closeArticle() {
        this.waitForElementAndClick(CLOSE_ARTICLE_BUTTON, "Cannot close article by X link", 10);
    }

    public void tapArticle() {
        driver.tap(1, this.waitForTitleElement(), 1);
    }

    public void assertTitlePresent() {
        assertElementPresent(TITLE, "Cannot find title in article");
    }
}
