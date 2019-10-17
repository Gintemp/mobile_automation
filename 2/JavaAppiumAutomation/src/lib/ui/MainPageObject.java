package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPageObject {
    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver)
    {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(By by) {
        return waitForElementPresent(by, "Element " + by.toString() + " not found", 5);
    }

    public WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    public WebElement waitForElementPresent(By by, String errorMessage, long timeout) {
        WebDriverWait driverWait = new WebDriverWait(driver, timeout);
        driverWait.withMessage(errorMessage + "\n");
        return driverWait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public WebElement waitForElementExists(By by, String errorMessage, long timeout) {
        WebDriverWait driverWait = new WebDriverWait(driver, timeout);
        driverWait.withMessage(errorMessage + "\n");
        return driverWait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public WebElement waitForElementAndClick(By by) {
        return waitForElementAndClick(by, "Element " + by.toString() + " not found", 5);
    }

    public WebElement waitForElementAndClick(By by, String errorMessage, long timeout) {
        WebElement element = waitForElementPresent(by, errorMessage, timeout);
        element.click();

        return element;
    }

    public WebElement waitForElementAndClear(By by, String errorMessage, long timeout) {
        WebElement element = waitForElementPresent(by, errorMessage, timeout);
        element.clear();

        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String value) {
        return waitForElementAndSendKeys(by, value, "Error\n", 10);
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeout) {
        WebElement element = waitForElementPresent(by, errorMessage, timeout);
        element.sendKeys(value);

        return element;
    }

    public boolean waitForElementNotPresent(By by) {
        return waitForElementNotPresent(by, "Error: element " + by.toString() + "has been found\n", 5);
    }

    public boolean waitForElementNotPresent(By by, String errorMessage, long timeout) {
        WebDriverWait driverWait = new WebDriverWait(driver, timeout);
        driverWait.withMessage(errorMessage);
        return driverWait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public boolean checkElementVisibility(By by) {
        WebDriverWait driverWait = new WebDriverWait(driver, 10);
        return driver.findElement(by).isDisplayed();

    }


    public void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);

        action.press(x, startY).waitAction(timeOfSwipe).moveTo(x, endY).release().perform();
    }

    public void swipeUpQuick() {
        swipeUp(200);
    }

    public void swipeUpToFindElement(By by, String errorMessage, int maxSwipes) {
        int alreadySwipes = 0;
        while (driver.findElements(by).size() == 0) {
            if (alreadySwipes > maxSwipes) {
                waitForElementPresent(by, errorMessage, 0);
                return;
            }

            swipeUpQuick();
            ++alreadySwipes;
        }
    }

    public void swipeDown(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
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

    public void swipeDownQuick() {
        swipeDown(200);
    }

    public void swipeElementToLeft(By by, String errorMessage) {
        WebElement element = waitForElementPresent(by, errorMessage);
        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(rightX, middleY)
                .waitAction(400)
                .moveTo(leftX, middleY)
                .release()
                .perform();
    }

    public String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeout) {
        WebElement element = waitForElementPresent(by, errorMessage, timeout);
        return element.getAttribute(attribute);
    }

    public int getAmountOfElements(By by)
    {
        return driver.findElements(by).size();
    }

    public void assertElementNotPresent(By by, String errorMessage)
    {
        int amountOfSearch = getAmountOfElements(by);
        Assert.assertEquals(errorMessage, amountOfSearch, 0);
    }
}
