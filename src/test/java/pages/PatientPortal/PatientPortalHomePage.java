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

    public void clickMyProfile(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(myProfileLink)).click();
    }
    public void selectDermatologyVisit() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(dermatologyVisit));
            element.click();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Dermatology Visit option not clickable.");
        } catch (Exception e) {
            System.err.println("Error clicking Dermatology Visit: " + e.getMessage());
        }
    }

    public void selectPrimaryCareVisit() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(primaryCareVisit));
            element.click();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Primary Care Visit option not clickable.");
        } catch (Exception e) {
            System.err.println("Error clicking Primary Care Visit: " + e.getMessage());
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
}
