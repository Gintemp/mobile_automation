package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidSearchPageObject extends SearchPageObject {
    static {
        SEARCH_INIT_ELEMENT = "id:org.wikipedia:id/search_container";
        SEARCH_INPUT = "id:org.wikipedia:id/search_src_text";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text, '{SUBSTRING}')]";
        SEARCH_RESULT_BY_SUBSTRING_ARTICLE_AND_DESCRIPTION_TPL =
                "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{ARTICLE}']/../*[@text='{DESCRIPTION}']";
        SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
        SEARCH_RESULT_ELEMENT = "id:org.wikipedia:id/page_list_item_container";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']";
        SEARCH_RESULT_TITLE = "id:org.wikipedia:id/page_list_item_container";
    }

    public AndroidSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
