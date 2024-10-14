package tests;

import org.openqa.selenium.WebDriver;
import driver.DriverManager;

public class BaseTest {

    protected WebDriver driver;
    protected DriverManager driverManager;

    public void setup(String browser) {
        driverManager = new DriverManager();
        driver = driverManager.getDriver(browser);
    }

    public void tearDown() {
        DriverManager.quitDriver();
    }
}