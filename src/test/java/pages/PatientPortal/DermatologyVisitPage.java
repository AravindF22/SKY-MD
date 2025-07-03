package pages.PatientPortal;

import base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DermatologyVisitPage extends BasePage {
    private WebDriverWait wait;

    public DermatologyVisitPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By selectDermProvider = By.xpath("//div[contains(@class,\"bg-white rounded-xl p-6 flex flex-col\")]");
    private final By continueAfterSelectingDermProviderOption = By.xpath("//button[text()='Continue']");
    private final By proceedBtnForRecommededProvider = By.xpath("(//button[text()='Proceed with consultation'])[1]");
    private final By selectPatient = By.xpath("//div[@class=\"px-20 py-6 border-b border-borderColor\"]/div[1]");
    private final By selectPatientAsMySelf = By.xpath("//div[@class=\"radio_button_card_container\"]/div[1]");
    private final By continueButtonAfterSelectPatient = By.xpath("//p[text()='Continue']/parent::button");
    //child
    private final By addChildBtn = By.xpath("//button[text()='Add Child']");
    private final By childFirstName = By.cssSelector("#first_name");
    private final By childLastName = By.cssSelector("#last_name");
    private final By saveChildBtn = By.xpath("//button[text()='Save']");

    private final By addWardBtn = By.xpath("//button[text()='Add Ward']");
    private final By wardFirstName = By.cssSelector("#first_name");
    private final By wardLastName = By.cssSelector("#last_name");
    private final By saveWardBtn = By.xpath("//button[text()='Save']");

    private final By addSomeOneElseBtn = By.xpath("//button[text()='Add Patient']");
    private final By someOneElseFirstName = By.cssSelector("#first_name");
    private final By someOneElseLastName = By.cssSelector("#last_name");
    private final By someOneElseZipcode = By.cssSelector("#zip_code");
    private final By saveSomeOneElseBtn = By.xpath("//button[text()='Save']");

    private final By continueButtonAfterInsurance = By.xpath("//button[text()='Continue']");
    private final By addressLineOne = By.id("street_address_1"); //value
    private final By addressLineTwo = By.id("street_address_2"); //value
    private final By zipCode = By.id("zip_code"); //value
    private final By continueButton = By.xpath("//p[text()='Continue']/parent::button");
    private final By dob = By.xpath("//div[@class=\"react-date-picker__inputGroup\"]/input[1]"); //value
    private final By feet = By.id("heightFeet"); //value
    private final By inches = By.id("heightInches"); //value
    private final By weight = By.id("weight"); //value
    private final By BackButton = By.xpath("//p[normalize-space()='Back']");
    private final By backArrowForVisitForm = By.xpath("//img[@class=\"flex-none object-contain w-10 cursor-pointer\"]");
    private final By backArrowForHomePage = By.xpath("//img[@class=\"object-contain w-10 cursor-pointer \"]");
    private final By deleteVisitButton = By.xpath("//p[text()='Delete']");
    private final By confirmForDeleteVisit = By.xpath("//button[text()='Confirm']");
    private final By primaryInsuranceName = By.xpath("(//p[text()='Insurance'])[1]/following-sibling::p");
    private final By secondaryInsuranceName = By.xpath("(//p[text()='Insurance'])[2]/following-sibling::p");
    private final By memberNameInPrimaryInsurance = By.xpath("(//p[text()='Member Name'])[1]/following-sibling::p");
    private final By memberNameInSecondaryInsurance = By.xpath("(//p[text()='Member Name'])[2]/following-sibling::p");
    private final By relationshipInPrimaryInsurance = By.xpath("(//p[contains(text(),'Relationship')])[1]/following-sibling::p");
    private final By relationshipInSecondaryInsurance = By.xpath("(//p[contains(text(),'Relationship')])[2]/following-sibling::p");
    private final By memberIDInPrimaryInsurance = By.xpath("(//p[text()='Member ID'])[1]/following-sibling::p");
    private final By memberIDInSecondaryInsurance = By.xpath("(//p[text()='Member ID'])[2]/following-sibling::p");
    private final By memberDobInPrimaryInsurance = By.xpath("(//p[text()='Member DOB'])[1]/following-sibling::p");
    private final By memberDobInSecondaryInsurance = By.xpath("(//p[text()='Member DOB'])[2]/following-sibling::p");
    private final By selfPay = By.xpath("//div[@class=\"w-full\"]");

    private final By selectPatientAsMyChild = By.xpath("//div[@class=\"radio_button_card_container\"]/div[2]");
    private final By selectPatientAsWard = By.xpath("//div[@class=\"radio_button_card_container\"]/div[3]");
    private final By selectPatientAsSomeOneElse = By.xpath("//div[@class=\"radio_button_card_container\"]/div[4]");
    private final By firstPatient = By.xpath("//div[@class=\"radio_button_card_container\"]/div[1]//p");
    private final By secondPatient = By.xpath("//div[@class=\"radio_button_card_container\"]/div[2]//p");
    private final By id = By.xpath("//input[@id=\"confirm_identity_image_picker\"]");
    private final By searchAffectedPart = By.xpath("//div[@class=\"flex items-center justify-between border border-borderColor rounded-1.6 p-6 cursor-text\"]");
    private final By statusDropDown = By.xpath("//select[@name=\"status\"]");
    private final By countDropDown = By.xpath("//select[@name=\"count\"]");
    private final By dayDropDown = By.xpath("//select[@name=\"day\"]");
    private final By severityDropDown = By.xpath("//select[@name=\"severity\"]");
    private final By noBtnForSmartPhoneUpload = By.xpath("//button[text()='No']");
    private final By uploadCloseUpPic = By.xpath("(//div[@class=\"flex justify-evenly\"]/div/div)[1]");
    private final By uploadFarAwayPic = By.xpath("(//div[@class=\"flex justify-evenly\"]/div/div)[2]");
    private final By worseText = By.xpath("//textarea[@id= 'worse']");
    private final By betterText = By.xpath("//textarea[@id= 'better']");
    //allergy
    private final By yesBtnInAllergy = By.xpath("//p[text()='Yes']/parent::div");
    private final By searchAllergy = By.xpath("//p[text()='Search Allergy']/parent::div");
    private final By enterAllergy = By.xpath("//input[contains(@id,\"react-select\")]");
    private final By reaction = By.xpath("//select[@name=\"reaction\"]");
    private final By environmentCategory = By.xpath("//p[text()='Environment or Food Allergy']");
    private final By addAllergyBtn = By.xpath("//button[text()='Add Allergy']");

    //medication
    private final By yesBtnInMedication = By.xpath("//p[text()='Yes']/parent::div/parent::div");
    private final By searchMedication = By.xpath("//p[text()='Search Medication']/parent::div");
    private final By enterMedication = By.xpath("//div[@class=\" css-s1z5xt\"]");
    private final By addMedicationBtn = By.xpath("//button[text()='Add Medication']");
    private final By dosage = By.cssSelector("input#dose");
    private final By form = By.cssSelector("select#form");
    private final By frequency = By.cssSelector("select#frequency");
    private final By per = By.cssSelector("select#per");

    //skin care
    private final By yesBtnInSkinCareProduct = By.xpath("//div[@class=\"radio_button_card_container\"]/div[1]");
    private final By skinCareTextArea = By.xpath("//textarea[@id='skin_care_products']");

    private final By noBtnInAllergy = By.xpath("//p[text()='No']");
    private final By noBtnInMedication = By.xpath("//p[text()='No']");
    //pharmacy
    private final By addPharmacy = By.xpath("//img[@alt=\"location_icon\"]");
    private final By switchToListView = By.xpath("//p[contains(text(),'Switch To ')]/parent::div");
    private final By firstPharmacy = By.xpath("//div[@class=\"mb-16\"][1]/div");
    private final By optionalField = By.xpath("//textarea[@id=\"other_feedbacks\"]");
    private final By nextButtonForTAndC = By.xpath("//button[text()='Next']");
    private final By acceptTerms = By.xpath("//div[@class=\"flex cursor-pointer\"]");

    //payment card
    private final By PaymentMethodSection = By.xpath("//div[@class=\"braintree-dropin braintree-loaded\"]");
    private final By cardType = By.xpath("//div[@data-braintree-id=\"payment-options-container\"]/div[1]");
    private final By cardNumber = By.xpath("//input[@id= \"credit-card-number\"]");
    private final By expiry = By.xpath("//input[@id=\"expiration\"]");
    private final By cvv = By.xpath("//input[@id='cvv']");
    private final By submitForEvaluation = By.xpath("//button[text()='Submit for Evaluation']");
    private final By visitSubmitted = By.xpath("//h2[text()='Visit Submitted']");
    private final By providerNameInVisitSubmittedPage = By.xpath("//div[@class='flex opacity-100']//h3");
    private final By goToMyVisitsButton = By.xpath("//button[text()='Go to My Visits']");
    public void selectDermProviderSection() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(selectDermProvider));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (TimeoutException e) {
            System.out.println("Select Dermatology Provider section not visible: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error while checking Select Dermatology Provider section: " + e.getMessage());
        }
    }

    public void clickContinueAfterSelectingDermProvider() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(continueAfterSelectingDermProviderOption));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        } catch (TimeoutException e) {
            System.out.println("Continue button not clickable: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error clicking Continue button: " + e.getMessage());
        }
    }

    public void proceedBasisOfProvider(String providerName) {
        try {
            By proceedButton = By.xpath("//h2[text()='" + providerName + "']/ancestor::div[contains(@class,'justify-start')]/following-sibling::div//button");
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(proceedButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

        } catch (Exception e) {
            System.out.println("Error proceeding with " + providerName + " " + e.getMessage());
        }
    }

    public void clickProceedWithRecommendedProvider() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(proceedBtnForRecommededProvider));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        } catch (TimeoutException e) {
            System.out.println("'Proceed with consultation' button not clickable: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error while clicking 'Proceed with consultation' button: " + e.getMessage());
        }
    }

    public void clickSelectPatient() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(selectPatient)).click();
        } catch (Exception e) {
            System.err.println("Error clicking selectPatient: " + e.getMessage());
        }
    }

    public void clickSelectPatientAsMySelf() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(selectPatientAsMySelf)).click();
        } catch (Exception e) {
            System.err.println("Error clicking selectPatientAsMySelf: " + e.getMessage());
        }
        try {
            wait.until(ExpectedConditions.elementToBeClickable(continueButtonAfterSelectPatient)).click();
        } catch (Exception e) {
            System.err.println("Error clicking continueButtonAfterSelectPatient: " + e.getMessage());
        }
    }

    public void clickContinueButtonAfterSelectPatient() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(continueButtonAfterSelectPatient)).click();
        } catch (Exception e) {
            System.err.println("Error clicking continueButtonAfterSelectPatient: " + e.getMessage());
        }
    }

    public void clickContinueButtonAfterInsurance() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(continueButtonAfterInsurance)).click();
        } catch (Exception e) {
            System.err.println("Error clicking continueButtonAfterInsurance: " + e.getMessage());
        }
    }

    public void clickNoInsurance() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='No insurance, Skip and provide later']"))).click();
        } catch (Exception e) {
            System.err.println("Error clicking continueButtonAfterInsurance: " + e.getMessage());
        }
    }

    public boolean clickContinueButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
            return true;
        } catch (Exception e) {
            System.err.println("Error clicking continueButton: " + e.getMessage());
            return false;
        }
    }

    public void clickBackButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(BackButton)).click();
        } catch (Exception e) {
            System.err.println("Error clicking BackButton: " + e.getMessage());
        }
    }

    public void clickBackArrowForVisitForm() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(backArrowForVisitForm)).click();
        } catch (Exception e) {
            System.err.println("Error clicking backArrowForVisitForm: " + e.getMessage());
        }
    }

    public void clickBackArrowForHomePage() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(backArrowForHomePage)).click();
        } catch (Exception e) {
            System.err.println("Error clicking backArrowForHomePage: " + e.getMessage());
        }
    }

    public void clickDeleteVisitButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(deleteVisitButton)).click();
        } catch (Exception e) {
            System.err.println("Error clicking deleteVisitButton: " + e.getMessage());
        }
    }

    public void setConfirmForDeleteVisit() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(confirmForDeleteVisit)).click();
        } catch (Exception e) {
            System.err.println("Error clicking deleteVisitButton: " + e.getMessage());
        }
    }

    public String getAddressLineOneValue() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(addressLineOne));
            return element.getAttribute("value").trim();
        } catch (Exception e) {
            System.err.println("Error getting Address Line One value: " + e.getMessage());
        }
        return null;
    }

    public String getPrimaryInsuranceName() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(primaryInsuranceName));
            return element.getText().trim();
        } catch (Exception e) {
            System.err.println("Error getting Primary Insurance Name: " + e.getMessage());
            return null;
        }
    }

    public String getSecondaryInsuranceName() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(secondaryInsuranceName));
            return element.getText().trim();
        } catch (Exception e) {
            System.err.println("Error getting Secondary Insurance Name: " + e.getMessage());
            return null;
        }
    }

    public String getMemberNameInPrimaryInsurance() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(memberNameInPrimaryInsurance));
            return element.getText().trim();
        } catch (Exception e) {
            System.err.println("Error getting Member Name in Primary Insurance: " + e.getMessage());
            return null;
        }
    }

    public String getMemberNameInSecondaryInsurance() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(memberNameInSecondaryInsurance));
            return element.getText().trim();
        } catch (Exception e) {
            System.err.println("Error getting Member Name in Secondary Insurance: " + e.getMessage());
            return null;
        }
    }

    public String getRelationshipInPrimaryInsurance() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(relationshipInPrimaryInsurance));
            return element.getText().trim();
        } catch (Exception e) {
            System.err.println("Error getting Relationship in Primary Insurance: " + e.getMessage());
            return null;
        }
    }

    public String getRelationshipInSecondaryInsurance() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(relationshipInSecondaryInsurance));
            return element.getText().trim();
        } catch (Exception e) {
            System.err.println("Error getting Relationship in Secondary Insurance: " + e.getMessage());
            return null;
        }
    }

    public String getMemberIDInPrimaryInsurance() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(memberIDInPrimaryInsurance));
            return element.getText().trim();
        } catch (Exception e) {
            System.err.println("Error getting Member ID in Primary Insurance: " + e.getMessage());
            return null;
        }
    }

    public String getMemberIDInSecondaryInsurance() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(memberIDInSecondaryInsurance));
            return element.getText().trim();
        } catch (Exception e) {
            System.err.println("Error getting Member ID in Secondary Insurance: " + e.getMessage());
            return null;
        }
    }

    public String getMemberDobInPrimaryInsurance() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(memberDobInPrimaryInsurance));
            String dob = element.getText().trim();
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date date = inputFormat.parse(dob);
            String format = Boolean.parseBoolean(ConfigReader.getProperty("gitHubActions"))
                    ? "MM/dd/yyyy"
                    : "dd/MM/yyyy";
            SimpleDateFormat targetFormat = new SimpleDateFormat(format, Locale.US);
            return targetFormat.format(date);
        } catch (Exception e) {
            System.err.println("Error getting Member DOB in Primary Insurance: " + e.getMessage());
            return null;
        }
    }


    public String getMemberDobInSecondaryInsurance() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(memberDobInSecondaryInsurance));
            String dob = element.getText().trim();

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date date = inputFormat.parse(dob);
            String format = Boolean.parseBoolean(ConfigReader.getProperty("gitHubActions"))
                    ? "MM/dd/yyyy"
                    : "dd/MM/yyyy";
            SimpleDateFormat targetFormat = new SimpleDateFormat(format, Locale.US);
            return targetFormat.format(date);
        } catch (Exception e) {
            System.err.println("Error getting Member DOB in Secondary Insurance: " + e.getMessage());
            return null;
        }
    }

    public void selectSelfPay() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(selfPay)).click();
        } catch (Exception e) {
            System.err.println("Error selecting self pay: " + e.getMessage());

        }
    }

    public String getAddressLineTwoValue() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(addressLineTwo));
            return element.getAttribute("value").trim();
        } catch (Exception e) {
            System.err.println("Error getting Address Line Two value: " + e.getMessage());
        }
        return null;
    }

    public String getZipCodeValue() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(zipCode));
            return element.getAttribute("value").trim();
        } catch (Exception e) {
            System.err.println("Error getting Zip Code value: " + e.getMessage());
        }
        return null;
    }

    public String getDOBValue() {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(dob));
            String dob = element.getAttribute("value").trim();
            // Adjust the format here to match your actual input!
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date date = inputFormat.parse(dob);

            // Format the date into the desired output format
            String format = Boolean.parseBoolean(ConfigReader.getProperty("gitHubActions"))
                    ? "MM/dd/yyyy"
                    : "dd/MM/yyyy";
            SimpleDateFormat targetFormat = new SimpleDateFormat(format, Locale.US);
            return targetFormat.format(date);
        } catch (Exception e) {
            System.err.println("Error getting DOB value: " + e.getMessage());
        }
        return null;
    }

    public String getFeetValue() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(feet));
            return element.getAttribute("value").trim();
        } catch (Exception e) {
            System.err.println("Error getting Feet value: " + e.getMessage());
        }
        return null;
    }

    public String getInchesValue() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(inches));
            return element.getAttribute("value").trim();
        } catch (Exception e) {
            System.err.println("Error getting Inches value: " + e.getMessage());
        }
        return null;
    }

    public String getWeightValue() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(weight));
            String[] weight = element.getAttribute("value").trim().split("\\.");
            return weight[0];
        } catch (Exception e) {
            System.err.println("Error getting Weight value: " + e.getMessage());
        }
        return null;
    }

    // Click "My Child" option
    public void selectPatientAsMyChild() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(selectPatientAsMyChild));
            element.click();
        } catch (TimeoutException e) {
            System.err.println("Timeout: 'My Child' option not clickable.");
        } catch (Exception e) {
            System.err.println("Error clicking 'My Child' option: " + e.getMessage());
        }
    }

    public void selectChild(String childName) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[text()='" + childName + "']")));
            element.click();
        } catch (TimeoutException e) {
            System.err.println("Timeout: 'child name' is not clickable.");
        } catch (Exception e) {
            System.err.println("Error clicking 'child name': " + e.getMessage());
        }
    }

    // Click "Ward" option
    public void selectPatientAsWard() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(selectPatientAsWard));
            element.click();
        } catch (TimeoutException e) {
            System.err.println("Timeout: 'Ward' option not clickable.");
        } catch (Exception e) {
            System.err.println("Error clicking 'Ward' option: " + e.getMessage());
        }
    }

    public void selectPatientAsSomeOneElse() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(selectPatientAsSomeOneElse)).click();
        } catch (Exception e) {
            System.out.println("Failed to click Select Patient As Someone Else: " + e.getMessage());
        }
    }

    // Get name of the child in 'Select Child'
    public String getFirstPatientName() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(firstPatient));
            return element.getText().trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Name of the child in 'Select Child' not visible.");
        } catch (Exception e) {
            System.err.println("Error getting name of the child in 'Select Child': " + e.getMessage());
        }
        return null;
    }

    // Get name of the ward in 'Select Ward'
    public String getFirstWardName() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(firstPatient));
            return element.getText().trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Name of the ward in 'Select Ward' not visible.");
        } catch (Exception e) {
            System.err.println("Error getting name of the ward in 'Select Ward': " + e.getMessage());
        }
        return null;
    }

    // Get name of the ward in 'Select Ward'
    public String getFirstSomeOneElseName() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(firstPatient));
            return element.getText().trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Name of the someone else in 'Select someone else' not visible.");
        } catch (Exception e) {
            System.err.println("Error getting name of the someone else in 'Select someone else': " + e.getMessage());
        }
        return null;
    }

    public boolean setAddressLineOne(String address) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(addressLineOne));
            element.clear();
            element.sendKeys(address);
            return true;
        } catch (Exception e) {
            System.out.println("Error setting Address Line One: " + e.getMessage());
            return false;
        }
    }

    public void setAddressLineTwo(String address) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(addressLineTwo));
            element.clear();
            element.sendKeys(address);
        } catch (Exception e) {
            System.out.println("Error setting Address Line Two: " + e.getMessage());
        }
    }

    public void setZipCode(String zip) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(zipCode));
            element.clear();
            element.sendKeys(zip);
        } catch (Exception e) {
            System.out.println("Error setting Zip Code: " + e.getMessage());
        }
    }

    public boolean setDOB(String dobValue) {
        try {
            String[] date = dobValue.split("/");
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

    public boolean setFeet(String feetValue) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(feet));
            element.clear();
            element.sendKeys(feetValue);
            return true;
        } catch (Exception e) {
            System.out.println("Error setting Feet: " + e.getMessage());
            return false;
        }
    }

    public boolean setInches(String inchesValue) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(inches));
            element.clear();
            element.sendKeys(inchesValue);
            return true;
        } catch (Exception e) {
            System.out.println("Error setting Inches: " + e.getMessage());
            return false;
        }
    }

    public boolean setWeight(String weightValue) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(weight));
            element.clear();
            element.sendKeys(weightValue);
            return true;
        } catch (Exception e) {
            System.out.println("Error setting Weight: " + e.getMessage());
            return false;
        }
    }

    public boolean setGender(String gender) {
        try {
            if (gender.equals("Male"))
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"flex items-center cursor-pointer mr-12\"][1]/img"))).click();
            else
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"flex items-center cursor-pointer mr-12\"][2]/img"))).click();
            return true;
        } catch (Exception e) {
            System.out.println("Error setting Gender: " + e.getMessage());
            return false;
        }
    }

    public boolean uploadId(String filePath) {
        try {
            // Wait for the file input to be present and interactable
            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(id));
            input.sendKeys(filePath);
            return true;
        } catch (Exception e) {
            System.out.println("Error uploading picture: " + e.getMessage());
            return false;
        }
    }

    public boolean setDermatologyConcern(String dermConcern) {
        try {
            By cencernList = By.xpath("//div[@class=\"radio_button_card_container\"]/div//p[text()='" + dermConcern + "']");
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(cencernList));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            element.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error setting Gender: " + e.getMessage());
            return false;
        }
    }

    public boolean selectAffectedBodyPart(String part) {
        try {
            By searchBodyParts = By.xpath("//input[@placeholder=\"Type something..\"]");
            By bodyPartAfterSearch = By.xpath("//p[contains(text(),'" + part + "')]");
            By doneButton = By.xpath("//p[text()='Done']");
            wait.until(ExpectedConditions.elementToBeClickable(searchAffectedPart)).click();
            wait.until(ExpectedConditions.elementToBeClickable(searchBodyParts)).sendKeys(part);
            wait.until(ExpectedConditions.elementToBeClickable(bodyPartAfterSearch)).click();
            wait.until(ExpectedConditions.elementToBeClickable(doneButton)).click();
            return true;
        } catch (Exception e) {
            System.out.println("Error setting Gender: " + e.getMessage());
            return false;
        }
    }

    public boolean selectStatus(String visibleText) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(statusDropDown));
            new Select(dropdown).selectByVisibleText(visibleText);
            return true;
        } catch (Exception e) {
            System.out.println("Error selecting status: " + e.getMessage());
            return false;
        }
    }

    public boolean selectCount(String visibleText) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(countDropDown));
            new Select(dropdown).selectByVisibleText(visibleText);
            return true;
        } catch (Exception e) {
            System.out.println("Error selecting count: " + e.getMessage());
            return false;
        }
    }

    public boolean selectDay(String visibleText) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dayDropDown));
            new Select(dropdown).selectByVisibleText(visibleText);
            return true;
        } catch (Exception e) {
            System.out.println("Error selecting day: " + e.getMessage());
            return false;
        }
    }

    public boolean selectSeverity(String visibleText) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(severityDropDown));
            new Select(dropdown).selectByVisibleText(visibleText);
            return true;
        } catch (Exception e) {
            System.out.println("Error selecting severity: " + e.getMessage());
            return false;
        }
    }

    public boolean clickNoBtnForSmartPhoneUpload() {
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(noBtnForSmartPhoneUpload));
            btn.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking 'No' button for smartphone upload: " + e.getMessage());
            return false;
        }
    }

    public boolean uploadCloseUpPic(String filePath) {
        try {
            WebElement closeUp = wait.until(ExpectedConditions.elementToBeClickable(uploadCloseUpPic));
            closeUp.click();
            Thread.sleep(1000);
            By input = By.xpath("//input[@id=\"case-image-input\"]");
            wait.until(ExpectedConditions.presenceOfElementLocated(input)).sendKeys(filePath);
            return true;
        } catch (Exception e) {
            System.out.println("Error 'Upload Close-Up Pic': " + e.getMessage());
            return false;
        }
    }

    public boolean uploadFarAwayPic(String filePath) {
        try {
            WebElement farAway = wait.until(ExpectedConditions.elementToBeClickable(uploadFarAwayPic));
            farAway.click();
            Thread.sleep(1000);
            By input = By.xpath("//input[@id=\"case-image-input\"]");
            wait.until(ExpectedConditions.presenceOfElementLocated(input)).sendKeys(filePath);
            return true;
        } catch (Exception e) {
            System.out.println("Error Upload Far-Away Pic': " + e.getMessage());
            return false;
        }
    }

    public boolean selectSymptoms(String symptom) {
        try {
            By Symptoms = By.xpath("//p[text()='" + symptom + "']/parent::div");
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(Symptoms));
            element.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking CheckBox Card Text: " + e.getMessage());
            return false;
        }
    }

    public boolean setWorseText(String text) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(worseText));
            element.clear();
            element.sendKeys(text);
            return true;
        } catch (Exception e) {
            System.out.println("Error setting 'Worse' text: " + e.getMessage());
            return false;
        }
    }

    public boolean setBetterText(String text) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(betterText));
            element.clear();
            element.sendKeys(text);
            return true;
        } catch (Exception e) {
            System.out.println("Error setting 'Better' text: " + e.getMessage());
            return false;
        }
    }

    public boolean selectLiftStyle(String lifeStyle) {
        try {
            By element = By.xpath("//p[text()='" + lifeStyle + "']/parent::div");
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
            return true;
        } catch (Exception e) {
            System.out.println("Error setting 'Better' text: " + e.getMessage());
            return false;
        }
    }

    public void clickNoBtnInAllergy() {
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(noBtnInAllergy));
            btn.click();
        } catch (Exception e) {
            System.out.println("Error clicking 'No' button in Allergy section: " + e.getMessage());
        }
    }

    public void clickNoBtnInMedication() {
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(noBtnInMedication));
            btn.click();
        } catch (Exception e) {
            System.out.println("Error clicking 'No' button in Medication section: " + e.getMessage());
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
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(firstPharmacy));
            element.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking 'First Pharmacy': " + e.getMessage());
            return false;
        }
    }

    // Set text in the optional feedback field
    public void setOptionalField(String feedback) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(optionalField));
            element.clear();
            element.sendKeys(feedback);
        } catch (Exception e) {
            System.out.println("Error setting text in Optional Feedback field: " + e.getMessage());
        }
    }

    // Click the Next button for Terms and Conditions
    public boolean clickNextButtonForTAndC() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(nextButtonForTAndC));
            element.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking Next button for T&C: " + e.getMessage());
            return false;
        }
    }

    // Clicks all accept terms checkboxes/images
    public boolean clickAllAcceptTerms() {
        try {
            List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(acceptTerms));
            for (int i = 0; i < elements.size(); i++) {
                try {
                    wait.until(ExpectedConditions.elementToBeClickable(elements.get(i))).click();
                } catch (Exception e) {
                    System.out.println("Error clicking accept term element #" + (i + 1) + ": " + e.getMessage());
                }
            }
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Accept & Continue']"))).click();
            return true;
        } catch (Exception e) {
            System.out.println("Error locating accept terms elements: " + e.getMessage());
            return false;
        }
    }

    public boolean addAllergy(String allergyName, String reactionType, String categoryType) {
        try {
            // 1. Click "Yes" button
            WebElement yesBtn = wait.until(ExpectedConditions.elementToBeClickable(yesBtnInAllergy));
            yesBtn.click();

            // 2. Click "Search Allergy"
            WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(searchAllergy));
            searchBtn.click();

            // 3. Enter allergy name
            WebElement allergyInput = wait.until(ExpectedConditions.elementToBeClickable(enterAllergy));
            // allergyInput.sendKeys(allergyName + Keys.ENTER);
            allergyInput.sendKeys(allergyName);

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
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", createOption);
            }
            // 4. Select reaction from dropdown
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

    public boolean clickYesButtonInMedication() {
        try {
            WebElement yesBtn = wait.until(ExpectedConditions.elementToBeClickable(yesBtnInMedication));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", yesBtn);
            return true;
        } catch (StaleElementReferenceException e) {
            System.out.println("StaleElementReferenceException caught while clicking 'Yes' button. Retrying with JavaScriptExecutor...");
            try {
                WebElement yesBtn = wait.until(ExpectedConditions.presenceOfElementLocated(yesBtnInMedication));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", yesBtn);
                return true;
            } catch (Exception ex) {
                System.out.println("Retry failed while clicking 'Yes' button using JavaScriptExecutor: " + ex.getMessage());
                return false;
            }
        } catch (Exception e) {
            System.out.println("Unexpected error while clicking 'Yes' button in medication: " + e.getMessage());
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
                WebElement medicationInput = wait.until(ExpectedConditions.elementToBeClickable(enterMedication));
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

    // Clicks the "Yes" button in the Skin Care Product section
    public boolean clickYesBtnInSkinCareProduct() {
        try {
            WebElement yesBtn = wait.until(ExpectedConditions.presenceOfElementLocated(yesBtnInSkinCareProduct));
            yesBtn.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking 'Yes' button in Skin Care Product section: " + e.getMessage());
            return false;
        }
    }

    // Enters text into the Skin Care Product textarea
    public void enterSkinCareProduct(String productText) {
        try {
            WebElement textArea = wait.until(ExpectedConditions.visibilityOfElementLocated(skinCareTextArea));
            textArea.clear();
            textArea.sendKeys(productText);
        } catch (Exception e) {
            System.out.println("Error entering text in Skin Care Product textarea: " + e.getMessage());
        }
    }

    public boolean paymentByCard(String number, String expiryValue, String cvvValue) {
        try {
            try {
                // First attempt to wait for the Payment Method Section
                Thread.sleep(1000);
                wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentMethodSection));
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
            System.out.println("Submit for Evaluation button is not enabled: " + e.getMessage());
            return false;
        }
    }

    public boolean clickSubmitForEvaluation() {
        try {
            WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(submitForEvaluation));
            submitBtn.click();
            return true;
        } catch (Exception e) {
            System.out.println("First attempt to click 'Submit for Evaluation' failed: " + e.getMessage());
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

    public String getProviderNameInVisitSubmittedPage() {
        try {
            WebElement providerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(providerNameInVisitSubmittedPage));
            return providerElement.getText().trim();
        } catch (Exception e) {
            System.out.println("Error getting provider name in Visit Submitted page: " + e.getMessage());
            return null;
        }
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

    public boolean clickAddChildButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(addChildBtn)).click();
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking 'Add Child' button: " + e.getMessage());
            return false;
        }
    }

    public boolean enterChildFirstName(String firstName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(childFirstName))
                    .sendKeys(firstName);
            return true;
        } catch (Exception e) {
            System.out.println("Failed to enter child first name: " + e.getMessage());
            return false;
        }
    }

    public boolean enterChildLastName(String lastName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(childLastName))
                    .sendKeys(lastName);
            return true;
        } catch (Exception e) {
            System.out.println("Failed to enter child last name: " + e.getMessage());
            return false;
        }
    }

    public boolean clickSaveChildButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(saveChildBtn))
                    .click();
            return true;
        } catch (Exception e) {
            System.out.println("Failed to click save child button: " + e.getMessage());
            return false;
        }
    }

    public String getFirstChildName() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(firstPatient))
                    .getText();
        } catch (Exception e) {
            System.out.println("Failed to get first child name: " + e.getMessage());
            return null;
        }
    }
    public boolean clickAddWardButton() {
        try {
            WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(addWardBtn));
            addBtn.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking 'Add Ward' button: " + e.getMessage());
            return false;
        }
    }

    public boolean enterWardFirstName(String firstName) {
        try {
            WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(wardFirstName));
            firstNameInput.clear();
            firstNameInput.sendKeys(firstName);
            return true;
        } catch (Exception e) {
            System.out.println("Error entering Ward First Name: " + e.getMessage());
            return false;
        }
    }

    public boolean enterWardLastName(String lastName) {
        try {
            WebElement lastNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(wardLastName));
            lastNameInput.clear();
            lastNameInput.sendKeys(lastName);
            return true;
        } catch (Exception e) {
            System.out.println("Error entering Ward Last Name: " + e.getMessage());
            return false;
        }
    }

    public boolean clickSaveWardButton() {
        try {
            WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(saveWardBtn));
            saveBtn.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking 'Save Ward' button: " + e.getMessage());
            return false;
        }
    }
    public boolean clickAddSomeOneElseButton() {
        try {
            WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(addSomeOneElseBtn));
            addBtn.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking 'Add Someone Else' button: " + e.getMessage());
            return false;
        }
    }

    public boolean enterSomeOneElseFirstName(String firstName) {
        try {
            WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(someOneElseFirstName));
            firstNameInput.clear();
            firstNameInput.sendKeys(firstName);
            return true;
        } catch (Exception e) {
            System.out.println("Error entering Someone Else First Name: " + e.getMessage());
            return false;
        }
    }

    public boolean enterSomeOneElseLastName(String lastName) {
        try {
            WebElement lastNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(someOneElseLastName));
            lastNameInput.clear();
            lastNameInput.sendKeys(lastName);
            return true;
        } catch (Exception e) {
            System.out.println("Error entering Someone Else Last Name: " + e.getMessage());
            return false;
        }
    }

    public boolean enterSomeOneElseZipcode(String zipcode) {
        try {
            WebElement zipInput = wait.until(ExpectedConditions.visibilityOfElementLocated(someOneElseZipcode));
            zipInput.clear();
            zipInput.sendKeys(zipcode);
            return true;
        } catch (Exception e) {
            System.out.println("Error entering Someone Else Zip Code: " + e.getMessage());
            return false;
        }
    }

    public boolean clickSaveSomeOneElseButton() {
        try {
            WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(saveSomeOneElseBtn));
            saveBtn.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking 'Save Someone Else' button: " + e.getMessage());
            return false;
        }
    }
}
