package pages.PatientPortal;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SetPasswordPage extends BasePage {

    public SetPasswordPage(WebDriver driver){
        super(driver);
    }

    private By newPasswordField = By.name("new_password");
    private  By confirmPasswordField = By.name("confirm_password");
    private By setPasswordButton = By.xpath("//button[text()='Set Password']");

    public void setPassword(String password){
        driver.findElement(newPasswordField).sendKeys(password);
        driver.findElement(confirmPasswordField).sendKeys(password);
        driver.findElement(setPasswordButton).click();
    }
}
