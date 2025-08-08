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
import pages.PatientPortal.PatientPortalHomePage;
import pages.PatientPortal.PatientPortalLoginPage;
import pages.PatientPortal.PatientPortalMyProfilePage;
import pages.PatientPortal.PrimaryCareVisitPage;
import utils.ConfigReader;
import utils.ExtentReportManager;
import utils.TestData;

import java.io.IOException;
import java.time.Duration;

public class TC_PV008_CreateSelfPayPrimaryCareVisitForEstablishedAccountHolder extends BaseTest {
    public PatientPortalLoginPage patientPortalLoginPage;
    public PatientPortalHomePage patientPortalHomePage;
    public PatientPortalMyProfilePage myProfilePage;
    public PrimaryCareVisitPage primaryCareVisitPage;

    public TestData testDataForAccountHolder;
    public SoftAssert softAssert;
    public WebDriverWait wait;
    public String selectedProviderName = null;
    @BeforeClass
    public void setUp() throws IOException {
        driver.get(ConfigReader.getProperty("PatientPortalLoginUrl"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        testDataForAccountHolder = new TestData();
    }
    @BeforeMethod
    public void initializeAsset(){
        softAssert = new SoftAssert();
        patientPortalLoginPage = new PatientPortalLoginPage(driver);
        patientPortalHomePage = new PatientPortalHomePage(driver);
        primaryCareVisitPage = new PrimaryCareVisitPage(driver);
        myProfilePage = new PatientPortalMyProfilePage(driver);
    }
    @Test(priority = 1)
    public void testLoginWithValidCredentials()
    {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        ExtentReportManager.getTest().log(Status.INFO, "Starting login with valid patient credentials");

        patientPortalLoginPage.setEmail(ConfigReader.getProperty("ExistingPatientEmail"));
        patientPortalLoginPage.setPassword(ConfigReader.getProperty("PatientPortalPassword"));
        patientPortalLoginPage.clickLoginButton();

        boolean isHome = patientPortalHomePage.isHomePage();

        softAssert.assertTrue(isHome, "Home page did not load properly after login.");

        if (isHome) {
            ExtentReportManager.getTest().log(Status.INFO, "Login successful, home page loaded.");
        } else {
            ExtentReportManager.getTest().log(Status.INFO, "Login failed or home page did not load.");
        }
        softAssert.assertAll();
    }
    @Test(priority = 2, dependsOnMethods = {"testLoginWithValidCredentials"} )
    public void testSelectPatient() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Assert.assertTrue(patientPortalHomePage.selectPrimaryCareVisit(), "Failed to select Primary Care Visit");
        Thread.sleep(5000);
        Assert.assertTrue(primaryCareVisitPage.clickPatientAsMySelf(), "Failed to click Patient As MySelf option");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next button after selecting Patient As MySelf option");
        //Staying in Self pay
        Assert.assertTrue(primaryCareVisitPage.clickProceedByBookingButton(), "Failed to click Proceed By Booking button");
        Thread.sleep(2000);
    }
    @Test(priority = 3, dependsOnMethods = {"testSelectPatient"})
    public void testSelectCondition() throws InterruptedException {
        //SELECT Condition
        Assert.assertTrue(primaryCareVisitPage.selectCondition(testDataForAccountHolder.getCondition()),
                "Failed to select Allergies condition");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next button after selecting condition");
    }
    @Test(priority = 4, dependsOnMethods = {"testSelectCondition"} )
    public void testSelectSlot() throws InterruptedException {
        //Select First the Available Day and First slot
        Assert.assertTrue(primaryCareVisitPage.selectFirstAvailableDay(), "Failed to select first available day");
        Assert.assertTrue(primaryCareVisitPage.selectFirstAvailableTimeSlot(), "Failed to select first available time slot");
        selectedProviderName = primaryCareVisitPage.getSelectedProviderName();
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next button after selecting time slot");
        Thread.sleep(2000);
    }
    @Test(priority = 5, dependsOnMethods = {"testSelectSlot"} )
    public void testDemographics() {
//        softAssert.assertEquals(primaryCareVisitPage.getFirstName(), testDataForAccountHolder.getFname(),
//                "First name is mismatching in demographics");
//        softAssert.assertEquals(primaryCareVisitPage.getLastName(), testDataForAccountHolder.getLname(),
//                "Last name is mismatching in demographics");
        Assert.assertTrue(primaryCareVisitPage.setDOB(testDataForAccountHolder.getDobForMajorInMMDD()),
                "Failed to enter date of birth in demographics");
        Assert.assertTrue(primaryCareVisitPage.setAddressLineOne(testDataForAccountHolder.getStreetAddressOne()),
                "Failed to enter address line one in demographics");
//        Assert.assertTrue(primaryCareVisitPage.setAddressLineTwo(testDataForAccountHolder.getStreetAddressTwo()),
//                "Failed to enter address line two in demographics");
        Assert.assertTrue(primaryCareVisitPage.setHeightFeet(testDataForAccountHolder.getFeet()),
                "Failed to enter feet in demographics");
        Assert.assertTrue(primaryCareVisitPage.setHeightInches(testDataForAccountHolder.getInch()),
                "Failed to enter inches in demographics");
        Assert.assertTrue(primaryCareVisitPage.setWeight(testDataForAccountHolder.getWeight()),
                "Failed to enter weight in demographics");
        Assert.assertTrue(primaryCareVisitPage.selectGender(testDataForAccountHolder.getGender()),
                "Failed to select gender in demographics");
        softAssert.assertEquals(primaryCareVisitPage.getZipcode(), testDataForAccountHolder.getZipCode(),
                "Zipcode is mismatching in demographics");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after demographics");
    }
    @Test(priority = 6, dependsOnMethods = {"testDemographics"} )
    public void testAddIdentity() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.uploadId(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))), "Failed to upload ID");
        Thread.sleep(3000);
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after ID upload");
    }
    @Test(priority = 7, dependsOnMethods = {"testAddIdentity"} )
    public void testAddPharmacy() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.clickAddPharmacyAndSwitchToListView(), "Failed to switch to pharmacy list view");
        Assert.assertTrue(primaryCareVisitPage.clickFirstPharmacy(), "Failed to select pharmacy");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after pharmacy selection");
    }
    @Test(priority = 8, dependsOnMethods = {"testAddPharmacy"} )
    public void testAddSymptoms() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.setGeneralSymptoms(testDataForAccountHolder.getGeneralSymptom()), "Failed to set general symptoms");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after symptoms");
    }
    @Test(priority = 9, dependsOnMethods = {"testAddSymptoms"} )
    public void testAddAllergies() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.clickNoneBtnInAllergy(),"Failed to select None button in allergy dropdown");
        Thread.sleep(2000);
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after allergy");
    }
    @Test(priority = 10, dependsOnMethods = {"testAddAllergies"} )
    public void testAddMedication() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.clickNoneBtnInMedication(),"Failed to select None button in medication dropdown");
        Thread.sleep(2000);
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after medication");

        if(testDataForAccountHolder.getGender().equalsIgnoreCase("Female")){
            Assert.assertTrue(primaryCareVisitPage.setAdditionalMedicalConditionsForFemale("Pregnant"),
                    "Failed to set additional medical conditions for female");
            Assert.assertTrue(primaryCareVisitPage.clickNextButton(),
                    "Failed to move to next step after additional medical conditions for female");
        }
    }
    @Test(priority = 11, dependsOnMethods = {"testAddMedication"} )
    public void testAdditionalInformation() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.setOptionalDetails(testDataForAccountHolder.getAdditionalText()), "Failed to set optional details");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after optional details");
    }
    @Test(priority = 12, dependsOnMethods = {"testAdditionalInformation"} )
    public void testAcknowledgeCodeOfConduct() throws InterruptedException {
        Assert.assertTrue(primaryCareVisitPage.acceptAllCodeOfConduct(), "Failed to accept code of conduct");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after code of conduct");
    }
    @Test(priority = 13, dependsOnMethods = {"testAcknowledgeCodeOfConduct"} )
    public void testPayment() throws InterruptedException {
                Assert.assertTrue(primaryCareVisitPage.paymentByCard(
                ConfigReader.getProperty("testCardNumber"),
                ConfigReader.getProperty("testCardExpiryDate"),
                ConfigReader.getProperty("testCardCVV")
        ), "Failed to process card payment");
        Thread.sleep(3000);
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after card payment");
    }
    @Test(priority = 14, dependsOnMethods = {"testPayment"} )
    public void testSubmitVisit(){
        try {
            boolean isEnabled = primaryCareVisitPage.isSubmitForEvaluationEnabled();
            softAssert.assertTrue(isEnabled, "Submit for Evaluation button should be enabled");

            if (isEnabled) {
                primaryCareVisitPage.clickSubmitForEvaluation();
                ExtentReportManager.getTest().log(Status.PASS,
                        "Submit for Evaluation button is enabled as expected");
            } else {
                ExtentReportManager.getTest().log(Status.FAIL,
                        "Submit for Evaluation button is disabled when it should be enabled");
            }
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL,
                    "Failed to verify Submit for Evaluation button state: " + e.getMessage());
        }
        softAssert.assertAll();
    }
    @Test(priority = 3, dependsOnMethods = {"testSubmitVisit"} )
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
