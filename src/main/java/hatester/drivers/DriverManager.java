package hatester.drivers;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverManager() {
        // Có thể ném exception nếu ai đó dùng Reflection cố gọi
        // Chặn không cho tạo instance của driver
//        throw new UnsupportedOperationException("DriverManager cannot be instantiated.");
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver driver) {
        DriverManager.driver.set(driver);
    }

    public static void quit() {
        DriverManager.driver.get().quit();
        driver.remove(); // Xóa driver khỏi ThreadLocal

    }
}
