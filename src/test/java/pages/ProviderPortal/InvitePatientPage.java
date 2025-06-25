package pages.ProviderPortal;

import base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InvitePatientPage extends BasePage {
    private WebDriverWait wait;
    private JavascriptExecutor js;
    public InvitePatientPage(WebDriver driver){
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    // Locators
    private By firstNameTextField = By.xpath("//input[@id='first_name']");
    private By lastNameTextField = By.xpath("//input[@id='last_name']");
    private By emailTextField = By.xpath("//input[@id='email']");
    private By mobileTextField = By.xpath("//input[@id='phone']");
    private By zipcodeTextField = By.xpath("//input[@id='zip']");
    private By providerNameDropdown = By.xpath("//div[@class='col-lg-4 ']//select[contains(@class,'form-control')]");
    private By addPatientButtonDisable = By.xpath("//div[@class='col-lg-offset-4 col-lg-8']");
    private By addPatientButton = By.xpath("//button[@type='submit']");
    //referral
    private By referralClinicCheckBoxForAccountHolder = By.xpath("//input[@ng-model=\"referredPatient[0]\"]");
    private By providerFirstNameInAHReferralClinic = By.xpath("//div[@ng-show=\"referredPatient[0]\"]/descendant::input[contains(@ng-model,\"provider_first_name\")]");
    private By providerLastNameInAHReferralClinic = By.xpath("//div[@ng-show=\"referredPatient[0]\"]/descendant::input[contains(@ng-model,\"provider_last_name\")]");
    private By clinicStateInAHReffalClinic = By.xpath("//div[@ng-show=\"referredPatient[0]\"]/descendant::select[contains(@ng-model,'state')]");
    private By clinicInAHReffalClinic = By.xpath("//div[@ng-show=\"referredPatient[0]\"]/descendant::select[contains(@ng-model,'clinic_id')]");
    //additional info
    private By addtionalInformationForAccountHolder = By.xpath("//input[@ng-model=\"additionalInfoEnabled[0]\"]");
    private By streetAddressOneForAccountHolder =By.xpath("//input[@id='street_add_1']");
    private By streetAddressTwoForAccountHolder =By.xpath("//input[@id='street_add_2']");
    private By dobForAccountHolder = By.xpath("//div[@class='col-lg-8']//input[@id='new-patient-dob']");
    private By genderForAccountHolder = By.xpath("//div[@class='col-lg-8']//select[@id='new-patient-gender']");
    private By heightFoAccountHolder = By.xpath("//div[@class='col-lg-8']//div//div//input[@id='new-patient-height-feet']");
    private By inchesForAccountHolder = By.xpath("//div[@class='col-lg-8']//div//div//input[@id='new-patient-height-inch']");
    private By weightFoAccountHolder = By.xpath("//div[@class='col-lg-8']//div//input[@id='new-patient-weight']");

    //primary insurance for AH
    private By insuranceCheckBoxForAccountHolder = By.xpath("//input[@ng-model=\"insurancesDetailsEnabled[0]\"]");
    private By primaryInsuranceDropDownForAccountHolder = By.xpath("//div[@ng-model=\"newPatient.patients_attributes[0].insurances_attributes[0].insurance_company\"]");
    private By memberNameForAHInPrimaryInsurance = By.xpath("//input[@ng-model=\"newPatient.patients_attributes[0].insurances_attributes[0].member_name\"]");
    private By memberIdForAHInPrimaryInsurance = By.xpath("//input[@ng-model=\"newPatient.patients_attributes[0].insurances_attributes[0].member_id\"]");
    private By dobForAHInPrimaryInsurance = By.xpath("//input[@ng-model=\"newPatient.patients_attributes[0].insurances_attributes[0].member_dob\"]");
    private By relationshipForAHInPrimaryInsurance = By.xpath("//select[@ng-model=\"newPatient.patients_attributes[0].insurances_attributes[0].relationship_to_patient\"]");

    //secondary insurance for AH
    private By secondaryInsuranceCheckBoxForAccountHolder = By.xpath("//input[@ng-model=\"secondaryInsurancesDetailsEnabled[0]\"]");
    private By secondaryInsuranceDropDownForAccountHolder = By.xpath("//div[@ng-model=\"newPatient.patients_attributes[0].insurances_attributes[1].insurance_company\"]");
    private By memberNameForAhInSecondaryInsurance = By.xpath("//input[@ng-model=\"newPatient.patients_attributes[0].insurances_attributes[1].member_name\"]");
    private By memberIdForAhInSecondaryInsurance = By.xpath("//input[@ng-model=\"newPatient.patients_attributes[0].insurances_attributes[1].member_id\"]");
    private By memberDobFoAhInSecondaryInsurance = By.xpath("//input[@ng-model=\"newPatient.patients_attributes[0].insurances_attributes[1].member_dob\"]");
    private By relationshipForAhInSecondaryInsurance = By.xpath("//select[@ng-model=\"newPatient.patients_attributes[0].insurances_attributes[1].relationship_to_patient\"]");

    //Health profile for AH
    private By healthProfileCheckBoxForAccountHolder = By.xpath("//input[@ng-model=\"showHealthProfile[0]\"]");
    private By addMedicationButtonForAccountHolder = By.xpath("//button[@ng-click=\"addNewMedication(0)\"]");
    private By addAllergyButtonForAccountHolder = By.xpath("//button[@ng-click=\"addNewAllergy(0)\"]");

    private By clickMedicationNameForAccountHolder = By.xpath("(//form[@name=\"hpForm\"]//div[@ng-model=\"med.drug_name\"])[1]");
    private By setMedicationNameForAccountHolder = By.xpath("//div[@class=\"ui-select-container ui-select-bootstrap dropdown ng-valid open\"]//input[1]");
    private By selectOptionInMedicationForAccountHolder = By.xpath("//div[@class=\"ui-select-choices-row ng-scope active\"]");

    //allery
    private By clickAlleryNameOneForAccountHolder = By.xpath("(//form[@name=\"hpForm\"]//div[@ng-model=\"allergy.name\"])[1]");
    private By setAllergyNameOneForAccountHolder = By.xpath("(//form[@name='hpForm']//input[@placeholder='Search allergy...'])[1]");
    private By selectAllergyOptionOneForAccountHolder = By.xpath("//form[@name='hpForm']//div[@id=\"ui-select-choices-row-3-0\"]");
    private By selectAllergyReactionOneForAccountHolder = By.xpath("(//form[@name=\"hpForm\"]//div[@ng-repeat=\"allergy in newPatient.health_profiles[0].health_profile_allergies_attributes track by $index\"][1]//div//select)[1]");
    private By selectAllergyCategoryOneForAccountHolder = By.xpath("(//form[@name=\"hpForm\"]//div[@ng-repeat=\"allergy in newPatient.health_profiles[0].health_profile_allergies_attributes track by $index\"][1]//div//select)[2]");

    private By clickAlleryNameTwoForAccountHolder = By.xpath("(//form[@name=\"hpForm\"]//div[@ng-model=\"allergy.name\"])[2]");
    private By setAllergyNameTwoForAccountHolder = By.xpath("(//form[@name='hpForm']//input[@placeholder='Search allergy...'])[2]");
    private By selectAllergyOptionTwoForAccountHolder = By.xpath("//form[@name='hpForm']//div[@id=\"ui-select-choices-row-4-0\"]");
    private By selectAllergyReactionTwoForAccountHolder = By.xpath("(//form[@name=\"hpForm\"]//div[@ng-repeat=\"allergy in newPatient.health_profiles[0].health_profile_allergies_attributes track by $index\"][2]//div//select)[1]");
    private By selectAllergyCategoryTwoForAccountHolder = By.xpath("(//form[@name=\"hpForm\"]//div[@ng-repeat=\"allergy in newPatient.health_profiles[0].health_profile_allergies_attributes track by $index\"][2]//div//select)[2]");

    private By clickAlleryNameThreeForAccountHolder = By.xpath("(//form[@name=\"hpForm\"]//div[@ng-model=\"allergy.name\"])[3]");
    private By setAllergyNameThreeForAccountHolder = By.xpath("(//form[@name='hpForm']//input[@placeholder='Search allergy...'])[3]");
    private By selectAllergyOptionThreeForAccountHolder = By.xpath("//form[@name='hpForm']//div[@id=\"ui-select-choices-row-5-0\"]");
    private By selectAllergyReactionThreeForAccountHolder = By.xpath("(//form[@name=\"hpForm\"]//div[@ng-repeat=\"allergy in newPatient.health_profiles[0].health_profile_allergies_attributes track by $index\"][3]//div//select)[1]");
    private By selectAllergyCategoryThreeForAccountHolder = By.xpath("(//form[@name=\"hpForm\"]//div[@ng-repeat=\"allergy in newPatient.health_profiles[0].health_profile_allergies_attributes track by $index\"][3]//div//select)[2]");


    //additional patient one
    private By addAdditionalPatientBtnforPatientOne = By.xpath("//button[text()='Add Additional Patient']");
    private By patientTypeDropdownforPatientOne = By.xpath("(//select[@ng-model=\"patient.relationship_with_account\"])[1]");
    private By firstnameForPatientOne = By.xpath("(//input[@ng-model='patient.first_name'])[1]");
    private By lastNameForPatientOne = By.xpath("(//input[@ng-model='patient.last_name'])[1]");
    private By zipCodeForPatientOne = By.xpath("(//input[@id='zip'])[2]");
    //referral
    private By referralClinicCheckBoxForPatientOne = By.xpath("(//input[@ng-model=\"referredPatient[$index]\"])[1]");
    private By providerFirstNameInPatientOneReferralClinic = By.xpath("(//input[@ng-model=\"newPatient.patients_attributes[$index].referral_provider_first_name\"])[1]");
    private By providerLastNameInPatientOneReferralClinic = By.xpath("(//input[@ng-model=\"newPatient.patients_attributes[$index].referral_provider_last_name\"])[1]");
    private By clinicStateInPatientOneReffalClinic = By.xpath("(//select[@ng-model=\"newPatient.referral_clinics[$index].state\"])[1]");
    private By clinicInPatientOneReffalClinic = By.xpath("(//select[@ng-model=\"newPatient.patients_attributes[$index].referral_clinic_id\"])[1]");

    //additional details
    private By addtionalInformationForPatientOne = By.xpath("(//input[@ng-model=\"additionalInfoEnabled[$index]\"])[1]");
    private By streetAddressOneForPatientOne = By.xpath("(//input[@ng-model=\"patient.street_add_1\"])[1]");
    private By streetAddressTwoForPatientOne = By.xpath("(//input[@ng-model=\"patient.street_add_2\"])[1]");
    private By dobForPatientOne = By.xpath("(//tr[@ng-show=\"additionalInfoEnabled[$index]\"]//input[@id=\"new-patient-dob\"])[1]");
    private By genderForPatientOne = By.xpath("(//tr[@ng-show=\"additionalInfoEnabled[$index]\"]//select[@id=\"new-patient-gender\"])[1]");
    private By feetForPatientOne = By.xpath("(//tr[@ng-show=\"additionalInfoEnabled[$index]\"]//input[@id=\"new-patient-height-feet\"])[1]");
    private By inchForPatientOne = By.xpath("(//tr[@ng-show=\"additionalInfoEnabled[$index]\"]//input[@id=\"new-patient-height-inch\"])[1]");
    private By weightForPatientOne = By.xpath("(//tr[@ng-show=\"additionalInfoEnabled[$index]\"]//input[@id=\"new-patient-weight\"])[1]");

    //insurance details for patient one
    private By insuranceCheckBoxForPatientOne = By.xpath("(//input[@ng-model=\"insurancesDetailsEnabled[$index]\"])[1]");
    private By primaryInsuranceDropDownForPatientOne = By.xpath("(//div[@ng-model=\"newPatient.patients_attributes[$index].insurances_attributes[0].insurance_company\"])[1]");
    private By primaryInsuranceMemberNameForPatientOne = By.xpath("(//input[@ng-model=\"newPatient.patients_attributes[$index].insurances_attributes[0].member_name\"])[1]");
    private By primaryInsuranceMemberIdForPatientOne =By.xpath("(//input[@ng-model=\"newPatient.patients_attributes[$index].insurances_attributes[0].member_id\"])[1]");
    private By primaryInsuranceMemberDOBForPatientOne = By.xpath("(//input[@ng-model=\"newPatient.patients_attributes[$index].insurances_attributes[0].member_dob\"])[1]");
    private By primaryInsuranceRelationshipForPatientOne = By.xpath("(//select[@ng-model=\"newPatient.patients_attributes[$index].insurances_attributes[0].relationship_to_patient\"])[1]");


    private By secondaryInsuranceCheckBoxForPatientOne =By.xpath("//input[@ng-model=\"secondaryInsurancesDetailsEnabled[$index]\"]");
    private By secondaryInsuranceDropDownForPatientOne = By.xpath("(//div[@ng-model=\"newPatient.patients_attributes[$index].insurances_attributes[1].insurance_company\"])[1]");
    private By secondaryInsuranceMemberNameForPatientOne = By.xpath("(//input[@ng-model=\"newPatient.patients_attributes[$index].insurances_attributes[1].member_name\"])[1]");
    private By secondaryInsuranceMemberIdForPatientOne =By.xpath("(//input[@ng-model=\"newPatient.patients_attributes[$index].insurances_attributes[1].member_id\"])[1]");
    private By secondaryInsuranceMemberDOBForPatientOne = By.xpath("(//input[@ng-model=\"newPatient.patients_attributes[$index].insurances_attributes[1].member_dob\"])[1]");
    private By secondaryInsuranceRelationshipForPatientOne = By.xpath("(//select[@ng-model=\"newPatient.patients_attributes[$index].insurances_attributes[1].relationship_to_patient\"])[1]");

    // Action Methods with Explicit Waits
    public void setFirstNameAs(String firstName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameTextField));
        element.clear();
        element.sendKeys(firstName);
    }

    public void setLastNameAs(String lastName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameTextField));
        element.clear();
        element.sendKeys(lastName);
    }

    public void setEmailAs(String email) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailTextField));
        element.clear();
        element.sendKeys(email);
    }

    public void setMobileAs(String mobile) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileTextField));
        element.clear();
        element.sendKeys(mobile);
    }

    public void setZipcodeAs(String zipcode) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(zipcodeTextField));
        element.clear();
        element.sendKeys(zipcode);
    }

    public void selectProviderNameAs(String providerName) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(providerNameDropdown));
        Select select = new Select(dropdown);
        select.selectByVisibleText(providerName);
    }

    public void clickReferralClinicCheckBoxForAccountHolder() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(referralClinicCheckBoxForAccountHolder));
        element.click();
    }

    public void setProviderFirstName(String firstName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(providerFirstNameInAHReferralClinic));
        element.clear();
        element.sendKeys(firstName);
    }

    public void setProviderLastName(String lastName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(providerLastNameInAHReferralClinic));
        element.clear();
        element.sendKeys(lastName);
    }

    public void selectClinicStateForAccountHolder(String state) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(clinicStateInAHReffalClinic));
        Select select = new Select(dropdown);
        select.selectByVisibleText(state);
    }

    public void selectClinicForAccountHolder(String clinicName) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(clinicInAHReffalClinic));
        Select select = new Select(dropdown);
        select.selectByVisibleText(clinicName);
    }

    // Check or uncheck the additional information checkbox
    public void clickAdditionalInformationCheckboxForAccountHolder() {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(addtionalInformationForAccountHolder));
        checkbox.click();
    }

    // Set Street Address Line 1
    public void setStreetAddressOne(String address) {
        WebElement addressField = wait.until(ExpectedConditions.visibilityOfElementLocated(streetAddressOneForAccountHolder));
        addressField.clear();
        addressField.sendKeys(address);
    }

    // Set Street Address Line 2
    public void setStreetAddressTwo(String address) {
        WebElement addressField = wait.until(ExpectedConditions.visibilityOfElementLocated(streetAddressTwoForAccountHolder));
        addressField.clear();
        addressField.sendKeys(address);
    }

    // Set Date of Birth (DOB)
    public void setDOB(String dob) {
        WebElement dobField = wait.until(ExpectedConditions.visibilityOfElementLocated(dobForAccountHolder));
        dobField.clear();
        dobField.sendKeys(dob);
    }

    // Select Gender by visible text
    public void selectGender(String gender) {
        WebElement genderDropdown = wait.until(ExpectedConditions.elementToBeClickable(genderForAccountHolder));
        Select select = new Select(genderDropdown);
        select.selectByVisibleText(gender);
    }

    // Set Height (feet)
    public void setHeightFeet(String feet) {
        try{
            WebElement heightFeetField = wait.until(ExpectedConditions.visibilityOfElementLocated(heightFoAccountHolder));
            int feetValue = Integer.parseInt(feet);
            while(feetValue>=1){
                // Increase value (Arrow Up)
                heightFeetField.sendKeys(Keys.ARROW_UP);
                feetValue--;
            }
        }catch (Exception e){
            System.err.println("Failed to set height in Feet: "  + e.getMessage());
        }
    }

    // Set Height (inches)
    public void setHeightInches(String inches) {
        try {
            WebElement heightInchesField = wait.until(ExpectedConditions.visibilityOfElementLocated(inchesForAccountHolder));
            heightInchesField.clear();
            heightInchesField.sendKeys(inches);
        } catch (Exception e) {
            System.out.println("Failed to set height in inches: " + e.getMessage());
        }
    }

    // Set Weight
    public void setWeight(String weight) {
        try {
            WebElement weightField = wait.until(ExpectedConditions.visibilityOfElementLocated(weightFoAccountHolder));
            int weightValue = Integer.parseInt(weight);
            while (weightValue >= 1) {
                // Increase value (Arrow Up)
                weightField.sendKeys(Keys.ARROW_UP);
                weightValue--;
            }
        } catch (Exception e) {
            System.out.println("Failed to set weight: " + e.getMessage());
        }
    }

    // Toggle Primary insurance checkbox for Account Holder
    public void clickPrimaryInsuranceCheckboxForAH() {
        try {
            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(insuranceCheckBoxForAccountHolder));
            checkbox.click();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Primary Insurance checkbox not clickable For AH.");
        } catch (Exception e) {
            System.err.println("Error toggling Primary insurance checkbox For AH: " + e.getMessage());
        }
    }

    // Open primary insurance dropdown
    public void setPrimaryInsuranceDropdownForAH(String insuranceName) {
        try {
            // 1. Click the dropdown to open the search input
            WebElement dropdownTrigger = wait.until(ExpectedConditions.elementToBeClickable(primaryInsuranceDropDownForAccountHolder));
            dropdownTrigger.click();

            // 2. Find the search input field INSIDE the opened dropdown
            By searchInputLocator = By.xpath("//div[@ng-model=\"newPatient.patients_attributes[0].insurances_attributes[0].insurance_company\"]//input[1]");
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputLocator));

            // 3. Type the insurance name to filter options
            searchInput.sendKeys(insuranceName);

            // 4. Wait for options to load and click the matching one
            By optionLocator = By.xpath("//div[@ng-show=\"insurancesDetailsEnabled[0]\"]//div[@ng-attr-id=\"ui-select-choices-row-{{ $select.generatedId }}-{{$index}}\"][1]//span[text()='"+insuranceName+"']");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            option.click();

        } catch (TimeoutException e) {
            System.err.println("Timeout while handling insurance dropdown: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error in setPrimaryInsuranceDropdownForAH: " + e.getMessage());
        }
    }

    // Set member name
    public void setMemberNameInPrimaryInsuranceForAh(String memberName) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(memberNameForAHInPrimaryInsurance));
            element.clear();
            element.sendKeys(memberName);
        } catch (Exception e) {
            System.err.println("Error setting member name in primary for Ah: " + e.getMessage());
        }
    }

    // Set member ID
    public void setMemberIdInPrimaryInsuranceForAh(String memberId) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(memberIdForAHInPrimaryInsurance));
            element.clear();
            element.sendKeys(memberId);
        } catch (Exception e) {
            System.err.println("Error setting member ID In PrimaryInsurance For Ah: " + e.getMessage());
        }
    }

    // Set member DOB
    public void setMemberDOBInPrimaryInsuranceForAh(String dob) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(dobForAHInPrimaryInsurance));
            element.clear();
            element.sendKeys(dob);
        } catch (Exception e) {
            System.err.println("Error setting member DOB In Primary Insurance For Ah: " + e.getMessage());
        }
    }

    // Select relationship from dropdown
    public void setRelationshipInPrimaryInsuranceForAh(String relationship) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(relationshipForAHInPrimaryInsurance));
            Select dropdown = new Select(element);
            dropdown.selectByVisibleText(relationship);
        } catch (NoSuchElementException e) {
            System.err.println("Relationship option '" + relationship + "' not found.");
        } catch (Exception e) {
            System.err.println("Error selecting relationship In Primary Insurance For Ah: " + e.getMessage());
        }
    }

    // click secondary insurance checkbox
    public void clickSecondaryInsuranceCheckbox() {
        try {
            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(secondaryInsuranceCheckBoxForAccountHolder));
            checkbox.click();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Secondary insurance checkbox not clickable.");
        } catch (Exception e) {
            System.err.println("Error toggling secondary insurance checkbox: " + e.getMessage());
        }
    }
    // Action method to click "Add Additional Patient" button
    public void clickAddAdditionalPatientBtnForPatientOne() {
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(addAdditionalPatientBtnforPatientOne));
        addButton.click();
    }
    // Set secondary insurance dropdown for AH
    public void setSecondaryInsuranceDropdownForAH(String insuranceName) {
        try {
            // 1. Click the dropdown to open the search input
            WebElement dropdownTrigger = wait.until(ExpectedConditions.elementToBeClickable(secondaryInsuranceDropDownForAccountHolder));
            dropdownTrigger.click();
            // 2. Find the search input field INSIDE the opened dropdown
            By searchInputInSecondaryInsurance = By.xpath("//div[@ng-model=\"newPatient.patients_attributes[0].insurances_attributes[1].insurance_company\"]/input[1]");
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputInSecondaryInsurance));
            // 3. Type the insurance name to filter options
            searchInput.sendKeys(insuranceName);
            // 4. Wait for options to load and click the matching one
            By optionLocatorInSecondaryInsurance = By.xpath("//div[@ng-show=\"insurancesDetailsEnabled[0] && secondaryInsurancesDetailsEnabled[0]\"]//div[@ng-attr-id=\"ui-select-choices-row-{{ $select.generatedId }}-{{$index}}\"][1]//span[text()='"+insuranceName+"']");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocatorInSecondaryInsurance));
            option.click();

        } catch (TimeoutException e) {
            System.err.println("Timeout while handling secondary insurance dropdown: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error in setSecondaryInsuranceDropdownForAH: " + e.getMessage());
        }
    }
    // Set member name in secondary insurance
    public void setMemberNameInSecondaryInsurance(String memberName) {
        try {
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(memberNameForAhInSecondaryInsurance));
            input.clear();
            input.sendKeys(memberName);
        } catch (Exception e) {
            System.err.println("Error setting member name in secondary insurance: " + e.getMessage());
        }
    }

    // Set member ID in secondary insurance
    public void setMemberIdInSecondaryInsurance(String memberId) {
        try {
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(memberIdForAhInSecondaryInsurance));
            input.clear();
            input.sendKeys(memberId);
        } catch (Exception e) {
            System.err.println("Error setting member ID in secondary insurance: " + e.getMessage());
        }
    }

    // Set member DOB in secondary insurance
    public void setMemberDobInSecondaryInsurance(String dob) {
        try {
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(memberDobFoAhInSecondaryInsurance));
            input.clear();
            input.sendKeys(dob);
        } catch (Exception e) {
            System.err.println("Error setting member DOB in secondary insurance: " + e.getMessage());
        }
    }

    // Select relationship in secondary insurance
    public void setRelationshipInSecondaryInsurance(String relationship) {
        try {
            WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(relationshipForAhInSecondaryInsurance));
            Select dropdown = new Select(selectElement);
            dropdown.selectByVisibleText(relationship);
        } catch (NoSuchElementException e) {
            System.err.println("Relationship option '" + relationship + "' not found in secondary insurance dropdown.");
        } catch (Exception e) {
            System.err.println("Error selecting relationship in secondary insurance: " + e.getMessage());
        }
    }
    // Action method to select patient type by visible text
    public void selectPatientTypeForPatientOne(String patientType) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(patientTypeDropdownforPatientOne));
        Select select = new Select(dropdown);
        select.selectByVisibleText(patientType);
    }
    // Click the Health Profile checkbox for Account Holder
    public void clickHealthProfileCheckBoxForAccountHolder() {
        try {
            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(healthProfileCheckBoxForAccountHolder));
            checkbox.click();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Health Profile checkbox not clickable.");
        } catch (Exception e) {
            System.err.println("Error clicking Health Profile checkbox: " + e.getMessage());
        }
    }

    // Click the Add Medication button for Account Holder
    public void clickAddMedicationButtonForAccountHolder() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(addMedicationButtonForAccountHolder));
            button.click();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Add Medication button not clickable.");
        } catch (Exception e) {
            System.err.println("Error clicking Add Medication button: " + e.getMessage());
        }
    }

    // Click the Add Allergy button for Account Holder
    public void clickAddAllergyButtonForAccountHolder() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(addAllergyButtonForAccountHolder));
            button.click();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Add Allergy button not clickable.");
        } catch (Exception e) {
            System.err.println("Error clicking Add Allergy button: " + e.getMessage());
        }
    }
    //select medication name
    public void selectMedicationForAccountHolder(String medicationName) {
        try {
            // 1. Click the dropdown to activate it
           WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(clickMedicationNameForAccountHolder));
           dropdown.click();

            // 2. Wait for the search input to appear and type the medication name
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(setMedicationNameForAccountHolder));
            searchInput.sendKeys(medicationName);

            // 3. Wait for the matching option to become clickable and select it
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(selectOptionInMedicationForAccountHolder));
            option.click();

        } catch (TimeoutException e) {
            System.err.println("Timeout: Medication dropdown or option not available. " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error selecting medication for account holder: " + e.getMessage());
        }
    }
    public void setAllergySetOne(String allergyName, String reaction, String category){
        //Allergy
        try {
            // 1. Click the allergy dropdown to activate it
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(clickAlleryNameOneForAccountHolder));
            dropdown.click();

            // 2. Wait for the input to appear and type the allergy name
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(setAllergyNameOneForAccountHolder));
            input.clear();
            input.sendKeys(allergyName);

            // 3. Wait for the matching option and select it
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//form[@name='hpForm']//div[@id=\"ui-select-choices-row-3-0\"]//span[text()='"+allergyName+"']")));
            option.click();
        } catch (Exception e) {
            System.err.println("Error selecting allergy for account holder: " + e.getMessage());
        }
        //Reaction
        try {
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(selectAllergyReactionOneForAccountHolder));
            Select select = new Select(dropdown);
            select.selectByVisibleText(reaction);
        }  catch (Exception e) {
            System.err.println("Error selecting allergy reaction: " + e.getMessage());
        }
        //Category
        try {
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(selectAllergyCategoryOneForAccountHolder));
            Select select = new Select(dropdown);
            select.selectByVisibleText(category);
        }  catch (Exception e) {
            System.err.println("Error selecting allergy category: " + e.getMessage());
        }
    }
    public void setAllergySetTwo(String allergyName, String reaction, String category){
        //Allergy
        try {
            // 1. Click the allergy dropdown to activate it
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(clickAlleryNameTwoForAccountHolder));
            dropdown.click();

            // 2. Wait for the input to appear and type the allergy name
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(setAllergyNameTwoForAccountHolder));
            input.clear();
            input.sendKeys(allergyName);

            // 3. Wait for the matching option and select it
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//form[@name='hpForm']//div[@id=\"ui-select-choices-row-4-0\"]//span[text()='"+allergyName+"']")));
            option.click();
        } catch (Exception e) {
            System.err.println("Error selecting allergy for account holder: " + e.getMessage());
        }
        //Reaction
        try {
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(selectAllergyReactionTwoForAccountHolder));
            Select select = new Select(dropdown);
            select.selectByVisibleText(reaction);
        } catch (Exception e) {
            System.err.println("Error selecting allergy reaction: " + e.getMessage());
        }
        //Category
        try {
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(selectAllergyCategoryTwoForAccountHolder));
            Select select = new Select(dropdown);
            select.selectByVisibleText(category);
        } catch (Exception e) {
            System.err.println("Error selecting allergy category: " + e.getMessage());
        }
    }

    public void setAllergySetThree(String allergyName, String reaction, String category){
        //Allergy
        try {
            // 1. Click the allergy dropdown to activate it
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(clickAlleryNameThreeForAccountHolder));
            dropdown.click();

            // 2. Wait for the input to appear and type the allergy name
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(setAllergyNameThreeForAccountHolder));
            input.clear();
            input.sendKeys(allergyName);

            // 3. Wait for the matching option and select it
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//form[@name='hpForm']//div[@id=\"ui-select-choices-row-5-0\"]//span[text()='"+allergyName+"']")));
            option.click();
        } catch (Exception e) {
            System.err.println("Error selecting allergy for account holder: " + e.getMessage());
        }
        //Reaction
        try {
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(selectAllergyReactionThreeForAccountHolder));
            Select select = new Select(dropdown);
            select.selectByVisibleText(reaction);
        } catch (Exception e) {
            System.err.println("Error selecting allergy reaction: " + e.getMessage());
        }
        //Category
        try {
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(selectAllergyCategoryThreeForAccountHolder));
            Select select = new Select(dropdown);
            select.selectByVisibleText(category);
        } catch (Exception e) {
            System.err.println("Error selecting allergy category: " + e.getMessage());
        }
    }
    public void setDrugAllergySetOne(String allergyName, String reaction, String category){
        //Allergy
        try {
            // 1. Click the allergy dropdown to activate it
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(clickAlleryNameOneForAccountHolder));
            dropdown.click();

            // 2. Wait for the input to appear and type the allergy name
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(setAllergyNameOneForAccountHolder));
            input.clear();
            input.sendKeys(allergyName);

            // 3. Wait for the matching option and select it
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//form[@name='hpForm']//div[@id=\"ui-select-choices-row-3-0\"]//span[text()='"+allergyName+"']")));
            option.click();
        } catch (Exception e) {
            System.err.println("Error selecting allergy for account holder: " + e.getMessage());
        }
        //Reaction
        try {
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(selectAllergyReactionOneForAccountHolder));
            Select select = new Select(dropdown);
            select.selectByVisibleText(reaction);
        }  catch (Exception e) {
            System.err.println("Error selecting allergy reaction: " + e.getMessage());
        }
        //Category
        try {
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(selectAllergyCategoryOneForAccountHolder));
            Select select = new Select(dropdown);
            select.selectByVisibleText(category);
        }  catch (Exception e) {
            System.err.println("Error selecting allergy category: " + e.getMessage());
        }
    }
    public void setEnvironmentAllergySetOne(String allergyName, String reaction, String category){
        //Allergy
        try {
            // 1. Click the allergy dropdown to activate it
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(clickAlleryNameTwoForAccountHolder));
            dropdown.click();

            // 2. Wait for the input to appear and type the allergy name
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(setAllergyNameTwoForAccountHolder));
            input.clear();
            input.sendKeys(allergyName);

            // 3. Wait for the matching option and select it
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//form[@name='hpForm']//div[@id=\"ui-select-choices-row-4-0\"]//span[text()='"+allergyName+"']")));
            option.click();
        } catch (Exception e) {
            System.err.println("Error selecting allergy for account holder: " + e.getMessage());
        }
        //Reaction
        try {
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(selectAllergyReactionTwoForAccountHolder));
            Select select = new Select(dropdown);
            select.selectByVisibleText(reaction);
        }  catch (Exception e) {
            System.err.println("Error selecting allergy reaction: " + e.getMessage());
        }
        //Category
        try {
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(selectAllergyCategoryTwoForAccountHolder));
            Select select = new Select(dropdown);
            select.selectByVisibleText(category);
        }  catch (Exception e) {
            System.err.println("Error selecting allergy category: " + e.getMessage());
        }
    }
    public void clickAddPatientButton() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(addPatientButton));
        element.click();
    }

    // Action method to set first name for additional patient one
    public void setFirstNameForPatientOne(String firstName) {
        WebElement firstNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(firstnameForPatientOne));
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
    }

    // Action method to set last name
    public void setLastNameForPatientOne(String lastName) {
        WebElement lastNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameForPatientOne));
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
    }

    // Action method to set zip code
    public void setZipCodeForPatientOne(String zipCode) {
        WebElement zipCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(zipCodeForPatientOne));
        zipCodeField.clear();
        zipCodeField.sendKeys(zipCode);
    }

    public void clickReferralClinicCheckBoxForPatientOne() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(referralClinicCheckBoxForPatientOne)).click();
        } catch (Exception e) {
            System.out.println("Error clicking referral clinic checkbox for patient one: " + e.getMessage());
        }
    }

    public void setProviderFirstNameInPatientOneReferralClinic(String firstName) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(providerFirstNameInPatientOneReferralClinic));
            element.clear();
            element.sendKeys(firstName);
        } catch (Exception e) {
            System.out.println("Error setting provider first name in patient one referral clinic: " + e.getMessage());
        }
    }

    public void setProviderLastNameInPatientOneReferralClinic(String lastName) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(providerLastNameInPatientOneReferralClinic));
            element.clear();
            element.sendKeys(lastName);
        } catch (Exception e) {
            System.out.println("Error setting provider last name in patient one referral clinic: " + e.getMessage());
        }
    }
    public void selectClinicStateInPatientOneReferralClinic(String stateValue) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(clinicStateInPatientOneReffalClinic));
            Select select = new Select(dropdown);
            select.selectByVisibleText(stateValue);
        } catch (Exception e) {
            System.out.println("Error selecting clinic state in patient one referral clinic: " + e.getMessage());
        }
    }

    public void selectClinicInPatientOneReferralClinic(String clinicValue) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(clinicInPatientOneReffalClinic));
            Select select = new Select(dropdown);
            select.selectByVisibleText(clinicValue);

        } catch (Exception e) {
            System.out.println("Error selecting clinic in patient one referral clinic: " + e.getMessage());
        }
    }

    //additional information for patient one
    public void clickAdditionalInformationForPatientOne() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(addtionalInformationForPatientOne)).click();
        } catch (Exception e) {
            System.out.println("Error clicking additional information for patient one: " + e.getMessage());
        }
    }

    public void setStreetAddressOneForPatientOne(String address1) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(streetAddressOneForPatientOne));
            element.clear();
            element.sendKeys(address1);
        } catch (Exception e) {
            System.out.println("Error setting street address one for patient one: " + e.getMessage());
        }
    }

    public void setStreetAddressTwoForPatientOne(String address2) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(streetAddressTwoForPatientOne));
            element.clear();
            element.sendKeys(address2);
        } catch (Exception e) {
            System.out.println("Error setting street address two for patient one: " + e.getMessage());
        }
    }

    public void setDobForPatientOne(String dob) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(dobForPatientOne));
            element.clear();
            element.sendKeys(dob);
        } catch (Exception e) {
            System.out.println("Error setting date of birth for patient one: " + e.getMessage());
        }
    }
    public void selectGenderForPatientOne(String genderValue) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(genderForPatientOne));
            Select select = new Select(dropdown);
            select.selectByVisibleText(genderValue);
        } catch (Exception e) {
            System.out.println("Error selecting gender for patient one: " + e.getMessage());
        }
    }
    public void setFeetForPatientOne(String feet) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(feetForPatientOne));
            int feetValue = Integer.parseInt(feet);
            while(feetValue>=1){
                // Increase value (Arrow Up)
                element.sendKeys(Keys.ARROW_UP);
                feetValue--;
            }
        } catch (Exception e) {
            System.out.println("Error setting feet for patient one: " + e.getMessage());
        }
    }

    public void setInchForPatientOne(String inch) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(inchForPatientOne));
            element.clear();
            element.sendKeys(inch);
        } catch (Exception e) {
            System.out.println("Error setting inch for patient one: " + e.getMessage());
        }
    }

    public void setWeightForPatientOne(String weight) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(weightForPatientOne));
            int weightValue = Integer.parseInt(weight);
            while(weightValue>=1){
                // Increase value (Arrow Up)
                element.sendKeys(Keys.ARROW_UP);
                weightValue--;
            }
        } catch (Exception e) {
            System.out.println("Error setting weight for patient one: " + e.getMessage());
        }
    }

    // Action Methods
    public void checkInsuranceCheckboxForPatientOne() {
        try {
            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(insuranceCheckBoxForPatientOne));
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        } catch (Exception e) {
            System.out.println("Failed to click insurance checkbox: " + e.getMessage());
        }
    }

    public void selectPrimaryInsuranceForPatientOne(String insuranceName) {
        try {
            // 1. Click the dropdown to open the search input
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(primaryInsuranceDropDownForPatientOne));
            dropdown.click();
           // 2. Find the search input field INSIDE the opened dropdown
            //By searchInputLocator = By.xpath("(//input[@class='form-control ui-select-search ng-pristine ng-valid ng-touched'])[1]");
            By searchInputLocator = By.xpath("(//p[text()='Patient 1']/parent::div[1]/parent::div//input[contains(@class, 'ui-select-search')])[1]");
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputLocator));

            // 3. Type the insurance name to filter options
            searchInput.sendKeys(insuranceName);

            // 4. Wait for options to load and click the matching one
            By optionLocator = By.xpath("//div[@ng-attr-id='ui-select-choices-row-{{ $select.generatedId }}-{{$index}}'][1]//span[text()='"+insuranceName+"']");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            option.click();

        } catch (Exception e) {
            System.out.println("Failed to select primary insurance: " + e.getMessage());
        }
    }

    public void setPrimaryInsuranceMemberName(String name) {
        try {
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(primaryInsuranceMemberNameForPatientOne));
            field.clear();
            field.sendKeys(name);
        } catch (Exception e) {
            System.out.println("Failed to set member name: " + e.getMessage());
        }
    }

    public void setPrimaryInsuranceMemberIdForPatientOne(String id) {
        try {
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(primaryInsuranceMemberIdForPatientOne));
            field.clear();
            field.sendKeys(id);
        } catch (Exception e) {
            System.out.println("Failed to set member ID: " + e.getMessage());
        }
    }

    public void setPrimaryInsuranceMemberDOBForPatientOne(String dob) {
        try {
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(primaryInsuranceMemberDOBForPatientOne));
            field.clear();
            field.sendKeys(dob);
        } catch (Exception e) {
            System.out.println("Failed to set member DOB: " + e.getMessage());
        }
    }

    public void selectPrimaryInsuranceRelationshipForPatientOne(String relationshipText) {
        try {
            WebElement selectElement = wait.until(ExpectedConditions.elementToBeClickable(primaryInsuranceRelationshipForPatientOne));
            Select select = new Select(selectElement);
            select.selectByVisibleText(relationshipText);
        } catch (Exception e) {
            System.out.println("Failed to select relationship: " + e.getMessage());
        }
    }

    public void checkSecondaryInsuranceForPatientOne() {
        try {
            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(secondaryInsuranceCheckBoxForPatientOne));
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        } catch (Exception e) {
            System.out.println("Failed to select Secondary Insurance checkbox for Patient One: " + e.getMessage());
        }
    }
    public void selectSecondaryInsuranceForPatientOne(String insuranceName) {
        try {
            // 1. Click the dropdown to open the search input
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(secondaryInsuranceDropDownForPatientOne));
            dropdown.click();
            // 2. Find the search input field INSIDE the opened dropdown
            By searchInputLocator = By.xpath("(//p[text()='Patient 1']/parent::div[1]/parent::div//input[contains(@class, 'ui-select-search')])[2]");
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputLocator));

            // 3. Type the insurance name to filter options
            searchInput.sendKeys(insuranceName);

            // 4. Wait for options to load and click the matching one
            By optionLocator = By.xpath("//div[@ng-attr-id='ui-select-choices-row-{{ $select.generatedId }}-{{$index}}'][1]//span[text()='"+insuranceName+"']");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            option.click();

        } catch (Exception e) {
            System.out.println("Failed to select primary insurance: " + e.getMessage());
        }
    }

    public void setSecondaryInsuranceMemberNameForPatientOne(String name) {
        try {
            WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(secondaryInsuranceMemberNameForPatientOne));
            nameField.clear();
            nameField.sendKeys(name);
        } catch (Exception e) {
            System.out.println("Failed to set Secondary Insurance Member Name: " + e.getMessage());
        }
    }

    public void setSecondaryInsuranceMemberIdForPatientOne(String memberId) {
        try {
            WebElement idField = wait.until(ExpectedConditions.visibilityOfElementLocated(secondaryInsuranceMemberIdForPatientOne));
            idField.clear();
            idField.sendKeys(memberId);
        } catch (Exception e) {
            System.out.println("Failed to set Secondary Insurance Member ID: " + e.getMessage());
        }
    }

    public void setSecondaryInsuranceMemberDOBForPatientOne(String dob) {
        try {
            WebElement dobField = wait.until(ExpectedConditions.visibilityOfElementLocated(secondaryInsuranceMemberDOBForPatientOne));
            dobField.clear();
            dobField.sendKeys(dob);
        } catch (Exception e) {
            System.out.println("Failed to set Secondary Insurance Member DOB: " + e.getMessage());
        }
    }

    public void selectSecondaryInsuranceRelationshipForPatientOne(String relationshipText) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(secondaryInsuranceRelationshipForPatientOne));
            Select select = new Select(dropdown);
            select.selectByVisibleText(relationshipText);
        } catch (Exception e) {
            System.out.println("Failed to select Secondary Insurance Relationship: " + e.getMessage());
        }
    }
}

