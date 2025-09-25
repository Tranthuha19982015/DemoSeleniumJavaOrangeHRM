package hatester.common;

import hatester.drivers.DriverManager;
import hatester.helpers.PropertiesHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

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

        if (PropertiesHelper.getValue("BROWSER") == null || PropertiesHelper.getValue("BROWSER").isEmpty()) {
            browserName = browserName;
        } else {
            browserName = PropertiesHelper.getValue("BROWSER");
        }

        switch (browserName.trim().toLowerCase()) {
            case "chrome":
                System.out.println("Launching Chrome browser...");

                ChromeOptions options = new ChromeOptions();
                if (PropertiesHelper.getValue("HEADLESS").equalsIgnoreCase("true")) {
                    options.addArguments("--headless=new"); //chạy headless
                    options.addArguments("--window-size=" + PropertiesHelper.getValue("WINDOW-SIZE")); //set kích thước
                }
                driver = new ChromeDriver();
                break;
            case "firefox":
                System.out.println("Launching Firefox browser...");
                driver = new FirefoxDriver();
                break;
            case "edge":
                System.out.println("Launching Edge browser...");
                driver = new EdgeDriver();
                break;
            default:
                System.out.println("Browser: " + browserName + " is invalid, Launching Chrome as browser of choice...");
                driver = new ChromeDriver();
        }
        DriverManager.setDriver(driver); //Set to ThreadLocal

        if (PropertiesHelper.getValue("HEADLESS").equalsIgnoreCase("false")) {
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
