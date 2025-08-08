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

public class TC_PV010_CreateInsurancePayPrimaryCareVisitForChild extends BaseTest {
    public PatientPortalLoginPage patientPortalLoginPage;
    public PatientPortalHomePage patientPortalHomePage;
    public PrimaryCareVisitPage primaryCareVisitPage;
    public PatientPortalMyProfilePage myProfilePage;
    public SignInPage signInPage;
    public TestData testDataForAccountHolder;
    public TestData testDataForChild;
    public SoftAssert softAssert;
    public WebDriverWait wait;
    public String selectedProviderName = null;

    @BeforeClass
    public void setUp() throws IOException {
        driver.get(ConfigReader.getProperty("PatientPortalLoginUrl"));
        testDataForAccountHolder = new TestData();
        testDataForChild = new TestData();
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
    public void testSelectChildPatient() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Thread.sleep(1000);
        Assert.assertTrue(patientPortalHomePage.selectPrimaryCareVisit(), "Failed to select Primary Care Visit");
        Thread.sleep(5000);
        Assert.assertTrue(primaryCareVisitPage.clickPatientAsChild(), "Failed to click Patient As child option");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next button");
        ExtentReportManager.getTest().log(Status.PASS, "Primary Care visit started for Child patient");
    }

    @Test(priority = 3, dependsOnMethods = {"testSelectChildPatient"})
    public void testAddChildDetails() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.clickAddChild(), "Failed to click Add Child");
        Thread.sleep(500);
        Assert.assertTrue(primaryCareVisitPage.setFirstName(testDataForChild.getFname()), "Failed to set first name");
        Assert.assertTrue(primaryCareVisitPage.setLastName(testDataForChild.getLname()), "Failed to set last name");
        Assert.assertTrue(primaryCareVisitPage.setDOB(testDataForChild.getDobForMinorInMMDD()), "Failed to set DOB");
        Assert.assertTrue(primaryCareVisitPage.setHeightFeet(testDataForChild.getFeet()), "Failed to set height feet");
        Assert.assertTrue(primaryCareVisitPage.setHeightInches(testDataForChild.getInch()), "Failed to set height inches");
        Assert.assertTrue(primaryCareVisitPage.setWeight(testDataForChild.getWeight()), "Failed to set weight");
        Assert.assertTrue(primaryCareVisitPage.selectGender(testDataForChild.getGender()), "Failed to select gender");
        Assert.assertTrue(primaryCareVisitPage.clickSaveButton(), "Failed to save child details");
        Thread.sleep(2000);
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed after saving child");
        ExtentReportManager.getTest().log(Status.PASS, "Child details filled and saved successfully");
    }
    @Test(priority = 4, dependsOnMethods = {"testAddChildDetails"})
    public void testAddInsurance() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.clickInsurancePay(), "Failed to click Insurance pay section");
        Thread.sleep(2000);
        Assert.assertTrue(primaryCareVisitPage.setInsuranceName(testDataForChild.getPrimaryInsurance()),
                "Failed to set primary insurance");
        Assert.assertTrue(primaryCareVisitPage.setMemberName(testDataForChild.getMemberNameForPrimaryInsurance()),
                "Failed to set member name");
        Assert.assertTrue(primaryCareVisitPage.setMemberId(testDataForChild.getMemberIdForPrimaryInsurance()),
                "Failed to set member ID");
        Assert.assertTrue(primaryCareVisitPage.setDOB(testDataForChild.getMemberDobForPrimaryInsuranceInMMDD()),
                "Failed to set date of birth");
        Assert.assertTrue(primaryCareVisitPage.uploadFrontPage(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))),
                "Failed to upload front page of insurance card");
        Assert.assertTrue(primaryCareVisitPage.uploadBackPage(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))),
                "Failed to upload back page of insurance card");
        Assert.assertTrue(primaryCareVisitPage.clickAddInsuranceButton(),
                "Failed to click add insurance button");
        Thread.sleep(3000);
        try {
            softAssert.assertEquals(primaryCareVisitPage.getPrimaryInsuranceNameInPricingPage(), testDataForChild.getPrimaryInsurance(),
                    "Primary Insurance name is mismatching in Insurance Pay section");
            ExtentReportManager.getTest().log(Status.PASS, "Successfully verified Primary Insurance name in Insurance Pay section");
        } catch (AssertionError e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Primary Insurance name verification failed in Insurance Pay section: " + e.getMessage());
        }
        try {
            softAssert.assertEquals(primaryCareVisitPage.getPrimaryInsuranceMemberIdInPricingPage(), testDataForChild.getMemberIdForPrimaryInsurance(),
                    "Member ID is mismatching in Insurance Pay section");
            ExtentReportManager.getTest().log(Status.PASS, "Successfully verified Member ID in Insurance Pay section");
        } catch (AssertionError e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Member ID verification failed in Insurance Pay section: " + e.getMessage());
        }
        Assert.assertTrue(primaryCareVisitPage.clickYesForSecondaryInsurance(),
                "Failed to click Yes for secondary insurance");
        Assert.assertTrue(primaryCareVisitPage.clickAddSecondaryInsuranceButton(),
                "Failed to click Add Secondary Insurance button");
        Assert.assertTrue(primaryCareVisitPage.setInsuranceName(testDataForChild.getSecondaryInsurance()),
                "Failed to set primary insurance");
        Assert.assertTrue(primaryCareVisitPage.setMemberName(testDataForChild.getMemberNameForSecondaryInsurance()),
                "Failed to set member name");
        Assert.assertTrue(primaryCareVisitPage.setMemberId(testDataForChild.getMemberIdForSecondaryInsurance()),
                "Failed to set member ID");
        Assert.assertTrue(primaryCareVisitPage.setDOB(testDataForChild.getMemberDobForSecondaryInsuranceInMMDD()),
                "Failed to set date of birth");
        Assert.assertTrue(primaryCareVisitPage.uploadFrontPage(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))),
                "Failed to upload front page of insurance card");
        Assert.assertTrue(primaryCareVisitPage.uploadBackPage(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))),
                "Failed to upload back page of insurance card");
        Assert.assertTrue(primaryCareVisitPage.clickAddInsuranceButton(),
                "Failed to click add insurance button");
        Thread.sleep(3000);
        try {
            softAssert.assertEquals(primaryCareVisitPage.getSecondaryInsuranceNameInPricingPage(), testDataForChild.getSecondaryInsurance(),
                    "Primary Insurance name is mismatching in Insurance Pay section");
            ExtentReportManager.getTest().log(Status.PASS, "Successfully verified Primary Insurance name in Insurance Pay section");
        } catch (AssertionError e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Primary Insurance name verification failed in Insurance Pay section: " + e.getMessage());
        }
        try {
            softAssert.assertEquals(primaryCareVisitPage.getSecondaryMemberIdInPricingPage(), testDataForChild.getMemberIdForSecondaryInsurance(),
                    "Member ID is mismatching in Insurance Pay section");
            ExtentReportManager.getTest().log(Status.PASS, "Successfully verified Member ID in Insurance Pay section");
        } catch (AssertionError e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Member ID verification failed in Insurance Pay section: " + e.getMessage());
        }
        Assert.assertTrue(primaryCareVisitPage.clickProceedByBookingButton(), "Failed to click proceed by booking button");
        softAssert.assertAll();
    }

    @Test(priority = 5, dependsOnMethods = {"testAddInsurance"})
    public void testVisitTypeAndCondition() throws InterruptedException {
        Thread.sleep(3000);
        Assert.assertTrue(primaryCareVisitPage.selectCondition("Allergies"), "Failed to select Allergies");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed after selecting condition");
        ExtentReportManager.getTest().log(Status.PASS, "Visit type set to self-pay and condition selected");
    }

    @Test(priority = 6, dependsOnMethods = {"testVisitTypeAndCondition"})
    public void testSlotAndProviderSelection() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.selectFirstAvailableDay(), "Failed to select first available day");
        Assert.assertTrue(primaryCareVisitPage.selectFirstAvailableTimeSlot(), "Failed to select time slot");
        selectedProviderName = primaryCareVisitPage.getSelectedProviderName();
        Thread.sleep(500);
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed after selecting time slot");
        ExtentReportManager.getTest().log(Status.PASS, "Appointment slot and provider selected");
    }

    @Test(priority = 7, dependsOnMethods = {"testSlotAndProviderSelection"})
    public void testVerifyDemographicsAndContinue() {
        softAssert.assertEquals(primaryCareVisitPage.getFirstName(), testDataForChild.getFname(), "First name mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getLastName(), testDataForChild.getLname(), "Last name mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getDOBInMMDD(), testDataForChild.getDobForMinorInMMDD(), "DOB mismatch");
        Assert.assertTrue(primaryCareVisitPage.setAddressLineOne(testDataForChild.getStreetAddressOne()), "Failed to set address 1");
        Assert.assertTrue(primaryCareVisitPage.setAddressLineTwo(testDataForChild.getStreetAddressTwo()), "Failed to set address 2");
        softAssert.assertEquals(primaryCareVisitPage.getZipcode(), testDataForChild.getZipCode(), "Zipcode mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getFeet(), testDataForChild.getFeet(), "Height feet mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getInch(), testDataForChild.getInch(), "Height inch mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getWeight(), testDataForChild.getWeight(), "Weight mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getGender(), testDataForChild.getGender(), "Gender mismatch");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed after demographics");
        ExtentReportManager.getTest().log(Status.PASS, "Demographics validated and continued");
        softAssert.assertAll();
    }

    @Test(priority = 8, dependsOnMethods = {"testVerifyDemographicsAndContinue"})
    public void testUploadIDForChild() {
        Assert.assertTrue(primaryCareVisitPage.uploadId(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))), "Failed to upload ID");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed after ID upload");
        ExtentReportManager.getTest().log(Status.PASS, "Child ID uploaded successfully");
    }

    @Test(priority = 9, dependsOnMethods = {"testUploadIDForChild"})
    public void testSelectPharmacyForChild() {
        Assert.assertTrue(primaryCareVisitPage.clickAddPharmacyAndSwitchToListView(), "Failed to switch to pharmacy list");
        Assert.assertTrue(primaryCareVisitPage.clickFirstPharmacy(), "Failed to select pharmacy");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to continue after pharmacy");
        ExtentReportManager.getTest().log(Status.PASS, "Pharmacy selected for child");
    }

    @Test(priority = 10, dependsOnMethods = {"testSelectPharmacyForChild"})
    public void testAddSymptomsForChild() {
        Assert.assertTrue(primaryCareVisitPage.setGeneralSymptoms("Fatigue"), "Failed to set symptoms");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed after symptoms");
        ExtentReportManager.getTest().log(Status.PASS, "General symptoms added");
    }

    @Test(priority = 11, dependsOnMethods = {"testAddSymptomsForChild"})
    public void testAddAllergyForChild() {
        Assert.assertTrue(primaryCareVisitPage.addAllergy(
                testDataForChild.getAllergyOne(),
                testDataForChild.getAllergyReactionOne(),
                testDataForChild.getDrugAllergyCategory()), "Failed to add allergy");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed after allergy");
        ExtentReportManager.getTest().log(Status.PASS, "Allergy added for child");
    }

    @Test(priority = 12, dependsOnMethods = {"testAddAllergyForChild"})
    public void testAddMedicationForChild() {
        Assert.assertTrue(primaryCareVisitPage.clickSearchMedicationButton(), "Failed to click search medication");
        Assert.assertTrue(primaryCareVisitPage.addMedication(
                testDataForChild.getMedicationOne(),
                testDataForChild.getDosageOne(),
                testDataForChild.getMedicationFormOne(),
                testDataForChild.getMedicationFrequencyOne(),
                testDataForChild.getMedicationPerOne()), "Failed to add medication");
        Assert.assertTrue(primaryCareVisitPage.clickDoneBtnInMedication(), "Failed to click Done btn in medication");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to continue after medication");

        if (testDataForChild.getGender().equalsIgnoreCase("Female")) {
            Assert.assertTrue(primaryCareVisitPage.setAdditionalMedicalConditionsForFemale("Pregnant"),
                    "Failed to set medical condition for female");
            Assert.assertTrue(primaryCareVisitPage.clickNextButton(),
                    "Failed to proceed after setting female condition");
        }
        ExtentReportManager.getTest().log(Status.PASS, "Medication added for child");
    }

    @Test(priority = 13, dependsOnMethods = {"testAddMedicationForChild"})
    public void testEnterOptionalDetails() {
        Assert.assertTrue(primaryCareVisitPage.setOptionalDetails(testDataForChild.getAdditionalText()), "Failed to enter optional details");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to continue after optional details");
        ExtentReportManager.getTest().log(Status.PASS, "Optional details filled");
    }

    @Test(priority = 14, dependsOnMethods = {"testEnterOptionalDetails"})
    public void testAcknowledgeCodeOfConduct() {
        Assert.assertTrue(primaryCareVisitPage.acceptAllCodeOfConduct(), "Failed to accept Code of Conduct");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to continue after Code of Conduct");
        ExtentReportManager.getTest().log(Status.PASS, "Code of Conduct accepted");
    }

    @Test(priority = 15, dependsOnMethods = {"testAcknowledgeCodeOfConduct"})
    public void testMakePaymentForChild() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.paymentByCard(
                ConfigReader.getProperty("testCardNumber"),
                ConfigReader.getProperty("testCardExpiryDate"),
                ConfigReader.getProperty("testCardCVV")
        ), "Failed to process payment");
        Thread.sleep(2000);
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed after payment");
        ExtentReportManager.getTest().log(Status.PASS, "Payment processed successfully for child");
    }

    @Test(priority = 16, dependsOnMethods = {"testMakePaymentForChild"})
    public void testSubmitVisitForChild() {
        try {
            boolean isEnabled = primaryCareVisitPage.isSubmitForEvaluationEnabled();
            softAssert.assertTrue(isEnabled, "Submit for Evaluation button should be enabled");

            if (isEnabled) {
                primaryCareVisitPage.clickSubmitForEvaluation();
                ExtentReportManager.getTest().log(Status.PASS, "Submit for Evaluation button clicked");
            } else {
                ExtentReportManager.getTest().log(Status.FAIL, "Submit for Evaluation button was disabled");
            }
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Error during submission check: " + e.getMessage());
        }
        softAssert.assertAll();
    }

    @Test(priority = 17, dependsOnMethods = {"testSubmitVisitForChild"})
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

                        "<b>Child (Patient) Details:</b><br>" +
                        "First Name: " + testDataForChild.getFname() + "<br>" +
                        "Last Name: " + testDataForChild.getLname() + "<br>" +
                        "Date of Birth: " + testDataForChild.getDobForMinor() + "<br>" +
                        "Height: " + testDataForChild.getFeet() + " feet " + testDataForChild.getInch() + " inches<br>" +
                        "Weight: " + testDataForChild.getWeight() + "<br>" +
                        "Gender: " + testDataForChild.getGender() + "<br>" +
                        "Address Line 1: " + testDataForChild.getStreetAddressOne() + "<br>" +
                        "Address Line 2: " + testDataForChild.getStreetAddressTwo() + "<br>" +
                        "Zipcode: " + testDataForChild.getZipCode() + "<br><br>" +

                        "<b>Insurance Details:</b><br>" +
                        "Primary Insurance Name: " + testDataForChild.getPrimaryInsurance() + "<br>" +
                        "Primary Member Name: " + testDataForChild.getMemberNameForPrimaryInsurance() + "<br>" +
                        "Primary Insurance ID: " + testDataForChild.getMemberIdForPrimaryInsurance() + "<br>" +
                        "Primary Insurance DOB: " + testDataForChild.getMemberDobForPrimaryInsurance() + "<br><br>" +

                        "Secondary Insurance Name: " + testDataForChild.getSecondaryInsurance() + "<br>" +
                        "Secondary Member Name: " + testDataForChild.getMemberNameForSecondaryInsurance() + "<br>" +
                        "Secondary Insurance ID: " + testDataForChild.getMemberIdForSecondaryInsurance() + "<br>" +
                        "Secondary Insurance DOB: " + testDataForChild.getMemberDobForSecondaryInsurance() + "<br><br>" +

                        "<b>Visit Details:</b><br>" +
                        "Visit Type: Primary Care Visit<br>" +
                        "Condition Selected: Allergies<br>" +
                        "<b>Provider Name: " + selectedProviderName + "</b><br>" +
                        "<b>Symptoms & Medical Info:</b><br>" +
                        "Symptoms: Fatigue<br>" +
                        "Allergy: " + testDataForChild.getAllergyOne() + "<br>" +
                        "Allergy Reaction: " + testDataForChild.getAllergyReactionOne() + "<br>" +
                        "Allergy Category: " + testDataForChild.getDrugAllergyCategory() + "<br>" +
                        "<b>Medication Details:</b><br>" +
                        "Medication: " + testDataForChild.getMedicationOne() + "<br>" +
                        "Dosage: " + testDataForChild.getDosageOne() + "<br>" +
                        "Form: " + testDataForChild.getMedicationFormOne() + "<br>" +
                        "Frequency: " + testDataForChild.getMedicationFrequencyOne() + "<br>" +
                        "Per: " + testDataForChild.getMedicationPerOne() + "<br>" +
                        "<b>Other Information:</b><br>" +
                        additionMedicalConditions +
                        "Additional Details: " + testDataForChild.getAdditionalText();

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
