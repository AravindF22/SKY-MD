package tests.DermatologyVisits;

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

public class TC_DV005_CreateDermatologyVisitForAccountHolderWithFourConditionPhotos  extends BaseTest{
    public PatientPortalLoginPage patientPortalLoginPage;
    public PatientPortalHomePage patientPortalHomePage;
    public DermatologyVisitPage dermatologyVisitPage;
    public TestData testDataForAccountHolder;
    public SignInPage signInPage;
    public SoftAssert softAssert;
    public WebDriverWait wait;
    private MyVisitsPage myVisitsPage;
    public String visitId = null;

    @BeforeClass
    public void setUp() throws IOException {
        driver.get(ConfigReader.getProperty("PatientPortalLoginUrl"));
        testDataForAccountHolder = new TestData();
    }
    @BeforeMethod
    public void initializeAsset(){
        softAssert = new SoftAssert();
        signInPage = new SignInPage(driver);
        patientPortalLoginPage = new PatientPortalLoginPage(driver);
        patientPortalHomePage = new PatientPortalHomePage(driver);
        dermatologyVisitPage = new DermatologyVisitPage(driver);
        myVisitsPage = new MyVisitsPage(driver);
    }
    @Test(priority = 1)
    public void testSignInAsNewValidPatient()  {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Sign in as new valid patient");
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
    @Test(priority = 2, dependsOnMethods = "testSignInAsNewValidPatient")
    public void testBasicDetails() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Basic Details");
        Thread.sleep(2000);
        patientPortalHomePage.selectDermatologyVisit();
        dermatologyVisitPage.selectDermProviderSection();
        dermatologyVisitPage.clickContinueAfterSelectingDermProvider();
        Thread.sleep(3000);
        dermatologyVisitPage.proceedBasisOfProvider("Dr. Janice Fahey");
        Thread.sleep(1000);
        dermatologyVisitPage.clickSelectPatient();
        dermatologyVisitPage.clickSelectPatientAsMySelf();
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();
        dermatologyVisitPage.clickContinueButtonAfterInsurance();
        ExtentReportManager.getTest().log(Status.INFO, "Basic details completed");
    }

    @Test(priority = 3, dependsOnMethods = "testBasicDetails")
    public void testVisitDetails() {
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Details");
        Assert.assertTrue(dermatologyVisitPage.setAddressLineOne(testDataForAccountHolder.getStreetAddressOne()));
        dermatologyVisitPage.setAddressLineTwo(testDataForAccountHolder.getStreetAddressTwo());
        dermatologyVisitPage.clickContinueButton();
        Assert.assertTrue(dermatologyVisitPage.setDOB(testDataForAccountHolder.getDobForMajor()), "Failed to set DOB");
        Assert.assertTrue(dermatologyVisitPage.setFeet(testDataForAccountHolder.getFeet()), "Failed to set Feet");
        Assert.assertTrue(dermatologyVisitPage.setInches(testDataForAccountHolder.getInch()), "Failed to set Inches");
        Assert.assertTrue(dermatologyVisitPage.setWeight(testDataForAccountHolder.getWeight()), "Failed to set Weight");
        Assert.assertTrue(dermatologyVisitPage.setGender("Male"), "Failed to set Gender");
        dermatologyVisitPage.clickContinueButton();

        Assert.assertTrue(dermatologyVisitPage.uploadId(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))), "Failed to upload ID photo");
        dermatologyVisitPage.clickContinueButton();
        ExtentReportManager.getTest().log(Status.INFO, "Visit details completed");
    }

    @Test(priority = 4, dependsOnMethods = "testVisitDetails")
    public void testVisitPhotos() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Photos");

        Assert.assertTrue(dermatologyVisitPage.setDermatologyConcern(testDataForAccountHolder.getConcern()), "Failed to set dermatology concern");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after setting dermatology concern");
        Assert.assertTrue(dermatologyVisitPage.selectAffectedBodyPart(testDataForAccountHolder.getBodyParts()), "Failed to select affected body part");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after selecting affected body part");
        Assert.assertTrue(dermatologyVisitPage.selectStatus(testDataForAccountHolder.getStatus()), "Failed to select status");
        Assert.assertTrue(dermatologyVisitPage.selectCount(testDataForAccountHolder.getSufferingConditionDays()), "Failed to select suffering condition days");
        Assert.assertTrue(dermatologyVisitPage.selectDay(testDataForAccountHolder.getSelectDays()), "Failed to select days");
        Assert.assertTrue(dermatologyVisitPage.selectSeverity(testDataForAccountHolder.getSeverity()), "Failed to select severity");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after providing condition details");
        Assert.assertTrue(dermatologyVisitPage.clickNoBtnForSmartPhoneUpload(), "Failed to click no button for smartphone upload");
        Thread.sleep(500);
        Assert.assertTrue(dermatologyVisitPage.uploadCloseUpPic(convertToAbsoluteURL(ConfigReader.getProperty("conditionPhotoOnePath"))), "Failed to upload close up photo");
        Thread.sleep(500);
        Assert.assertTrue(dermatologyVisitPage.uploadFarAwayPic(convertToAbsoluteURL(ConfigReader.getProperty("conditionPhotoTwoPath"))), "Failed to upload far away photo");
        Thread.sleep(500);
        Assert.assertTrue(dermatologyVisitPage.clickAddMorePhotosBtn(), "Failed to click add more photos button");
        Assert.assertTrue(dermatologyVisitPage.uploadCloseUpPic2(convertToAbsoluteURL(ConfigReader.getProperty("conditionPhotoThreePath"))), "Failed to upload close up photo 2");
        Thread.sleep(500);
        Assert.assertTrue(dermatologyVisitPage.uploadFarAwayPic2(convertToAbsoluteURL(ConfigReader.getProperty("conditionPhotoFourPath"))), "Failed to upload far away photo 2");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after uploading photos");
        Thread.sleep(5000);
        ExtentReportManager.getTest().log(Status.INFO, "Visit photos completed");
    }

    @Test(priority = 5, dependsOnMethods = "testVisitPhotos")
    public void testVisitSymptoms() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Symptoms");
        Thread.sleep(1000);
        Assert.assertTrue(dermatologyVisitPage.selectSymptoms(testDataForAccountHolder.getSymptomOne()), "Failed to select symptoms");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after selecting symptoms");
        Assert.assertTrue(dermatologyVisitPage.setWorseText(testDataForAccountHolder.getWhatMakesWorse()), "Failed to set what makes worse text");
        Assert.assertTrue(dermatologyVisitPage.setBetterText(testDataForAccountHolder.getWhatMakesBetter()), "Failed to set what makes better text");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after setting texts");
        Assert.assertTrue(dermatologyVisitPage.selectLiftStyle(testDataForAccountHolder.getLifeStyleItem()), "Failed to select lifestyle");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after selecting lifestyle");
        ExtentReportManager.getTest().log(Status.INFO, "Visit symptoms completed");
    }

    @Test(priority = 6, dependsOnMethods = "testVisitSymptoms")
    public void testVisitMedicalHistory() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Medical History");
        Assert.assertTrue(dermatologyVisitPage.addAllergy(
                testDataForAccountHolder.getAllergyOne(),
                testDataForAccountHolder.getAllergyReactionOne(),
                testDataForAccountHolder.getDrugAllergyCategory()), "Failed to add allergy details");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after adding allergy");
        Thread.sleep(3000);
        Assert.assertTrue(dermatologyVisitPage.clickYesButtonInMedication(), "Failed to click Yes button in medication");
        Thread.sleep(1000);
        Assert.assertTrue(dermatologyVisitPage.clickSearchMedicationButton(), "Failed to click search medication button");
        Assert.assertTrue(dermatologyVisitPage.addMedication(
                testDataForAccountHolder.getMedicationOne(),
                testDataForAccountHolder.getDosageOne(),
                testDataForAccountHolder.getMedicationFormOne(),
                testDataForAccountHolder.getMedicationFrequencyOne(),
                testDataForAccountHolder.getMedicationPerOne()), "Failed to add medication details");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after adding medication");
        dermatologyVisitPage.clickYesBtnInSkinCareProduct();
        dermatologyVisitPage.enterSkinCareProduct("test");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after providing medical history");
        ExtentReportManager.getTest().log(Status.INFO, "Visit medical history completed");
    }

    @Test(priority = 7, dependsOnMethods = "testVisitMedicalHistory")
    public void testVisitPayment() {
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Payment");
        Assert.assertTrue(dermatologyVisitPage.clickAddPharmacyAndSwitchToListView(), "Failed to click Add Pharmacy and switch to list view");
        Assert.assertTrue(dermatologyVisitPage.clickFirstPharmacy(), "Failed to click first pharmacy");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button");
        dermatologyVisitPage.setOptionalField(testDataForAccountHolder.getOptionalFieldText());
        dermatologyVisitPage.clickContinueButton();
        Assert.assertTrue(dermatologyVisitPage.clickNextButtonForTAndC(), "Failed to click next button for Terms and Conditions");
        Assert.assertTrue(dermatologyVisitPage.clickAllAcceptTerms(), "Failed to accept all terms");
        Assert.assertTrue(dermatologyVisitPage.paymentByCard(
                ConfigReader.getProperty("testCardNumber"),
                ConfigReader.getProperty("testCardExpiryDate"),
                ConfigReader.getProperty("testCardCVV")), "Failed to process card payment");
        try {
            boolean isEnabled = dermatologyVisitPage.isSubmitForEvaluationEnabled();

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
        Assert.assertTrue(dermatologyVisitPage.clickSubmitForEvaluation(), "Failed to click submit for evaluation button");
        visitId =  myVisitsPage.getVisitIdOfCreatedVisit();
        Assert.assertTrue(myVisitsPage.clickFirstVisitSummary(visitId),"Failed to click first visit summary");
        Assert.assertTrue(myVisitsPage.clickPhotosInVisitSummary(visitId),"Failed to click photos in visit summary");
        softAssert.assertEquals(myVisitsPage.getVisitPhotosCount(),4,"Photo count mismatch in visit summary");

        ExtentReportManager.getTest().log(Status.INFO, "Visit payment completed");
        softAssert.assertAll();
    }

    @Test(priority = 8, dependsOnMethods = "testVisitPayment")
    public void testVisitValidationInPatientPortal() {
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Validation in Patient Portal");
        // Visit submission verification
        boolean isVisitSubmitted = dermatologyVisitPage.isVisitSubmitted();
        try {
            softAssert.assertTrue(isVisitSubmitted, "Visit submission verification failed");
            ExtentReportManager.getTest().log(Status.PASS, "Visit submission verification successful: Visit was submitted successfully");
        } catch (AssertionError e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Visit submission verification failed: Visit was not submitted as expected");
        }

        // Provider name verification
        String expectedProvider = testDataForAccountHolder.getProviderName();
        String actualProvider = dermatologyVisitPage.getProviderNameInVisitSubmittedPage();
        try {
            softAssert.assertEquals(actualProvider, expectedProvider, "Provider name mismatch in submitted visit");
            ExtentReportManager.getTest().log(Status.PASS, "Provider name verification successful: " +
                    "Expected [" + expectedProvider + "] matches Actual [" + actualProvider + "]");
        } catch (AssertionError e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Provider name verification failed: " +
                    "Expected [" + expectedProvider + "] but found [" + actualProvider + "]");
        }

        dermatologyVisitPage.clickGoToMyVisitsButton();
        softAssert.assertAll();
    }
    @Test(priority = 9, description = "Print Account Holder and Visit Details in Extent Report")
    public void printVisitDetailsInReport() {
        String accountAndVisitDetailsHtml =
                "<b>Entered Account Holder Details:</b><br>" +
                        "First Name: " + testDataForAccountHolder.getFname() + "<br>" +
                        "Last Name: " + testDataForAccountHolder.getLname() + "<br>" +
                        "Email: " + testDataForAccountHolder.getEmail() + "<br>" +
                        "Mobile Number: " + testDataForAccountHolder.getMobileNumber() + "<br>" +
                        "Zipcode: " + testDataForAccountHolder.getZipCode() + "<br>" +
                        "Provider Name: " + testDataForAccountHolder.getProviderName() + "<br><br>" +

                        "<b>Visit Type: Dermatology Visit</b><br>" +
                        "<b>Basic Details:</b><br>" +
                        "Address Line 1: " + testDataForAccountHolder.getStreetAddressOne() + "<br>" +
                        "Address Line 2: " + testDataForAccountHolder.getStreetAddressTwo() + "<br>" +
                        "Date of Birth: " + testDataForAccountHolder.getDobForMajor() + "<br>" +
                        "Height: " + testDataForAccountHolder.getFeet() + " feet " + testDataForAccountHolder.getInch() + " inches<br>" +
                        "Weight: " + testDataForAccountHolder.getWeight() + "<br>" +
                        "Gender: Male<br>" +
                        "<b>Visit Details:</b><br>" +
                        "Dermatology Concern: " + testDataForAccountHolder.getConcern() + "<br>" +
                        "Affected Body Parts: " + testDataForAccountHolder.getBodyParts() + "<br>" +
                        "Current Status: " + testDataForAccountHolder.getStatus() + "<br>" +
                        "Condition Duration: " + testDataForAccountHolder.getSufferingConditionDays() + " " + testDataForAccountHolder.getSelectDays() + "<br>" +
                        "Severity Level: " + testDataForAccountHolder.getSeverity() + "<br>" +
                        "<b>Symptoms:</b><br>" +
                        "Selected Symptoms: " + testDataForAccountHolder.getSymptomOne() + "<br>" +
                        "What Makes It Worse: " + testDataForAccountHolder.getWhatMakesWorse() + "<br>" +
                        "What Makes It Better: " + testDataForAccountHolder.getWhatMakesBetter() + "<br>" +
                        "<b>Medical History:</b><br>" +
                        "Lifestyle Factors: " + testDataForAccountHolder.getLifeStyleItem() + "<br>" +
                        "Allergies: " + testDataForAccountHolder.getAllergyOne() + " (Reaction: " +
                        testDataForAccountHolder.getAllergyReactionOne() + ", Category: " +
                        testDataForAccountHolder.getDrugAllergyCategory() + ")<br>" +
                        "Current Medications: " + testDataForAccountHolder.getMedicationOne() +"<br>"+
                        "(Dosage: " + testDataForAccountHolder.getDosageOne() + ", " +
                        "Form: " + testDataForAccountHolder.getMedicationFormOne() + ", " +
                        "Frequency: " + testDataForAccountHolder.getMedicationFrequencyOne() + ", " +
                        "Per: " + testDataForAccountHolder.getMedicationPerOne() +")<br>"+
                        "Current Skin Care Products: test<br>" +
                        "Additional Information: " + testDataForAccountHolder.getOptionalFieldText();
        ExtentReportManager.getTest().info(accountAndVisitDetailsHtml);
    }
}
