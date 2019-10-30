package lib.ui;

import io.appium.java_client.AppiumDriver;

public class MyListsPageObject extends MainPageObject {
    public static final String
            FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']",
            ARTICLE_BY_TITLE_TPL = "xpath://*[@text='{TITLE}']";

    private static String getFolderXpathByName(String nameOfFolder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }

    private static String getSavedArticleXpathByTitle(String articleTitle) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle);
    }

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void openFolderByName(String nameOfFolder) {
        this.waitForElementAndClick(
                getFolderXpathByName(nameOfFolder),
                "Cannot find folder by name " + nameOfFolder,
                5
        );
    }

    public void swipeByArticleToDelete(String articleTitle) {
        this.waitForArticleToAppear(articleTitle);
        this.swipeElementToLeft(
                getSavedArticleXpathByTitle(articleTitle),
                "Cannot delete article " + articleTitle);
        this.waitForArticleToDisappear(articleTitle);
    }

    public void waitForArticleToDisappear(String articleTitle) {
        this.waitForElementNotPresent(getSavedArticleXpathByTitle(articleTitle), "Saved article still preset with title " + articleTitle, 15);
    }

    public void waitForArticleToAppear(String articleTitle) {
        this.waitForElementPresent(getSavedArticleXpathByTitle(articleTitle), "Cannot find saved article by title " + articleTitle, 15);
    }

    public void openArticle(String articleTitle) {
        this.waitForElementAndClick(
                getSavedArticleXpathByTitle(articleTitle),
                "Cannot open article " + articleTitle + " in list",
                15);
    }
}
