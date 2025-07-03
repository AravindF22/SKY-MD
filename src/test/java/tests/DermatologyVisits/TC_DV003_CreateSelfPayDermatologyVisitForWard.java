package tests.DermatologyVisits;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.PatientPortal.DermatologyVisitPage;
import pages.PatientPortal.PatientPortalHomePage;
import pages.PatientPortal.PatientPortalLoginPage;
import pages.PatientPortal.SignInPage;
import utils.ConfigReader;
import utils.ExtentReportManager;
import utils.TestData;

import java.io.IOException;
import java.time.Duration;

public class TC_DV003_CreateSelfPayDermatologyVisitForWard extends BaseTest {
    public PatientPortalLoginPage patientPortalLoginPage;
    public PatientPortalHomePage patientPortalHomePage;
    public DermatologyVisitPage dermatologyVisitPage;
    public TestData testDataForAccountHolder;
    public TestData testDataForWard;
    public SignInPage signInPage;
    public SoftAssert softAssert;
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
        signInPage = new SignInPage(driver);
        patientPortalLoginPage = new PatientPortalLoginPage(driver);
        patientPortalHomePage = new PatientPortalHomePage(driver);
        dermatologyVisitPage = new DermatologyVisitPage(driver);

    }
    @Test(priority = 1)
    public void testSignInAsNewValidPatient()  {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Sign in as new valid patient");
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

        patientPortalHomePage = new PatientPortalHomePage(driver);
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
    public void testBasicDetails() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Basic Details");
        patientPortalHomePage.selectDermatologyVisit();
        dermatologyVisitPage.selectDermProviderSection();
        dermatologyVisitPage.clickContinueAfterSelectingDermProvider();
        Thread.sleep(2000);
        dermatologyVisitPage.proceedBasisOfProvider("Dr. Janice Fahey");
        Thread.sleep(1000);
        dermatologyVisitPage.clickSelectPatient();
        //Ward
        dermatologyVisitPage.selectPatientAsWard();
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();
        dermatologyVisitPage.clickAddWardButton();
        dermatologyVisitPage.enterWardFirstName(testDataForWard.getFname());
        dermatologyVisitPage.enterWardLastName(testDataForWard.getLname());
        dermatologyVisitPage.clickSaveWardButton();
        softAssert.assertEquals(dermatologyVisitPage.getFirstWardName(), testDataForWard.getFullName(),
                "First Ward name mismatch in dermatology visit");
        dermatologyVisitPage.clickContinueButtonAfterInsurance();
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();
        dermatologyVisitPage.clickContinueButtonAfterInsurance();
        ExtentReportManager.getTest().log(Status.INFO, "Basic details completed");
        softAssert.assertAll();
    }

    @Test(priority = 3, dependsOnMethods = "testBasicDetails")
    public void testVisitDetails() {
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Details");
        Assert.assertTrue(dermatologyVisitPage.setAddressLineOne(testDataForWard.getStreetAddressOne()), "Failed to set Address Line One");
        dermatologyVisitPage.setAddressLineTwo(testDataForWard.getStreetAddressTwo());
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click Continue button");
        Assert.assertTrue(dermatologyVisitPage.setDOB(testDataForWard.getDobForMajor()), "Failed to set DOB");
        Assert.assertTrue(dermatologyVisitPage.setFeet(testDataForWard.getFeet()), "Failed to set Feet");
        Assert.assertTrue(dermatologyVisitPage.setInches(testDataForWard.getInch()), "Failed to set Inches");
        Assert.assertTrue(dermatologyVisitPage.setWeight(testDataForWard.getWeight()), "Failed to set Weight");
        Assert.assertTrue(dermatologyVisitPage.setGender("Male"), "Failed to set Gender");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click Continue button");
        Assert.assertTrue(dermatologyVisitPage.uploadId(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))), "Failed to upload ID");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click Continue button");
        ExtentReportManager.getTest().log(Status.INFO, "Visit details completed");
    }

    @Test(priority = 4, dependsOnMethods = "testVisitDetails")
    public void testVisitPhotos() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Photos");

        Assert.assertTrue(dermatologyVisitPage.setDermatologyConcern(testDataForWard.getConcern()), "Failed to set dermatology concern");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button");
        Assert.assertTrue(dermatologyVisitPage.selectAffectedBodyPart(testDataForWard.getBodyParts()), "Failed to select affected body part");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button");
        Assert.assertTrue(dermatologyVisitPage.selectStatus(testDataForWard.getStatus()), "Failed to select status");
        Assert.assertTrue(dermatologyVisitPage.selectCount(testDataForWard.getSufferingConditionDays()), "Failed to select condition days");
        Assert.assertTrue(dermatologyVisitPage.selectDay(testDataForWard.getSelectDays()), "Failed to select days");
        Assert.assertTrue(dermatologyVisitPage.selectSeverity(testDataForWard.getSeverity()), "Failed to select severity");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button");
        Assert.assertTrue(dermatologyVisitPage.clickNoBtnForSmartPhoneUpload(), "Failed to click No button for smartphone upload");
        Thread.sleep(1000);
        Assert.assertTrue(dermatologyVisitPage.uploadCloseUpPic(convertToAbsoluteURL(ConfigReader.getProperty("conditionPhotoOnePath"))), "Failed to upload close up photo");
        Thread.sleep(1000);
        Assert.assertTrue(dermatologyVisitPage.uploadFarAwayPic(convertToAbsoluteURL(ConfigReader.getProperty("conditionPhotoTwoPath"))), "Failed to upload far away photo");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after uploading photos");
        ExtentReportManager.getTest().log(Status.INFO, "Visit photos completed");
    }

    @Test(priority = 5, dependsOnMethods = "testVisitPhotos")
    public void testVisitSymptoms() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Symptoms");
        Thread.sleep(1000);
        Assert.assertTrue(dermatologyVisitPage.selectSymptoms(testDataForWard.getSymptomOne()), "Failed to select symptoms");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click Continue button");
        Assert.assertTrue(dermatologyVisitPage.setWorseText(testDataForWard.getWhatMakesWorse()), "Failed to set worse text");
        Assert.assertTrue(dermatologyVisitPage.setBetterText(testDataForWard.getWhatMakesBetter()), "Failed to set better text");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click Continue button");
        Assert.assertTrue(dermatologyVisitPage.selectLiftStyle(testDataForWard.getLifeStyleItem()), "Failed to select lifestyle");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click Continue button after selecting lifestyle");
        ExtentReportManager.getTest().log(Status.INFO, "Visit symptoms completed");
    }

    @Test(priority = 6, dependsOnMethods = "testVisitSymptoms")
    public void testVisitMedicalHistory() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Medical History");
        Assert.assertTrue(dermatologyVisitPage.addAllergy(
                testDataForWard.getAllergyOne(),
                testDataForWard.getAllergyReactionOne(),
                testDataForWard.getDrugAllergyCategory()), "Failed to add allergy details");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click Continue button");
        Thread.sleep(3000);
        Assert.assertTrue(dermatologyVisitPage.clickYesButtonInMedication(), "Failed to click Yes button in medication");
        Thread.sleep(1000);
        Assert.assertTrue(dermatologyVisitPage.clickSearchMedicationButton(), "Failed to click Search Medication button");
        Assert.assertTrue(dermatologyVisitPage.addMedication(
                testDataForWard.getMedicationOne(),
                "test",
                "Oil",
                "PRN",
                "PRN"), "Failed to add medication details");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click Continue button");
        dermatologyVisitPage.clickYesBtnInSkinCareProduct();
        dermatologyVisitPage.enterSkinCareProduct("test");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click Continue button");
        ExtentReportManager.getTest().log(Status.INFO, "Visit medical history completed");
    }

    @Test(priority = 7, dependsOnMethods = "testVisitMedicalHistory")
    public void testVisitPayment() {
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Payment");
        Assert.assertTrue(dermatologyVisitPage.clickAddPharmacyAndSwitchToListView(), "Failed to click Add Pharmacy and switch to list view");
        Assert.assertTrue(dermatologyVisitPage.clickFirstPharmacy(), "Failed to click first pharmacy");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click Continue button after selecting pharmacy");
        dermatologyVisitPage.setOptionalField(testDataForWard.getOptionalFieldText());
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click Continue button after setting optional field");
        Assert.assertTrue(dermatologyVisitPage.clickNextButtonForTAndC(), "Failed to click Next button for Terms and Conditions");
        Assert.assertTrue(dermatologyVisitPage.clickAllAcceptTerms(), "Failed to accept all terms and conditions");
        dermatologyVisitPage.paymentByCard(
                ConfigReader.getProperty("testCardNumber"),
                ConfigReader.getProperty("testCardExpiryDate"),
                ConfigReader.getProperty("testCardCVV"));
        try {
            boolean isEnabled = dermatologyVisitPage.isSubmitForEvaluationEnabled();
            softAssert.assertTrue(isEnabled, "Submit for Evaluation button should be enabled");

            if (isEnabled) {
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
        Assert.assertTrue(dermatologyVisitPage.clickSubmitForEvaluation(), "Failed to submit for evaluation");
        ExtentReportManager.getTest().log(Status.INFO, "Visit payment completed");
        softAssert.assertAll();
    }

    @Test(priority = 8, dependsOnMethods = "testVisitPayment")
    public void testVisitValidationInPatientPortal() {
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Validation in Patient Portal");
        boolean isVisitSubmitted = dermatologyVisitPage.isVisitSubmitted();
        try {
            softAssert.assertTrue(isVisitSubmitted, "Visit submission verification failed");
            ExtentReportManager.getTest().log(Status.PASS, "Visit submission verification successful: Visit was submitted successfully");
        } catch (AssertionError e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Visit submission verification failed: Visit was not submitted as expected");
        }
        String expectedProvider = testDataForWard.getProviderName();
        String actualProvider = dermatologyVisitPage.getProviderNameInVisitSubmittedPage();
        try {
            softAssert.assertEquals(actualProvider, expectedProvider, "Provider name mismatch in submitted visit");
            ExtentReportManager.getTest().log(Status.PASS, "Provider name verification successful: " +
                    "Expected [" + expectedProvider + "] matches Actual [" + actualProvider + "]");
        } catch (AssertionError e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Provider name verification failed: " +
                    "Expected [" + expectedProvider + "] but found [" + actualProvider + "]");
        }
        Assert.assertTrue(dermatologyVisitPage.clickGoToMyVisitsButton(), "Failed to click Go To My Visits button");
        try {
            softAssert.assertAll();
            ExtentReportManager.getTest().log(Status.PASS, "All visit validations passed successfully");
        } catch (AssertionError e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Visit validation failed with the following errors: \n" + e.getMessage());
        }
    }
    @Test(priority = 9, description = "Print Account Holder and Visit Details in Extent Report")
    public void printVisitDetailsInReport() {
        String accountAndVisitDetailsHtml = "<b>Entered Account Holder Details:</b><br>" +
                "First Name: " + testDataForAccountHolder.getFname() + "<br>" +
                "Last Name: " + testDataForAccountHolder.getLname() + "<br>" +
                "Email: " + testDataForAccountHolder.getEmail() + "<br>" +
                "Mobile Number: " + testDataForAccountHolder.getMobileNumber() + "<br>" +
                "Zipcode: " + testDataForAccountHolder.getZipCode() + "<br>" +
                "Provider Name: " + testDataForAccountHolder.getProviderName() + "<br><br>" +

                "<b>Entered Visit Details:</b><br>" +
                "Visit Type: Dermatology Visit<br>" +
                "Ward First Name: " + testDataForWard.getFname() + "<br>" +
                "Ward Last Name: " + testDataForWard.getLname() + "<br>" +
                "Ward Full Name: " + testDataForWard.getFullName() + "<br>" +
                "Address Line 1: " + testDataForWard.getStreetAddressOne() + "<br>" +
                "Address Line 2: " + testDataForWard.getStreetAddressTwo() + "<br>" +
                "Date of Birth: " + testDataForWard.getDobForMajor() + "<br>" +
                "Height: " + testDataForWard.getFeet() + " feet " + testDataForWard.getInch() + " inches<br>" +
                "Weight: " + testDataForWard.getWeight() + "<br>" +
                "Gender: Male<br>" +
                "Dermatology Concern: " + testDataForWard.getConcern() + "<br>" +
                "Affected Body Parts: " + testDataForWard.getBodyParts() + "<br>" +
                "Current Status: " + testDataForWard.getStatus() + "<br>" +
                "Condition Duration: " + testDataForWard.getSufferingConditionDays() + " " + testDataForWard.getSelectDays() + "<br>" +
                "Severity Level: " + testDataForWard.getSeverity() + "<br>" +
                "Selected Symptoms: " + testDataForWard.getSymptomOne() + "<br>" +
                "What Makes It Worse: test worse<br>" +
                "What Makes It Better: test better<br>" +
                "Lifestyle Factors: Stress<br>" +
                "Allergies: " + testDataForWard.getAllergyOne() + " (Reaction: " + testDataForWard.getAllergyReactionOne() + ", Category: " + testDataForWard.getDrugAllergyCategory() + ")<br>" +
                "Current Medications: " + testDataForWard.getMedicationOne() + " (Reason: test, Form: Oil, Frequency: PRN, Duration: PRN)<br>" +
                "Current Skin Care Products: test<br>" +
                "Additional Information: test optional...";
        ExtentReportManager.getTest().info(accountAndVisitDetailsHtml);
    }

}
