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

public class TC_PV001_CreateSelfPayPrimaryCareVisitForAccountHolder extends BaseTest {
    public PatientPortalLoginPage patientPortalLoginPage;
    public PatientPortalHomePage patientPortalHomePage;
    public PrimaryCareVisitPage primaryCareVisitPage;
    public PatientPortalMyProfilePage myProfilePage;
    public SignInPage signInPage;
    public TestData testDataForAccountHolder;
    public SoftAssert softAssert;
    public WebDriverWait wait;
    public String selectedProviderName = null;

    @BeforeClass
    public void setUp() throws IOException {
        driver.get(ConfigReader.getProperty("PatientPortalLoginUrl"));
        testDataForAccountHolder = new TestData();
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
    public void testSignInAsNewValidPatient(){
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
    public void testSelectPatient() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Assert.assertTrue(patientPortalHomePage.selectPrimaryCareVisit(), "Failed to select Primary Care Visit");
        Thread.sleep(5000);
        Assert.assertTrue(primaryCareVisitPage.clickPatientAsMySelf(), "Failed to click Patient As MySelf option");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next button after selecting Patient As MySelf option");
        Assert.assertTrue(primaryCareVisitPage.clickProceedByBookingButton(), "Failed to click Proceed By Booking button");
        Thread.sleep(2000);
        ExtentReportManager.getTest().log(Status.PASS, "Successfully selected patient and initiated primary care visit");
    }

    @Test(priority = 3, dependsOnMethods = {"testSelectPatient"})
    public void testSelectCondition() {
        Assert.assertTrue(primaryCareVisitPage.selectCondition(testDataForAccountHolder.getCondition()), "Failed to select condition");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next button after selecting condition");
        ExtentReportManager.getTest().log(Status.PASS, "Condition selected and continued to next step");
    }

    @Test(priority = 4, dependsOnMethods = {"testSelectCondition"})
    public void testSelectSlot() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.selectFirstAvailableDay(), "Failed to select first available day");
        Assert.assertTrue(primaryCareVisitPage.selectFirstAvailableTimeSlot(), "Failed to select first available time slot");
        selectedProviderName = primaryCareVisitPage.getSelectedProviderName();
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next button after selecting time slot");
        Thread.sleep(2000);
        ExtentReportManager.getTest().log(Status.PASS, "Time slot selected and moved to demographics");
    }

    @Test(priority = 5, dependsOnMethods = {"testSelectSlot"})
    public void testDemographics() {
        softAssert.assertEquals(primaryCareVisitPage.getFirstName(), testDataForAccountHolder.getFname(), "First name mismatch");
        softAssert.assertEquals(primaryCareVisitPage.getLastName(), testDataForAccountHolder.getLname(), "Last name mismatch");
        Assert.assertTrue(primaryCareVisitPage.setDOB(testDataForAccountHolder.getDobForMajorInMMDD()), "Failed to enter DOB");
        Assert.assertTrue(primaryCareVisitPage.setAddressLineOne(testDataForAccountHolder.getStreetAddressOne()), "Failed to enter address line 1");
        Assert.assertTrue(primaryCareVisitPage.setAddressLineTwo(testDataForAccountHolder.getStreetAddressTwo()), "Failed to enter address line 2");
        Assert.assertTrue(primaryCareVisitPage.setHeightFeet(testDataForAccountHolder.getFeet()), "Failed to enter height (feet)");
        Assert.assertTrue(primaryCareVisitPage.setHeightInches(testDataForAccountHolder.getInch()), "Failed to enter height (inches)");
        Assert.assertTrue(primaryCareVisitPage.setWeight(testDataForAccountHolder.getWeight()), "Failed to enter weight");
        Assert.assertTrue(primaryCareVisitPage.selectGender(testDataForAccountHolder.getGender()), "Failed to select gender");
        softAssert.assertEquals(primaryCareVisitPage.getZipcode(), testDataForAccountHolder.getZipCode(), "Zipcode mismatch");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next after demographics");
        ExtentReportManager.getTest().log(Status.PASS, "Demographics filled successfully");
        softAssert.assertAll();
    }

    @Test(priority = 6, dependsOnMethods = {"testDemographics"})
    public void testUploadIdentity() {
        Assert.assertTrue(primaryCareVisitPage.uploadId(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))), "Failed to upload ID");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next after ID upload");
        ExtentReportManager.getTest().log(Status.PASS, "ID uploaded and moved to next step");
    }

    @Test(priority = 7, dependsOnMethods = {"testUploadIdentity"})
    public void testAddPharmacy() {
        Assert.assertTrue(primaryCareVisitPage.clickAddPharmacyAndSwitchToListView(), "Failed to switch to pharmacy list");
        Assert.assertTrue(primaryCareVisitPage.clickFirstPharmacy(), "Failed to select pharmacy");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next after pharmacy");
        ExtentReportManager.getTest().log(Status.PASS, "Pharmacy selected and moved to next step");
    }

    @Test(priority = 8, dependsOnMethods = {"testAddPharmacy"})
    public void testAddSymptoms() {
        Assert.assertTrue(primaryCareVisitPage.setGeneralSymptoms(testDataForAccountHolder.getGeneralSymptom()), "Failed to set general symptoms");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next after symptoms");
        ExtentReportManager.getTest().log(Status.PASS, "Symptoms entered and continued");
    }

    @Test(priority = 9, dependsOnMethods = {"testAddSymptoms"})
    public void testAllergies() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.clickNoneBtnInAllergy(), "Failed to select 'None' in allergy");
        Thread.sleep(3000);
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next after allergy");
        ExtentReportManager.getTest().log(Status.PASS, "Allergy section skipped and moved forward");
    }

    @Test(priority = 10, dependsOnMethods = {"testAllergies"})
    public void testMedication() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.clickNoneBtnInMedication(), "Failed to select 'None' in medication");
        Thread.sleep(3000);
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next after medication");

        if (testDataForAccountHolder.getGender().equalsIgnoreCase("Female")) {
            Assert.assertTrue(primaryCareVisitPage.setAdditionalMedicalConditionsForFemale("Pregnant"), "Failed to set medical conditions");
            Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next after medical conditions");
        }
        ExtentReportManager.getTest().log(Status.PASS, "Medication section completed");
    }

    @Test(priority = 11, dependsOnMethods = {"testMedication"})
    public void testOptionalDetails() {
        Assert.assertTrue(primaryCareVisitPage.setOptionalDetails(testDataForAccountHolder.getAdditionalText()), "Failed to set optional details");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next after optional details");
        ExtentReportManager.getTest().log(Status.PASS, "Optional details set and moved ahead");
    }

    @Test(priority = 12, dependsOnMethods = {"testOptionalDetails"})
    public void testCodeOfConduct() {
        Assert.assertTrue(primaryCareVisitPage.acceptAllCodeOfConduct(), "Failed to accept code of conduct");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next after code of conduct");
        ExtentReportManager.getTest().log(Status.PASS, "Code of Conduct accepted");
    }

    @Test(priority = 13, dependsOnMethods = {"testCodeOfConduct"})
    public void testPayment() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.paymentByCard(
                ConfigReader.getProperty("testCardNumber"),
                ConfigReader.getProperty("testCardExpiryDate"),
                ConfigReader.getProperty("testCardCVV")
        ), "Failed to process payment");
        Thread.sleep(1000);
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next after payment");
        ExtentReportManager.getTest().log(Status.PASS, "Payment processed successfully");
    }

    @Test(priority = 14, dependsOnMethods = {"testPayment"})
    public void testSubmitVisit() {
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
            ExtentReportManager.getTest().log(Status.FAIL, "Exception verifying Submit for Evaluation: " + e.getMessage());
        }
        softAssert.assertAll();
    }
    @Test(priority = 15, dependsOnMethods = {"testSubmitVisit"} )
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
    @Test(priority = 16)
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

                        "<b>Visit Details:</b><br>" +
                        "Visit Type: Primary Care Visit<br>" +
                        "Condition Selected: "+testDataForAccountHolder.getCondition()+"<br>" +
                        "<b>Provider Name: " + selectedProviderName + "</b><br><br>" +

                        "<b>Demographics:</b><br>" +
                        "Date of Birth: " + testDataForAccountHolder.getDobForMajor() + "<br>" +
                        "Height: " + testDataForAccountHolder.getFeet() + " feet " + testDataForAccountHolder.getInch() + " inches<br>" +
                        "Weight: " + testDataForAccountHolder.getWeight() + "<br>" +
                        "Gender: " + testDataForAccountHolder.getGender() + "<br>" +
                        "Address Line 1: " + testDataForAccountHolder.getStreetAddressOne() + "<br>" +
                        "Address Line 2: " + testDataForAccountHolder.getStreetAddressTwo() + "<br>" +
                        "Zipcode: " + testDataForAccountHolder.getZipCode() + "<br><br>" +

                        "<b>Symptoms & Medical Info:</b><br>" +
                        "General Symptoms: "+testDataForAccountHolder.getGeneralSymptom()+"<br>" +
                        "Allergy: None<br>" +
                        "Medication: None<br>" +
                        "<b>Other Information:</b><br>" +
                        additionMedicalConditions +
                        "Additional Details: " + testDataForAccountHolder.getAdditionalText();

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
