package pages.PatientPortal;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MyVisitsPage extends BasePage {
        public WebDriverWait wait;
        private JavascriptExecutor js;

        public MyVisitsPage(WebDriver driver) {
            super(driver);
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        }

        private final By visitHeaderText = By.xpath("//div[@class='flex items-center justify-between']//h3");
        private final By visitIdOfCreatedVisit = By.xpath("(//div[@class=\"flex flex-col space-y-10\"]//h3[contains(text(),'Dermatology')])[1]");
        private final By visitPhotos = By.xpath("//div[@class=\"flex flex-wrap w-2/3 space-x-4\"]/div");

    public String getVisitName() {
            try {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(visitHeaderText));
                return element.getText().trim().replaceAll("[^a-zA-Z]", "");
            } catch (Exception e) {
                System.out.println("Error getting visit name: " + e.getMessage());
                return null;
            }
        }

        public String getVisitId() {
            try {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(visitHeaderText));
                element.getText().trim().replaceAll("[^0-9]", "");  // "Dermatology Visit #93129"
            } catch (Exception e) {
                System.out.println("Error getting visit ID: " + e.getMessage());
            }
            return null;
        }

        public boolean clickPhotosInVisitSummary(String visitId) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                        "(//h3[contains(text(),'" + visitId + "')]/ancestor::div[contains(@class,'flex-col font-medium')]//div[@class=\"border-t\"]//div[@class=\"space-y-4\"]//button)[3]")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                element.click();
                return true;
            } catch (Exception e) {
                System.out.println("Error clicking photos in visit summary: " + e.getMessage());
                return false;
            }
        }

        public boolean clickFirstVisitSummary(String visitId) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                        "//h3[contains(text(),'" + visitId + "')]/ancestor::div[contains(@class,'flex-col font-medium')]//h2[contains(text(),'Visit')]")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                element.click();
                return true;
            } catch (Exception e) {
                System.out.println("Error clicking first visit summary: " + e.getMessage());
                return false;
            }
        }
        public String getVisitIdOfCreatedVisit() {
            try {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(visitIdOfCreatedVisit));
                return element.getText().trim().replaceAll("[^0-9]", "");
            } catch (Exception e) {
                System.out.println("Error getting visit ID of created visit: " + e.getMessage());
                return null;
            }
        }
        public int getVisitPhotosCount() {
            try {
                List<WebElement> photos = driver.findElements(visitPhotos);
                return photos.size();
            } catch (Exception e) {
                System.out.println("Error getting visit photos count: " + e.getMessage());
                return 0;
            }
        }
}
