package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class iOSMyListsPageObject extends MyListsPageObject {
    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name, '{TITLE}')]";
        SEARCH_IN_ARTICLES = "xpath://XCUIElementTypeSearchField[@name='Search']";
        RESULT_IN_SEARCH = "xpath://XCUIElementTypeCell";
    }

    public iOSMyListsPageObject(AppiumDriver driver) {
        super(driver);
    }
}
