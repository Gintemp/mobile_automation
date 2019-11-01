package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSMyListsPageObject extends MyListsPageObject {
    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name, '{TITLE}')]";
        SEARCH_IN_ARTICLES = "xpath://XCUIElementTypeSearchField[@name='Search']";
        RESULT_IN_SEARCH = "xpath://XCUIElementTypeCell";
    }

    public iOSMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
