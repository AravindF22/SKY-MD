package pages.PatientPortal;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class PrimaryCareVisitPage extends BasePage {
    private WebDriverWait wait;
    public PrimaryCareVisitPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By patientAsMySelf = By.xpath("//div[@class=\"radio_button_card_container\"]/div[1]");
    private final By patientAsChild = By.xpath("//div[@class=\"radio_button_card_container\"]/div[2]");
    private final By patientAsWard = By.xpath("//div[@class=\"radio_button_card_container\"]/div[3]");
    private final By nextBtn = By.xpath("//button[text()='Next']");
    private final By proceedByBookingbtn = By.xpath("//button[text()='Proceed with Booking']");
    private final By selectCondition = By.xpath("//div[@class=\"radio_button_card_container\"]/div[1]//img");
    private final By selectFirstDateForSlot = By.xpath("//div[@class=\"flex pb-10\"]/div[1]");
    private final By selectFirstSlot = By.xpath("//div[@class= 'relative p-10 overflow-x-hidden bg-white shadow-card rounded-b-4xl']/div/div[3]/div[2]/div[2]/div[1]");
    //demographics
    private final By firstName = By.xpath("//input[@name=\"first_name\"]");
    private final By lastName = By.xpath("//input[@name=\"last_name\"]");
    private final By dob = By.xpath("//div[@class=\"react-date-picker__inputGroup\"]/input[1]");
    private final By addressOne = By.xpath("//input[@id='address_1']");
    private final By addressTwo = By.xpath("//input[@id='address_2']");
    private final By zipcode = By.xpath("//input[@id='zipcode']");
    private final By feet = By.xpath("//input[@id='heightFeet']");
    private final By inch = By.xpath("//input[@id=\"heightInches\"]");
    private final By weight = By.xpath("//input[@id='weight']");

    private final By backArrowToHomePage = By.xpath("//img[@class=\"object-contain w-10 cursor-pointer \"]");

    public void clickPatientAsMySelf() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(patientAsMySelf)).click();
        } catch (Exception e) {
            System.out.println("Error clicking 'Patient as Myself': " + e.getMessage());
        }
    }

    public void clickPatientAsChild() {
        try {
            Thread.sleep(3000);
            wait.until(ExpectedConditions.elementToBeClickable(patientAsChild)).click();
        } catch (Exception e) {
            System.out.println("Error clicking 'Patient as Child': " + e.getMessage());
        }
    }

    public void clickPatientAsWard() {
        try {
            Thread.sleep(3000);
            wait.until(ExpectedConditions.elementToBeClickable(patientAsWard)).click();

        } catch (Exception e) {
            System.out.println("Error clicking 'Patient as Ward': " + e.getMessage());
        }
    }

    public void clickNextButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(nextBtn)).click();
        } catch (Exception e) {
            System.out.println("Error clicking 'Next' button: " + e.getMessage());
        }
    }

    public void clickSelectPatient(String name) {
        By selectPatient = By.xpath("//p[text()='" + name + "']/parent::div");
        try {
            wait.until(ExpectedConditions.elementToBeClickable(selectPatient)).click();
        } catch (Exception e) {
            System.out.println("Error clicking 'Select Patient': " + e.getMessage());
        }
    }

    public void clickProceedByBookingButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(proceedByBookingbtn)).click();
        } catch (Exception e) {
            System.out.println("Error clicking 'Proceed with Booking' button: " + e.getMessage());
        }
    }

    public void clickSelectFirstCondition() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(selectCondition)).click();
        } catch (Exception e) {
            System.out.println("Error clicking 'Select Condition': " + e.getMessage());
        }
    }

    public void clickSelectFirstDateForSlot() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(selectFirstDateForSlot)).click();
        } catch (Exception e) {
            System.out.println("Error clicking 'Select First Date for Slot': " + e.getMessage());
        }
    }

    public void clickSelectFirstSlot() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(selectFirstSlot)).click();
        } catch (Exception e) {
            System.out.println("Error clicking 'Select First Slot': " + e.getMessage());
        }
    }


    public String getFirstName() {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(firstName));
            return element.getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting first name: " + e.getMessage());
            return null;
        }
    }

    public String getLastName() {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(lastName));
            return element.getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting last name: " + e.getMessage());
            return null;
        }
    }

    public String getDOB() {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(dob));
            String dob = element.getAttribute("value").trim();
            // Adjust the format here to match your actual input!
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dob);

            // Format the date into the desired output format
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
            return targetFormat.format(date);
        }
        catch (Exception e) {
            System.out.println("Error getting DOB: " + e.getMessage());
            return null;
        }
    }

    public String getAddressOne() {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(addressOne));
            return element.getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting address one: " + e.getMessage());
            return null;
        }
    }

    public String getAddressTwo() {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(addressTwo));
            return element.getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting address two: " + e.getMessage());
            return null;
        }
    }

    public String getZipcode() {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(zipcode));
            return element.getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting zipcode: " + e.getMessage());
            return null;
        }
    }

    public String getFeet() {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(feet));
            return element.getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting feet: " + e.getMessage());
            return null;
        }
    }

    public String getInch() {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(inch));
            return element.getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting inch: " + e.getMessage());
            return null;
        }
    }

    public String getWeight() {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(weight));
            return element.getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting weight: " + e.getMessage());
            return null;
        }
    }
    public void clickBackArrowToHomePage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement backArrow = wait.until(ExpectedConditions.elementToBeClickable(backArrowToHomePage));
            backArrow.click();
        } catch (Exception e) {
            System.out.println("Error clicking back arrow to home page: " + e.getMessage());
        }
    }
}
