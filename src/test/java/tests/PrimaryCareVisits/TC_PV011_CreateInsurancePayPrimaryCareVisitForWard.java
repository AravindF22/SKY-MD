package tests.PrimaryCareVisits;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.PatientPortal.*;
import utils.ConfigReader;
import utils.ExtentReportManager;
import utils.TestData;

import java.io.IOException;
import java.time.Duration;

public class TC_PV011_CreateInsurancePayPrimaryCareVisitForWard extends BaseTest {
    public PatientPortalLoginPage patientPortalLoginPage;
    public PatientPortalHomePage patientPortalHomePage;
    public PrimaryCareVisitPage primaryCareVisitPage;
    public PatientPortalMyProfilePage myProfilePage;
    public SignInPage signInPage;
    public TestData testDataForAccountHolder;
    public TestData testDataForWard;
    public SoftAssert softAssert;
    public WebDriverWait wait;
    public String selectedProviderName = null;

    @BeforeClass
    public void setUp() throws IOException {
        driver.get(ConfigReader.getProperty("PatientPortalLoginUrl"));
        testDataForAccountHolder = new TestData();
        testDataForWard = new TestData();
    }
    @BeforeMethod
    public void initializeAsset(){
        softAssert = new SoftAssert();
        patientPortalLoginPage = new PatientPortalLoginPage(driver);
        patientPortalHomePage = new PatientPortalHomePage(driver);
        primaryCareVisitPage = new PrimaryCareVisitPage(driver);
        myProfilePage = new PatientPortalMyProfilePage(driver);
        signInPage = new SignInPage(driver);
    }
    @Test(priority = 1)
    public void testSignInAsNewValidPatient()  {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Sign in as new valid patient");

        patientPortalLoginPage = new PatientPortalLoginPage(driver);
        ExtentReportManager.getTest().log(Status.INFO, "Navigated to Patient Portal Login Page");

        patientPortalLoginPage.clickSignUpLink();
        ExtentReportManager.getTest().log(Status.INFO, "Clicked on Sign Up link");

        signInPage.enterEmail(testDataForAccountHolder.getEmail());
        signInPage.clickNextButton();
        ExtentReportManager.getTest().log(Status.INFO, "Entered email and clicked Next");

        signInPage.enterPassword(ConfigReader.getProperty("PatientPortalPassword"));
        ExtentReportManager.getTest().log(Status.INFO, "Entered password");

        signInPage.clickAgeCheckIcon();
        signInPage.clickContinueButton();
        ExtentReportManager.getTest().log(Status.INFO, "Accepted age and clicked Continue");

        signInPage.enterFirstName(testDataForAccountHolder.getFname());
        signInPage.enterLastName(testDataForAccountHolder.getLname());
        signInPage.enterMobileNumber(testDataForAccountHolder.getMobileNumber());
        signInPage.enterZipCode(testDataForAccountHolder.getZipCode());
        ExtentReportManager.getTest().log(Status.INFO, "Filled personal details");

        signInPage.clickSignInButton();
        ExtentReportManager.getTest().log(Status.INFO, "Clicked Sign In button");

        boolean isHome = patientPortalHomePage.isHomePage();
        ExtentReportManager.getTest().log(Status.INFO, "Verifying home page load");

        softAssert.assertTrue(isHome, "Home page did not load properly after sign in.");
        if (isHome) {
            ExtentReportManager.getTest().log(Status.INFO, "New patient signed in successfully and home page loaded");
        } else {
            ExtentReportManager.getTest().log(Status.INFO, "Sign in failed or home page did not load");
        }
        softAssert.assertAll();
    }
    @Test(priority = 2, dependsOnMethods = {"testSignInAsNewValidPatient"})
    public void testSelectPrimaryCareVisitAsWard() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Thread.sleep(1000);
        Assert.assertTrue(patientPortalHomePage.selectPrimaryCareVisit(), "Failed to select Primary Care Visit");
        Thread.sleep(5000);
        Assert.assertTrue(primaryCareVisitPage.clickPatientAsWard(), "Failed to click Patient As Ward option");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next after selecting Ward");
        ExtentReportManager.getTest().log(Status.PASS, "Selected Primary Care Visit with Ward option");
    }

    @Test(priority = 3, dependsOnMethods = {"testSelectPrimaryCareVisitAsWard"})
    public void testAddWardDetails() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.clickAddWard(), "Failed to click Add Ward");
        Thread.sleep(500);
        Assert.assertTrue(primaryCareVisitPage.setFirstName(testDataForWard.getFname()), "Failed to set first name");
        Assert.assertTrue(primaryCareVisitPage.setLastName(testDataForWard.getLname()), "Failed to set last name");
        Assert.assertTrue(primaryCareVisitPage.setDOB(testDataForWard.getDobForMajorInMMDD()), "Failed to set DOB");
        Assert.assertTrue(primaryCareVisitPage.setHeightFeet(testDataForWard.getFeet()), "Failed to set height feet");
        Assert.assertTrue(primaryCareVisitPage.setHeightInches(testDataForWard.getInch()), "Failed to set height inches");
        Assert.assertTrue(primaryCareVisitPage.setWeight(testDataForWard.getWeight()), "Failed to set weight");
        Assert.assertTrue(primaryCareVisitPage.selectGender(testDataForWard.getGender()), "Failed to select gender");
        Assert.assertTrue(primaryCareVisitPage.clickSaveButton(), "Failed to save Ward details");
        Thread.sleep(2000);
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed after adding Ward");
        ExtentReportManager.getTest().log(Status.PASS, "Ward details added successfully");
    }
    @Test(priority = 4, dependsOnMethods = {"testAddWardDetails"})
    public void testAddInsurance() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.clickInsurancePay(), "Failed to click Insurance pay section");
        Thread.sleep(2000);
        Assert.assertTrue(primaryCareVisitPage.setInsuranceName(testDataForWard.getPrimaryInsurance()),
                "Failed to set primary insurance");
        Assert.assertTrue(primaryCareVisitPage.setMemberName(testDataForWard.getMemberNameForPrimaryInsurance()),
                "Failed to set member name");
        Assert.assertTrue(primaryCareVisitPage.setMemberId(testDataForWard.getMemberIdForPrimaryInsurance()),
                "Failed to set member ID");
        Assert.assertTrue(primaryCareVisitPage.setDOB(testDataForWard.getMemberDobForPrimaryInsuranceInMMDD()),
                "Failed to set date of birth");
        Assert.assertTrue(primaryCareVisitPage.uploadFrontPage(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))),
                "Failed to upload front page of insurance card");
        Assert.assertTrue(primaryCareVisitPage.uploadBackPage(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))),
                "Failed to upload back page of insurance card");
        Assert.assertTrue(primaryCareVisitPage.clickAddInsuranceButton(),
                "Failed to click add insurance button");
        Thread.sleep(3000);
        try {
            softAssert.assertEquals(primaryCareVisitPage.getPrimaryInsuranceNameInPricingPage(), testDataForWard.getPrimaryInsurance(),
                    "Primary Insurance name is mismatching in Insurance Pay section");
            ExtentReportManager.getTest().log(Status.PASS, "Successfully verified Primary Insurance name in Insurance Pay section");
        } catch (AssertionError e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Primary Insurance name verification failed in Insurance Pay section: " + e.getMessage());
        }
        try {
            softAssert.assertEquals(primaryCareVisitPage.getPrimaryInsuranceMemberIdInPricingPage(), testDataForWard.getMemberIdForPrimaryInsurance(),
                    "Member ID is mismatching in Insurance Pay section");
            ExtentReportManager.getTest().log(Status.PASS, "Successfully verified Member ID in Insurance Pay section");
        } catch (AssertionError e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Member ID verification failed in Insurance Pay section: " + e.getMessage());
        }
        Assert.assertTrue(primaryCareVisitPage.clickYesForSecondaryInsurance(),
                "Failed to click Yes for secondary insurance");
        Assert.assertTrue(primaryCareVisitPage.clickAddSecondaryInsuranceButton(),
                "Failed to click Add Secondary Insurance button");
        Assert.assertTrue(primaryCareVisitPage.setInsuranceName(testDataForWard.getSecondaryInsurance()),
                "Failed to set primary insurance");
        Assert.assertTrue(primaryCareVisitPage.setMemberName(testDataForWard.getMemberNameForSecondaryInsurance()),
                "Failed to set member name");
        Assert.assertTrue(primaryCareVisitPage.setMemberId(testDataForWard.getMemberIdForSecondaryInsurance()),
                "Failed to set member ID");
        Assert.assertTrue(primaryCareVisitPage.setDOB(testDataForWard.getMemberDobForSecondaryInsuranceInMMDD()),
                "Failed to set date of birth");
        Assert.assertTrue(primaryCareVisitPage.uploadFrontPage(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))),
                "Failed to upload front page of insurance card");
        Assert.assertTrue(primaryCareVisitPage.uploadBackPage(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))),
                "Failed to upload back page of insurance card");
        Assert.assertTrue(primaryCareVisitPage.clickAddInsuranceButton(),
                "Failed to click add insurance button");
        Thread.sleep(3000);
        try {
            softAssert.assertEquals(primaryCareVisitPage.getSecondaryInsuranceNameInPricingPage(), testDataForWard.getSecondaryInsurance(),
                    "Primary Insurance name is mismatching in Insurance Pay section");
            ExtentReportManager.getTest().log(Status.PASS, "Successfully verified Primary Insurance name in Insurance Pay section");
        } catch (AssertionError e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Primary Insurance name verification failed in Insurance Pay section: " + e.getMessage());
        }
        try {
            softAssert.assertEquals(primaryCareVisitPage.getSecondaryMemberIdInPricingPage(), testDataForWard.getMemberIdForSecondaryInsurance(),
                    "Member ID is mismatching in Insurance Pay section");
            ExtentReportManager.getTest().log(Status.PASS, "Successfully verified Member ID in Insurance Pay section");
        } catch (AssertionError e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Member ID verification failed in Insurance Pay section: " + e.getMessage());
        }
        Assert.assertTrue(primaryCareVisitPage.clickProceedByBookingButton(), "Failed to click proceed by booking button");
        softAssert.assertAll();
    }
    @Test(priority = 6, dependsOnMethods = {"testAddInsurance"})
    public void testSelectConditionWard() {
        Assert.assertTrue(primaryCareVisitPage.selectCondition("Allergies"), "Failed to select condition");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next after condition");
        ExtentReportManager.getTest().log(Status.PASS, "Condition selected");
    }

    @Test(priority = 7, dependsOnMethods = {"testSelectConditionWard"})
    public void testSelectSlotWard() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.selectFirstAvailableDay(), "Failed to select available day");
        Assert.assertTrue(primaryCareVisitPage.selectFirstAvailableTimeSlot(), "Failed to select time slot");
        selectedProviderName = primaryCareVisitPage.getSelectedProviderName();
        Thread.sleep(500);
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed after selecting slot");
        ExtentReportManager.getTest().log(Status.PASS, "Time slot and provider selected");
    }

    @Test(priority = 8, dependsOnMethods = {"testSelectSlotWard"})
    public void testValidateDemographicsWard() {
        softAssert.assertEquals(primaryCareVisitPage.getFirstName(), testDataForWard.getFname(), "First name mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getLastName(), testDataForWard.getLname(), "Last name mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getDOBInMMDD(), testDataForWard.getDobForMajorInMMDD(), "DOB mismatch");
        Assert.assertTrue(primaryCareVisitPage.setAddressLineOne(testDataForWard.getStreetAddressOne()), "Failed to enter address line 1");
        Assert.assertTrue(primaryCareVisitPage.setAddressLineTwo(testDataForWard.getStreetAddressTwo()), "Failed to enter address line 2");
        softAssert.assertEquals(primaryCareVisitPage.getZipcode(), testDataForWard.getZipCode(), "Zipcode mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getFeet(), testDataForWard.getFeet(), "Feet mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getInch(), testDataForWard.getInch(), "Inch mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getWeight(), testDataForWard.getWeight(), "Weight mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getGender(), testDataForWard.getGender(), "Gender mismatch");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed after demographics");
        ExtentReportManager.getTest().log(Status.PASS, "Demographics validated");
        softAssert.assertAll();
    }

    @Test(priority = 9, dependsOnMethods = {"testValidateDemographicsWard"})
    public void testUploadIDWard() {
        Assert.assertTrue(primaryCareVisitPage.uploadId(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))), "Failed to upload ID");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed after ID upload");
        ExtentReportManager.getTest().log(Status.PASS, "ID uploaded");
    }

    @Test(priority = 10, dependsOnMethods = {"testUploadIDWard"})
    public void testSelectPharmacyWard() {
        Assert.assertTrue(primaryCareVisitPage.clickAddPharmacyAndSwitchToListView(), "Failed to switch pharmacy view");
        Assert.assertTrue(primaryCareVisitPage.clickFirstPharmacy(), "Failed to select pharmacy");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed after pharmacy");
        ExtentReportManager.getTest().log(Status.PASS, "Pharmacy selected");
    }

    @Test(priority = 11, dependsOnMethods = {"testSelectPharmacyWard"})
    public void testAddSymptomsWard() {
        Assert.assertTrue(primaryCareVisitPage.setGeneralSymptoms("Fatigue"), "Failed to set symptoms");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed after symptoms");
        ExtentReportManager.getTest().log(Status.PASS, "Symptoms added");
    }

    @Test(priority = 12, dependsOnMethods = {"testAddSymptomsWard"})
    public void testAddAllergyWard() {
        Assert.assertTrue(primaryCareVisitPage.addAllergy(
                testDataForWard.getAllergyOne(),
                testDataForWard.getAllergyReactionOne(),
                testDataForWard.getDrugAllergyCategory()), "Failed to add allergy");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed after allergy");
        ExtentReportManager.getTest().log(Status.PASS, "Allergy added");
    }

    @Test(priority = 13, dependsOnMethods = {"testAddAllergyWard"})
    public void testAddMedicationWard() {
        Assert.assertTrue(primaryCareVisitPage.clickSearchMedicationButton(), "Failed to click medication search");
        Assert.assertTrue(primaryCareVisitPage.addMedication(
                testDataForWard.getMedicationOne(),
                testDataForWard.getDosageOne(),
                testDataForWard.getMedicationFormOne(),
                testDataForWard.getMedicationFrequencyOne(),
                testDataForWard.getMedicationPerOne()), "Failed to add medication");
        Assert.assertTrue(primaryCareVisitPage.clickDoneBtnInMedication(), "Failed to click Done in medication");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed after medication");

        if (testDataForWard.getGender().equalsIgnoreCase("Female")) {
            Assert.assertTrue(primaryCareVisitPage.setAdditionalMedicalConditionsForFemale("Pregnant"), "Failed to set female condition");
            Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed after female condition");
        }

        ExtentReportManager.getTest().log(Status.PASS, "Medication added");
    }

    @Test(priority = 14, dependsOnMethods = {"testAddMedicationWard"})
    public void testAddOptionalDetailsWard() {
        Assert.assertTrue(primaryCareVisitPage.setOptionalDetails(testDataForWard.getAdditionalText()), "Failed to set optional details");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed after optional details");
        ExtentReportManager.getTest().log(Status.PASS, "Optional details added");
    }

    @Test(priority = 15, dependsOnMethods = {"testAddOptionalDetailsWard"})
    public void testAcknowledgeAndPayWard() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.acceptAllCodeOfConduct(), "Failed to accept Code of Conduct");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed after Code of Conduct");

        Assert.assertTrue(primaryCareVisitPage.paymentByCard(
                ConfigReader.getProperty("testCardNumber"),
                ConfigReader.getProperty("testCardExpiryDate"),
                ConfigReader.getProperty("testCardCVV")
        ), "Failed to process card payment");

        Thread.sleep(2000);
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed to confirmation");
        ExtentReportManager.getTest().log(Status.PASS, "Payment completed");
    }

    @Test(priority = 16, dependsOnMethods = {"testAcknowledgeAndPayWard"})
    public void testSubmitVisitWard() {
        try {
            boolean isEnabled = primaryCareVisitPage.isSubmitForEvaluationEnabled();
            softAssert.assertTrue(isEnabled, "Submit for Evaluation should be enabled");

            if (isEnabled) {
                primaryCareVisitPage.clickSubmitForEvaluation();
                ExtentReportManager.getTest().log(Status.PASS, "Visit submitted successfully");
            } else {
                ExtentReportManager.getTest().log(Status.FAIL, "Submit for Evaluation button is disabled");
            }
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Exception during submission: " + e.getMessage());
        }
        softAssert.assertAll();
    }
    @Test(priority = 17, dependsOnMethods = {"testSubmitVisitWard"})
    public void testVisitValidationInPatientPortal() {
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Validation in Patient Portal");
        // Visit submission verification
        boolean isVisitSubmitted = primaryCareVisitPage.isVisitSubmitted();
        try {
            softAssert.assertTrue(isVisitSubmitted, "Visit submission verification failed");
            ExtentReportManager.getTest().log(Status.PASS, "Visit submission verification successful: Visit was submitted successfully");
        } catch (AssertionError e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Visit submission verification failed: Visit was not submitted as expected");
        }
        softAssert.assertEquals(selectedProviderName,primaryCareVisitPage.getProviderNameInTheVisitSubmittedPage(),
                "Provider name in the visit submitted page is not matching with the selected provider name");
        primaryCareVisitPage.clickGoToMyVisitsButton();
        softAssert.assertAll();
    }
    @Test(priority = 18)
    public void logPrimaryCareVisitDetails() {
        String additionMedicalConditions = "";
        if(testDataForAccountHolder.getGender().equalsIgnoreCase("Female")){
            additionMedicalConditions = "Additional Medical Condition for Female : "+" Pregnant " + "<br>";
        }
        String visitDetailsHtml =
                "<b>Account Holder Details:</b><br>" +
                        "First Name: " + testDataForAccountHolder.getFname() + "<br>" +
                        "Last Name: " + testDataForAccountHolder.getLname() + "<br>" +
                        "Email: " + testDataForAccountHolder.getEmail() + "<br>" +
                        "Mobile Number: " + testDataForAccountHolder.getMobileNumber() + "<br>" +
                        "Zipcode: " + testDataForAccountHolder.getZipCode() + "<br><br>" +

                        "<b>Ward (Patient) Details:</b><br>" +
                        "First Name: " + testDataForWard.getFname() + "<br>" +
                        "Last Name: " + testDataForWard.getLname() + "<br>" +
                        "Date of Birth: " + testDataForWard.getDobForMinor() + "<br>" +
                        "Height: " + testDataForWard.getFeet() + " feet " + testDataForWard.getInch() + " inches<br>" +
                        "Weight: " + testDataForWard.getWeight() + "<br>" +
                        "Gender: " + testDataForWard.getGender() + "<br>" +
                        "Address Line 1: " + testDataForWard.getStreetAddressOne() + "<br>" +
                        "Address Line 2: " + testDataForWard.getStreetAddressTwo() + "<br>" +
                        "Zipcode: " + testDataForWard.getZipCode() + "<br><br>" +

                        "<b>Visit Details:</b><br>" +
                        "Visit Type: Primary Care Visit<br>" +
                        "Condition Selected: Allergies<br>" +
                        "<b>Provider Name: " + selectedProviderName + "</b><br>" +
                        "<b>Symptoms & Medical Info:</b><br>" +
                        "Symptoms: Fatigue<br>" +
                        "Allergy: " + testDataForWard.getAllergyOne() + "<br>" +
                        "Allergy Reaction: " + testDataForWard.getAllergyReactionOne() + "<br>" +
                        "Allergy Category: " + testDataForWard.getDrugAllergyCategory() + "<br>" +
                        "<b>Medication Details:</b><br>" +
                        "Medication: " + testDataForWard.getMedicationOne() + "<br>" +
                        "Dosage: " + testDataForWard.getDosageOne() + "<br>" +
                        "Form: " + testDataForWard.getMedicationFormOne() + "<br>" +
                        "Frequency: " + testDataForWard.getMedicationFrequencyOne() + "<br>" +
                        "Per: " + testDataForWard.getMedicationPerOne() + "<br>" +
                        "<b>Other Information:</b><br>" +
                        additionMedicalConditions +
                        "Additional Details: " + testDataForWard.getAdditionalText();

        ExtentReportManager.getTest().info(visitDetailsHtml);
    }
    @AfterClass
    public void cleanUp() throws InterruptedException {
        Thread.sleep(2000);
        patientPortalHomePage.clickMyProfile();
        myProfilePage.clickSettingsLink();
        myProfilePage.clickLogoutButton();
        myProfilePage.clickConfirmLogoutButton();
        ExtentReportManager.getTest().log(Status.INFO, "Logged out from Patient Portal");
    }
}
