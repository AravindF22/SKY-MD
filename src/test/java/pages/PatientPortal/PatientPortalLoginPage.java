package pages.PatientPortal;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PatientPortalLoginPage extends BasePage {
    public PatientPortalLoginPage(WebDriver driver){
        super(driver);
    }

    private By emailTextField = By.id("email");
    private By passwordTextField = By.id("password");
    private By loginButton = By.xpath("//button[text()='Login']");

    public void login(String email, String Password){
        driver.findElement(emailTextField).sendKeys(email);
        driver.findElement(passwordTextField).sendKeys(Password);
        driver.findElement(loginButton).click();
    }
}
