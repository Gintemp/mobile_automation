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

public class FirstClassTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","D:\\git\\mobile_automation\\2\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }

    @Test
    public void findSearchText()
    {
        String searchedString = "Search…";
        String searchInputInactiveId = "org.wikipedia:id/search_container";
        String searchInputInProcessId = "org.wikipedia:id/search_src_text";

        waitForElementAndClick(
                By.id(searchInputInactiveId),
                "Element " + searchInputInactiveId + " not found",
                5
        );
        WebElement element = waitForElementPresent(By.id(searchInputInProcessId), searchInputInactiveId);
        Assert.assertEquals(
                "Expected default text in search field is not equal " + searchedString,
                searchedString,
                element.getAttribute("text"));
    }

    private WebElement waitForElementPresent(By by, String searchInputInactiveId)
    {
        return waitForElementPresent(by, "Element " + searchInputInactiveId + " not found", 5);
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeout)
    {
        WebDriverWait driverWait = new WebDriverWait(driver, timeout);
        driverWait.withMessage(errorMessage + "\n");
        return driverWait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeout)
    {
        WebElement element = waitForElementPresent(by, errorMessage, timeout);
        element.click();

        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeout)
    {
        WebElement element = waitForElementPresent(by, errorMessage, timeout);
        element.sendKeys(value);

        return element;
    }

    private boolean waiForElementNotPresent(By by, String errorMessage, long timeout)
    {
        WebDriverWait driverWait = new WebDriverWait(driver, timeout);
        driverWait.withMessage(errorMessage);
        return driverWait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }
}
