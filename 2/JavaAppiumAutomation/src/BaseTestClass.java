import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

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

    protected AppiumDriver getDriver() {
        return driver;
    }

    protected WebElement waitForElementPresent(By by) {
        return waitForElementPresent(by, "Element " + by.toString() + " not found", 5);
    }

    protected WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    protected WebElement waitForElementPresent(By by, String errorMessage, long timeout) {
        WebDriverWait driverWait = new WebDriverWait(getDriver(), timeout);
        driverWait.withMessage(errorMessage + "\n");
        return driverWait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    protected WebElement waitForElementAndClick(By by) {
        return waitForElementAndClick(by, "Element " + by.toString() + " not found", 5);
    }

    protected WebElement waitForElementAndClick(By by, String errorMessage, long timeout) {
        WebElement element = waitForElementPresent(by, errorMessage, timeout);
        element.click();

        return element;
    }

    protected WebElement waitForElementAndClear(By by, String errorMessage, long timeout) {
        WebElement element = waitForElementPresent(by, errorMessage, timeout);
        element.clear();

        return element;
    }

    protected WebElement waitForElementAndSendKeys(By by, String value) {
        return waitForElementAndSendKeys(by, value, "Error\n", 10);
    }

    protected WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeout) {
        WebElement element = waitForElementPresent(by, errorMessage, timeout);
        element.sendKeys(value);

        return element;
    }

    protected boolean waitForElementNotPresent(By by) {
        return waitForElementNotPresent(by, "Error: element " + by.toString() + "has been found\n", 5);
    }

    protected boolean waitForElementNotPresent(By by, String errorMessage, long timeout) {
        WebDriverWait driverWait = new WebDriverWait(getDriver(), timeout);
        driverWait.withMessage(errorMessage);
        return driverWait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(getDriver());
        Dimension size = getDriver().manage().window().getSize();
        int x = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);

        action.press(x, startY).waitAction(timeOfSwipe).moveTo(x, endY).release().perform();
    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String errorMessage, int maxSwipes) {
        int alreadySwipes = 0;
        while (getDriver().findElements(by).size() == 0) {
            if (alreadySwipes > maxSwipes) {
                waitForElementPresent(by, errorMessage, 0);
                return;
            }

            swipeUpQuick();
            ++alreadySwipes;
        }
    }

    protected void swipeDown(int timeOfSwipe) {
        TouchAction action = new TouchAction(getDriver());
        Dimension size = getDriver().manage().window().getSize();
        int x = size.width / 2;
        int startY = (int) (size.height * 0.2);
        int endY = (int) (size.height * 0.8);

        action
                .press(x, startY)
                .waitAction(timeOfSwipe)
                .moveTo(x, endY)
                .release()
                .perform();
    }

    protected void swipeDownQuick() {
        swipeDown(200);
    }

    protected void swipeElementToLeft(By by, String errorMessage)
    {
        WebElement element = waitForElementPresent(by, errorMessage);
        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction(getDriver());
        action
                .press(rightX, middleY)
                .waitAction(300)
                .moveTo(leftX, middleY)
                .release()
                .perform();
    }

    protected String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeout)
    {
        WebElement element = waitForElementPresent(by, errorMessage, timeout);
        return element.getAttribute(attribute);
    }
}
