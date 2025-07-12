package pages.PatientPortal;

import base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class PrimaryCareVisitPage extends BasePage {
    private final WebDriverWait wait;
    public PrimaryCareVisitPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By patientAsMySelf = By.xpath("//div[@class=\"radio_button_card_container\"]/div[1]");
    private final By patientAsChild = By.xpath("//div[@class=\"radio_button_card_container\"]/div[2]");
    private final By patientAsWard = By.xpath("//div[@class=\"radio_button_card_container\"]/div[3]");
    private final By patientAsSomeOnelse= By.xpath("//div[@class=\"radio_button_card_container\"]/div[4]");
    private final By addChild = By.xpath("//button[text()='Add Child']");
    private final By addWard = By.xpath("//button[text()='Add Ward']");
    private final By addSomeOneElse = By.xpath("//button[text()='Add Patient']");
    private final By saveBtn = By.xpath("//button[text()='Save']");
    private final By nextBtn = By.xpath("//button[text()='Next']");
    private final By proceedByBookingBtn = By.xpath("//button[text()='Proceed with Booking']");
    private final By firstAvailableDay = By.xpath("//div[@class=\"flex pb-10\"]/div[1]");
    private final By firstAvailableTSlot = By.xpath("//div[contains(@class,'grid grid-cols-2')]/div[1]");
    private final By providerNameBelowSelectedSlot = By.xpath("//div[contains(@class,'relative p-6')]//h3");
    //demographics
    private final By firstName = By.xpath("//input[@name=\"first_name\"]");
    private final By lastName = By.xpath("//input[@name=\"last_name\"]");
    private final By dob = By.xpath("//div[@class=\"react-date-picker__inputGroup\"]/input[1]");
    private final By addressOne = By.xpath("//input[contains(@id,'address_1')]");
    private final By addressTwo = By.xpath("//input[contains(@id,'address_2')]");
    private final By city = By.cssSelector("#city");
    private final By zipcode = By.xpath("//input[contains(@id,'zip')]");
    private final By feet = By.xpath("//input[@id='heightFeet']");
    private final By inch = By.xpath("//input[@id=\"heightInches\"]");
    private final By weight = By.xpath("//input[@id='weight']");
    private final By selectedGender = By.xpath("//img[contains(@src,'5Lg')]/following-sibling::p");
    private final By id = By.xpath("//input[@id=\"confirm_identity_image_picker\"]");

    //pharmacy
    private final By addPharmacy = By.xpath("//img[@alt=\"location_icon\"]");
    private final By switchToListView = By.xpath("//p[contains(text(),'Switch To ')]/parent::div");
    private final By firstPharmacy = By.xpath("//div[@class=\"mb-16\"][1]/div");

    //allergy
    private final By noneBtnInAllergy = By.xpath("//p[text()='None']/parent::div");
    private final By searchAllergy = By.xpath("//p[text()='Search Allergy']/parent::div");
    private final By enterAllergy = By.xpath("//input[contains(@id,\"react-select\")]");
    private final By reaction = By.xpath("//select[@name=\"reaction\"]");
    private final By environmentCategory = By.xpath("//p[text()='Environment or Food Allergy']");
    private final By addAllergyBtn = By.xpath("//button[text()='Add Allergy']");
    private final By backArrowToHomePage = By.xpath("//img[@class=\"object-contain w-10 cursor-pointer \"]");
    //medications
    private final By noneBtnInMedication = By.xpath("//p[text()='None']/parent::div/parent::div");
    private final By searchMedication = By.xpath("//p[text()='Search Medication']/parent::div");
    private final By enterMedication = By.xpath("//div[@class=\" css-s1z5xt\"]");
    private final By addMedicationBtn = By.xpath("//button[text()='Add Medication']");
    private final By dosage = By.cssSelector("input#dose");
    private final By form = By.cssSelector("select#form");
    private final By frequency = By.cssSelector("select#frequency");
    private final By per = By.cssSelector("select#per");

    //additional area
    private final By additionalArea = By.cssSelector("#additional_details");
    //acceptCodeOfConduct
    private final By acceptCodeOfConduct = By.xpath(
            "//h3[contains(text(),'code of conduct')]/ancestor::button/following-sibling::div//p");

    //payment card
    private final By PaymentMethodSection = By.xpath("//div[@class=\"braintree-dropin braintree-loaded\"]");
    private final By cardType = By.xpath("//div[@data-braintree-id=\"payment-options-container\"]/div[1]");
    private final By cardNumber = By.xpath("//input[@id= \"credit-card-number\"]");
    private final By expiry = By.xpath("//input[@id=\"expiration\"]");
    private final By cvv = By.xpath("//input[@id='cvv']");

    private final By submitForEvaluation = By.xpath("//button[text()='Submit Visit']");

    private final By visitSubmitted = By.xpath("//h2[text()='Visit Submitted']");
    private final By goToMyVisitsButton = By.xpath("//button[text()='Go to My Visits']");
    private final By providerNameInVisitSubmittedPage= By.xpath("//div[contains(@class,'p-5 rounded-4xl')]//h3");

    public boolean clickPatientAsMySelf() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(patientAsMySelf));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            element.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking 'Patient as Myself': " + e.getMessage());
            return false;
        }
    }

    public boolean clickPatientAsChild() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(patientAsChild));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            element.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking 'Patient as Child': " + e.getMessage());
            return false;
        }
    }

    public boolean clickPatientAsWard() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(patientAsWard));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            element.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking 'Patient as Ward': " + e.getMessage());
            return false;
        }
    }
    public boolean clickPatientAsSomeOneElse(){
        try{
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(patientAsSomeOnelse));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            return true;
        }catch (Exception e){
            System.out.println("Error clicking 'Patient as Someone else': " + e.getMessage());
            return false;
        }
    }

    public boolean clickAddChild(){
        try{
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(addChild));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            return true;
        }catch (Exception e){
            System.out.println("Error clicking 'Add Child': " + e.getMessage());
            return false;
        }
    }
    public boolean clickAddWard(){
        try{
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(addWard));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            return true;
        }catch (Exception e){
            System.out.println("Error clicking 'Add Ward': " + e.getMessage());
            return false;
        }
    }
    public boolean clickAddSomeOneElse(){
        try{
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(addSomeOneElse));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            return true;
        }catch (Exception e){
            System.out.println("Error clicking 'Add SomeOne Else': " + e.getMessage());
            return false;
        }
    }

    public boolean clickNextButton() {
        try {
            WebElement element= wait.until(ExpectedConditions.visibilityOfElementLocated(nextBtn));
          if(element.isEnabled()){
              ((JavascriptExecutor) driver).executeScript(
                      "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
              ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
              return true;
          }else {
              System.out.println("Next button is disabled, waiting and retrying...");
              WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
              shortWait.until(ExpectedConditions.elementToBeClickable(nextBtn));
              ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
              return true;
          }
        } catch (Exception e) {
            System.out.println("Error clicking 'Next' button: " + e.getMessage());
            return false;
        }
    }
    public boolean clickSaveButton() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(saveBtn));
             ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            return true;
        }catch (Exception e){
            System.out.println("Error clicking 'Save' button: " + e.getMessage());
            return false;
        }
    }

    public boolean selectPatient(String name) {
        By selectPatient = By.xpath("//p[text()='" + name + "']/parent::div/parent::div");
        WebElement element= wait.until(ExpectedConditions.visibilityOfElementLocated(selectPatient));
        try {
          if(element.isEnabled()){
              ((JavascriptExecutor) driver).executeScript(
                      "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
              ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
              return true;
          }else {
              System.out.println("Patient is disabled, waiting and retrying...");
              WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
              shortWait.until(ExpectedConditions.elementToBeClickable(selectPatient));
              ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
              return true;
          }
        } catch (Exception e) {
            System.out.println("Error clicking 'Select Patient': " + e.getMessage());
            return false;
        }
    }

    public boolean clickProceedByBookingButton() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(proceedByBookingBtn));
             ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking 'Proceed with Booking' button: " + e.getMessage());
            return false;
        }
    }

    public boolean clickSelectFirstCondition(String conditionName) {
            By condition = By.xpath(
                    "//div[contains(@class,'radio_button_card rounded-xl')]//p[normalize-space(text())='"+conditionName+"']");
            int attempts = 0;

            while (attempts < 3) {
                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(condition));
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(condition));
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                    return true;
                } catch (StaleElementReferenceException e) {
                    System.out.println("Stale element encountered, retrying... Attempt: " + (attempts + 1));
                    attempts++;
                } catch (Exception e) {
                    System.out.println("Error clicking 'Select Condition': " + e.getMessage());
                    return false;
                }
            }
            System.out.println("Failed to click 'Select Condition' after retries.");
            return false;
    }

    public boolean selectFirstAvailableDay() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(firstAvailableDay));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            return true;
        } catch (Exception e) {
            System.out.println("Error selecting first available day: " + e.getMessage());
            return false;
        }
    }

    public boolean selectFirstAvailableTimeSlot() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(firstAvailableTSlot));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            return true;
        } catch (Exception e) {
            System.out.println("Error selecting first available time slot: " + e.getMessage());
            return false;
        }
    }
    public String getSelectedProviderName(){
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(providerNameBelowSelectedSlot));
            return driver.findElement(providerNameBelowSelectedSlot).getText();
        }catch (Exception e){
            System.out.println("Error getting selected providers name : " + e.getMessage());
            return null;
        }
    }
    public String getFirstName() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
            return element.getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting first name: " + e.getMessage());
            return null;
        }
    }

    public String getLastName() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(lastName));
            return element.getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting last name: " + e.getMessage());
            return null;
        }
    }

    public String getDOB() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(dob));
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

    public String getDOBInMMDD() {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(dob));
            String dob = element.getAttribute("value").trim();

            // Adjust the format here to match your actual input!
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dob);

            // Format the date into the desired output format 
            SimpleDateFormat targetFormat = new SimpleDateFormat("MM/dd/yyyy");
            String formattedDate = targetFormat.format(date);
            return formattedDate;
        } catch (Exception e) {
            System.out.println("Error getting DOB: " + e.getMessage());
            return null;
        }
    }
    public String getAddressOne() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(addressOne));
            return element.getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting address one: " + e.getMessage());
            return null;
        }
    }

    public String getAddressTwo() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(addressTwo));
            return element.getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting address two: " + e.getMessage());
            return null;
        }
    }

    public String getZipcode() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(zipcode));
            return element.getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting zipcode: " + e.getMessage());
            return null;
        }
    }

    public String getFeet() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(feet));
            return element.getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting feet: " + e.getMessage());
            return null;
        }
    }

    public String getInch() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(inch));
            return element.getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting inch: " + e.getMessage());
            return null;
        }
    }

    public String getWeight() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(weight));
            return element.getAttribute("value").replaceAll(".0","").trim();
        } catch (Exception e) {
            System.out.println("Error getting weight: " + e.getMessage());
            return null;
        }
    }
    public String getGender() {
        try{
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(selectedGender));
            return element.getText();
        }catch (Exception e){
            System.out.println("Error getting gender: " + e.getMessage());
            return null;
        }
    }
    public boolean setFirstName(String fName) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(firstName));
             element.sendKeys(fName);
            return true;
        } catch (Exception e) {
            System.out.println("Error getting first name: " + e.getMessage());
            return false;
        }
    }
    public boolean setLastName(String lName) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(lastName));
            element.sendKeys(lName);
            return true;
        } catch (Exception e) {
            System.out.println("Error getting last name: " + e.getMessage());
            return false;
        }
    }
    public boolean setDOB(String dob) {
        try {
            String[] date = dob.split("/");
            String day = date[1];
            String month = date[0];
            String year = date[2];
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name=\"month\"]"))).sendKeys(month);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name=\"day\"]"))).sendKeys(day);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name=\"year\"]"))).sendKeys(year);
            return true;
        } catch (Exception e) {
            System.out.println("Error setting DOB: " + e.getMessage());
            return false;
        }
    }
    public boolean setAddressLineOne(String address1) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(addressOne));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            element.clear();
            element.sendKeys(address1);
            return true;
        } catch (Exception e) {
            System.out.println("Error entering address line 1: " + e.getMessage());
            return false;
        }
    }

    public boolean setAddressLineTwo(String address2) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(addressTwo));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            element.clear();
            element.sendKeys(address2);
            return true;
        } catch (Exception e) {
            System.out.println("Error entering address line 2: " + e.getMessage());
            return false;
        }
    }

    public boolean setHeightFeet(String heightFeet) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(feet));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            element.clear();
            element.sendKeys(heightFeet);
            return true;
        } catch (Exception e) {
            System.out.println("Error entering height (feet): " + e.getMessage());
            return false;
        }
    }

    public boolean setHeightInches(String inches) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(inch));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            element.clear();
            element.sendKeys(inches);
            return true;
        } catch (Exception e) {
            System.out.println("Error entering height (inches): " + e.getMessage());
            return false;
        }
    }

    public boolean setWeight(String w) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(weight));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            element.clear();
            element.sendKeys(w);
            return true;
        } catch (Exception e) {
            System.out.println("Error entering weight: " + e.getMessage());
            return false;
        }
    }

    public boolean selectGender(String gender) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                    "//div[@class=\"flex items-center cursor-pointer mr-12\"]/p[text()='"+gender+"']")));
            element.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error selecting 'Male' gender: " + e.getMessage());
            return false;
        }
    }
    public boolean setCity(String cityValue) {
        try{
            wait.until(ExpectedConditions.elementToBeClickable(city)).sendKeys(cityValue);
            return true;
        }catch (Exception e){
            System.out.println("Error setting city: " + e.getMessage());
            return false;
        }
    }
    public boolean setZipcode(String zipcodeValue) {
        try{
            wait.until(ExpectedConditions.elementToBeClickable(zipcode)).sendKeys(zipcodeValue);
            return true;
        }catch (Exception e){
            System.out.println("Error setting zipcode: " + e.getMessage());
            return false;
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
    public boolean uploadId(String filePath) {
        try {
            // Wait for the file input to be present and interactable
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(id));
             ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            element.sendKeys(filePath);
            return true;
        } catch (Exception e) {
            System.out.println("Error uploading picture: " + e.getMessage());
            return false;
        }
    }
    public boolean clickAddPharmacyAndSwitchToListView() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(addPharmacy));
            element.click();
            Thread.sleep(3000);
            WebElement switchElement = wait.until(ExpectedConditions.presenceOfElementLocated(switchToListView));
            switchElement.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking 'Add Pharmacy': " + e.getMessage());
            return false;
        }
    }

    public boolean clickFirstPharmacy() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(firstPharmacy));
            Thread.sleep(1500);
            element.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking 'First Pharmacy': " + e.getMessage());
            return false;
        }
    }

    public boolean setGeneralSymptoms(String symptom) {
        return clickSymptomByCategory("General", symptom);
    }

    public boolean setSkinSymptoms(String symptom) {
        return clickSymptomByCategory("Skin", symptom);
    }

    public boolean setHeadSymptoms(String symptom) {
        return clickSymptomByCategory("Head", symptom);
    }

    public boolean setEarSymptoms(String symptom) {
        return clickSymptomByCategory("Ears", symptom);
    }

    public boolean setEyeSymptoms(String symptom) {
        return clickSymptomByCategory("Eyes", symptom);
    }

    public boolean setVisionSymptoms(String symptom) {
        return clickSymptomByCategory("Vision", symptom);
    }

    public boolean setNoseSymptoms(String symptom) {
        return clickSymptomByCategory("Nose", symptom);
    }

    public boolean setMouthThroatSymptoms(String symptom) {
        return clickSymptomByCategory("Mouth / Throat", symptom);
    }

    public boolean setNeckSymptoms(String symptom) {
        return clickSymptomByCategory("Neck", symptom);
    }

    public boolean setBreastSymptoms(String symptom) {
        return clickSymptomByCategory("Breasts", symptom);
    }

    public boolean setRespiratorySymptoms(String symptom) {
        return clickSymptomByCategory("Respiratory", symptom);
    }

    public boolean setCardiovascularSymptoms(String symptom) {
        return clickSymptomByCategory("Cardiovascular", symptom);
    }

    public boolean setGastrointestinalSymptoms(String symptom) {
        return clickSymptomByCategory("Gastrointestinal", symptom);
    }

    public boolean setUrinarySymptoms(String symptom) {
        return clickSymptomByCategory("Urinary", symptom);
    }

    public boolean setGenitalSymptoms(String symptom) {
        return clickSymptomByCategory("Genital", symptom);
    }

    public boolean setFemaleSymptoms(String symptom) {
        return clickSymptomByCategory("Female", symptom);
    }

    public boolean setVascularSymptoms(String symptom) {
        return clickSymptomByCategory("Vascular", symptom);
    }

    public boolean setMusculoskeletalSymptoms(String symptom) {
        return clickSymptomByCategory("Musculoskeletal", symptom);
    }

    public boolean setNeurologicSymptoms(String symptom) {
        return clickSymptomByCategory("Neurologic", symptom);
    }

    public boolean setHematologicSymptoms(String symptom) {
        return clickSymptomByCategory("Hematologic", symptom);
    }

    public boolean setEndocrineSymptoms(String symptom) {
        return clickSymptomByCategory("Endocrine", symptom);
    }

    public boolean setPsychiatricSymptoms(String symptom) {
        return clickSymptomByCategory("Psychiatric", symptom);
    }

    private boolean clickSymptomByCategory(String category, String symptom) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                    "//p[text()='" + category + "']/following-sibling::div//p[normalize-space(text())='" + symptom + "']")));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            element.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error setting " + category + " symptoms: " + e.getMessage());
            return false;
        }
    }
    public boolean clickNoneBtnInAllergy() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(noneBtnInAllergy));
             ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            element.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking 'None': " + e.getMessage());
            return false;
        }
    }
    public boolean addAllergy(String allergyName, String reactionType, String categoryType) {
        try {
            // 2. Click "Search Allergy"
            WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(searchAllergy));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", searchBtn);
            searchBtn.click();

            // 3. Enter allergy name
            WebElement allergyInput = wait.until(ExpectedConditions.elementToBeClickable(enterAllergy));
            allergyInput.sendKeys(allergyName);
            Thread.sleep(2000);
            // Step 4: Wait for the matching Allergy option (by exact text) to appear
            try {
                WebElement matchingOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'option') and text()='" + allergyName + "']")));
                // Step 5: Scroll to and click the option using JavaScript
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", matchingOption);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", matchingOption);
            } catch (Exception e) {
                WebElement createOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(@id,'option') and text()='Create \"" + allergyName + "\"']")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", createOption);
                Thread.sleep(500);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", createOption);
            }
            // 4. Select reaction from Dropdown
            WebElement reactionDropdown = wait.until(ExpectedConditions.elementToBeClickable(reaction));
            new Select(reactionDropdown).selectByVisibleText(reactionType);
            String cType = null;
            if (categoryType.equals("Drug or Medication Allergy")) {
                cType = "Medication Allergy";
            }
            By medicationCategory = By.xpath("//p[text()='" + cType + "']");

            // 6. Click medication allergy category image
            WebElement categoryImg = wait.until(ExpectedConditions.elementToBeClickable(medicationCategory));
            categoryImg.click();

            // 7. Click "Add Allergy" button
            WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(addAllergyBtn));
            addBtn.click();

            //Click done
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[text()='Done']"))).click();
            return true;
        } catch (Exception e) {
            System.out.println("Error adding allergy: " + e.getMessage());
            return false;
        }
    }

    public boolean clickSearchMedicationButton() {
        try {
            Thread.sleep(2000); // Optional wait for UI stability
            WebElement searchBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(searchMedication));
            searchBtn.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking 'Search Medication' button: " + e.getMessage());
            return false;
        }
    }
    public boolean addMedication(String medicationName, String dosageValue, String formValue, String frequencyValue, String perValue) {
        try {
            // Step 1: Click the medication input to activate the dropdown
            try {
                Thread.sleep(1000);
                WebElement medicationInput = wait.until(ExpectedConditions.visibilityOfElementLocated(enterMedication));
                medicationInput.click();
            } catch (Exception e) {
                System.out.println("Error clicking medication input: " + e.getMessage());
                return false;
            }
            // Step 2: Locate the real input field and type the medication name
            try {
                WebElement realInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//input[contains(@id,'react')]")));
                realInput.sendKeys(medicationName);

                // Step 3: Wait for the dropdown menu to become visible
                WebElement dropdownMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'menu')]")));

                // Step 4: Wait for the matching medication option (by exact text) to appear
                WebElement matchingOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'option') and text()='" + medicationName + "']")));

                // Step 5: Scroll to and click the option using JavaScript
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", matchingOption);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", matchingOption);

            } catch (Exception e) {
                System.out.println("Error entering/selecting medication name: " + e.getMessage());
                try {
                   By medication= By.xpath("//div[contains(@id,'option') and text()='Create \"" + medicationName + "\"']");
                   wait.until(ExpectedConditions.visibilityOfElementLocated(medication ));
                    WebElement createMedication = driver.findElement(medication);
                    Thread.sleep(2000);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", createMedication);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", createMedication);
                } catch (Exception e1) {
                    System.out.println("Error creating medication: " + e1.getMessage());
                    return false;
                }
            }
            // Fill dosage
            try {
                WebElement dosageField = wait.until(ExpectedConditions.visibilityOfElementLocated(dosage));
                dosageField.clear();
                dosageField.sendKeys(dosageValue);
            } catch (Exception e) {
                System.out.println("Error entering dosage: " + e.getMessage());
            }
            //  Select form
            try {
                new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(form)))
                        .selectByVisibleText(formValue);
            } catch (Exception e) {
                System.out.println("Error selecting form: " + e.getMessage());
            }
            //  Select frequency
            try {
                new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(frequency)))
                        .selectByVisibleText(frequencyValue);
            } catch (Exception e) {
                System.out.println("Error selecting frequency: " + e.getMessage());
            }
            //  Select per
            try {
                WebElement perElement = wait.until(ExpectedConditions.visibilityOfElementLocated(per));
                perElement.sendKeys(perValue);
            } catch (Exception e) {
                System.out.println("Error selecting 'per': " + e.getMessage());
            }
            //  Click the external Add button
            try {
                WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(addMedicationBtn));
                addBtn.click();
                // Step 10: Click Done
                try {
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[text()='Done']"))).click();
                } catch (Exception e) {
                    System.out.println("Error clicking 'Done' button: " + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Error clicking 'Add Medication' button: " + e.getMessage());
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean clickNoneBtnInMedication() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(noneBtnInMedication));
             ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            element.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking 'None': " + e.getMessage());
            return false;
        }
    }
    public boolean setAdditionalMedicalConditionsForFemale(String medicalCondition) {
        try{
            By conditionOption= By.xpath("//div[@class='check_box_card_container']//p[text()='"+medicalCondition+"']");
            wait.until(ExpectedConditions.visibilityOfElementLocated(conditionOption));
            WebElement element = driver.findElement(conditionOption);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
            element.click();
            return true;
        }catch (Exception e){
            System.out.println("Error setting additional medical conditions for female: " + e.getMessage());
            return false;
        }
    }
    public boolean setOptionalDetails(String details){
        try{
            wait.until(ExpectedConditions.elementToBeClickable(additionalArea)).sendKeys(details);
            return true;
        }catch (Exception e){
            System.out.println("Error setting Additional Details: " + e.getMessage());
            return false;
        }
    }
    // Clicks all accept terms checkboxes/images
    public boolean acceptAllCodeOfConduct() {
        try {
            List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(acceptCodeOfConduct));
            for (int i = 0; i < elements.size(); i++) {
                try {
                    wait.until(ExpectedConditions.elementToBeClickable(elements.get(i))).click();
                } catch (Exception e) {
                    System.out.println("Error clicking accept term element #" + (i + 1) + ": " + e.getMessage());
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error locating accept terms elements: " + e.getMessage());
            return false;
        }
    }
    public boolean paymentByCard(String number, String expiryValue, String cvvValue) {
        try {
            try {
                // First attempt to wait for the Payment Method Section
                Thread.sleep(1000);
                WebElement paymentSection = wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentMethodSection));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", paymentSection);
            } catch (Exception e) {
                System.out.println("Payment section not visible on first try, retrying after short delay...");
                Thread.sleep(3000);
                wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentMethodSection));
            }
            // Click card type option
            try {
                WebElement cardTypeElement = wait.until(ExpectedConditions.elementToBeClickable(cardType));
                cardTypeElement.click();
            } catch (Exception e) {
                System.out.println("Error clicking card type: " + e.getMessage());
            }

            // Enter card number
            try {
                WebElement cardNumberIframe = driver.findElement(By.xpath("//iframe[@id=\"braintree-hosted-field-number\"]"));
                driver.switchTo().frame(cardNumberIframe);
                WebElement cardNumberElement = wait.until(ExpectedConditions.visibilityOfElementLocated(cardNumber));
                cardNumberElement.clear();
                cardNumberElement.sendKeys(number);
            } catch (Exception e) {
                System.out.println("Error entering card number: " + e.getMessage());
            } finally {
                driver.switchTo().defaultContent();
            }

            // Enter expiry
            try {
                WebElement iframe = driver.findElement(By.xpath("//iframe[@id=\"braintree-hosted-field-expirationDate\"]"));
                driver.switchTo().frame(iframe);
                WebElement expiryElement = wait.until(ExpectedConditions.visibilityOfElementLocated(expiry));
                expiryElement.clear();
                expiryElement.sendKeys(expiryValue);
            } catch (Exception e) {
                System.out.println("Error entering expiry: " + e.getMessage());
            } finally {
                driver.switchTo().defaultContent();
            }

            // Enter CVV
            try {
                WebElement iframe = driver.findElement(By.xpath("//iframe[@id=\"braintree-hosted-field-cvv\"]"));
                driver.switchTo().frame(iframe);
                WebElement cvvElement = wait.until(ExpectedConditions.visibilityOfElementLocated(cvv));
                cvvElement.clear();
                cvvElement.sendKeys(cvvValue);
            } catch (Exception e) {
                System.out.println("Error entering CVV: " + e.getMessage());
            } finally {
                driver.switchTo().defaultContent();
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error in paymentByCard: " + e.getMessage());
            return false;
        }
    }
    public boolean isSubmitForEvaluationEnabled() {
        try {
            WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(submitForEvaluation));
            return submitBtn.isEnabled();
        } catch (Exception e) {
            System.out.println("Submit Visit button is not enabled: " + e.getMessage());
            return false;
        }
    }
    public boolean clickSubmitForEvaluation() {
        try {
            WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(submitForEvaluation));
            submitBtn.click();
            return true;
        } catch (Exception e) {
            System.out.println("First attempt to click 'Submit Visit' failed: " + e.getMessage());
            try {
                driver.navigate().refresh();
                WebElement refreshedBtn = wait.until(ExpectedConditions.elementToBeClickable(submitForEvaluation));
                refreshedBtn.click();
                return true;
            } catch (Exception ex) {
                System.out.println("Retry after refresh also failed: " + ex.getMessage());
                return false;
            }
        }
    }
    public String getProviderNameInTheVisitSubmittedPage(){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(providerNameInVisitSubmittedPage));
            WebElement element = driver.findElement(providerNameInVisitSubmittedPage);
            return element.getText();
        }catch (Exception e){
            System.out.println("Error getting provider name in Visit submitted page: "+e.getMessage());
            return null;
        }
    }
    public boolean isVisitSubmitted() {
        try {
            WebElement visitSubmittedText = wait.until(ExpectedConditions.visibilityOfElementLocated(visitSubmitted));
            return visitSubmittedText.isDisplayed();
        } catch (TimeoutException e) {
            System.out.println("Visit submitted text not found within wait time.");
        } catch (Exception e) {
            System.out.println("Error while checking 'Visit Submitted' text: " + e.getMessage());
        }
        return false;
    }
    public boolean clickGoToMyVisitsButton() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(goToMyVisitsButton));
            button.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking 'Go to My Visits' button: " + e.getMessage());
            return false;
        }
    }
}
