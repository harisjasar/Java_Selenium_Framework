package pages;

import com.fasterxml.jackson.databind.JsonNode;
import data.TestDataLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;

public class LoginPage extends BasePage {

    private JsonNode testData;
    @FindBy(id = "login-username")
    private WebElement usernameField;

    @FindBy(id = "login-password")
    private WebElement passwordField;

    @FindBy(id = "login")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) throws IOException {
        super(driver);
        testData = new TestDataLoader().loadTestData("src/main/java/data/testData.json");
    }

    public void enterUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.submit();
    }

    public void loginIntoApplication() {
        navigateTo(testData.get("appUrl").asText());
        enterUsername(testData.get("userName").asText());
        enterPassword(testData.get("password").asText());
        clickLogin();
    }
}