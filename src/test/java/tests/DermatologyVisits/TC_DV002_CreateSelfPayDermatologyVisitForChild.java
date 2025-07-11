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

public class TC_DV002_CreateSelfPayDermatologyVisitForChild extends BaseTest {
    public PatientPortalLoginPage patientPortalLoginPage;
    public PatientPortalHomePage patientPortalHomePage;
    public DermatologyVisitPage dermatologyVisitPage;
    public TestData testDataForAccountHolder;
    public TestData testDataForChild;
    public SignInPage signInPage;
    public SoftAssert softAssert;
    public WebDriverWait wait;

    @BeforeClass
    public void setUp() throws IOException {
        driver.get(ConfigReader.getProperty("PatientPortalLoginUrl"));
        testDataForAccountHolder = new TestData();
        testDataForChild = new TestData();
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
        Thread.sleep(1000);
        patientPortalHomePage.selectDermatologyVisit();
        dermatologyVisitPage.selectDermProviderSection();
        dermatologyVisitPage.clickContinueAfterSelectingDermProvider();
        Thread.sleep(3000);
        dermatologyVisitPage.proceedBasisOfProvider("Dr. Janice Fahey");
        Thread.sleep(1000);
        dermatologyVisitPage.clickSelectPatient();
        //child
        dermatologyVisitPage.selectPatientAsMyChild();
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();
        dermatologyVisitPage.clickAddChildButton();
        dermatologyVisitPage.enterChildFirstName(testDataForChild.getFname());
        dermatologyVisitPage.enterChildLastName(testDataForChild.getLname());
        dermatologyVisitPage.clickSaveChildButton();
        softAssert.assertEquals(dermatologyVisitPage.getFirstChildName(), testDataForChild.getFullName(),
                "First child name mismatch in dermatology visit");
        dermatologyVisitPage.clickContinueButtonAfterInsurance();
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();
        dermatologyVisitPage.clickContinueButtonAfterInsurance();
        ExtentReportManager.getTest().log(Status.INFO, "Basic details completed");
        softAssert.assertAll();
    }

    @Test(priority = 3, dependsOnMethods = "testBasicDetails")
    public void testVisitDetails() {
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Details");
        Assert.assertTrue(dermatologyVisitPage.setAddressLineOne(testDataForChild.getStreetAddressOne()), "Failed to set address line one");
        dermatologyVisitPage.setAddressLineTwo(testDataForChild.getStreetAddressTwo());
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button");
        Assert.assertTrue(dermatologyVisitPage.setDOB(testDataForChild.getDobForMinor()), "Failed to set date of birth");
        Assert.assertTrue(dermatologyVisitPage.setFeet(testDataForChild.getFeet()), "Failed to set height in feet");
        Assert.assertTrue(dermatologyVisitPage.setInches(testDataForChild.getInch()), "Failed to set height in inches");
        Assert.assertTrue(dermatologyVisitPage.setWeight(testDataForChild.getWeight()), "Failed to set weight");
        Assert.assertTrue(dermatologyVisitPage.setGender("Male"), "Failed to set gender");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after setting visit details");
        Assert.assertTrue(dermatologyVisitPage.uploadId(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))), "Failed to upload ID photo");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after uploading ID photo"); 
        ExtentReportManager.getTest().log(Status.INFO, "Visit details completed");
    }

    @Test(priority = 4, dependsOnMethods = "testVisitDetails")
    public void testVisitPhotos() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Photos");

        Assert.assertTrue(dermatologyVisitPage.setDermatologyConcern(testDataForChild.getConcern()), "Failed to set dermatology concern");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after setting concern");
        Assert.assertTrue(dermatologyVisitPage.selectAffectedBodyPart(testDataForChild.getBodyParts()), "Failed to select affected body part");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after selecting body part");
        Assert.assertTrue(dermatologyVisitPage.selectStatus(testDataForChild.getStatus()), "Failed to select status");
        Assert.assertTrue(dermatologyVisitPage.selectCount(testDataForChild.getSufferingConditionDays()), "Failed to select condition duration count");
        Assert.assertTrue(dermatologyVisitPage.selectDay(testDataForChild.getSelectDays()), "Failed to select days");
        Assert.assertTrue(dermatologyVisitPage.selectSeverity(testDataForChild.getSeverity()), "Failed to select severity");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after selecting status details");
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
        Assert.assertTrue(dermatologyVisitPage.selectSymptoms(testDataForChild.getSymptomOne()), "Failed to select symptoms");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after selecting symptoms");
        Assert.assertTrue(dermatologyVisitPage.setWorseText(testDataForChild.getWhatMakesWorse()), "Failed to set what makes condition worse");
        Assert.assertTrue(dermatologyVisitPage.setBetterText(testDataForChild.getWhatMakesBetter()), "Failed to set what makes condition better");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after setting condition details");
        Assert.assertTrue(dermatologyVisitPage.selectLiftStyle(testDataForChild.getLifeStyleItem()), "Failed to select lifestyle item");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after selecting lifestyle");
        ExtentReportManager.getTest().log(Status.INFO, "Visit symptoms completed");
    }

    @Test(priority = 6, dependsOnMethods = "testVisitSymptoms")
    public void testVisitMedicalHistory() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Medical History");
        Assert.assertTrue(dermatologyVisitPage.addAllergy(
                testDataForChild.getAllergyOne(),
                testDataForChild.getAllergyReactionOne(),
                testDataForChild.getDrugAllergyCategory()), "Failed to add allergy details");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after adding allergy");
        Thread.sleep(3000);
        Assert.assertTrue(dermatologyVisitPage.clickYesButtonInMedication(), "Failed to click Yes button in medication section");
        Thread.sleep(1000);
        Assert.assertTrue(dermatologyVisitPage.clickSearchMedicationButton(), "Failed to click search medication button");
        Assert.assertTrue(dermatologyVisitPage.addMedication(
                testDataForChild.getMedicationOne(),
                testDataForChild.getDosageOne(),
                testDataForChild.getMedicationFormOne(),
                testDataForChild.getMedicationFrequencyOne(),
                testDataForChild.getMedicationPerOne()), "Failed to add medication details");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after adding medication");
        dermatologyVisitPage.clickYesBtnInSkinCareProduct();
        dermatologyVisitPage.enterSkinCareProduct("test");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button");
        ExtentReportManager.getTest().log(Status.INFO, "Visit medical history completed");
    }

    @Test(priority = 7, dependsOnMethods = "testVisitMedicalHistory")
    public void testVisitPayment() {
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Payment");
        Assert.assertTrue(dermatologyVisitPage.clickAddPharmacyAndSwitchToListView(), "Failed to click add pharmacy and switch to list view");
        Assert.assertTrue(dermatologyVisitPage.clickFirstPharmacy(), "Failed to select first pharmacy");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after selecting pharmacy");
        dermatologyVisitPage.setOptionalField(testDataForChild.getOptionalFieldText());
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after setting optional field");
        Assert.assertTrue(dermatologyVisitPage.clickNextButtonForTAndC(), "Failed to click next button for Terms and Conditions");
        Assert.assertTrue(dermatologyVisitPage.clickAllAcceptTerms(), "Failed to accept all terms");
        Assert.assertTrue(dermatologyVisitPage.paymentByCard(
                ConfigReader.getProperty("testCardNumber"),
                ConfigReader.getProperty("testCardExpiryDate"),
                ConfigReader.getProperty("testCardCVV")), "Failed to complete payment by card");
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
        Assert.assertTrue(dermatologyVisitPage.clickSubmitForEvaluation(), "Failed to click Submit for Evaluation button");
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
        String expectedProvider = testDataForChild.getProviderName();
        String actualProvider = dermatologyVisitPage.getProviderNameInVisitSubmittedPage();
        try {
            softAssert.assertEquals(actualProvider, expectedProvider, "Provider name mismatch in submitted visit");
            ExtentReportManager.getTest().log(Status.PASS, "Provider name verification successful: " +
                    "Expected [" + expectedProvider + "] matches Actual [" + actualProvider + "]");
        } catch (AssertionError e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Provider name verification failed: " +
                    "Expected [" + expectedProvider + "] but found [" + actualProvider + "]");
        }
        Assert.assertTrue(dermatologyVisitPage.clickGoToMyVisitsButton(), "Failed to click on Go To My Visits button");
        try {
            softAssert.assertAll();
            ExtentReportManager.getTest().log(Status.PASS, "All visit validations passed successfully");
        } catch (AssertionError e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Visit validation failed with the following errors: \n" + e.getMessage());
        }
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
                        "Address Line 1: " + testDataForChild.getStreetAddressOne() + "<br>" +
                        "Address Line 2: " + testDataForChild.getStreetAddressTwo() + "<br>" +
                        "Date of Birth: " + testDataForChild.getDobForMinor() + "<br>" +
                        "Height: " + testDataForChild.getFeet() + " feet " + testDataForChild.getInch() + " inches<br>" +
                        "Weight: " + testDataForChild.getWeight() + "<br>" +
                        "Gender: Male<br>" +

                        "<b>Visit Details:</b><br>" +
                        "Dermatology Concern: " + testDataForChild.getConcern() + "<br>" +
                        "Affected Body Parts: " + testDataForChild.getBodyParts() + "<br>" +
                        "Current Status: " + testDataForChild.getStatus() + "<br>" +
                        "Condition Duration: " + testDataForChild.getSufferingConditionDays() + " " + testDataForChild.getSelectDays() + "<br>" +
                        "Severity Level: " + testDataForChild.getSeverity() + "<br>" +

                        "<b>Symptoms:</b><br>" +
                        "Selected Symptoms: " + testDataForChild.getSymptomOne() + "<br>" +
                        "What Makes It Worse: " + testDataForChild.getWhatMakesWorse() + "<br>" +
                        "What Makes It Better: " + testDataForChild.getWhatMakesBetter() + "<br>" +

                        "<b>Medical History:</b><br>" +
                        "Lifestyle Factors: " + testDataForChild.getLifeStyleItem() + "<br>" +
                        "Allergies: " + testDataForChild.getAllergyOne() + " (Reaction: " +
                        testDataForChild.getAllergyReactionOne() + ", Category: " +
                        testDataForChild.getDrugAllergyCategory() + ")<br>" +
                        "Current Medications: " + testDataForAccountHolder.getMedicationOne() + "<br>" +
                        "(Dosage: " + testDataForAccountHolder.getDosageOne() + ", " +
                        "Form: " + testDataForAccountHolder.getMedicationFormOne() + ", " +
                        "Frequency: " + testDataForAccountHolder.getMedicationFrequencyOne() + ", " +
                        "Per: " + testDataForAccountHolder.getMedicationPerOne() + ")<br>" +
                        "Current Skin Care Products: test<br>" +
                        "Additional Information: " + testDataForChild.getOptionalFieldText();

        ExtentReportManager.getTest().info(accountAndVisitDetailsHtml);
    }
}
