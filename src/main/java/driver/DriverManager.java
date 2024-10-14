package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {
    private static final String CHROME_DRIVER_VERSION = "129.0";
    private static ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();
    public static WebDriver getDriver(String browser) {
        if (webDriverThreadLocal.get() == null) {
            if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().browserVersion(CHROME_DRIVER_VERSION).setup();
                webDriverThreadLocal.set(new ChromeDriver());
            } else if (browser.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                webDriverThreadLocal.set(new FirefoxDriver());
            } else {
                throw new IllegalArgumentException("Browser not supported: " + browser);
            }
            webDriverThreadLocal.get().manage().window().maximize();
        }
        return webDriverThreadLocal.get();
    }

    public static void quitDriver() {
        if (webDriverThreadLocal.get() != null) {
            webDriverThreadLocal.get().quit();
            webDriverThreadLocal.remove();
        }
    }
}
