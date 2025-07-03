package pages.PatientPortal;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class MyVisitsPage extends BasePage {
    public WebDriverWait wait;
    private JavascriptExecutor js;
    public MyVisitsPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    private final By visitHeaderText = By.xpath("//div[@class='flex items-center justify-between']//h3");
    public String getVisitName() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(visitHeaderText));
            return element.getText().trim().replaceAll("[^a-zA-Z]","");
        } catch (Exception e) {
            System.out.println("Error getting visit name: " + e.getMessage());
            return null;
        }
    }
    public String getVisitId() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(visitHeaderText));
            element.getText().trim().replaceAll("[^0-9]","");  // "Dermatology Visit #93129"
        } catch (Exception e) {
            System.out.println("Error getting visit ID: " + e.getMessage());
        }
        return null;
    }


}
