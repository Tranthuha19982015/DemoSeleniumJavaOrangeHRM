package hatester.keywords;

import hatester.drivers.DriverManager;
import hatester.helpers.PropertiesHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WebUI {
    private static int WAIT_TIMEOUT = Integer.parseInt(PropertiesHelper.getValue("WAIT_TIMEOUT"));
    private static double STEP_TIME = Double.parseDouble(PropertiesHelper.getValue("STEP_TIME"));
    private static int PAGE_LOAD_TIMEOUT = Integer.parseInt(PropertiesHelper.getValue("PAGE_LOAD_TIMEOUT"));

    public static void sleep(double second) {
        try {
            Thread.sleep((long) (second * 1000)); //ép kiểu do Thread.sleep chỉ nhận kiểu long
        } catch (
                InterruptedException ie) { //ném ra InterruptedException nếu luồng bị “đánh thức” (interrupt). //Bắt exception để tránh compile error.
            throw new RuntimeException(ie); //Nếu bị interrupt, ném exception dạng unchecked (RuntimeException) để test dừng luôn.
        }
    }

    public static void logConsole(String message) {
        System.out.println(message);
    }

    //Hàm trả về 1 phần tử WebElement
    public static WebElement getWebElement(By by) {
        return DriverManager.getDriver().findElement(by);  //Nếu không tìm thấy → Selenium ném NoSuchElementException.
    }

    //Hàm trả về 1 danh sách các phần tử WebElement
    public static List<WebElement> getWebElements(By by) {
        return DriverManager.getDriver().findElements(by);  //Nếu không tìm thấy → trả về list rỗng (không ném exception).
    }

    // highlight phần tử
    public static void highlightElement(By by) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].style.border='3px solid red';", getWebElement(by));
    }

    public static void highlightElement(By by, String color) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].style.border='3px solid " + color + "';", getWebElement(by));
    }

    //đợi phần tử hiển thị lên giao diện (có trong DOM)
    public static WebElement waitForElementVisible(By by) {
        WebElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_TIMEOUT), Duration.ofMillis(500)); //polling interval (mỗi 500ms sẽ thử lại).
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));  //Chờ cho đến khi phần tử được visible (có trong DOM + hiển thị).
            highlightElement(by);
            return element;
        } catch (Throwable error) {
            logConsole("Timeout waiting for the element Visible. " + by.toString());
            Assert.fail("Timeout waiting for the element Visible." + by.toString());
        }
        return element;
    }

    public static WebElement waitForElementVisible(By by, int waitTimeout) {
        WebElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(waitTimeout), Duration.ofMillis(500));
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            highlightElement(by);
            return element;
        } catch (Throwable error) {
            logConsole("Timeout waiting for the element Visible. " + by.toString());
            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
        }
        return element;
    }

    //đợi phần tử hiển thị lên giao diện và enable
    public static WebElement waitForElementToBeClickable(By by) {
        WebElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_TIMEOUT), Duration.ofMillis(500));
            element = wait.until(ExpectedConditions.elementToBeClickable(by)); //Chờ cho đến khi phần tử có thể click (tức là hiển thị + enabled).
            highlightElement(by);
            return element;
        } catch (Throwable error) {
            logConsole("Timeout waiting for the element To Be Clickable. " + by.toString());
            Assert.fail("Timeout waiting for the element To Be Clickable. " + by.toString());
        }
        return element;
    }

    public static WebElement waitForElementToBeClickable(By by, int waitTimeout) {
        WebElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(waitTimeout), Duration.ofMillis(500));
            element = wait.until(ExpectedConditions.elementToBeClickable(by));
            highlightElement(by);
            return element;
        } catch (Throwable error) {
            logConsole("Timeout waiting for the element To Be Clickable. " + by.toString());
            Assert.fail("Timeout waiting for the element To Be Clickable. " + by.toString());
        }
        return element;
    }

    //chờ element có trong DOM (có thể ẩn, chưa hiển thị)
    public static WebElement waitForElementPresent(By by) {
        WebElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_TIMEOUT), Duration.ofMillis(500));
            element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
            highlightElement(by);
            return element;
        } catch (Throwable error) {
            logConsole("Element not exist. " + by.toString());
            Assert.fail("Element not exist. " + by.toString());
        }
        return element;
    }

    public static WebElement waitForElementPresent(By by, int waitTimeout) {
        WebElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(waitTimeout), Duration.ofMillis(500));
            element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
            highlightElement(by);
            return element;
        } catch (Throwable error) {
            logConsole("Element not exist. " + by.toString());
            Assert.fail("Element not exist. " + by.toString());
        }
        return element;
    }

    //Chờ cho đến khi element biến mất khỏi màn hình (ẩn hoặc không còn trong DOM).
    //Thường dùng để chờ loading icon/spinner biến mất.
    public static void waitForElementNotVisible(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (Throwable error) {
            logConsole("Timeout waiting for element Not Visible. " + by.toString());
            Assert.fail("Timeout waiting for element Not Visible. " + by.toString());
        }
    }

    //chờ trang load
    public static void waitForPageLoaded() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_TIMEOUT), Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

        // wait for Javascript to loaded
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) DriverManager.getDriver()).executeScript("return document.readyState")
                .toString().equals("complete");

        //Get js is Ready
        boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");

        //Wait Javascript until it is Ready!
        if (!jsReady) {
            try {
                wait.until(jsLoad);
            } catch (Throwable error) {
                error.printStackTrace();
                Assert.fail("Timeout waiting for page load (Javascript). (" + PAGE_LOAD_TIMEOUT + "s)");
            }
        }
    }

    public static void waitForSearchResult(By by) {
        try {
            int oldCount = getWebElements(by).size();
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.numberOfElementsToBeLessThan(by, oldCount));
        } catch (Throwable error) {
            logConsole("Timeout while waiting for search results." + by.toString());
            Assert.fail("Timeout while waiting for search results." + by.toString());
        }
    }

    //Chờ cho đến khi frame có trong DOM và sẵn sàng.
    //Khi có, tự động switch driver sang frame đó.
    public static void waitForSwitchToFrameWhenAvailable(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
            logConsole("Switch to frame: " + by.toString());
        } catch (Throwable error) {
            logConsole("Timeout while waiting for the iFrame to be available. " + by.toString());
            Assert.fail("Timeout while waiting for the iFrame to be available. " + by.toString());
        }
    }

    //chuyển context điều khiển Selenium từ trong một iframe/frame về lại main document
    public static void switchToDefaultContent() {
        DriverManager.getDriver().switchTo().defaultContent();
        logConsole("Switched back to default content");
    }

    public static boolean checkElementExist(By by) {
        waitForPageLoaded();
        List<WebElement> listElement = getWebElements(by);

        if (listElement.size() > 0) {
            logConsole("checkElementExist: " + true + " --- " + by);
            return true;
        } else {
            logConsole("checkElementExist: " + false + " --- " + by);
            return false;
        }
    }

    // Hàm kiểm tra sự tồn tại của phần tử với lặp lại nhiều lần
    public static boolean checkElementExist(By by, int maxRetries, int waitTimeMillis) {
        for (int attempt = 0; attempt < maxRetries; attempt++) {
            if (!getWebElements(by).isEmpty()) {
                logConsole("Tìm thấy phần tử ở lần thứ " + (attempt + 1));
                return true;
            }
            logConsole("Không tìm thấy phần tử. Thử lại lần " + (attempt + 1));
            try {
                Thread.sleep(waitTimeMillis); // Chờ trước khi thử lại
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        // Trả về false nếu không tìm thấy phần tử sau maxRetries lần
        logConsole("Không tìm thấy phần tử sau " + maxRetries + " lần thử.");
        return false;
    }

    //Hàm kiểm tra tồn tại với WebDriverWait
    public static boolean checkElementExist(By by, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeoutSeconds), Duration.ofMillis(500));
            //driver trong lambda là WebDriver hiện tại, Selenium tự inject.
            //Không cần gọi DriverManager.getDriver() trong lambda nữa.
            wait.until(driver -> !driver.findElements(by).isEmpty()); //Chờ cho đến khi driver.findElements(by) tìm thấy ít nhất một phần tử (list không rỗng) - xuất hiện trong DOM.
            logConsole("Tìm thấy phần tử (wait)." + by);
            return true;
        } catch (TimeoutException e) {
            logConsole("Không tìm thấy phần tử sau " + timeoutSeconds + "s: " + by);
            return false;
        }
    }

    public static void openURL(String url) {
        DriverManager.getDriver().get(url);
        waitForPageLoaded();
        logConsole("Open: " + url);
    }

    public static void clickToElement(By by) {
        sleep(STEP_TIME);
        waitForElementToBeClickable(by).click();
        logConsole("Click to element: " + by.toString());
    }

    public static void clickToElement(By by, int second) {
        sleep(STEP_TIME);
        waitForElementToBeClickable(by, second).click();
        logConsole("Click to element: " + by.toString());
    }

    public static void clearElementText(By by) {
        sleep(STEP_TIME);
        waitForElementVisible(by).clear();
        logConsole("Clear text of element: " + by.toString());
    }

    //Cách này mạnh hơn .clear(), đặc biệt trong các input được custom bằng JavaScript, vì .clear() đôi khi không xóa hết giá trị.
    public static void clearElementTextActions(By by) {
        sleep(STEP_TIME);
        WebElement element = waitForElementVisible(by);
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
        logConsole("Clear text of element: " + by.toString());
    }

    public static void setText(By by, String text) {
        sleep(STEP_TIME);
        waitForElementVisible(by).sendKeys(text);
        logConsole("Set text \"" + text + "\" on element: " + by.toString());
    }

    public static void setText(By by, String text, int second) {
        sleep(STEP_TIME);
        waitForElementVisible(by, second).sendKeys(text);
        logConsole("Set text \"" + text + "\" on element: " + by.toString());
    }

    public static void setKey(By by, Keys key) {
        sleep(STEP_TIME);
        waitForElementVisible(by).sendKeys(key);
        logConsole("Set key on element: " + by.toString());
    }

    public static void setTextAndKey(By by, String text, Keys key) {
        sleep(STEP_TIME);
        waitForElementVisible(by).sendKeys(text, key);
        logConsole("Set text: \"" + text + "\" and key on element: " + by.toString());
    }

    public static String getElementText(By by) {
        sleep(STEP_TIME);
        logConsole("Get text of element: " + by.toString());
        String text = waitForElementVisible(by).getText();
        logConsole("===>TEXT = \"" + text + "\"");
        return text;
    }

    public static String getElementAttribute(By by, String value) {
        sleep(STEP_TIME);
        logConsole("Get attribute of element: " + by.toString());
        String text = waitForElementVisible(by).getAttribute(value);
        logConsole("===>Attribute = \"" + text + "\"");
        return text;
    }

    //Dùng nhiều trong UI testing (visual check, style validation).
    //Trả về chuỗi, thường ở dạng chuẩn của browser (ví dụ: rgba(0, 0, 0, 1) thay vì #000)
    public static String getElementCSSValue(By by, String cssPropertyName) {
        sleep(STEP_TIME);
        logConsole("Get CSS Value: " + cssPropertyName + " of element: " + by.toString());
        String value = waitForElementVisible(by).getCssValue(cssPropertyName);
        logConsole("===>CSS Value: \"" + value + "\"");
        return value;
    }

    //Cuộn trang để element chỉ định nằm gọn ở đầu trang hiển thị (viewport).
    public static void scrollToElementAtTop(By by) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", waitForElementVisible(by));
//        logConsole("Scroll to element at top: " + by.toString());
    }

    //Cuộn trang để element chỉ định nằm gọn ở cuối trang hiển thị (viewport).
    public static void scrollToElementAtBottom(By by) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", waitForElementVisible(by));
//        logConsole("Scroll to element at bottom: " + by.toString());
    }

    //hàm này ít dùng hoặc hạn chế dùng do kích thước màn hình mỗi ng khác nhau
    public static void scrollToPosition(int X, int Y) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("window.scrollTo(" + X + "," + Y + ");");
    }

    //Hàm này dùng để hover chuột tới một element.
    public static boolean moveToElement(By by) {
        try {
            Actions actions = new Actions(DriverManager.getDriver());
            actions.moveToElement(getWebElement(by)).release().build().perform();
            return true;
        } catch (Exception e) {
            logConsole(e.getMessage());
            return false;
        }
    }

    public static boolean moveToElementOffset(By by, int X, int Y) {
        try {
            Actions actions = new Actions(DriverManager.getDriver());
            actions.moveByOffset(X, Y).perform();
            return true;
        } catch (Exception e) {
            logConsole(e.getMessage());
            return false;
        }
    }

    //Hàm này dùng để hover chuột tới một element.
    public static boolean hoverElement(By by) {
        try {
            Actions actions = new Actions(DriverManager.getDriver());
            actions.moveToElement(getWebElement(by)).perform();
            return true;
        } catch (Exception e) {
            logConsole(e.getMessage());
            return false;
        }
    }

    // Hàm này kéo phần tử từ fromElement đến toElement bằng dragAndDrop (Selenium sẽ tự động thêm release()).
    public static boolean dragAndDrop(By fromElement, By toElement) {
        try {
            Actions actions = new Actions(DriverManager.getDriver());
            actions.dragAndDrop(getWebElement(fromElement), getWebElement(toElement)).perform();
            return true;
        } catch (Exception e) {
            logConsole(e.getMessage());
            return false;
        }
    }

    // Hàm này kéo phần tử từ fromElement đến toElement bằng clickAndHold
    // Với nhiều web dùng HTML5/React/Angular thì dragAndDrop() mặc định của Selenium hay fail, còn cách này thường ổn hơn
    public static boolean dragAndDropElement(By fromElement, By toElement) {
        try {
            Actions actions = new Actions(DriverManager.getDriver());
            actions.clickAndHold(getWebElement(fromElement)).moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
            return true;
        } catch (Exception e) {
            logConsole(e.getMessage());
            return false;
        }
    }

    //Kéo thả theo tọa độ cụ thể
    //Linh hoạt trong các tình huống không có phần tử đích (ví dụ: kéo slider, drag trong canvas, kéo map Google).
    public static boolean dragAndDropOffset(By fromElement, int X, int Y) {
        try {
            Actions actions = new Actions(DriverManager.getDriver());
            actions.clickAndHold(getWebElement(fromElement)).pause(Duration.ofMillis(500)).moveByOffset(X, Y).release().build().perform();
            return true;
        } catch (Exception e) {
            logConsole(e.getMessage());
            return false;
        }
    }

    public static boolean clickElementByActions(By by) {
        try {
            Actions actions = new Actions(DriverManager.getDriver());
            actions.moveToElement(getWebElement(by)).click().build().perform();
            return true;
        } catch (Exception e) {
            logConsole(e.getMessage());
            return false;
        }
    }

    public static boolean pressEnter(By by) {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            return true;
        } catch (Exception e) {
            logConsole(e.getMessage());
            return false;
        }
    }

    public static boolean pressESC(By by) {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ESCAPE);
            robot.keyRelease(KeyEvent.VK_ESCAPE);
            return true;
        } catch (Exception e) {
            logConsole(e.getMessage());
            return false;
        }
    }

    public static boolean pressF11(By by) {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_F11);
            robot.keyRelease(KeyEvent.VK_F11);
            return true;
        } catch (Exception e) {
            logConsole(e.getMessage());
            return false;
        }
    }

    public static boolean verifyEquals(Object actual, Object expected) {
        waitForPageLoaded();
        logConsole("Verify equals: " + actual + " and " + expected);
        boolean check = actual.equals(expected);
        return check;
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        waitForPageLoaded();
        logConsole("Assert equals: " + actual + " and " + expected);
        Assert.assertEquals(actual, expected, message);
    }

    public static boolean verifyContains(String actual, String expected) {
        waitForPageLoaded();
        logConsole("Verify contains: " + actual + " and " + expected);
        boolean check = actual.contains(expected);
        return check;
    }

    public static void assertContains(String actual, String expected, String message) {
        waitForPageLoaded();
        logConsole("Assert contains: " + actual + " and " + expected);
        boolean check = actual.contains(expected);
        Assert.assertTrue(check, message);
    }
}
