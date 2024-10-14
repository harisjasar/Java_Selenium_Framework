package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProjectsExplorerPage extends BasePage{
    public ProjectsExplorerPage(WebDriver driver) {
        super(driver);
    }

    public void selectProject(String projectName){
        waitForElementToBePresent(By.cssSelector("h3[title='" + projectName + "']")).click();
    }
}
