package hatester.common;

import hatester.constants.FrameworkConstant;
import hatester.drivers.DriverManager;
import hatester.helpers.PropertiesHelper;
import hatester.listeners.TestListener;
import hatester.utils.LogUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

@Listeners(TestListener.class)
public class BaseTest {
    public SoftAssert softAssert;

    @BeforeSuite
    public void setupBeforeSuite() {
        PropertiesHelper.loadAllFiles();
    }

    @BeforeMethod
    @Parameters({"browser"})
    public void createDriver(@Optional("chrome") String browserName) {
        WebDriver driver;

        if (FrameworkConstant.BROWSER == null || FrameworkConstant.BROWSER.isEmpty()) {
            browserName = browserName;
        } else {
            browserName = FrameworkConstant.BROWSER;
        }

        switch (browserName.trim().toLowerCase()) {
            case "chrome":
                LogUtils.info("Launching Chrome browser...");

                ChromeOptions options = new ChromeOptions();
                if (FrameworkConstant.HEADLESS.equalsIgnoreCase("true")) {
                    options.addArguments("--headless=new"); //chạy headless
                    options.addArguments("--window-size=" + FrameworkConstant.WINDOW_SIZE); //set kích thước
                }
                driver = new ChromeDriver();
                break;
            case "firefox":
                LogUtils.info("Launching Firefox browser...");
                driver = new FirefoxDriver();
                break;
            case "edge":
                LogUtils.info("Launching Edge browser...");
                driver = new EdgeDriver();
                break;
            default:
                LogUtils.info("Browser: " + browserName + " is invalid, Launching Chrome as browser of choice...");
                driver = new ChromeDriver();
        }
        DriverManager.setDriver(driver); //Set to ThreadLocal

        if (FrameworkConstant.HEADLESS.equalsIgnoreCase("false")) {
            DriverManager.getDriver().manage().window().maximize();
        }
        softAssert = new SoftAssert();
    }

    @AfterMethod
    public void closeDriver() {
        if (DriverManager.getDriver() != null) {
            DriverManager.quit();
        }
        softAssert.assertAll();
    }
}
