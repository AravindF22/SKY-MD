package pages.PatientPortal;

import base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BehaviouralHealthVisitPage extends BasePage {
        public WebDriverWait wait;
        public BehaviouralHealthVisitPage(WebDriver driver) {
            super(driver);
            wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        }
        private final By patientAsMySelf = By.xpath("//div[@class=\"radio_button_card_container\"]/div[1]");
        private final By nextBtn = By.xpath("//button[text()='Next']");
        private final By proceedWithBookingBtn = By.xpath("//button[text()='Proceed with Booking']");
        private final By individualBtn = By.xpath("//div[@class=\"radio_button_card_container\"]/div[1]");
        private final By firstAvailableDay = By.xpath("//div[@class=\"flex pb-10\"]/div[1]");
        private final By SeventhDay = By.xpath("//div[@class=\"flex pb-10\"]/div[7]");
        private final By firstAvailableTSlot = By.xpath("//div[contains(@class,'grid grid-cols-2')]/div[1]");
        private final By providerNameBelowSelectedSlot = By.xpath("//div[contains(@class,'relative p-6')]//h3");

        private final By firstNameField = By.cssSelector("input#first_name");
        private final By lastNameField =By.cssSelector("input#last_name");
        private final By dobField  = By.xpath("//div[@class=\"react-date-picker__inputGroup\"]/input[1]");
        private final By addressOneField = By.cssSelector("#address_1");
        private final By addressTwoField = By.cssSelector("#address_2");
        private final By heightFeetField =  By.cssSelector("#heightFeet");
        private final By heightInchesField = By.cssSelector("#heightInches");
        private final By weightField = By.cssSelector("#weight");
        private final By zipcode = By.cssSelector("#zipcode");
        private final By id = By.xpath("//input[@id=\"confirm_identity_image_picker\"]");
        //MedicalCondition
        private final By medicalCondition = By.xpath("//p[text()='Search Medical Conditions']/parent::div");
        private final By searchMedicalCondition = By.xpath("//input[@placeholder=\"Start typing...\"]");
        private final By DoneBtnAfterAddingMedicalCondition = By.xpath("//p[text()='Done']");
        //MEDICATIONS
        private final By searchMedication = By.xpath("//p[text()='Search Medication']/parent::div");
        private final By enterMedication = By.xpath("//div[@class=\" css-s1z5xt\"]");
        private final By addMedicationBtn = By.xpath("//button[text()='Add Medication']");
        private final By dosage = By.cssSelector("input#dose");
        private final By form = By.cssSelector("select#form");
        private final By frequency = By.cssSelector("select#frequency");
        private final By per = By.cssSelector("select#per");

        //additional area
        private final By additionalArea = By.cssSelector("#additional_details");

        //terms
        private final By terms = By.xpath("//p[contains(text(),'agree to the terms')]");
        private final By acceptCodeOfConduct = By.xpath(
                "//h3[contains(text(),'code of conduct')]/ancestor::button/following-sibling::div//p");

        //payment
        //payment card
        private final By PaymentMethodSection = By.xpath("//div[@class=\"braintree-dropin braintree-loaded\"]");
        private final By cardType = By.xpath("//div[@data-braintree-id=\"payment-options-container\"]/div[1]");
        private final By cardNumber = By.xpath("//input[@id= \"credit-card-number\"]");
        private final By expiry = By.xpath("//input[@id=\"expiration\"]");
        private final By cvv = By.xpath("//input[@id='cvv']");

        private final By submitForEvaluation = By.xpath("//button[text()='Submit Visit']");
        private final By providerNameInVisitSubmittedPage= By.xpath("//div[contains(@class,'p-5 rounded-4xl')]//h3");
        private final By visitSubmitted = By.xpath("//h2[text()='Visit Submitted']");
        private final By goToMyVisitsButton = By.xpath("//button[text()='Go to My Visits']");

    public boolean clickPatientAsMySelf() {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(patientAsMySelf));
                element.click();
                return true;
            } catch (Exception e) {
                System.out.println("Error clicking 'Patient As Myself': " + e.getMessage());
                return false;
            }
        }

        public boolean clickNextButton() {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(nextBtn));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                return true;
            } catch (Exception e) {
                System.out.println("Error clicking 'Next' button: " + e.getMessage());
                return false;
            }
        }

        public boolean clickProceedWithBookingButton() {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(proceedWithBookingBtn));
                element.click();
                return true;
            } catch (Exception e) {
                System.out.println("Error clicking 'Proceed with Booking' button: " + e.getMessage());
                return false;
            }
        }

        public boolean clickIndividualButton() {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(individualBtn));
                element.click();
                return true;
            } catch (Exception e) {
                System.out.println("Error clicking 'Individual' button: " + e.getMessage());
                return false;
            }
        }

        public boolean selectFirstAvailableDay() {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(firstAvailableDay));
                element.click();
                return true;
            } catch (Exception e) {
                System.out.println("Error selecting first available day: " + e.getMessage());
                return false;
            }
        }
        public boolean selectSeventhDay(){
            try
            {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(SeventhDay));
                element.click();
                return true;
            }catch (Exception e){
                System.out.println("Error selecting seventh day: " + e.getMessage());
                return false;
            }
        }

        public boolean selectFirstAvailableTimeSlot() {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(firstAvailableTSlot));
                element.click();
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
        public boolean enterFirstName(String firstName) {
            try {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
                element.clear();
                element.sendKeys(firstName);
                return true;
            } catch (Exception e) {
                System.out.println("Error entering first name: " + e.getMessage());
                return false;
            }
        }

        public boolean enterLastName(String lastName) {
            try {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameField));
                element.clear();
                element.sendKeys(lastName);
                return true;
            } catch (Exception e) {
                System.out.println("Error entering last name: " + e.getMessage());
                return false;
            }
        }
    public String getFirstName() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
            return element.getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error entering first name: " + e.getMessage());
            return null;
        }
    }

    public String getLastName() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameField));
            return element.getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error entering last name: " + e.getMessage());
            return null;
        }
    }
        public boolean setDOB(String dob) {
            try {
                String[] date = dob.split("/");
                String day = date[0];
                String month = date[1];
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
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(addressOneField));
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
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(addressTwoField));
                element.clear();
                element.sendKeys(address2);
                return true;
            } catch (Exception e) {
                System.out.println("Error entering address line 2: " + e.getMessage());
                return false;
            }
        }

        public boolean setHeightFeet(String feet) {
            try {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(heightFeetField));
                element.clear();
                element.sendKeys(feet);
                return true;
            } catch (Exception e) {
                System.out.println("Error entering height (feet): " + e.getMessage());
                return false;
            }
        }

        public boolean setHeightInches(String inches) {
            try {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(heightInchesField));
                element.clear();
                element.sendKeys(inches);
                return true;
            } catch (Exception e) {
                System.out.println("Error entering height (inches): " + e.getMessage());
                return false;
            }
        }

        public boolean setWeight(String weight) {
            try {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(weightField));
                element.clear();
                element.sendKeys(weight);
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

        public String getZipcode(){
            try{
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(zipcode));
                return element.getAttribute("value");
            }
            catch (Exception e) {
                System.out.println("Error getting zipcode: " + e.getMessage());
                return null;
            }
        }
        public boolean uploadId(String filePath) {
            try {
                // Wait for the file input to be present and interactable
                WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(id));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", input);
                input.sendKeys(filePath);
                return true;
            } catch (Exception e) {
                System.out.println("Error uploading picture: " + e.getMessage());
                return false;
            }
        }
        public boolean setTherapyReason(String therapyReason){
            try{
                By therapy = By.xpath("//p[@class=\"text-2xl font-bold text-indigo\" and text()='"+therapyReason+"']");
                   wait.until(ExpectedConditions.visibilityOfElementLocated(therapy)).click();
                   return true;
            }catch (Exception e){
                System.out.println("Error setting therapy reason: " + e.getMessage());
                return false;
            }
        }
        public boolean setSymptoms(String symp){
            try{
                By Symptom = By.xpath("//p[@class=\"font-medium text-2xl\" and text()='"+symp+"']");
                wait.until(ExpectedConditions.visibilityOfElementLocated(Symptom)).click();
                return true;
            }catch (Exception e){
                System.out.println("Error setting Symptom: " + e.getMessage());
                return false;
            }
        }
        public boolean setExerciseRoutine(String exerciseRoutine){
            try{
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//label[text()='Do you exercise regularly?']/following-sibling::div//p[@class=\"text-xl font-bold\" and text()='"+exerciseRoutine+"']"))).click();
                return true;
            }catch (Exception e){
                System.out.println("Error setting exercise routine: " + e.getMessage());
                return false;
            }
        }
        public boolean setAlcoholConsumption(String alcoholConsumption){
            try{
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//label[text()='Do you drink alcohol?']/following-sibling::div//p[@class=\"text-xl font-bold\" and text()='"+alcoholConsumption+"']"))).click();
                return true;
            }catch (Exception e){
                System.out.println("Error setting alcohol consumption: " + e.getMessage());
                return false;
            }
        }
        public boolean setSmokingStatus(String smokingStatus){
            try{
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//label[text()='Do you smoke?']/following-sibling::div//p[@class=\"text-xl font-bold\" and text()='"+smokingStatus+"']"))).click();
                return true;
            }catch (Exception e){
                System.out.println("Error setting smoking: " + e.getMessage());
                return false;
            }
        }
        public boolean setSmokingItems(String smokingItems){
            try{
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//p[contains(@class,'text-xl') and text()='"+smokingItems+"']"))).click();
                return true;
            }
            catch (Exception e) {
                System.out.println("Error setting smoking items: " + e.getMessage());
                return false;
            }
        }
        public boolean clickAddMedicalCondition(){
            try{
                wait.until(ExpectedConditions.visibilityOfElementLocated(medicalCondition)).click();
                return true;
            }catch (Exception e){
                System.out.println("Error clicking Medical Condition: " + e.getMessage());
                return false;
            }
        }
        public boolean searchMedicalCondition(String medicalCondition){
            try{
                wait.until(ExpectedConditions.visibilityOfElementLocated(searchMedicalCondition)).sendKeys(medicalCondition);
                try {
                    WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                            "//p[text()='"+medicalCondition+"']/parent::div")));
                    element.click();
                    return true;
                }
                catch (Exception e) {
                    System.out.println("Error searching Medical Condition: " + e.getMessage());
                    return false;
                }
            }catch (Exception e){
                System.out.println("Error searching Medical Condition: " + e.getMessage());
                return false;
            }
        }
        public boolean clickDoneBtnAfterAddingMedicalCondition(){
            try{
                wait.until(ExpectedConditions.visibilityOfElementLocated(DoneBtnAfterAddingMedicalCondition)).click();
                return true;
            }catch (Exception e){
                System.out.println("Error clicking Done Button after adding Medical Condition: " + e.getMessage());
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
        public boolean addMedicationOne(String medicationName, String dosageValue, String formValue, String frequencyValue, String perValue) {
            try {
                // Step 1: Click the medication input to activate the dropdown
                try {
                    Thread.sleep(500);
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
                        WebElement createMedication = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//div[contains(@id,'option') and text()='Create \"" + medicationName + "\"']")));
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
                } catch (Exception e) {
                    System.out.println("Error clicking 'Add Medication' button: " + e.getMessage());
                }
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
    public boolean addMedicationTwo(String medicationName, String dosageValue, String formValue, String frequencyValue, String perValue) {
        try {
            // Step 1: Click the medication input to activate the dropdown
            try {
                Thread.sleep(500);
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
                    WebElement createMedication = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//div[contains(@id,'option') and text()='Create \"" + medicationName + "\"']")));
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
            } catch (Exception e) {
                System.out.println("Error clicking 'Add Medication' button: " + e.getMessage());
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
        public boolean clickDoneBtnAfterAddingMedication() {

            try {
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[text()='Done']"))).click();
                return true;
            } catch (Exception e) {
                System.out.println("Error clicking 'Done' button: " + e.getMessage());
                return false;
            }
        }
        public boolean setTherapistHistoryStatus(String status){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                        "//label[contains(text(),'therapist ')]/following-sibling::div//p[text()='"+status+"']"))).click();
                return true;
            } catch (Exception e) {
                System.out.println("Error setting therapist history: " + e.getMessage());
                return false;
            }
        }
        public boolean setReasonForTreatment(String reason){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                        "//label[text()='Reason for treatment?']/following-sibling::div//textarea"))).sendKeys(reason);
                return true;
            }catch (Exception e){
                System.out.println("Error setting reason for treatment: " + e.getMessage());
                return false;
            }
        }
        public boolean setHospitalizedHistory(String status){
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(
                       By.xpath("//label[contains(text(),'hospitalized')]/following-sibling::div//p[text()='"+status+"']")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                element.click();
                return true;
            }catch (Exception e){
                System.out.println("Error setting Hospitalized History: " + e.getMessage());
                return false;
            }
        }
        public boolean setHospitalizedReason(String reason){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                        "//p[contains(@class,'text-xl') and text()='"+reason+"']"))).click();
                return true;
            }catch (Exception e){
                System.out.println("Error setting Hospitalized Reason: " + e.getMessage());
                return false;
            }
        }
        public boolean setSuicidalThoughts(String thoughts){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                        "//p[contains(text(),'suicide')]/following-sibling::div//p[text()='"+thoughts+"']"))).click();
                return true;
            }catch (Exception e){
                System.out.println("Error setting Suicidal Thoughts: " + e.getMessage());
                return false;
            }
        }
        public boolean setAdditionalDetails(String details){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(additionalArea)).sendKeys(details);
                return true;
            }catch (Exception e){
                System.out.println("Error setting Additional Details: " + e.getMessage());
                return false;
            }
        }

    public int scoreOf(String questionnaireOption){
        switch (questionnaireOption) {
            case "Not at all":
                return 0;
            case "Several days":
                return 1;
            case "More than half the days":
                return 2;
            case "Nearly every day":
                return 3;
        }
        return -1;
    }

    public int setQuestionnaireOne(String questionnaire) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                    "//label[contains(text(),'1.')]/parent::div//p[text()='" + questionnaire + "']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            return scoreOf(questionnaire);
        } catch (Exception e) {
            System.out.println("Error setting Questionnaire 1: " + e.getMessage());
            return -1;
        }
    }

    public int setQuestionnaireTwo(String questionnaire) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                    "//label[contains(text(),'2.')]/parent::div//p[text()='" + questionnaire + "']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            return scoreOf(questionnaire);
        } catch (Exception e) {
            System.out.println("Error setting Questionnaire 2: " + e.getMessage());
            return -1;
        }
    }

    public int setQuestionnaireThree(String questionnaire) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                    "//label[contains(text(),'3.')]/parent::div//p[text()='" + questionnaire + "']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            return scoreOf(questionnaire);
        } catch (Exception e) {
            System.out.println("Error setting Questionnaire 3: " + e.getMessage());
            return -1;
        }
    }

    public int setQuestionnaireFour(String questionnaire) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                    "//label[contains(text(),'4.')]/parent::div//p[text()='" + questionnaire + "']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            return scoreOf(questionnaire);
        } catch (Exception e) {
            System.out.println("Error setting Questionnaire 4: " + e.getMessage());
            return -1;
        }
    }

    public int setQuestionnaireFive(String questionnaire) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                    "//label[contains(text(),'5.')]/parent::div//p[text()='" + questionnaire + "']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            return scoreOf(questionnaire);
        } catch (Exception e) {
            System.out.println("Error setting Questionnaire 5: " + e.getMessage());
            return -1;
        }
    }

    public int setQuestionnaireSix(String questionnaire) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                    "//label[contains(text(),'6.')]/parent::div//p[text()='" + questionnaire + "']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            return scoreOf(questionnaire);
        } catch (Exception e) {
            System.out.println("Error setting Questionnaire 6: " + e.getMessage());
            return -1;
        }
    }

    public int setQuestionnaireSeven(String questionnaire) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                    "//label[contains(text(),'7.')]/parent::div//p[text()='" + questionnaire + "']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            return scoreOf(questionnaire);
        } catch (Exception e) {
            System.out.println("Error setting Questionnaire 7: " + e.getMessage());
            return -1;
        }
    }

    public int setQuestionnaireEight(String questionnaire) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                    "//label[contains(text(),'8.')]/parent::div//p[text()='" + questionnaire + "']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            return scoreOf(questionnaire);
        } catch (Exception e) {
            System.out.println("Error setting Questionnaire 8: " + e.getMessage());
            return -1;
        }
    }

    public int setQuestionnaireNine(String questionnaire) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                    "//label[contains(text(),'9.')]/parent::div//p[text()='" + questionnaire + "']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            return scoreOf(questionnaire);
        } catch (Exception e) {
            System.out.println("Error setting Questionnaire 9: " + e.getMessage());
            return -1;
        }
    }

    public boolean setQuestionnaireTen(String TenthQuestionnaire) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                    "//label[contains(text(),'10.')]/parent::div//p[text()='" + TenthQuestionnaire + "']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            return true;
        } catch (Exception e) {
            System.out.println("Error setting Questionnaire 10: " + e.getMessage());
            return false;
        }
    }
    public boolean acceptTerms(){
        try{
            WebElement termsCheck = wait.until(ExpectedConditions.visibilityOfElementLocated(terms));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", termsCheck);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", termsCheck);
            return true;
        }catch (Exception e){
            System.out.println("Error accepting Terms: " + e.getMessage());
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