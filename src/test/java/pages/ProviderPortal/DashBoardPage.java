package pages.ProviderPortal;

import base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashBoardPage extends BasePage {

    WebDriverWait wait;
    public DashBoardPage(WebDriver driver){
        super(driver);
         this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }
    private final By invitePatientLink = By.xpath("(//div[@id=\"navbar\"]//a)[1]");
    public void clickInvitePatientLink(){
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@id='navbar']//a)[1]"))).click();
        } catch (TimeoutException e) {
            // Fallback to JavaScript click
            WebElement element = driver.findElement(By.xpath("(//div[@id='navbar']//a)[1]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }
}
