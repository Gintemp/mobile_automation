package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject {
    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            SEARCH_IN_ARTICLES,
            RESULT_IN_SEARCH,
            REMOVE_FROM_SAVED_BUTTON;

    private static String getFolderXpathByName(String nameOfFolder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }

    private static String getSavedArticleXpathByTitle(String articleTitle) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle);
    }

    private static String getRemoveButtonByTitle(String articleTitle) {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", articleTitle);
    }

    public MyListsPageObject(RemoteWebDriver driver) {
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
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);

        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.swipeElementToLeft(
                    articleXpath,
                    "Cannot delete article " + articleTitle);
        } else {
            String removeLocator = getRemoveButtonByTitle(articleTitle);
            this.waitForElementAndClick(removeLocator, "Cannot click button to remove article from saved\n", 10);
        }

        if (Platform.getInstance().isIOS()) {
            this.clickElementToTheRightUpperCorner(articleXpath, "Cannot find saved article.\n");
        }

        if (Platform.getInstance().isMW()) {
            driver.navigate().refresh();
        }
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

    public void typeMyListsSearchLine(String searchMask) {
        this.waitForElementAndSendKeys(SEARCH_IN_ARTICLES, searchMask, "Cannot send keys to search input in my lists", 10);
    }

    public void waitForAnySearchResultInMyLists() {
        this.waitForElementPresent(RESULT_IN_SEARCH, "Cannot find search result");
    }
}
