package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {
    private static final String
            TITLE = "org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "//*[@text='View page in browser']",
            OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
            ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
            CREATE_NEW_FOLDER_BUTTON = "org.wikipedia:id/create_button",
            MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "//*[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(By.id(TITLE), "Cannot find title article on page!", 15);
    }

    public String getArticleTitle() {
        WebElement titleElement = waitForTitleElement();
        return titleElement.getAttribute("text");
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(By.xpath(FOOTER_ELEMENT), "Cannot find the end of article", 20);
    }

    public void addArticleToMyList(String nameOfFolder) {
        this.waitForElementAndClick(By.xpath(OPTIONS_BUTTON), "Could not find More Options button", 10);
        this.waitForElementAndClick(By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON), "Could not find Add to list option", 10);

        if (this.checkElementVisibility(By.id(ADD_TO_MY_LIST_OVERLAY))) {
            this.waitForElementAndClick(By.id(ADD_TO_MY_LIST_OVERLAY), "Could not find GOT IT button.", 5);
        } else {
            this.waitForElementAndClick(By.id(CREATE_NEW_FOLDER_BUTTON), "Could not find Create new list button.", 5);
        }
        this.waitForElementAndClear(By.id(MY_LIST_NAME_INPUT), "Could not find input field of new title", 5);
        this.waitForElementAndSendKeys(By.id(MY_LIST_NAME_INPUT), nameOfFolder, "Cannotput text to articles folder input", 5);
        this.waitForElementAndClick(By.id(MY_LIST_OK_BUTTON), "Cannot press OK button", 5);
    }

    public void closeArticle()
    {
        this.waitForElementAndClick(By.xpath(CLOSE_ARTICLE_BUTTON), "Cannot close article by X link", 10);
    }
}
