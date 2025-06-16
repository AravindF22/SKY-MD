package pages.ProviderPortal;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
        try {
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailTextField));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", emailField);
            emailField.clear();
            emailField.sendKeys(email);
        } catch (Exception e) {
            System.err.println("Error setting email: " + e.getMessage());
        }
    }

    public void setPasswordAs(String password) {
        try {
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordTextField));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", passwordField);
            passwordField.clear();
            passwordField.sendKeys(password);
        } catch (Exception e) {
            System.err.println("Error setting password: " + e.getMessage());
        }
    }

    public void clickLoginButton() {
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);
            btn.click();
        } catch (Exception e) {
            try {
                // Fallback to JavaScript click
                WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(loginButton));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", btn);
            } catch (Exception ex) {
                System.err.println("Failed to click login button even with JS: " + ex.getMessage());

            }
            System.err.println("Fallback click used due to: " + e.getMessage());
        }
    }

}
