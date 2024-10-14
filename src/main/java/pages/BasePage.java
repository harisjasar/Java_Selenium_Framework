package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Set;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public void switchToWindowHandleByUrl(String urlPart) {
        String currentWindow = driver.getWindowHandle();
        int retryInterval = 3000;
        int maxRetries = 20;
        int retries = 0;
        while (retries < maxRetries) {
            Set<String> allWindows = driver.getWindowHandles();
            for (String window : allWindows) {
                driver.switchTo().window(window);
                if (driver.getCurrentUrl().contains(urlPart)) {
                    return;
                }
            }
            retries++;

            try {
                Thread.sleep(retryInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("Failed to switch to window with URL containing: " + urlPart + " after 1 minute.");
    }

    private static WebElement elementIfVisible(WebElement element) {
        return element.isDisplayed() ? element : null;
    }

    public WebElement waitForElementToBePresent(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

}