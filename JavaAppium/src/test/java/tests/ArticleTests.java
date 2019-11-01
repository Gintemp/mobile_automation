package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

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
        }
        else {
            ArticlePageObject.waitForTitleElementOnIOS(firstArticleTitle);
            ArticlePageObject.addArticlesToMySaved();
        }
        if(Platform.getInstance().isIOS())
            ArticlePageObject.closeArticle();
        ArticlePageObject.closeArticle();

        //second article
        SearchPageObject.initSearchInput();
        if(Platform.getInstance().isAndroid())
            SearchPageObject.typeSearchLine(searchedString);
        SearchPageObject.waitForAnySearchResult();
        SearchPageObject.clickByArticleWithSubstring(secondArticleTitle);
        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.waitForTitleElement();
            ArticlePageObject.addArticleToMyList(listTitle);
        }
        else {
            ArticlePageObject.waitForTitleElementOnIOS(secondArticleTitle);
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        //open My Lists
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();
        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid())
            MyListsPageObject.openFolderByName(listTitle);
        MyListsPageObject.swipeByArticleToDelete(secondArticleTitle);
        MyListsPageObject.waitForArticleToDisappear(secondArticleTitle);

        //альтернативная проверка
        if(Platform.getInstance().isIOS()) {
            MyListsPageObject.typeMyListsSearchLine(firstArticleTitle);
            MyListsPageObject.waitForAnySearchResultInMyLists();
        }

        MyListsPageObject.openArticle(firstArticleTitle);
        String currentTitle;
        if (Platform.getInstance().isAndroid())
            currentTitle = ArticlePageObject.getArticleTitle();
        else
            currentTitle = ArticlePageObject.getArticleTitleOnIOS(firstArticleTitle);
        assertEquals("Expected title string is not equal to real", firstArticleTitle, currentTitle);
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
