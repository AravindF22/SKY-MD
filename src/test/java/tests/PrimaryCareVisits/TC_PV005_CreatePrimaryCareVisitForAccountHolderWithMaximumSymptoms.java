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

public class TC_PV005_CreatePrimaryCareVisitForAccountHolderWithMaximumSymptoms extends BaseTest {
    public PatientPortalLoginPage patientPortalLoginPage;
    public PatientPortalHomePage patientPortalHomePage;
    public PrimaryCareVisitPage primaryCareVisitPage;
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
        //Staying in Self pay
        Assert.assertTrue(primaryCareVisitPage.clickProceedByBookingButton(), "Failed to click Proceed By Booking button");
        Thread.sleep(2000);
        //SELECT Condition
        Assert.assertTrue(primaryCareVisitPage.clickSelectFirstCondition(testDataForAccountHolder.getCondition()),
                "Failed to select Allergies condition");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next button after selecting condition");
        //Select First the Available Day and First slot
        Assert.assertTrue(primaryCareVisitPage.selectFirstAvailableDay(), "Failed to select first available day");
        Assert.assertTrue(primaryCareVisitPage.selectFirstAvailableTimeSlot(), "Failed to select first available time slot");
        selectedProviderName = primaryCareVisitPage.getSelectedProviderName();
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to click Next button after selecting time slot");

        softAssert.assertEquals(primaryCareVisitPage.getFirstName(), testDataForAccountHolder.getFname(),
                "First name is mismatching in demographics");
        softAssert.assertEquals(primaryCareVisitPage.getLastName(), testDataForAccountHolder.getLname(),
                "Last name is mismatching in demographics");
        Assert.assertTrue(primaryCareVisitPage.setDOB(testDataForAccountHolder.getDobForMajorInMMDD()),
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

        Assert.assertTrue(primaryCareVisitPage.setGeneralSymptoms(testDataForAccountHolder.getGeneralSymptom()), "Failed to set general symptoms");
        Assert.assertTrue(primaryCareVisitPage.setGeneralSymptoms(testDataForAccountHolder.getGeneralSymptom()), "Failed to set general symptoms");
        Assert.assertTrue(primaryCareVisitPage.setSkinSymptoms(testDataForAccountHolder.getSkinSymptom()), "Failed to set skin symptoms");
        Assert.assertTrue(primaryCareVisitPage.setHeadSymptoms(testDataForAccountHolder.getHeadSymptom()), "Failed to set head symptoms");
        Assert.assertTrue(primaryCareVisitPage.setEarSymptoms(testDataForAccountHolder.getEarSymptom()), "Failed to set ear symptoms");
        Assert.assertTrue(primaryCareVisitPage.setEyeSymptoms(testDataForAccountHolder.getEyeSymptom()), "Failed to set eye symptoms");
        Assert.assertTrue(primaryCareVisitPage.setVisionSymptoms(testDataForAccountHolder.getVisionSymptom()), "Failed to set vision symptoms");
        Assert.assertTrue(primaryCareVisitPage.setNoseSymptoms(testDataForAccountHolder.getNoseSymptom()), "Failed to set nose symptoms");
        Assert.assertTrue(primaryCareVisitPage.setMouthThroatSymptoms(testDataForAccountHolder.getMouthThroatSymptom()), "Failed to set mouth/throat symptoms");
        Assert.assertTrue(primaryCareVisitPage.setNeckSymptoms(testDataForAccountHolder.getNeckSymptom()), "Failed to set neck symptoms");
        Assert.assertTrue(primaryCareVisitPage.setBreastSymptoms(testDataForAccountHolder.getBreastSymptom()), "Failed to set breast symptoms");
        Assert.assertTrue(primaryCareVisitPage.setRespiratorySymptoms(testDataForAccountHolder.getRespiratorySymptom()), "Failed to set respiratory symptoms");
        Assert.assertTrue(primaryCareVisitPage.setCardiovascularSymptoms(testDataForAccountHolder.getCardiovascularSymptom()), "Failed to set cardiovascular symptoms");
        Assert.assertTrue(primaryCareVisitPage.setGastrointestinalSymptoms(testDataForAccountHolder.getGastrointestinalSymptom()), "Failed to set gastrointestinal symptoms");
        Assert.assertTrue(primaryCareVisitPage.setUrinarySymptoms(testDataForAccountHolder.getUrinarySymptom()), "Failed to set urinary symptoms");
        Assert.assertTrue(primaryCareVisitPage.setGenitalSymptoms(testDataForAccountHolder.getGenitalSymptom()), "Failed to set genital symptoms");
        Assert.assertTrue(primaryCareVisitPage.setFemaleSymptoms(testDataForAccountHolder.getFemaleSymptom()), "Failed to set female symptoms");
        Assert.assertTrue(primaryCareVisitPage.setVascularSymptoms(testDataForAccountHolder.getVascularSymptom()), "Failed to set vascular symptoms");
        Assert.assertTrue(primaryCareVisitPage.setMusculoskeletalSymptoms(testDataForAccountHolder.getMusculoskeletalSymptom()), "Failed to set musculoskeletal symptoms");
        Assert.assertTrue(primaryCareVisitPage.setNeurologicSymptoms(testDataForAccountHolder.getNeurologicSymptom()), "Failed to set neurologic symptoms");
        Assert.assertTrue(primaryCareVisitPage.setHematologicSymptoms(testDataForAccountHolder.getHematologicSymptom()), "Failed to set hematologic symptoms");
        Assert.assertTrue(primaryCareVisitPage.setEndocrineSymptoms(testDataForAccountHolder.getEndocrineSymptom()), "Failed to set endocrine symptoms");
        Assert.assertTrue(primaryCareVisitPage.setPsychiatricSymptoms(testDataForAccountHolder.getPsychiatricSymptom()), "Failed to set psychiatric symptoms");

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

        if(testDataForAccountHolder.getGender().equalsIgnoreCase("Female")){
            Assert.assertTrue(primaryCareVisitPage.setAdditionalMedicalConditionsForFemale("Pregnant"),
                    "Failed to set additional medical conditions for female");
            Assert.assertTrue(primaryCareVisitPage.clickNextButton(),
                    "Failed to move to next step after additional medical conditions for female");
        }
        Assert.assertTrue(primaryCareVisitPage.setOptionalDetails(testDataForAccountHolder.getAdditionalText()), "Failed to set optional details");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after optional details");

        Assert.assertTrue(primaryCareVisitPage.acceptAllCodeOfConduct(), "Failed to accept code of conduct");
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(), "Failed to move to next step after code of conduct");

        Assert.assertTrue(primaryCareVisitPage.paymentByCard(
                ConfigReader.getProperty("testCardNumber"),
                ConfigReader.getProperty("testCardExpiryDate"),
                ConfigReader.getProperty("testCardCVV")
        ), "Failed to process card payment");
        Thread.sleep(1000);
        Assert.assertTrue(primaryCareVisitPage.clickNextButton(),"Failed to move to next step after card payment");
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

                        "<b>Symptoms :</b><br>" +
                        "General Symptoms: " + testDataForAccountHolder.getGeneralSymptom() + "<br>" +
                        "Skin Symptoms: " + testDataForAccountHolder.getSkinSymptom() + "<br>" +
                        "Head Symptoms: " + testDataForAccountHolder.getHeadSymptom() + "<br>" +
                        "Ear Symptoms: " + testDataForAccountHolder.getEarSymptom() + "<br>" +
                        "Eye Symptoms: " + testDataForAccountHolder.getEyeSymptom() + "<br>" +
                        "Vision Symptoms: " + testDataForAccountHolder.getVisionSymptom() + "<br>" +
                        "Nose Symptoms: " + testDataForAccountHolder.getNoseSymptom() + "<br>" +
                        "Mouth/Throat Symptoms: " + testDataForAccountHolder.getMouthThroatSymptom() + "<br>" +
                        "Neck Symptoms: " + testDataForAccountHolder.getNeckSymptom() + "<br>" +
                        "Breast Symptoms: " + testDataForAccountHolder.getBreastSymptom() + "<br>" +
                        "Respiratory Symptoms: " + testDataForAccountHolder.getRespiratorySymptom() + "<br>" +
                        "Cardiovascular Symptoms: " + testDataForAccountHolder.getCardiovascularSymptom() + "<br>" +
                        "Gastrointestinal Symptoms: " + testDataForAccountHolder.getGastrointestinalSymptom() + "<br>" +
                        "Urinary Symptoms: " + testDataForAccountHolder.getUrinarySymptom() + "<br>" +
                        "Genital Symptoms: " + testDataForAccountHolder.getGenitalSymptom() + "<br>" +
                        "Female Symptoms: " + testDataForAccountHolder.getFemaleSymptom() + "<br>" +
                        "Vascular Symptoms: " + testDataForAccountHolder.getVascularSymptom() + "<br>" +
                        "Musculoskeletal Symptoms: " + testDataForAccountHolder.getMusculoskeletalSymptom() + "<br>" +
                        "Neurologic Symptoms: " + testDataForAccountHolder.getNeurologicSymptom() + "<br>" +
                        "Hematologic Symptoms: " + testDataForAccountHolder.getHematologicSymptom() + "<br>" +
                        "Endocrine Symptoms: " + testDataForAccountHolder.getEndocrineSymptom() + "<br>" +
                        "Psychiatric Symptoms: " + testDataForAccountHolder.getPsychiatricSymptom() + "<br>" +

                        "<b>Allergy :</b><br>" +
                        "Allergy: " + testDataForAccountHolder.getAllergyOne() + "<br>" +
                        "Reaction: " + testDataForAccountHolder.getAllergyReactionOne() + "<br>" +
                        "Category: " + testDataForAccountHolder.getDrugAllergyCategory() + "<br><br>" +

                        "<b>Medication Details:</b><br>" +
                        "Medication: " + testDataForAccountHolder.getMedicationOne() + "<br>" +
                        "Dosage: " + testDataForAccountHolder.getDosageOne() + "<br>" +
                        "Form: " + testDataForAccountHolder.getMedicationFormOne() + "<br>" +
                        "Frequency: " + testDataForAccountHolder.getMedicationFrequencyOne() + "<br>" +
                        "Per: " + testDataForAccountHolder.getMedicationPerOne() + "<br><br>" +

                        "<b>Other Information:</b><br>" +
                        additionMedicalConditions +
                        "Additional Details: " + testDataForAccountHolder.getAdditionalText();

        ExtentReportManager.getTest().info(visitDetailsHtml);
    }
    @Test(priority = 4, dependsOnMethods = {"testPrimaryCareVisitValidation"} )
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
