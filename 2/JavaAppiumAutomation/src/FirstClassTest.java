import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FirstClassTest extends CoreTestCase {

    @Test
    public void testFindSearchText() {
        String searchedString = "Search…";
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        assertEquals(
                "Expected default text in search field is not equal " + searchedString,
                searchedString,
                SearchPageObject.getSearchInputText());
    }

    @Test
    public void testSearchTextAndClearResult() {
        String searchedString = "Java";
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchedString);
        SearchPageObject.waitForAnySearchResult();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testSearchTextInResult() {
        String searchedString = "Java";
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchedString);
        SearchPageObject.waitForAnySearchResult();

        boolean isAsserted = false;
        StringBuilder errorString = new StringBuilder();
        for (String s : SearchPageObject.getListOfTitlesInResults()) {
            if (!s.toLowerCase().contains(searchedString.toLowerCase())) {
                isAsserted = true;
                errorString.append("String ")
                        .append(searchedString)
                        .append(" was not found in string: ")
                        .append(s).append("\n");
            }
        }
        assertFalse(errorString.toString(), isAsserted);
    }
}
