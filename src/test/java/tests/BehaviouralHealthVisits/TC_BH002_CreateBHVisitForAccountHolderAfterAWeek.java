package tests.BehaviouralHealthVisits;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.PatientPortal.BehaviouralHealthVisitPage;
import pages.PatientPortal.PatientPortalHomePage;
import pages.PatientPortal.PatientPortalLoginPage;
import pages.PatientPortal.SignInPage;
import utils.ConfigReader;
import utils.ExtentReportManager;
import utils.TestData;

import java.io.IOException;
import java.time.Duration;

public class TC_BH002_CreateBHVisitForAccountHolderAfterAWeek  extends BaseTest{
    public PatientPortalLoginPage patientPortalLoginPage;
    public PatientPortalHomePage patientPortalHomePage;
    public TestData testDataForAccountHolder;
    public BehaviouralHealthVisitPage behaviouralHealthVisitPage;
    public SignInPage signInPage;
    public SoftAssert softAssert;
    public WebDriverWait wait;
    public int score = 0;

    @BeforeClass
    public void setUp() throws IOException {
        driver.get(ConfigReader.getProperty("PatientPortalLoginUrlBhBranch"));
        testDataForAccountHolder = new TestData();
    }
    @BeforeMethod
    public void initializeAsset(){
        softAssert = new SoftAssert();
        signInPage = new SignInPage(driver);
        patientPortalLoginPage = new PatientPortalLoginPage(driver);
        patientPortalHomePage = new PatientPortalHomePage(driver);
        behaviouralHealthVisitPage = new BehaviouralHealthVisitPage(driver);
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
    public void testVisitCreation() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Basic Details");
        Thread.sleep(1000);
        Assert.assertTrue(patientPortalHomePage.selectBehaviouralHealthVisit(), "Failed to select Behavioural Health Visit");
        Assert.assertTrue(behaviouralHealthVisitPage.clickPatientAsMySelf(), "Failed to select patient as myself");
        Assert.assertTrue(behaviouralHealthVisitPage.clickNextButton(), "Failed to click next button");
        Assert.assertTrue(behaviouralHealthVisitPage.clickProceedWithBookingButton(), "Failed to proceed with booking");
        Thread.sleep(1000);
        //behaviouralHealthVisitPage.clickIndividualButton();
        Assert.assertTrue(behaviouralHealthVisitPage.clickNextButton(), "Failed to click next button");
        Assert.assertTrue(behaviouralHealthVisitPage.selectSeventhDay(), "Failed to select first available day");
        Assert.assertTrue(behaviouralHealthVisitPage.selectFirstAvailableTimeSlot(), "Failed to select first available time slot");
        Assert.assertTrue(behaviouralHealthVisitPage.clickNextButton(), "Failed to click next button");

        softAssert.assertEquals(behaviouralHealthVisitPage.getFirstName(), testDataForAccountHolder.getFname(),
                "First name is mismatching in demographics");
        softAssert.assertEquals(behaviouralHealthVisitPage.getLastName(), testDataForAccountHolder.getLname(),
                "Last name is mismatching in demographics");
        Assert.assertTrue(behaviouralHealthVisitPage.setDOB(testDataForAccountHolder.getDobForMajor()),
                "Failed to enter date of birth in demographics");
        Assert.assertTrue(behaviouralHealthVisitPage.setAddressLineOne(testDataForAccountHolder.getStreetAddressOne()),
                "Failed to enter address line one in demographics");
        Assert.assertTrue(behaviouralHealthVisitPage.setAddressLineTwo(testDataForAccountHolder.getStreetAddressTwo()),
                "Failed to enter address line two in demographics");

        Assert.assertTrue(behaviouralHealthVisitPage.setHeightFeet(testDataForAccountHolder.getFeet()),
                "Failed to enter feet in demographics");
        Assert.assertTrue(behaviouralHealthVisitPage.setHeightInches(testDataForAccountHolder.getInch()),
                "Failed to enter inches in demographics");
        Assert.assertTrue(behaviouralHealthVisitPage.setWeight(testDataForAccountHolder.getWeight()),
                "Failed to enter weight in demographics");
        Assert.assertTrue(behaviouralHealthVisitPage.selectGender(testDataForAccountHolder.getGender()),
                "Failed to select gender in demographics");
        softAssert.assertEquals(behaviouralHealthVisitPage.getZipcode(), testDataForAccountHolder.getZipCode(),"" +
                "Zipcode is mismatching in demographics");
        Assert.assertTrue(behaviouralHealthVisitPage.clickNextButton(), "Failed to click next button after completing previous section");

        Assert.assertTrue(behaviouralHealthVisitPage.uploadId(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))), "Failed to upload ID photo");
        Thread.sleep(3000);
        Assert.assertTrue(behaviouralHealthVisitPage.clickNextButton(), "Failed to click next button after uploading ID");

        Assert.assertTrue(behaviouralHealthVisitPage.setTherapyReason(testDataForAccountHolder.getTherapyReasons()), "Failed to set therapy reasons");
        Assert.assertTrue(behaviouralHealthVisitPage.clickNextButton(), "Failed to click next button after setting therapy reasons");

        Assert.assertTrue(behaviouralHealthVisitPage.setSymptoms(testDataForAccountHolder.getSymptoms()), "Failed to set symptoms");
        Assert.assertTrue(behaviouralHealthVisitPage.clickNextButton(), "Failed to click next button after setting symptoms");

        Assert.assertTrue(behaviouralHealthVisitPage.setExerciseRoutine(testDataForAccountHolder.getExerciseStatus()), "Failed to set exercise routine");
        Assert.assertTrue(behaviouralHealthVisitPage.setAlcoholConsumption(testDataForAccountHolder.getAlcoholStatus()), "Failed to set alcohol consumption");
        Assert.assertTrue(behaviouralHealthVisitPage.setSmokingStatus("Yes"), "Failed to set smoking status");
        Assert.assertTrue(behaviouralHealthVisitPage.setSmokingItems(testDataForAccountHolder.getSmokingItem()), "Failed to set smoking items");
        Assert.assertTrue(behaviouralHealthVisitPage.clickAddMedicalCondition(), "Failed to click add medical condition button");
        Assert.assertTrue(behaviouralHealthVisitPage.searchMedicalCondition(testDataForAccountHolder.getMedicalCondition()), "Failed to search medical condition");
        Assert.assertTrue(behaviouralHealthVisitPage.clickDoneBtnAfterAddingMedicalCondition(), "Failed to click done button after adding medical condition");
        Assert.assertTrue(behaviouralHealthVisitPage.clickNextButton(), "Failed to click next button after setting health information");

        Assert.assertTrue(behaviouralHealthVisitPage.clickSearchMedicationButton(), "Failed to click search medication button");
        //medication One
        Assert.assertTrue(behaviouralHealthVisitPage.addMedicationOne(
                testDataForAccountHolder.getMedicationOne(),
                testDataForAccountHolder.getDosageOne(),
                testDataForAccountHolder.getMedicationFormOne(),
                testDataForAccountHolder.getMedicationFrequencyOne(),
                testDataForAccountHolder.getMedicationPerOne()), "Failed to add medication one");
        Assert.assertTrue(behaviouralHealthVisitPage.clickDoneBtnAfterAddingMedication(), "Failed to click done button after adding medications");
        Thread.sleep(500);
        Assert.assertTrue(behaviouralHealthVisitPage.clickNextButton(), "Failed to click next button after adding medications");
        String therapistHistoryStatus = testDataForAccountHolder.getTherapistHistoryStatus();
        Assert.assertTrue(behaviouralHealthVisitPage.setTherapistHistoryStatus(therapistHistoryStatus), "Failed to set therapist history");
        if (therapistHistoryStatus.equals("Yes")) {
            Assert.assertTrue(behaviouralHealthVisitPage.setReasonForTreatment(testDataForAccountHolder.getReasonForPastTherapistVisit()), "Failed to set reason for treatment");
        }
        Thread.sleep(2000);
        String hospitalizedHistoryStatus = testDataForAccountHolder.getHospitalizedHistoryStatus();
        Assert.assertTrue(behaviouralHealthVisitPage.setHospitalizedHistory(hospitalizedHistoryStatus), "Failed to set hospitalized history");
        if (hospitalizedHistoryStatus.equals("Yes")) {
            Assert.assertTrue(behaviouralHealthVisitPage.setHospitalizedReason(testDataForAccountHolder.getHospitalizedReason()), "Failed to set hospitalized reason");
        }
        Assert.assertTrue(behaviouralHealthVisitPage.setSuicidalThoughts(testDataForAccountHolder.getSuicidalThoughtStatus()), "Failed to set suicidal thoughts");
        Assert.assertTrue(behaviouralHealthVisitPage.clickNextButton(), "Failed to click next button after setting medical history");

        Assert.assertTrue(behaviouralHealthVisitPage.setAdditionalDetails(testDataForAccountHolder.getAdditionalText()), "Failed to set additional details");
        Assert.assertTrue(behaviouralHealthVisitPage.clickNextButton(), "Failed to click next button after setting additional details");

        int q1 = behaviouralHealthVisitPage.setQuestionnaireOne(testDataForAccountHolder.getQuestionnaireOne());
        if (q1 != -1) score += q1;

        int q2 = behaviouralHealthVisitPage.setQuestionnaireTwo(testDataForAccountHolder.getQuestionnaireTwo());
        if (q2 != -1) score += q2;

        int q3 = behaviouralHealthVisitPage.setQuestionnaireThree(testDataForAccountHolder.getQuestionnaireThree());
        if (q3 != -1) score += q3;

        int q4 = behaviouralHealthVisitPage.setQuestionnaireFour(testDataForAccountHolder.getQuestionnaireFour());
        if (q4 != -1) score += q4;

        int q5 = behaviouralHealthVisitPage.setQuestionnaireFive(testDataForAccountHolder.getQuestionnaireFive());
        if (q5 != -1) score += q5;

        int q6 = behaviouralHealthVisitPage.setQuestionnaireSix(testDataForAccountHolder.getQuestionnaireSix());
        if (q6 != -1) score += q6;

        int q7 = behaviouralHealthVisitPage.setQuestionnaireSeven(testDataForAccountHolder.getQuestionnaireSeven());
        if (q7 != -1) score += q7;

        int q8 = behaviouralHealthVisitPage.setQuestionnaireEight(testDataForAccountHolder.getQuestionnaireEight());
        if (q8 != -1) score += q8;

        int q9 = behaviouralHealthVisitPage.setQuestionnaireNine(testDataForAccountHolder.getQuestionnaireNine());
        if (q9 != -1) score += q9;

        behaviouralHealthVisitPage.setQuestionnaireTen(testDataForAccountHolder.getQuestionnaireTen());

        Assert.assertTrue(behaviouralHealthVisitPage.clickNextButton(), "Failed to click next button after completing questionnaire");
        ExtentReportManager.getTest().log(Status.INFO, "Total Questionnaire Score: " + score);

        Assert.assertTrue(behaviouralHealthVisitPage.acceptTerms(), "Failed to accept terms and conditions");
        Assert.assertTrue(behaviouralHealthVisitPage.clickNextButton(), "Failed to click next button after accepting terms");

        Assert.assertTrue(behaviouralHealthVisitPage.acceptAllCodeOfConduct(), "Failed to accept code of conduct");
        Thread.sleep(500);
        Assert.assertTrue(behaviouralHealthVisitPage.clickNextButton(), "Failed to click next button after accepting code of conduct");

        Assert.assertTrue(behaviouralHealthVisitPage.paymentByCard(
                ConfigReader.getProperty("testCardNumber"),
                ConfigReader.getProperty("testCardExpiryDate"),
                ConfigReader.getProperty("testCardCVV")
        ), "Failed to process payment by card");
        Thread.sleep(2000);
        Assert.assertTrue(behaviouralHealthVisitPage.clickNextButton(), "Failed to click next button after payment");
        Thread.sleep(2000);
        try {
            boolean isEnabled = behaviouralHealthVisitPage.isSubmitForEvaluationEnabled();
            softAssert.assertTrue(isEnabled, "Submit for Evaluation button should be enabled");

            if (isEnabled) {
                behaviouralHealthVisitPage.clickSubmitForEvaluation();
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
        ExtentReportManager.getTest().log(Status.INFO, "Visit process completed");
        softAssert.assertAll();
    }

    @Test(priority = 3)
    public void logVisitDetails() {
        String therapyReason = "";
        if (testDataForAccountHolder.getTherapistHistoryStatus().equals("Yes")) {
            therapyReason = "Past Hospitalized Reason: " + testDataForAccountHolder.getReasonForPastTherapistVisit() + "<br>";
        }
        else {
            therapyReason = "Therapy Reason: " + "-" + "<br>";
        }
        String hospitalizedHistory ="";
        if (testDataForAccountHolder.getHospitalizedHistoryStatus().equals("Yes")) {
            hospitalizedHistory = "Hospitalized History: " + testDataForAccountHolder.getHospitalizedReason() + "<br>";
        }else {
            hospitalizedHistory = "Hospitalized History: " + "-" + "<br>";
        }
        String visitDetailsHtml = "<b>Entered Account Holder Details:</b><br>" +
                "First Name: " + testDataForAccountHolder.getFname() + "<br>" +
                "Last Name: " + testDataForAccountHolder.getLname() + "<br>" +
                "Email: " + testDataForAccountHolder.getEmail() + "<br>" +
                "Mobile Number: " + testDataForAccountHolder.getMobileNumber() + "<br>" +
                "Zipcode: " + testDataForAccountHolder.getZipCode() + "<br><br>" +

                "<b>Visit Type: Behavioural Health Visit</b><br>" +
                "<b>Demographics:</b><br>"+
                "Address Line 1: " + testDataForAccountHolder.getStreetAddressOne() + "<br>" +
                "Address Line 2: " + testDataForAccountHolder.getStreetAddressTwo() + "<br>" +
                "Date of Birth: " + testDataForAccountHolder.getDobForMajor() + "<br>" +
                "Height: " + testDataForAccountHolder.getFeet() + " feet " + testDataForAccountHolder.getInch() + " inches<br>" +
                "Weight: " + testDataForAccountHolder.getWeight() + "<br>" +
                "Gender: " + testDataForAccountHolder.getGender() + "<br>" +
                "<b>What led you to therapy today?</b><br>"+
                "Therapy Reasons: " + testDataForAccountHolder.getTherapyReasons() + "<br>" +
                "<b>List all symptoms from the past 2 weeks:</b><br>"+
                "Symptoms: " + testDataForAccountHolder.getSymptoms() + "<br>" +
                "<b>Social/Medical History:</b><br>"+
                "Exercise Status: " + testDataForAccountHolder.getExerciseStatus() + "<br>" +
                "Alcohol Status: " + testDataForAccountHolder.getAlcoholStatus() + "<br>" +
                "Smoking Items: " + testDataForAccountHolder.getSmokingItem() + "<br>" +
                "Medical Condition: " + testDataForAccountHolder.getMedicalCondition() + "<br>" +
                "<b>List any medications you are currently taking and/or review your current medication list.</b><br>"+
                "Medications: " + testDataForAccountHolder.getMedicationOne() + "<br>" +
                "<b>Behavioral Health History.</b><br>"+
                "Therapist History: " + testDataForAccountHolder.getTherapistHistoryStatus() + "<br>" +
                therapyReason + // log when the Therapist History is Yes
                "Hospitalized History: " + testDataForAccountHolder.getHospitalizedHistoryStatus() + "<br>" +
                hospitalizedHistory +
                "Suicide thought History: " + testDataForAccountHolder.getSuicidalThoughtStatus() + "<br>" +
                "<b>Record/Write additional information.</b><br>"+
                "Additional Details: " + testDataForAccountHolder.getAdditionalText() + "<br>" +
                "<b>Patient Health Questionnaire.</b><br>" +
                "Questionnaire 1: " + testDataForAccountHolder.getQuestionnaireOne() + "<br>" +
                "Questionnaire 2: " + testDataForAccountHolder.getQuestionnaireTwo() + "<br>" +
                "Questionnaire 3: " + testDataForAccountHolder.getQuestionnaireThree() + "<br>" +
                "Questionnaire 4: " + testDataForAccountHolder.getQuestionnaireFour() + "<br>" +
                "Questionnaire 5: " + testDataForAccountHolder.getQuestionnaireFive() + "<br>" +
                "Questionnaire 6: " + testDataForAccountHolder.getQuestionnaireSix() + "<br>" +
                "Questionnaire 7: " + testDataForAccountHolder.getQuestionnaireSeven() + "<br>" +
                "Questionnaire 8: " + testDataForAccountHolder.getQuestionnaireEight() + "<br>" +
                "Questionnaire 9: " + testDataForAccountHolder.getQuestionnaireNine() + "<br>" +
                "Questionnaire 10: " + testDataForAccountHolder.getQuestionnaireTen() + "<br>" +
                "Questionnaire Score: " + score;

        ExtentReportManager.getTest().info(visitDetailsHtml);
    }

    @Test(priority = 4, dependsOnMethods = "testVisitCreation")
    public void testVisitValidationInPatientPortal() {
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Validation in Patient Portal");
        // Visit submission verification
        boolean isVisitSubmitted = behaviouralHealthVisitPage.isVisitSubmitted();
        try {
            softAssert.assertTrue(isVisitSubmitted, "Visit submission verification failed");
            ExtentReportManager.getTest().log(Status.PASS, "Visit submission verification successful: Visit was submitted successfully");
        } catch (AssertionError e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Visit submission verification failed: Visit was not submitted as expected");
        }
        behaviouralHealthVisitPage.clickGoToMyVisitsButton();
        softAssert.assertAll();
    }
}
