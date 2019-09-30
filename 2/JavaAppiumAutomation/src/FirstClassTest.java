import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstClassTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "D:\\git\\mobile_automation\\2\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void findSearchText() {
        String searchedString = "Search…";
        String searchInputInactiveId = "org.wikipedia:id/search_container";
        String searchInputInProcessId = "org.wikipedia:id/search_src_text";

        waitForElementAndClick(
                By.id(searchInputInactiveId),
                "Element " + searchInputInactiveId + " not found",
                5
        );
        WebElement element = waitForElementPresent(By.id(searchInputInProcessId));
        Assert.assertEquals(
                "Expected default text in search field is not equal " + searchedString,
                searchedString,
                element.getAttribute("text"));
    }

    @Test
    public void searchTextAndClearResult() {
        String searchedString = "Java";
        String searchInputInactiveId = "org.wikipedia:id/search_container";
        String searchInputInProcessId = "org.wikipedia:id/search_src_text";
        String searchResultId = "org.wikipedia:id/page_list_item_container";
        String cancelSearchId = "org.wikipedia:id/search_close_btn";

        waitForElementAndClick(By.id(searchInputInactiveId));
        waitForElementAndSendKeys(By.id(searchInputInProcessId), searchedString);
        waitForElementPresent(By.id(searchResultId));

        List<WebElement> articlesList = driver.findElements(By.id(searchResultId));
        Assert.assertNotEquals(
                "Amount of articles equal 0. Searched string: " + searchedString, articlesList.size(), 0);

        waitForElementAndClick(By.id(cancelSearchId));
        waitForElementNotPresent(By.id(searchResultId));

        List<WebElement> emptyArticlesList = driver.findElements(By.id(searchResultId));
        Assert.assertEquals(
                "Amount of articles not equal 0.", emptyArticlesList.size(), 0);

    }

    @Test
    public void searchTextInResult() {
        String searchedString = "Java";
        String searchInputInactiveId = "org.wikipedia:id/search_container";
        String searchInputInProcessId = "org.wikipedia:id/search_src_text";
        String searchResultId = "org.wikipedia:id/page_list_item_container";
        String resultTitleId = "org.wikipedia:id/page_list_item_title";

        waitForElementAndClick(By.id(searchInputInactiveId));
        waitForElementAndSendKeys(By.id(searchInputInProcessId), searchedString);
        waitForElementPresent(By.id(searchResultId));

        List<WebElement> articlesList = driver.findElements(By.id(searchResultId));
        Assert.assertNotEquals(
                "Amount of articles equal 0. Searched string: " + searchedString, articlesList.size(), 0);

        boolean isAsserted = false;
        StringBuilder errorString = new StringBuilder();
        for (WebElement e : articlesList) {
            WebElement resultTitle = e.findElement(By.id(resultTitleId));
            String titleText = resultTitle.getAttribute("text");

            if (!titleText.toLowerCase().contains(searchedString.toLowerCase())) {
                isAsserted = true;
                errorString.append("String " + searchedString + " was not found in string: " + titleText + "\n");
            }
        }

        Assert.assertFalse(errorString.toString(), isAsserted);
    }

    private WebElement waitForElementPresent(By by) {
        return waitForElementPresent(by, "Element " + by.toString() + " not found", 5);
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeout) {
        WebDriverWait driverWait = new WebDriverWait(driver, timeout);
        driverWait.withMessage(errorMessage + "\n");
        return driverWait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClick(By by) {
        return waitForElementAndClick(by, "Element " + by.toString() + " not found", 5);
    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeout) {
        WebElement element = waitForElementPresent(by, errorMessage, timeout);
        element.click();

        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value) {
        return waitForElementAndSendKeys(by, value, "Error\n", 10);
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeout) {
        WebElement element = waitForElementPresent(by, errorMessage, timeout);
        element.sendKeys(value);

        return element;
    }

    private boolean waitForElementNotPresent(By by) {
        return waitForElementNotPresent(by, "Error: element " + by.toString() + "has been found\n", 5);
    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeout) {
        WebDriverWait driverWait = new WebDriverWait(driver, timeout);
        driverWait.withMessage(errorMessage);
        return driverWait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }


}
