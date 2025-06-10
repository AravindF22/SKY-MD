package pages.ProviderPortal;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
        wait.until(ExpectedConditions.elementToBeClickable(invitePatientLink)).click();
    }
}
