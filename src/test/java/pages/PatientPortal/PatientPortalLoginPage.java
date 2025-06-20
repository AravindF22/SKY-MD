package pages.PatientPortal;

import base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PatientPortalLoginPage extends BasePage {
    public PatientPortalLoginPage(WebDriver driver){
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By emailTextField = By.id("email");
    private By passwordTextField = By.id("password");
    private By loginButton = By.xpath("//button[text()='Login']");
    private By signInLink = By.xpath("//a[text()='Sign up']");
    private By invalidEmailPassowrdCombination = By.xpath("//div[text()='Invalid email/password combination']");
    private By emailRequiredError = By.xpath("//p[text()='Email is required']");
    private By PasswordRequiredError = By.xpath("//p[text()='Password is required']");
    private By enterValidEmail = By.xpath("//p[text()='Please enter a valid email address']");
    private By enterEightCharacters = By.xpath("//p[text()='Password must be atleast 8 characters']");
    private By lessThanTwentyChar = By.xpath("//p[text()=\"Password cannot exceed 20 characters\"]");
    private  WebDriverWait wait;

    public void login(String email, String password){

        try {
            WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(emailTextField));
            emailField.clear();
            emailField.sendKeys(email);

            WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(passwordTextField));
            passwordField.clear();
            passwordField.sendKeys(password);

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
    public void setEmail(String email){
        try {
            WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(emailTextField));
            emailField.clear();
            emailField.sendKeys(email);
        } catch (Exception e) {
            System.out.println("Error setting email in login page "+ e.getMessage());
        }
    }
    public void setPassword(String password){
        try {
            WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(passwordTextField));
            passwordField.clear();
            passwordField.sendKeys(password);
        } catch (Exception e) {
            System.out.println("Error setting password in login page "+ e.getMessage());
        }
    }
    public void clickLoginButton(){
        try{
            WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            loginBtn.click();
        } catch (Exception e) {
            System.out.println("Error clicking login button in login page "+ e.getMessage());
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

    public boolean isInvalidEmailPasswordCombinationToastPresent(){
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(invalidEmailPassowrdCombination));
            return true;
        }
        catch (Exception e){
            System.out.println("Invalid Email / Password Combination Toast message is not appeared "+ e.getMessage());
            return false;
        }
    }
    public boolean isEmailRequiredErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(emailRequiredError));
            return true;
        } catch (Exception e) {
            System.out.println("'Email is required' error message not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isPasswordRequiredErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(PasswordRequiredError));
            return true;
        } catch (Exception e) {
            System.out.println("'Password is required' error message not displayed: " + e.getMessage());
            return false;
        }
    }
    public boolean isEnterValidEmailErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(enterValidEmail));
            return true;
        } catch (Exception e) {
            System.out.println("'Please enter a valid email address' error message not displayed: " + e.getMessage());
            return false;
        }
    }
    public boolean isMinEightCharactersErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(enterEightCharacters));
            return true;
        } catch (Exception e) {
            System.out.println("'Password must be at least 8 characters' error message not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isMaxTwentyCharactersErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(lessThanTwentyChar));
            return true;
        } catch (Exception e) {
            System.out.println("'Password cannot exceed 20 characters' error message not displayed: " + e.getMessage());
            return false;
        }
    }
}
