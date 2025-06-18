package pages.PatientPortal;

import base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PatientPortalLoginPage extends BasePage {
    public PatientPortalLoginPage(WebDriver driver){
        super(driver);
    }

    private By emailTextField = By.id("email");
    private By passwordTextField = By.id("password");
    private By loginButton = By.xpath("//button[text()='Login']");
    private By signInLink = By.xpath("//a[text()='Sign up']");
    private  WebDriverWait wait;

    public void login(String email, String password){
         wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Wait for email field to be clickable and enter email
            WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(emailTextField));
            emailField.clear();
            emailField.sendKeys(email);

            // Wait for password field to be clickable and enter password
            WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(passwordTextField));
            passwordField.clear();
            passwordField.sendKeys(password);

            // Wait for login button to be clickable and click
            WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            loginBtn.click();


        } catch (TimeoutException e) {
            System.err.println("Timeout occurred while trying to login: " + e.getMessage());
            throw new RuntimeException("Login failed due to timeout", e);

        } catch (NoSuchElementException e) {
            System.err.println("Element not found during login: " + e.getMessage());
            throw new RuntimeException("Login failed - element not found", e);

        } catch (ElementNotInteractableException e) {
            System.err.println("Element not interactable during login: " + e.getMessage());
            throw new RuntimeException("Login failed - element not interactable", e);

        } catch (Exception e) {
            System.err.println("Unexpected error during login: " + e.getMessage());
            throw new RuntimeException("Login failed due to unexpected error", e);
        }
    }
    public void clickSignUpLink() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(signInLink));
            element.click();
        } catch (TimeoutException e) {
            System.out.println("Timeout: 'Sign up' link was not clickable within the wait time.");
        } catch (NoSuchElementException e) {
            System.out.println("Error: 'Sign up' link was not found on the page.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while clicking 'Sign up': " + e.getMessage());
        }
    }
}
