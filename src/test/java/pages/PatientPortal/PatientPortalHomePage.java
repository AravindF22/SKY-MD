package pages.PatientPortal;

import base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PatientPortalHomePage extends BasePage {
    private final WebDriverWait wait;
    public PatientPortalHomePage(WebDriver driver){
        super(driver);
        this.wait = new WebDriverWait(driver,Duration.ofSeconds(10));
    }
    private final By myProfileLink = By.xpath("(//p[text()='My Profile'])[1]");
    private final By dermatologyVisit = By.xpath("//p[text()='Dermatology Visit']");
    private final By primaryCareVisit = By.xpath("//p[text()='Primary Care Visit']");
    private final By behaviouralHealthVisit = By.xpath("//p[contains(text(),'behavioral health')]/parent::div");

    public void clickMyProfile(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(myProfileLink)).click();
    }
    public void selectDermatologyVisit() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(dermatologyVisit));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (TimeoutException e) {
            System.err.println("Timeout: 'Dermatology Visit' option not clickable within the wait time.");
        } catch (Exception e) {
            System.err.println("Error clicking Dermatology Visit: " + e.getMessage());
        }
    }

    public boolean selectPrimaryCareVisit() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(primaryCareVisit));
            element.click();
            return true;
        } catch (TimeoutException e) {
            System.err.println("Timeout: Primary Care Visit option not clickable.");
            return false;
        } catch (Exception e) {
            System.err.println("Error clicking Primary Care Visit: " + e.getMessage());
            return false;
        }
    }
    public boolean isHomePage(){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Welcome to SkyMD']")));
            return true;
        }catch (Exception e){
            System.out.println("Welcome to SkyMD is not visible "+ e.getMessage());
            return false;
        }
    }
    public boolean selectBehaviouralHealthVisit(){
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(behaviouralHealthVisit));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            return true;
        }catch (Exception e){
            System.out.println("Error clicking Behavioural Health Visit: " + e.getMessage());
            return false;
        }
    }
}
