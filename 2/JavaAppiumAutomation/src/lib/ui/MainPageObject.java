package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.regex.Pattern;

public class MainPageObject {
    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(String locator) {
        return waitForElementPresent(locator, "Element " + locator + " not found", 5);
    }

    public WebElement waitForElementPresent(String locator, String errorMessage) {
        return waitForElementPresent(locator, errorMessage, 5);
    }

    public WebElement waitForElementPresent(String locator, String errorMessage, long timeout) {
        By by = this.getLocatorByString(locator);
        WebDriverWait driverWait = new WebDriverWait(driver, timeout);
        driverWait.withMessage(errorMessage + "\n");
        return driverWait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public WebElement waitForElementAndClick(String locator) {
        return waitForElementAndClick(locator, "Element " + locator + " not found, can't click\n", 5);
    }

    public WebElement waitForElementAndClick(String locator, String errorMessage, long timeout) {

        WebElement element = waitForElementPresent(locator, errorMessage, timeout);
        element.click();

        return element;
    }

    public WebElement waitForElementAndClear(String locator, String errorMessage, long timeout) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeout);
        element.clear();

        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value) {
        return waitForElementAndSendKeys(locator, value, "Error\n", 10);
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String errorMessage, long timeout) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeout);
        element.sendKeys(value);

        return element;
    }

    public boolean waitForElementNotPresent(String locator) {
        return waitForElementNotPresent(locator, "Error: element " + locator + "has been found\n", 5);
    }

    public boolean waitForElementNotPresent(String locator, String errorMessage, long timeout) {
        By by = this.getLocatorByString(locator);
        WebDriverWait driverWait = new WebDriverWait(driver, timeout);
        driverWait.withMessage(errorMessage);
        return driverWait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public boolean checkElementVisibility(String locator) {
        By by = this.getLocatorByString(locator);
        try {
            driver.findElement(by);
            return true;
        } catch (Exception e) {
            System.out.println(by.toString() + " not found.\n");
            return false;
        }
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

    public void swipeUpToFindElement(String locator, String errorMessage, int maxSwipes) {
        By by = this.getLocatorByString(locator);
        int alreadySwipes = 0;
        while (driver.findElements(by).size() == 0) {
            if (alreadySwipes > maxSwipes) {
                waitForElementPresent(locator, errorMessage, 0);
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

    public void swipeElementToLeft(String locator, String errorMessage) {
        WebElement element = waitForElementPresent(locator, errorMessage);
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

    public String waitForElementAndGetAttribute(String locator, String attribute, String errorMessage, long timeout) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeout);
        return element.getAttribute(attribute);
    }

    public int getAmountOfElements(String locator) {
        By by = this.getLocatorByString(locator);
        return driver.findElements(by).size();
    }

    public void assertElementNotPresent(String locator, String errorMessage) {
        int amountOfSearch = getAmountOfElements(locator);
        if (amountOfSearch > 0) {
            String defaultMessage = "An element " + locator + " suposed to br not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    public void assertElementPresent(String locator, String errorMessage) {
        int amountOfSearch = getAmountOfElements(locator);
        if (amountOfSearch != 1) {
            String defaultMessage = "An element supposed to have one title\n";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    private By getLocatorByString(String locatorWithType) {
        String[] explodedLocator = locatorWithType.split(Pattern.quote(":"), 2);
        String byType = explodedLocator[0];
        String locator = explodedLocator[1];

        if (byType.equals("xpath")) {
            return By.xpath(locator);
        } else if (byType.equals("id")) {
            return By.id(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locatorWithType);
        }
    }
}
