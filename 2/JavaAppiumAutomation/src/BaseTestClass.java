import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class BaseTestClass {
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

    AppiumDriver getDriver() {
        return driver;
    }

    WebElement waitForElementPresent(By by) {
        return waitForElementPresent(by, "Element " + by.toString() + " not found", 5);
    }

    WebElement waitForElementPresent(By by, String errorMessage, long timeout) {
        WebDriverWait driverWait = new WebDriverWait(getDriver(), timeout);
        driverWait.withMessage(errorMessage + "\n");
        return driverWait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    WebElement waitForElementAndClick(By by) {
        return waitForElementAndClick(by, "Element " + by.toString() + " not found", 5);
    }

    WebElement waitForElementAndClick(By by, String errorMessage, long timeout) {
        WebElement element = waitForElementPresent(by, errorMessage, timeout);
        element.click();

        return element;
    }

    WebElement waitForElementAndSendKeys(By by, String value) {
        return waitForElementAndSendKeys(by, value, "Error\n", 10);
    }

    WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeout) {
        WebElement element = waitForElementPresent(by, errorMessage, timeout);
        element.sendKeys(value);

        return element;
    }

    boolean waitForElementNotPresent(By by) {
        return waitForElementNotPresent(by, "Error: element " + by.toString() + "has been found\n", 5);
    }

    boolean waitForElementNotPresent(By by, String errorMessage, long timeout) {
        WebDriverWait driverWait = new WebDriverWait(getDriver(), timeout);
        driverWait.withMessage(errorMessage);
        return driverWait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }
}
