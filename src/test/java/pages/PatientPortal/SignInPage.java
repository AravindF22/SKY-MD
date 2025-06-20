package pages.PatientPortal;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignInPage extends BasePage {
    public WebDriverWait wait;
    public SignInPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    private By emailField = By.cssSelector("input#email");
    private By nextBtn = By.xpath("//button[text()='Next']");
    private By passwordField = By.cssSelector("input#password");
    private By ageCheck =By.xpath("//img[@class='object-contain w-8 h-8']");
    private By continueBtn = By.cssSelector("button#signup_next");
    private By firstNameField = By.cssSelector("input#first_name");
    private By lastNameField =By.cssSelector("input#last_name");
    private By mobileField = By.xpath("//input[@class='form-control ']");
    private By zipcodeField = By.cssSelector("input#zip_code");
    private By signInBtn = By.cssSelector("button#GTM_portal_signup");
    private By emailErrorToast = By.xpath("//div[text()='Email already exists']");

    public void enterEmail(String email) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
            element.clear();
            element.sendKeys(email);
        } catch (Exception e) {
            System.out.println("Error entering email: " + e.getMessage());
        }
    }

    public void clickNextButton() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(nextBtn));
            element.click();
        } catch (Exception e) {
            System.out.println("Error clicking Next button: " + e.getMessage());
        }
    }

    public void enterPassword(String password) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
            element.clear();
            element.sendKeys(password);
        } catch (Exception e) {
            System.out.println("Error entering password: " + e.getMessage());
        }
    }

    public void clickAgeCheckIcon() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(ageCheck));
            element.click();
        } catch (Exception e) {
            System.out.println("Error clicking age check icon: " + e.getMessage());
        }
    }

    public void clickContinueButton() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(continueBtn));
            element.click();
        } catch (Exception e) {
            System.out.println("Error clicking Continue button: " + e.getMessage());
        }
    }

    public void enterFirstName(String firstName) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
            element.clear();
            element.sendKeys(firstName);
        } catch (Exception e) {
            System.out.println("Error entering first name: " + e.getMessage());
        }
    }

    public void enterLastName(String lastName) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameField));
            element.clear();
            element.sendKeys(lastName);
        } catch (Exception e) {
            System.out.println("Error entering last name: " + e.getMessage());
        }
    }

    public void enterMobileNumber(String mobile) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileField));
            element.clear();
            element.sendKeys(mobile);
        } catch (Exception e) {
            System.out.println("Error entering mobile number: " + e.getMessage());
        }
    }

    public void enterZipCode(String zip) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(zipcodeField));
            element.clear();
            element.sendKeys(zip);
        } catch (Exception e) {
            System.out.println("Error entering ZIP code: " + e.getMessage());
        }
    }

    public void clickSignInButton() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(signInBtn));
            element.click();
        } catch (Exception e) {
            System.out.println("Error clicking Sign In button: " + e.getMessage());
        }
    }
    public boolean isEmailAlreadyExistsToastPresent(){
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(emailErrorToast));
            return true;
        }catch (Exception e){
            System.out.println("Error in Toast message " + e.getMessage());
            return false;
        }
    }
}
