package tests.PrimaryCareVisits;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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

public class TC_PV001_CreateSelfPayPrimaryCareVisit extends BaseTest {
    public PatientPortalLoginPage patientPortalLoginPage;
    public PatientPortalHomePage patientPortalHomePage;
    public PrimaryCareVisitPage primaryCareVisitPage;
    public TestData testDataForAccountHolder;
    public TestData testDataForWard;
    public SoftAssert softAssert;
    public SignInPage signInPage;
    public WebDriverWait wait;

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
    @Test(priority = 2)
    public void testPrimaryCareVisitValidation() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Assert.assertTrue(patientPortalHomePage.selectPrimaryCareVisit(), "Failed to select Primary Care Visit");
        Thread.sleep(5000);
        Assert.assertTrue(primaryCareVisitPage.clickPatientAsMySelf(), "Failed to click Patient As MySelf option");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next button after selecting Patient As MySelf option");
        Assert.assertTrue(primaryCareVisitPage.clickProceedByBookingButton(), "Failed to click Proceed By Booking button");

        Assert.assertTrue(primaryCareVisitPage.clickSelectFirstCondition("Allergies"), "Failed to select Allergies condition");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next button after selecting condition");

        Assert.assertTrue(primaryCareVisitPage.selectFirstAvailableDay(), "Failed to select first available day");
        Assert.assertTrue(primaryCareVisitPage.selectFirstAvailableTimeSlot(), "Failed to select first available time slot");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next button after selecting time slot");

        softAssert.assertEquals(primaryCareVisitPage.getFirstName(), testDataForAccountHolder.getFname(),
                "First name is mismatching in demographics");
        softAssert.assertEquals(primaryCareVisitPage.getLastName(), testDataForAccountHolder.getLname(),
                "Last name is mismatching in demographics");
        Assert.assertTrue(primaryCareVisitPage.setDOB(testDataForAccountHolder.getDobForMajor()),
                "Failed to enter date of birth in demographics");
        Assert.assertTrue(primaryCareVisitPage.setAddressLineOne(testDataForAccountHolder.getStreetAddressOne()),
                "Failed to enter address line one in demographics");
        Assert.assertTrue(primaryCareVisitPage.setAddressLineTwo(testDataForAccountHolder.getStreetAddressTwo()),
                "Failed to enter address line two in demographics");
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

        Assert.assertTrue(primaryCareVisitPage.uploadId(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))), "Failed to upload ID");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after ID upload");

        Assert.assertTrue(primaryCareVisitPage.clickAddPharmacyAndSwitchToListView(), "Failed to switch to pharmacy list view");
        Assert.assertTrue(primaryCareVisitPage.clickFirstPharmacy(), "Failed to select pharmacy");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after pharmacy selection");

        Assert.assertTrue(primaryCareVisitPage.setGeneralSymptoms("Fatigue"), "Failed to set general symptoms");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after symptoms");

        Assert.assertTrue(primaryCareVisitPage.addAllergy(testDataForAccountHolder.getAllergyOne(),
                testDataForAccountHolder.getAllergyReactionOne(),
                testDataForAccountHolder.getDrugAllergyCategory()), "Failed to add allergy");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after allergy");

        Assert.assertTrue(primaryCareVisitPage.clickSearchMedicationButton(), "Failed to click search medication");
        Assert.assertTrue(primaryCareVisitPage.addMedication(
                testDataForAccountHolder.getMedicationOne(),
                testDataForAccountHolder.getDosageOne(),
                testDataForAccountHolder.getMedicationFormOne(),
                testDataForAccountHolder.getMedicationFrequencyOne(),
                testDataForAccountHolder.getMedicationPerOne()), "Failed to add medication");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after medication");

        Assert.assertTrue(primaryCareVisitPage.setOptionalDetails(testDataForAccountHolder.getAdditionalText()), "Failed to set optional details");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after optional details");

        Assert.assertTrue(primaryCareVisitPage.acceptAllCodeOfConduct(), "Failed to accept code of conduct");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after code of conduct");

        Assert.assertTrue(primaryCareVisitPage.paymentByCard(
                ConfigReader.getProperty("testCardNumber"),
                ConfigReader.getProperty("testCardExpiryDate"),
                ConfigReader.getProperty("testCardCVV")
        ), "Failed to process card payment");
        Thread.sleep(2000);
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to payment confirmation step");
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
}
