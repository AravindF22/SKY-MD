package pages.ProviderPortal;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {
    private WebDriverWait wait;
    public LoginPage(WebDriver driver){
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators
    private By emailTextField = By.xpath("//input[@type='email']");
    private By passwordTextField = By.xpath("//input[@type='password']");
    private By loginButton = By.xpath("//button[@type='submit']");

    public void setEmailAs(String email) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailTextField));
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void setPasswordAs(String password) {
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordTextField));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        btn.click();
    }
}
