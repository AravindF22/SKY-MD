package pages;

import base.BasePage;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class YopMail extends BasePage {
    public WebDriverWait wait;
    public YopMail(WebDriver driver){
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    private By emailTextField =  By.xpath("//input[@class=\"ycptinput\"]");
    private By enterEmailArrow = By.xpath("//div[@id=\"refreshbut\"]/button");
    private By setPasswordMail = By.xpath("//div[@class='m']//div[@class='lms' and text()='Set Your SkyMD Password']/parent::button");
    private By setPasswordLink = By.xpath("//a[text()='SET PASSWORD']");
    private By refresh = By.id("refresh");

    public void clickSetPasswordMail(String email) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to("https://yopmail.com/?" + email);

        boolean mailClicked = false;
        int retries = 2;

        while (!mailClicked && retries-- > 0) {
            try {
                // Switch to inbox frame and click the mail
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("ifinbox"));
                wait.until(ExpectedConditions.elementToBeClickable(setPasswordMail)).click();
                mailClicked = true;
            } catch (Exception e) {
                // Retry after refresh
                driver.switchTo().defaultContent();
                try {
                    wait.until(ExpectedConditions.elementToBeClickable(refresh)).click();
                } catch (Exception ignore) {
                    System.out.println("Refresh button not found or not clickable.");
                }
                Thread.sleep(2000); // short wait before retry
            }
        }

        if (!mailClicked) {
            throw new RuntimeException("Set Password mail not found after retries.");
        }

        // Switch to mail content and click the set password link
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("ifmail"));
        wait.until(ExpectedConditions.elementToBeClickable(setPasswordLink)).click();
    }

}
