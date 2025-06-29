package pages.ProviderPortal;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PatientChart extends BasePage {
    private WebDriverWait wait;
    public PatientChart(WebDriver driver){
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    private final By isPatientChart = By.xpath("//h1[@class=\"m-n font-thin h3 text-center ng-binding\"]");
    private final By nameInTopBar = By.xpath("//p[text()='Name: ']/span");
    private final By nameInThePatientChart = By.xpath("//td[text()='Name:']/following-sibling::td");
    private final By emailInThePatientChart = By.xpath("//table[@class=\"table borderless\"]//tr[2]//td[2]/span");
    private final By mobileInPatientChart = By.xpath("//table[@class=\"table borderless\"]//tr[3]//td[2]/a");
    private final By zipcodeInPatientChart = By.xpath("//table[@class=\"table borderless\"]//tr[4]//td[2]/span");
    private final By providerNameInReferralClinic = By.xpath("(//tr[@ng-show=\"patientData.is_referred\"]/td/span)[1]");
    private final By clinicNameInReferralClinic = By.xpath("(//tr[@ng-show=\"patientData.is_referred\"]/td/span)[3]");
    private final By searchField = By.xpath("//input[@placeholder='Search Patient']");
    private final By address = By.xpath("//td[text()='Address:']/parent::tr/td[2]/span");
    private final By gender = By.xpath("//td[text()='Gender At Birth:']/following-sibling::td");
    private final By Height = By.xpath("//td[contains(text(),'Height in ')]/following-sibling::td");
    private final By weight = By.xpath("//td[contains(text(),'Weight in ')]/following-sibling::td");
    private final By dob = By.xpath("//td[contains(text(),'Date of Birth:')]/following-sibling::td");
    private final By dobInTopBar = By.xpath("//p[text()='DOB: ']/span");
    private final By genderAtTopBar = By.xpath("//p[text()='Gender At Birth: ']/span");
    private final By primaryInsurance = By.xpath("//tr[contains(@ng-if,'patientData.insurance')]//span[1]");
    private final By memberNameInPrimaryInsurance = By.xpath("//tr[contains(@ng-if,'patientData.insurance')]//span[2]");
    private final By memberIdInPrimaryInsurance = By.xpath("//tr[contains(@ng-if,'patientData.insurance')]//span[3]");
    private final By memberDobInPrimaryInsurance = By.xpath("//tr[contains(@ng-if,'patientData.insurance')]//span[4]");

    private final By secondaryInsurance = By.xpath("//tr[contains(@ng-if,\"patientData.secondary_insurance\")]//span[1]");
    private final By memberNameInSecondaryInsurance = By.xpath("//tr[contains(@ng-if,\"patientData.secondary_insurance\")]//span[2]");
    private final By memberIdInSecondaryInsurance = By.xpath("//tr[contains(@ng-if,\"patientData.secondary_insurance\")]//span[3]");
    private final By memberDobInSecondaryInsurance = By.xpath("//tr[contains(@ng-if,\"patientData.secondary_insurance\")]//span[4]");
    private final By healthProfileButton = By.xpath("//a[text()='Health Profile']");

    private final By medicationNameOne = By.xpath("//div[@ng-repeat=\"medication in activeMeds\"][1]/div[1]");
    private final By guardian = By.xpath("//td[text()='Guardian:']/following-sibling::td");

    private final By profileIcon = By.xpath("//a[@class=\"dropdown-toggle clear\"]/span[1]");
    private final By logoutButton = By.xpath("//a[text()='Logout']");
    public boolean isPatientChart() {
        try {
            String text = wait.until(ExpectedConditions.visibilityOfElementLocated(isPatientChart)).getText();
            return text.contains("PATIENT CHART");
        } catch (Exception e) {
            System.out.println("Error: this is not Patient chart: " + e.getMessage());
            return false;
        }
    }
    public String getNameInTopBar() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInTopBar));
            return element.getText();
        } catch (Exception e) {
            System.err.println("Failed to get name in top bar: " + e.getMessage());
            return null;
        }
    }

    public String getNameInThePatientChart() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInThePatientChart));
            return element.getText();
        } catch (Exception e) {
            System.err.println("Failed to get name in patient chart: " + e.getMessage());
            return null;
        }
    }

    public String getEmailInThePatientChart() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInThePatientChart));
            return element.getText();
        } catch (Exception e) {
            System.err.println("Failed to get email in patient chart: " + e.getMessage());
            return null;
        }
    }

    public String getMobileInPatientChart() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileInPatientChart));
            return element.getText().replaceAll("[^$0-9]", "");
        } catch (Exception e) {
            System.err.println("Failed to get mobile number in patient chart: " + e.getMessage());
            return null;
        }
    }

    public String getZipcodeInPatientChart() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(zipcodeInPatientChart));
            String[] arrayOfAddress = element.getText().split("\\s");
            return arrayOfAddress[arrayOfAddress.length - 1];
        } catch (Exception e) {
            System.err.println("Failed to get zip code in patient chart: " + e.getMessage());
            return null;
        }
    }

    public String getProviderNameFromReferralSection() {
        try {
            WebElement providerNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(providerNameInReferralClinic));
            String fullText = providerNameElement.getText(); // e.g., "Provider Name: John Doe"
            return fullText.replace("Provider Name:", "").trim();
        } catch (Exception e) {
            System.err.println("Failed to get provider name in referral section: " + e.getMessage());
            return null;
        }
    }

    public String getClinicNameFromReferralSection() {
        try {
            WebElement clinicNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(clinicNameInReferralClinic));
            String fullText = clinicNameElement.getText(); // e.g., "Name: Clinic ABC"
            return fullText.replace("Name:", "").trim();
        } catch (Exception e) {
            System.err.println("Failed to get clinic name in referral section: " + e.getMessage());
            return null;
        }
    }

    // Action method to enter text in the search field
    public void searchPatient(String searchText) {
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Wait for the search input field to be visible and interactable
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchField));
            searchInput.clear();
            searchInput.sendKeys(searchText);

            // Build XPath for the result and wait for it to be clickable
            By resultLocator = By.xpath("//a[@title='" + searchText + " ']");
            WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(resultLocator));
            result.click();

        } catch (Exception e) {
            System.err.println("Error while searching for patient '" + searchText + "': " + e.getMessage());
        }
    }

    public String getAddress() {
        WebElement addressElement = wait.until(ExpectedConditions.visibilityOfElementLocated(address));
        return addressElement.getText().trim();
    }

    public String getGender() {
        WebElement genderElement = wait.until(ExpectedConditions.visibilityOfElementLocated(gender));
        return genderElement.getText().trim();
    }

    public String getHeight() {
        WebElement heightElement = wait.until(ExpectedConditions.visibilityOfElementLocated(Height));
        return heightElement.getText().replaceAll("foot\\(s\\)","").trim();
    }

    public String getWeight() {
        WebElement weightElement = wait.until(ExpectedConditions.visibilityOfElementLocated(weight));
        return weightElement.getText().replaceAll("\\.0 pound\\(s\\)","").trim();
    }

    public String getDOB() {
        WebElement dobElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dob));
        String dob =  dobElement.getText().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        Date date = null;
        try {
            date = sdf.parse(dob);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String formattedDate = targetFormat.format(date);
        return formattedDate;
    }

    public String getDOBInTopBar() {
        WebElement dobTopBarElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dobInTopBar));
        return dobTopBarElement.getText().trim();
    }

    public String getGenderAtTopBar() {
        WebElement genderTopBarElement = wait.until(ExpectedConditions.visibilityOfElementLocated(genderAtTopBar));
        String gender = genderTopBarElement.getText().trim();
        if (gender.equals("M"))
            return "Male";
        return "Female";
    }
    public String getPrimaryInsurance() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(primaryInsurance));
            return element.getText().replaceAll("Insurance Company:","").trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Primary Insurance element not found.");
        } catch (Exception e) {
            System.err.println("Error retrieving Primary Insurance: " + e.getMessage());
        }
        return null;
    }

    public String getMemberNameInPrimaryInsurance() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(memberNameInPrimaryInsurance));
            return element.getText().replaceAll("Member Name:","").trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Member Name in Primary Insurance element not found.");
        } catch (Exception e) {
            System.err.println("Error retrieving Member Name in Primary Insurance: " + e.getMessage());
        }
        return null;
    }

    public String getMemberIdInPrimaryInsurance() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(memberIdInPrimaryInsurance));
            return element.getText().replaceAll("Member ID:","").trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Member ID in Primary Insurance element not found.");
        } catch (Exception e) {
            System.err.println("Error retrieving Member ID in Primary Insurance: " + e.getMessage());
        }
        return null;
    }

    public String getMemberDobInPrimaryInsurance() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(memberDobInPrimaryInsurance));
            String dob=  element.getText().replaceAll("Member DOB:","").trim();
            // Adjust the format here to match your actual input!
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy",Locale.US);
            Date date = sdf.parse(dob);
            // Format the date into the desired output format
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
            return targetFormat.format(date);
        } catch (TimeoutException e) {
            System.err.println("Timeout: Member DOB in Primary Insurance element not found.");
        } catch (Exception e) {
            System.err.println("Error retrieving Member DOB in Primary Insurance: " + e.getMessage());
        }
        return null;
    }
    public String getSecondaryInsurance() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(secondaryInsurance));
            return element.getText().replaceAll("Insurance Company:","").trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Secondary Insurance element not found.");
        } catch (Exception e) {
            System.err.println("Error retrieving Secondary Insurance: " + e.getMessage());
        }
        return null;
    }

    public String getMemberNameInSecondaryInsurance() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(memberNameInSecondaryInsurance));
            return element.getText().replaceAll("Member Name:","").trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Member Name in Secondary Insurance element not found.");
        } catch (Exception e) {
            System.err.println("Error retrieving Member Name in Secondary Insurance: " + e.getMessage());
        }
        return null;
    }

    public String getMemberIdInSecondaryInsurance() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(memberIdInSecondaryInsurance));
            return element.getText().replaceAll("Member ID:","").trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Member ID in Secondary Insurance element not found.");
        } catch (Exception e) {
            System.err.println("Error retrieving Member ID in Secondary Insurance: " + e.getMessage());
        }
        return null;
    }

    public String getMemberDobInSecondaryInsurance() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(memberDobInSecondaryInsurance));
            String dob = element.getText().replaceAll("Member DOB:","").trim();
            // Adjust the format here to match your actual input!
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            Date date = sdf.parse(dob);
            // Format the date into the desired output format
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
            return targetFormat.format(date);
        } catch (TimeoutException e) {
            System.err.println("Timeout: Member DOB in Secondary Insurance element not found.");
        } catch (Exception e) {
            System.err.println("Error retrieving Member DOB in Secondary Insurance: " + e.getMessage());
        }
        return null;
    }
    // Click the Health Profile button
    public void clickHealthProfileButton() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(healthProfileButton));
            button.click();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Health Profile button not clickable.");
        } catch (Exception e) {
            System.err.println("Error clicking Health Profile button: " + e.getMessage());
        }
    }

    public String getFirstDrugAllergyName() {
        try {
            List<WebElement> allergies = driver.findElements(By.xpath("//div[@ng-repeat=\"allergy in activeDrugAllergies\"]/div[1]"));
            return allergies.get(allergies.size()-1).getText().trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: First allergy name not visible.");
        } catch (Exception e) {
            System.err.println("Error getting first allergy name: " + e.getMessage());
        }
        return null;
    }

    public String getSecondDrugAllergyName() {
        try {
            List<WebElement> allergies = driver.findElements(By.xpath("//div[@ng-repeat=\"allergy in activeOtherAllergies\"]/div[1]"));
            return allergies.get(allergies.size()-2).getText().trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: First allergy name not visible.");
        } catch (Exception e) {
            System.err.println("Error getting first allergy name: " + e.getMessage());
        }
        return null;
    }
    public String getFirstDrugReactionName(){
        try {
            List<WebElement> allergies = driver.findElements(By.xpath("//div[@ng-repeat=\"allergy in activeDrugAllergies\"]/div[2]"));
            return allergies.get(allergies.size()-1).getText().trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: First Reaction name not visible.");
        } catch (Exception e) {
            System.err.println("Error getting Reaction allergy name: " + e.getMessage());
        }
        return null;
    }
    public String getSecondDrugReactionName(){
        try {
            List<WebElement> allergies = driver.findElements(By.xpath("//div[@ng-repeat=\"allergy in activeDrugAllergies\"]/div[2]"));
            return allergies.get(allergies.size()-1).getText().trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: SECOND Reaction name not visible.");
        } catch (Exception e) {
            System.err.println("Error getting SECOND Reaction name: " + e.getMessage());
        }
        return null;
    }

    public String getFirstEnvironmentDrugAllergyName() {
        try {
            List<WebElement> allergies = driver.findElements(By.xpath("//div[@ng-repeat=\"allergy in activeOtherAllergies\"]/div[1]"));
            return allergies.get(allergies.size()-1).getText().trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: First allergy name not visible.");
        } catch (Exception e) {
            System.err.println("Error getting first allergy name: " + e.getMessage());
        }
        return null;
    }
    public String getSecondEnvironmentDrugAllergyName() {
        try {
            List<WebElement> allergies = driver.findElements(By.xpath("//div[@ng-repeat=\"allergy in activeOtherAllergies\"]/div[1]"));
            return allergies.get(allergies.size()-2).getText().trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: First allergy name not visible.");
        } catch (Exception e) {
            System.err.println("Error getting first allergy name: " + e.getMessage());
        }
        return null;
    }

    public String getFirstEnvironmentReactionName(){
        try {
            List<WebElement> allergies = driver.findElements(By.xpath("//div[@ng-repeat=\"allergy in activeOtherAllergies\"]/div[2]"));
            return allergies.get(allergies.size()-1).getText().trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: First Reaction name not visible.");
        } catch (Exception e) {
            System.err.println("Error getting first Reaction name: " + e.getMessage());
        }
        return null;
    }
    public String getSecondEnvironmentReactionName(){
        try {
            List<WebElement> allergies = driver.findElements(By.xpath("//div[@ng-repeat=\"allergy in activeOtherAllergies\"]/div[2]"));
            return allergies.get(allergies.size()-2).getText().trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Second Reaction name not visible.");
        } catch (Exception e) {
            System.err.println("Error getting Second Reaction name: " + e.getMessage());
        }
        return null;
    }

    // Get the first medication name
    public String getFirstMedicationName() {
        try {
            WebElement medication = wait.until(ExpectedConditions.visibilityOfElementLocated(medicationNameOne));
            return medication.getText().trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: First medication name not visible.");
        } catch (Exception e) {
            System.err.println("Error getting first medication name: " + e.getMessage());
        }
        return null;
    }
    public String getGuardianName(){
        try{
            return wait.until(ExpectedConditions.visibilityOfElementLocated(guardian)).getText();
        }catch (Exception e){
            System.err.println("Error getting guardian name: " + e.getMessage());
            return null;
        }
    }
    // Click the profile icon
    public void clickProfileIcon() {
        try {
            WebElement icon = wait.until(ExpectedConditions.elementToBeClickable(profileIcon));
            icon.click();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Profile icon not clickable.");
        } catch (Exception e) {
            System.err.println("Error clicking profile icon: " + e.getMessage());
        }
    }

    // Click the logout button
    public void clickLogoutButton() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
            button.click();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Logout button not clickable.");
        } catch (Exception e) {
            System.err.println("Error clicking logout button: " + e.getMessage());
        }
    }
}
