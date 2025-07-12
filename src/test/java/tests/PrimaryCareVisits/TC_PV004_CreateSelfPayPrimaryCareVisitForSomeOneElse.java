package tests.PrimaryCareVisits;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.PatientPortal.PatientPortalHomePage;
import pages.PatientPortal.PatientPortalLoginPage;
import pages.PatientPortal.PrimaryCareVisitPage;
import pages.PatientPortal.SignInPage;
import utils.ConfigReader;
import utils.ExtentReportManager;
import utils.TestData;

import java.io.IOException;
import java.time.Duration;

public class TC_PV004_CreateSelfPayPrimaryCareVisitForSomeOneElse extends BaseTest {
    public PatientPortalLoginPage patientPortalLoginPage;
    public PatientPortalHomePage patientPortalHomePage;
    public PrimaryCareVisitPage primaryCareVisitPage;
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
        Thread.sleep(1000);
        Assert.assertTrue(patientPortalHomePage.selectPrimaryCareVisit(), "Failed to select Primary Care Visit");
        Thread.sleep(5000);
        Assert.assertTrue(primaryCareVisitPage.clickPatientAsSomeOneElse(), "Failed to click Patient As Someone else option");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next button after selecting Patient As SomeOneElse option");

        //addSomeOneElse
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
        Thread.sleep(2000);
        Assert.assertTrue(primaryCareVisitPage.selectPatient(testDataForSomeOneElse.getFullName()),"Failed to select the patient");
        Thread.sleep(1000);
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next button after adding SomeOneElse");
        //Staying in Self pay
        Assert.assertTrue(primaryCareVisitPage.clickProceedByBookingButton(), "Failed to click Proceed By Booking button");
        Thread.sleep(3000);
        //condition
        Assert.assertTrue(primaryCareVisitPage.clickSelectFirstCondition("Allergies"), "Failed to select Allergies condition");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next button after selecting condition");
        //Select the First Available Day and First slot
        Assert.assertTrue(primaryCareVisitPage.selectFirstAvailableDay(), "Failed to select first available day");
        Assert.assertTrue(primaryCareVisitPage.selectFirstAvailableTimeSlot(), "Failed to select first available time slot");
        //storing provider name
        selectedProviderName = primaryCareVisitPage.getSelectedProviderName();
        Thread.sleep(500);
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next button after selecting time slot");
        softAssert.assertEquals(primaryCareVisitPage.getFirstName(), testDataForSomeOneElse.getFname(),
                "First name is mismatching in demographics");
        softAssert.assertEquals(primaryCareVisitPage.getLastName(), testDataForSomeOneElse.getLname(),
                "Last name is mismatching in demographics");
        softAssert.assertEquals(primaryCareVisitPage.getDOBInMMDD(), testDataForSomeOneElse.getDobForMajorInMMDD(),
                "DOB is mismatching in demographics");
        softAssert.assertEquals(primaryCareVisitPage.getAddressOne(), testDataForSomeOneElse.getStreetAddressOne(),
                "Address line one is mismatching in demographics");
        softAssert.assertEquals(primaryCareVisitPage.getAddressTwo(), testDataForSomeOneElse.getStreetAddressTwo(),
                "Address line two is mismatching in demographics");
        softAssert.assertEquals(primaryCareVisitPage.getZipcode(), testDataForSomeOneElse.getZipCode(),
                "Zipcode is mismatching in demographics");
        softAssert.assertEquals(primaryCareVisitPage.getFeet(), testDataForSomeOneElse.getFeet(),
                "Height Feet is mismatching in demographics");
        softAssert.assertEquals(primaryCareVisitPage.getInch(), testDataForSomeOneElse.getInch(),
                "Height Inches is mismatching in demographics");
        softAssert.assertEquals(primaryCareVisitPage.getWeight(), testDataForSomeOneElse.getWeight(),
                "Weight is mismatching in demographics");
        softAssert.assertEquals(primaryCareVisitPage.getGender(),testDataForSomeOneElse.getGender(),
                "Gender is mismatching in demographics");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after demographics");

        Assert.assertTrue(primaryCareVisitPage.uploadId(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))), "Failed to upload ID");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after ID upload");

        Assert.assertTrue(primaryCareVisitPage.clickAddPharmacyAndSwitchToListView(), "Failed to switch to pharmacy list view");
        Assert.assertTrue(primaryCareVisitPage.clickFirstPharmacy(), "Failed to select pharmacy");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after pharmacy selection");

        Assert.assertTrue(primaryCareVisitPage.setGeneralSymptoms("Fatigue"), "Failed to set general symptoms");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after symptoms");

        Assert.assertTrue(primaryCareVisitPage.addAllergy(testDataForSomeOneElse.getAllergyOne(),
                testDataForSomeOneElse.getAllergyReactionOne(),
                testDataForSomeOneElse.getDrugAllergyCategory()), "Failed to add allergy");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after allergy");

        Assert.assertTrue(primaryCareVisitPage.clickSearchMedicationButton(), "Failed to click search medication");
        Assert.assertTrue(primaryCareVisitPage.addMedication(
                testDataForSomeOneElse.getMedicationOne(),
                testDataForSomeOneElse.getDosageOne(),
                testDataForSomeOneElse.getMedicationFormOne(),
                testDataForSomeOneElse.getMedicationFrequencyOne(),
                testDataForSomeOneElse.getMedicationPerOne()), "Failed to add medication");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after medication");
        if(testDataForSomeOneElse.getGender().equalsIgnoreCase("Female")){
            Assert.assertTrue(primaryCareVisitPage.setAdditionalMedicalConditionsForFemale("Pregnant"),
                    "Failed to set additional medical conditions for female");
            Assert.assertTrue(primaryCareVisitPage.clickNextButton(),
                    "Failed to move to next step after additional medical conditions for female");
        }
        Assert.assertTrue(primaryCareVisitPage.setOptionalDetails(testDataForSomeOneElse.getAdditionalText()),
                "Failed to set optional details");
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
    @Test(priority = 3)
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
    @Test(priority = 4, dependsOnMethods = {"testPrimaryCareVisitValidation"})
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
}
