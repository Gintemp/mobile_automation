package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.regex.Pattern;

public class MainPageObject {
    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver) {
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
        if (driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver) driver);
            Dimension size = driver.manage().window().getSize();
            int x = size.width / 2;
            int startY = (int) (size.height * 0.8);
            int endY = (int) (size.height * 0.2);

            action.press(x, startY).waitAction(timeOfSwipe).moveTo(x, endY).release().perform();
        } else {
            System.out.println("swipeUp() does nothing to platform " + Platform.getInstance().getPlatformVar());
        }
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

    public void swipeUpTillElementAppear(String locator, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;
        while (!this.isElementLocatedOnTheScreen(locator)) {
            if (alreadySwiped > maxSwipes) {
                Assert.assertTrue(errorMessage, this.isElementLocatedOnTheScreen(locator));
            }

            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    public boolean isElementLocatedOnTheScreen(String locator) {
        int elementLocationByY = this.waitForElementPresent(locator, "Cannot find element by locator", 5)
                .getLocation().getY();
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            Object jsResult = JSExecutor.executeScript("return window.pageYOffset");
            elementLocationByY -= Integer.parseInt(jsResult.toString());
        }
        int screenSizeByY = driver.manage().window().getSize().getHeight();
        return elementLocationByY < screenSizeByY;
    }

    public void swipeDown(int timeOfSwipe) {
        if (driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver) driver);
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
        } else {
            System.out.println("swipeDown() does nothing to platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void swipeDownQuick() {
        swipeDown(200);
    }

    public void swipeElementToLeft(String locator, String errorMessage) {
        if (driver instanceof AppiumDriver) {
            WebElement element = waitForElementPresent(locator, errorMessage);
            int leftX = element.getLocation().getX();
            int rightX = leftX + element.getSize().getWidth();
            int upperY = element.getLocation().getY();
            int lowerY = upperY + element.getSize().getHeight();
            int middleY = (upperY + lowerY) / 2;

            TouchAction action = new TouchAction((AppiumDriver) driver);
            action.press(rightX, middleY);
            action.waitAction(400);
            if (Platform.getInstance().isAndroid())
                action.moveTo(leftX, middleY);
            else {
                int offsetX = (-1 * element.getSize().getWidth());
                action.moveTo(offsetX, 0);
            }
            action.release();

            action.perform();
        } else {
            System.out.println("swipeElementToLeft() does nothing to platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void clickElementToTheRightUpperCorner(String locator, String errorMessage) {
        if (driver instanceof AppiumDriver) {
            WebElement element = this.waitForElementPresent(locator + "/..", errorMessage);
            int rightX = element.getLocation().getX();
            int upperY = element.getLocation().getY();
            int lowerY = upperY + element.getSize().getHeight();
            int middleY = (upperY + lowerY) / 2;
            int width = element.getSize().getWidth();

            int pointToClickX = (rightX + width) - 3;
            int pointToClickY = middleY;

            TouchAction action = new TouchAction((AppiumDriver) driver);
            action.tap(pointToClickX, pointToClickY).perform();
        } else {
            System.out.println("clickElementToTheRightUpperCorner() do nothing to platform " + Platform.getInstance().getPlatformVar());
        }
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
        } else if (byType.equals("css")) {
            return By.cssSelector(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locatorWithType);
        }
    }

    public void scrollWebPageUp() {
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            JSExecutor.executeScript("window.scrollBy(0, 250)");
        } else {
            System.out.println("scrollWebPageUp() do nothing to platform" + Platform.getInstance().getPlatformVar());
        }
    }

    public void scrollWebPageTillElementNotVisible(String locator, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;
        WebElement element = this.waitForElementPresent(locator, errorMessage);

        while (!this.isElementLocatedOnTheScreen(locator)) {
            scrollWebPageUp();
            ++alreadySwiped;
            if (alreadySwiped > maxSwipes) {
                Assert.assertTrue(errorMessage, element.isDisplayed());
            }
        }
    }

    public boolean isElementPresent(String locator) {
        return getAmountOfElements(locator) > 0;
    }

    public void tryClickElementWithFewAttempts(String locator, String errorMessage, int amountOfAttempts) {
        int currentAttempts = 0;
        boolean needMoreAttempts = true;

        while (needMoreAttempts) {
            try {
                this.waitForElementAndClick(locator, errorMessage, 1);
                needMoreAttempts = false;
            } catch (Exception e) {
                if (currentAttempts > amountOfAttempts) {
                    this.waitForElementAndClick(locator, errorMessage, 1);
                }
            }
            ++currentAttempts;
        }
    }
}
