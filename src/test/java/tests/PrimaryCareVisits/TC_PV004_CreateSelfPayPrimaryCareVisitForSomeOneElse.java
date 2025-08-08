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

public class TC_PV004_CreateSelfPayPrimaryCareVisitForSomeOneElse extends BaseTest {
    public PatientPortalLoginPage patientPortalLoginPage;
    public PatientPortalHomePage patientPortalHomePage;
    public PrimaryCareVisitPage primaryCareVisitPage;
    public PatientPortalMyProfilePage myProfilePage;
    public SignInPage signInPage;
    public TestData testDataForAccountHolder;
    public TestData testDataForSomeOneElse;
    public SoftAssert softAssert;
    public WebDriverWait wait;
    public String selectedProviderName = null;

    @BeforeClass
    public void setUp() throws IOException {
        driver.get(ConfigReader.getProperty("PatientPortalLoginUrl"));
        testDataForAccountHolder = new TestData();
        testDataForSomeOneElse = new TestData();
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
    public void testSelectPrimaryCareVisitForSomeoneElse() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Thread.sleep(1000);
        Assert.assertTrue(patientPortalHomePage.selectPrimaryCareVisit(), "Failed to select Primary Care Visit");
        Thread.sleep(5000);
        Assert.assertTrue(primaryCareVisitPage.clickPatientAsSomeOneElse(), "Failed to click Patient As Someone else option");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next button after selecting Someone Else option");
        ExtentReportManager.getTest().log(Status.PASS, "Selected Primary Care Visit and 'Someone Else' option");
    }

    @Test(priority = 3, dependsOnMethods = {"testSelectPrimaryCareVisitForSomeoneElse"})
    public void testAddSomeoneElseDetails() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.clickAddSomeOneElse(), "Failed to click Add SomeOneElse Button");
        Thread.sleep(500);
        Assert.assertTrue(primaryCareVisitPage.setFirstName(testDataForSomeOneElse.getFname()), "Failed to set first name");
        Assert.assertTrue(primaryCareVisitPage.setLastName(testDataForSomeOneElse.getLname()), "Failed to set last name");
        Assert.assertTrue(primaryCareVisitPage.setDOB(testDataForSomeOneElse.getDobForMajorInMMDD()), "Failed to set DOB");
        Assert.assertTrue(primaryCareVisitPage.setAddressLineOne(testDataForSomeOneElse.getStreetAddressOne()), "Failed to set address line one");
        Assert.assertTrue(primaryCareVisitPage.setAddressLineTwo(testDataForSomeOneElse.getStreetAddressTwo()), "Failed to set address line two");
        Assert.assertTrue(primaryCareVisitPage.setCity("test"), "Failed to set city");
        Assert.assertTrue(primaryCareVisitPage.setZipcode(testDataForSomeOneElse.getZipCode()), "Failed to set zipcode");
        Assert.assertTrue(primaryCareVisitPage.setHeightFeet(testDataForSomeOneElse.getFeet()), "Failed to set height feet");
        Assert.assertTrue(primaryCareVisitPage.setHeightInches(testDataForSomeOneElse.getInch()), "Failed to set height inches");
        Assert.assertTrue(primaryCareVisitPage.setWeight(testDataForSomeOneElse.getWeight()), "Failed to set weight");
        Assert.assertTrue(primaryCareVisitPage.selectGender(testDataForSomeOneElse.getGender()), "Failed to select gender");
        Assert.assertTrue(primaryCareVisitPage.clickSaveButton(), "Failed to click Save SomeOneElse Button");
        ExtentReportManager.getTest().log(Status.PASS, "Added Someone Else's details successfully");
    }

    @Test(priority = 4, dependsOnMethods = {"testAddSomeoneElseDetails"})
    public void testSelectSavedSomeoneElsePatient() throws InterruptedException {
        Thread.sleep(2000);
        Assert.assertTrue(primaryCareVisitPage.selectPatient(testDataForSomeOneElse.getFullName()), "Failed to select the patient");
        Thread.sleep(1000);
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next button after selecting Someone Else");
        ExtentReportManager.getTest().log(Status.PASS, "Selected saved Someone Else patient and moved forward");
    }

    @Test(priority = 5, dependsOnMethods = {"testSelectSavedSomeoneElsePatient"})
    public void testProceedWithSelfPayVisit() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.clickProceedByBookingButton(), "Failed to click Proceed By Booking button");
        Thread.sleep(3000);
        ExtentReportManager.getTest().log(Status.PASS, "Clicked Proceed By Booking in Self Pay flow");
    }

    @Test(priority = 6, dependsOnMethods = {"testProceedWithSelfPayVisit"})
    public void testSelectVisitCondition() {
        Assert.assertTrue(primaryCareVisitPage.selectCondition("Allergies"), "Failed to select Allergies condition");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next button after selecting condition");
        ExtentReportManager.getTest().log(Status.PASS, "Condition selected and continued");
    }

    @Test(priority = 7, dependsOnMethods = {"testSelectVisitCondition"})
    public void testSelectTimeSlot() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.selectFirstAvailableDay(), "Failed to select first available day");
        Assert.assertTrue(primaryCareVisitPage.selectFirstAvailableTimeSlot(), "Failed to select time slot");
        selectedProviderName = primaryCareVisitPage.getSelectedProviderName();
        Thread.sleep(500);
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next button after selecting time slot");
        ExtentReportManager.getTest().log(Status.PASS, "Slot and provider selected");
    }

    @Test(priority = 8, dependsOnMethods = {"testSelectTimeSlot"})
    public void testValidateDemographicsForSomeoneElse() {
        softAssert.assertEquals(primaryCareVisitPage.getFirstName(), testDataForSomeOneElse.getFname(), "First name mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getLastName(), testDataForSomeOneElse.getLname(), "Last name mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getDOBInMMDD(), testDataForSomeOneElse.getDobForMajorInMMDD(), "DOB mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getAddressOne(), testDataForSomeOneElse.getStreetAddressOne(), "Address 1 mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getAddressTwo(), testDataForSomeOneElse.getStreetAddressTwo(), "Address 2 mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getZipcode(), testDataForSomeOneElse.getZipCode(), "Zip mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getFeet(), testDataForSomeOneElse.getFeet(), "Feet mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getInch(), testDataForSomeOneElse.getInch(), "Inch mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getWeight(), testDataForSomeOneElse.getWeight(), "Weight mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getGender(), testDataForSomeOneElse.getGender(), "Gender mismatch");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after demographics");
        ExtentReportManager.getTest().log(Status.PASS, "Demographics validated successfully");
        softAssert.assertAll();
    }

    @Test(priority = 9, dependsOnMethods = {"testValidateDemographicsForSomeoneElse"})
    public void testUploadID() {
        Assert.assertTrue(primaryCareVisitPage.uploadId(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))), "Failed to upload ID");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after ID upload");
        ExtentReportManager.getTest().log(Status.PASS, "ID uploaded successfully");
    }

    @Test(priority = 10, dependsOnMethods = {"testUploadID"})
    public void testSelectPharmacy() {
        Assert.assertTrue(primaryCareVisitPage.clickAddPharmacyAndSwitchToListView(), "Failed to switch to pharmacy list view");
        Assert.assertTrue(primaryCareVisitPage.clickFirstPharmacy(), "Failed to select pharmacy");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after pharmacy selection");
        ExtentReportManager.getTest().log(Status.PASS, "Pharmacy selected successfully");
    }

    @Test(priority = 11, dependsOnMethods = {"testSelectPharmacy"})
    public void testAddSymptoms() {
        Assert.assertTrue(primaryCareVisitPage.setGeneralSymptoms("Fatigue"), "Failed to set general symptoms");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after symptoms");
        ExtentReportManager.getTest().log(Status.PASS, "Symptoms added successfully");
    }

    @Test(priority = 12, dependsOnMethods = {"testAddSymptoms"})
    public void testAddAllergy() {
        Assert.assertTrue(primaryCareVisitPage.addAllergy(
                testDataForSomeOneElse.getAllergyOne(),
                testDataForSomeOneElse.getAllergyReactionOne(),
                testDataForSomeOneElse.getDrugAllergyCategory()), "Failed to add allergy");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after allergy");
        ExtentReportManager.getTest().log(Status.PASS, "Allergy added successfully");
    }

    @Test(priority = 13, dependsOnMethods = {"testAddAllergy"})
    public void testAddMedication() {
        Assert.assertTrue(primaryCareVisitPage.clickSearchMedicationButton(), "Failed to click search medication");
        Assert.assertTrue(primaryCareVisitPage.addMedication(
                testDataForSomeOneElse.getMedicationOne(),
                testDataForSomeOneElse.getDosageOne(),
                testDataForSomeOneElse.getMedicationFormOne(),
                testDataForSomeOneElse.getMedicationFrequencyOne(),
                testDataForSomeOneElse.getMedicationPerOne()), "Failed to add medication");
        Assert.assertTrue(primaryCareVisitPage.clickDoneBtnInMedication(), "Failed to click Done btn in medication");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after medication");

        if (testDataForSomeOneElse.getGender().equalsIgnoreCase("Female")) {
            Assert.assertTrue(primaryCareVisitPage.setAdditionalMedicalConditionsForFemale("Pregnant"), "Failed to set additional medical condition");
            Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after female condition");
        }
        ExtentReportManager.getTest().log(Status.PASS, "Medication added successfully");
    }

    @Test(priority = 14, dependsOnMethods = {"testAddMedication"})
    public void testAddOptionalDetails() {
        Assert.assertTrue(primaryCareVisitPage.setOptionalDetails(testDataForSomeOneElse.getAdditionalText()), "Failed to set optional details");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after optional details");
        ExtentReportManager.getTest().log(Status.PASS, "Optional details added successfully");
    }

    @Test(priority = 15, dependsOnMethods = {"testAddOptionalDetails"})
    public void testAcknowledgeAndPay() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.acceptAllCodeOfConduct(), "Failed to accept Code of Conduct");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed after Code of Conduct");

        Assert.assertTrue(primaryCareVisitPage.paymentByCard(
                ConfigReader.getProperty("testCardNumber"),
                ConfigReader.getProperty("testCardExpiryDate"),
                ConfigReader.getProperty("testCardCVV")
        ), "Failed to process payment");
        Thread.sleep(2000);
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to proceed to confirmation");

        ExtentReportManager.getTest().log(Status.PASS, "Code of Conduct accepted and payment processed");
    }

    @Test(priority = 16, dependsOnMethods = {"testAcknowledgeAndPay"})
    public void testSubmitVisitForSomeoneElse() {
        try {
            boolean isEnabled = primaryCareVisitPage.isSubmitForEvaluationEnabled();
            softAssert.assertTrue(isEnabled, "Submit for Evaluation should be enabled");

            if (isEnabled) {
                primaryCareVisitPage.clickSubmitForEvaluation();
                ExtentReportManager.getTest().log(Status.PASS, "Submit for Evaluation successful");
            } else {
                ExtentReportManager.getTest().log(Status.FAIL, "Submit for Evaluation button was disabled");
            }
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Error checking Submit for Evaluation: " + e.getMessage());
        }
        softAssert.assertAll();
    }
    @Test(priority = 17, dependsOnMethods = {"testSubmitVisitForSomeoneElse"})
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

                        "<b>SomeOneElse (Patient) Details:</b><br>" +
                        "First Name: " + testDataForSomeOneElse.getFname() + "<br>" +
                        "Last Name: " + testDataForSomeOneElse.getLname() + "<br>" +
                        "Date of Birth: " + testDataForSomeOneElse.getDobForMinor() + "<br>" +
                        "Height: " + testDataForSomeOneElse.getFeet() + " feet " + testDataForSomeOneElse.getInch() + " inches<br>" +
                        "Weight: " + testDataForSomeOneElse.getWeight() + "<br>" +
                        "Gender: " + testDataForSomeOneElse.getGender() + "<br>" +
                        "Address Line 1: " + testDataForSomeOneElse.getStreetAddressOne() + "<br>" +
                        "Address Line 2: " + testDataForSomeOneElse.getStreetAddressTwo() + "<br>" +
                        "Zipcode: " + testDataForSomeOneElse.getZipCode() + "<br><br>" +

                        "<b>Visit Details:</b><br>" +
                        "Visit Type: Primary Care Visit<br>" +
                        "Condition Selected: Allergies<br>" +
                        "<b>Provider Name: " + selectedProviderName + "</b><br>" +
                        "<b>Symptoms & Medical Info:</b><br>" +
                        "Symptoms: Fatigue<br>" +
                        "Allergy: " + testDataForSomeOneElse.getAllergyOne() + "<br>" +
                        "Allergy Reaction: " + testDataForSomeOneElse.getAllergyReactionOne() + "<br>" +
                        "Allergy Category: " + testDataForSomeOneElse.getDrugAllergyCategory() + "<br>" +
                        "<b>Medication Details:</b><br>" +
                        "Medication: " + testDataForSomeOneElse.getMedicationOne() + "<br>" +
                        "Dosage: " + testDataForSomeOneElse.getDosageOne() + "<br>" +
                        "Form: " + testDataForSomeOneElse.getMedicationFormOne() + "<br>" +
                        "Frequency: " + testDataForSomeOneElse.getMedicationFrequencyOne() + "<br>" +
                        "Per: " + testDataForSomeOneElse.getMedicationPerOne() + "<br>" +
                        "<b>Other Information:</b><br>" +
                        additionMedicalConditions +
                        "Additional Details: " + testDataForSomeOneElse.getAdditionalText();

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
