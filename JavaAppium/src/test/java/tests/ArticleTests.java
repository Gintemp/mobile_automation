package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

    private static final String login = "QATestTravel";
    private static final String password = "c2347ryg#8R";

    @Test
    public void testSaveAndDeleteArticle() {
        String searchedString = "Skyrim";
        String listTitle = "Tests for Nords";
        String firstArticleTitle = "The Elder Scrolls V: Skyrim";
        String secondArticleTitle = "The Elder Scrolls V: Skyrim – Dawnguard";

        //first article
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchedString);
        SearchPageObject.waitForAnySearchResult();
        SearchPageObject.clickByArticleWithSubstring(firstArticleTitle);
        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.waitForTitleElement();
            ArticlePageObject.addArticleToMyList(listTitle);
        } else if (Platform.getInstance().isIOS()) {
            ArticlePageObject.waitForTitleElementOnIOS(firstArticleTitle);
            ArticlePageObject.addArticlesToMySaved();
        } else {
            ArticlePageObject.addArticlesToMySaved();
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();
            ArticlePageObject.waitForTitleElement();
            assertEquals("We are not on the same page after login.", firstArticleTitle, ArticlePageObject.getArticleTitle());
            ArticlePageObject.addArticlesToMySaved();
        }

        if (Platform.getInstance().isIOS())
            ArticlePageObject.closeArticle();
        ArticlePageObject.closeArticle();

        //second article
        SearchPageObject.initSearchInput();
        if (!Platform.getInstance().isIOS())
            SearchPageObject.typeSearchLine(searchedString);
        SearchPageObject.waitForAnySearchResult();
        SearchPageObject.clickByArticleWithSubstring(secondArticleTitle);
        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.waitForTitleElement();
            ArticlePageObject.addArticleToMyList(listTitle);
        } else if (Platform.getInstance().isIOS()) {
            ArticlePageObject.waitForTitleElementOnIOS(firstArticleTitle);
            ArticlePageObject.addArticlesToMySaved();
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        //open My Lists
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();
        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid())
            MyListsPageObject.openFolderByName(listTitle);
        MyListsPageObject.swipeByArticleToDelete(secondArticleTitle);
        MyListsPageObject.waitForArticleToDisappear(secondArticleTitle);

        //альтернативная проверка
        if (Platform.getInstance().isIOS()) {
            MyListsPageObject.typeMyListsSearchLine(firstArticleTitle);
            MyListsPageObject.waitForAnySearchResultInMyLists();
        }

        MyListsPageObject.openArticle(firstArticleTitle);
        String currentTitle;
        if (Platform.getInstance().isAndroid()) {
            currentTitle = ArticlePageObject.getArticleTitle();
            assertEquals("Expected title string is not equal to real", firstArticleTitle, currentTitle);
        } else if (Platform.getInstance().isIOS()) {
            currentTitle = ArticlePageObject.getArticleTitleOnIOS(firstArticleTitle);
            assertEquals("Expected title string is not equal to real", firstArticleTitle, currentTitle);
        }

        if (Platform.getInstance().isMW())
            assertTrue("Expected title string is not equal to real",
                    driver.getCurrentUrl().contains(firstArticleTitle.replace(" ", "_")));
    }

    @Test
    public void testAssertElementPresent() {
        String searchedString = "Skyrim";
        String articleInSearch = "The Elder Scrolls V: Skyrim";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchedString);
        SearchPageObject.waitForAnySearchResult();
        SearchPageObject.clickByArticleWithSubstring(articleInSearch);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.assertTitlePresent();
    }
}
