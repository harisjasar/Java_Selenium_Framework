package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProjectPage extends BasePage{
    public ProjectPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnReviewFile(String fileId){
        waitForElementToBePresent(By.id("reviewFiles-" + fileId)).click();
    }
}
