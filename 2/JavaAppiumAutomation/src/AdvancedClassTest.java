import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class AdvancedClassTest extends CoreTestCase {

    @Test
    public void testSaveAndDeleteArticle() {
        String searchedString = "Skyrim";
        String listTitle = "Tests for Nords";
        String firstArticleTitle = "The Elder Scrolls V: Skyrim";
        String secondArticleTitle = "The Elder Scrolls V: Skyrim – Dawnguard";

        //first article
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchedString);
        SearchPageObject.waitForAnySearchResult();
        SearchPageObject.clickByArticleWithSubstring(firstArticleTitle);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.addArticleToMyList(listTitle);
        ArticlePageObject.closeArticle();

        //second article
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchedString);
        SearchPageObject.waitForAnySearchResult();
        SearchPageObject.clickByArticleWithSubstring("The Elder Scrolls V: Skyrim – Dawnguard");
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.addArticleToMyList(listTitle);
        ArticlePageObject.closeArticle();

        //open My Lists
        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();
        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(listTitle);
        MyListsPageObject.swipeByArticleToDelete(firstArticleTitle);
        MyListsPageObject.waitForArticleToDisappear(firstArticleTitle);
        MyListsPageObject.openArticle(secondArticleTitle);
        assertEquals("Expected title string is not equal to real", secondArticleTitle, ArticlePageObject.getArticleTitle());
    }

    @Test
    public void testAssertElementPresent() {
        String searchedString = "Skyrim";
        String articleInSearch = "The Elder Scrolls V: Skyrim";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchedString);
        SearchPageObject.waitForAnySearchResult();
        SearchPageObject.clickByArticleWithSubstring(articleInSearch);

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.assertTitlePresent();
    }
}
