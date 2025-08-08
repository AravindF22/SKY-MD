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
    private final By pediatricVisit = By.xpath("//p[text()='Pediatric Visit']");
    private final By behaviouralHealthVisit = By.xpath("//p[contains(text(),'behavioral health')]/parent::div");
    public void clickMyProfile(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(myProfileLink)).click();
    }
    public boolean selectDermatologyVisit() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(dermatologyVisit));
            scrollAndClick(element);
            return true;
        } catch (TimeoutException e) {
            System.err.println("Timeout: 'Dermatology Visit' option not clickable within the wait time.");
            return false;
        } catch (Exception e) {
            System.err.println("Error clicking Dermatology Visit: " + e.getMessage());
            return false;
        }
    }

    public boolean selectPrimaryCareVisit() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(primaryCareVisit));
            scrollAndClick(element);
            return true;
        } catch (TimeoutException e) {
            System.err.println("Timeout: Primary Care Visit option not clickable.");
            return false;
        }
        catch (StaleElementReferenceException e ){
            System.err.println("Stale Element Reference Exception: Primary Care Visit option not clickable.");
            return retryClick(primaryCareVisit);
        }catch (Exception e) {
            System.err.println("Error clicking Primary Care Visit: " + e.getMessage());
            return false;
        }
    }
    public boolean selectPediatricVisit() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(pediatricVisit));
            scrollAndClick(element);
            return true;
        } catch (TimeoutException e) {
            System.err.println("Timeout: Primary Care Visit option not clickable.");
            return false;
        }
        catch (StaleElementReferenceException e ){
            System.err.println("Stale Element Reference Exception: Primary Care Visit option not clickable.");
            return retryClick(primaryCareVisit);
        }catch (Exception e) {
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
            scrollAndClick(element);
            return true;
        }catch (Exception e){
            System.out.println("Error clicking Behavioural Health Visit: " + e.getMessage());
            return false;
        }
    }
}
