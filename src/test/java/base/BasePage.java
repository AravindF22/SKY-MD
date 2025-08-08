package base;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
   public WebDriver driver;
   public WebDriverWait wait;
    public BasePage(WebDriver driver) {
        this.driver=driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public boolean retryClick(By locator) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                scrollAndClick(element);
                return true;
            } catch (StaleElementReferenceException e) {
                attempts++;
            } catch (Exception e) {
                break;
            }
        }
        return false;
    }
    public void scrollAndClick(WebElement element) {
           ((JavascriptExecutor) driver).executeScript(
               "arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", element);
           ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
