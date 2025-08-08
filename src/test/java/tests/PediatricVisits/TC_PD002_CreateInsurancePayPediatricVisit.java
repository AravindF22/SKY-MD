package tests.PediatricVisits;

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

public class TC_PD002_CreateInsurancePayPediatricVisit extends BaseTest {
    public PatientPortalLoginPage patientPortalLoginPage;
    public PatientPortalHomePage patientPortalHomePage;
    public PediatricVisitPage pediatricVisitPage;
    public PatientPortalMyProfilePage myProfilePage;
    public SignInPage signInPage;
    public TestData testDataForAccountHolder;
    public TestData testDataForChild;
    public SoftAssert softAssert;
    public WebDriverWait wait;
    public String selectedProviderName = null;

    @BeforeClass
    public void setUp() throws IOException {
        driver.get(ConfigReader.getProperty("PatientPortalLoginUrl"));
        testDataForAccountHolder = new TestData();
        testDataForChild = new TestData();
    }
    @BeforeMethod
    public void initializeAsset(){
        softAssert = new SoftAssert();
        patientPortalLoginPage = new PatientPortalLoginPage(driver);
        patientPortalHomePage = new PatientPortalHomePage(driver);
        pediatricVisitPage = new PediatricVisitPage(driver);
        myProfilePage = new PatientPortalMyProfilePage(driver);
        signInPage = new SignInPage(driver);
    }
    @Test(priority = 1)
    public void testSignInAsNewValidPatient() throws InterruptedException {
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
        Thread.sleep(5000);
        softAssert.assertAll();
    }
    @Test(priority = 2, dependsOnMethods = {"testSignInAsNewValidPatient"})
    public void testSelectPediatricVisit() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Thread.sleep(1000);
        Assert.assertTrue(patientPortalHomePage.selectPediatricVisit(), "Failed to select Pediatric Visit");
        Thread.sleep(2000);
        ExtentReportManager.getTest().log(Status.PASS, "Pediatric visit is selected");
    }
    @Test(priority = 3, dependsOnMethods = {"testSelectPediatricVisit"})
    public void testAddChild() throws InterruptedException {
        Assert.assertTrue(pediatricVisitPage.clickAddChild(), "Failed to click Add Child");
        Thread.sleep(500);
        Assert.assertTrue(pediatricVisitPage.setFirstName(testDataForChild.getFname()), "Failed to set first name");
        Assert.assertTrue(pediatricVisitPage.setLastName(testDataForChild.getLname()), "Failed to set last name");
        Assert.assertTrue(pediatricVisitPage.setDOB(testDataForChild.getDobForMinorInMMDD()), "Failed to set DOB");
        Assert.assertTrue(pediatricVisitPage.setHeightFeet(testDataForChild.getFeet()), "Failed to set height feet");
        Assert.assertTrue(pediatricVisitPage.setHeightInches(testDataForChild.getInch()), "Failed to set height inches");
        Assert.assertTrue(pediatricVisitPage.setWeight(testDataForChild.getWeight()), "Failed to set weight");
        Assert.assertTrue(pediatricVisitPage.selectGender(testDataForChild.getGender()), "Failed to select gender");
        Assert.assertTrue(pediatricVisitPage.clickSaveButton(), "Failed to save child details");
        Thread.sleep(2000);
        Assert.assertTrue(pediatricVisitPage.clickNextButton(), "Failed to proceed after saving child");
        ExtentReportManager.getTest().log(Status.PASS, "Child details filled and saved successfully");
    }

    @Test(priority = 4, dependsOnMethods = {"testAddChild"})
    public void testAddInsurance() throws InterruptedException {
        Assert.assertTrue(pediatricVisitPage.clickInsurancePay(), "Failed to click Insurance pay section");
        Thread.sleep(2000);
        Assert.assertTrue(pediatricVisitPage.setInsuranceName("AETNA"),
                "Failed to set primary insurance");
        Assert.assertTrue(pediatricVisitPage.setMemberName(testDataForChild.getMemberNameForPrimaryInsurance()),
                "Failed to set member name");
        Assert.assertTrue(pediatricVisitPage.setMemberId(testDataForChild.getMemberIdForPrimaryInsurance()),
                "Failed to set member ID");
        Assert.assertTrue(pediatricVisitPage.setDOB(testDataForChild.getMemberDobForPrimaryInsuranceInMMDD()),
                "Failed to set date of birth");
        Assert.assertTrue(pediatricVisitPage.uploadFrontPage(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))),
                "Failed to upload front page of insurance card");
        Assert.assertTrue(pediatricVisitPage.uploadBackPage(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))),
                "Failed to upload back page of insurance card");
        Assert.assertTrue(pediatricVisitPage.clickAddInsuranceButton(),
                "Failed to click add insurance button");
        Thread.sleep(3000);
        try {
            softAssert.assertEquals(pediatricVisitPage.getPrimaryInsuranceNameInPricingPage(), "AETNA",
                    "Primary Insurance name is mismatching in Insurance Pay section");
            ExtentReportManager.getTest().log(Status.PASS, "Successfully verified Primary Insurance name in Insurance Pay section");
        } catch (AssertionError e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Primary Insurance name verification failed in Insurance Pay section: " + e.getMessage());
        }
        try {
            softAssert.assertEquals(pediatricVisitPage.getPrimaryInsuranceMemberIdInPricingPage(), testDataForChild.getMemberIdForPrimaryInsurance(),
                    "Member ID is mismatching in Insurance Pay section");
            ExtentReportManager.getTest().log(Status.PASS, "Successfully verified Member ID in Insurance Pay section");
        } catch (AssertionError e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Member ID verification failed in Insurance Pay section: " + e.getMessage());
        }

        Assert.assertTrue(pediatricVisitPage.clickProceedByBookingButton(), "Failed to click proceed by booking button");
        softAssert.assertAll();
    }
    @Test(priority = 5, dependsOnMethods = {"testAddInsurance"})
    public void testSelectCondition(){
        Assert.assertTrue(pediatricVisitPage.selectCondition(testDataForChild.getCondition()),"Failed to select conditions");
        Assert.assertTrue(pediatricVisitPage.clickNextButton(),"Failed to click next button after selecting conditions");
        ExtentReportManager.getTest().log(Status.INFO, "Successfully selected conditions");
    }
    @Test(priority = 6, dependsOnMethods = {"testSelectCondition"})
    public void testSlotAndProviderSelection() throws InterruptedException {
        Assert.assertTrue(pediatricVisitPage.selectFirstAvailableDay(), "Failed to select first available day");
        Assert.assertTrue(pediatricVisitPage.selectFirstAvailableTimeSlot(), "Failed to select time slot");
        selectedProviderName = pediatricVisitPage.getSelectedProviderName();
        Thread.sleep(500);
        Assert.assertTrue(pediatricVisitPage.clickNextButton(), "Failed to proceed after selecting time slot");
        ExtentReportManager.getTest().log(Status.PASS, "Appointment slot and provider selected");
    }
    @Test(priority = 7, dependsOnMethods = {"testSlotAndProviderSelection"})
    public void testVerifyDemographics() {
        softAssert.assertEquals(pediatricVisitPage.getFirstName(), testDataForChild.getFname(), "First name mismatch");
        softAssert.assertEquals(pediatricVisitPage.getLastName(), testDataForChild.getLname(), "Last name mismatch");
        softAssert.assertEquals(pediatricVisitPage.getDOBInMMDD(), testDataForChild.getDobForMinorInMMDD(), "DOB mismatch");
        Assert.assertTrue(pediatricVisitPage.setAddressLineOne(testDataForChild.getStreetAddressOne()), "Failed to set address 1");
        Assert.assertTrue(pediatricVisitPage.setAddressLineTwo(testDataForChild.getStreetAddressTwo()), "Failed to set address 2");
        softAssert.assertEquals(pediatricVisitPage.getZipcode(), testDataForChild.getZipCode(), "Zipcode mismatch");
        softAssert.assertEquals(pediatricVisitPage.getFeet(), testDataForChild.getFeet(), "Height feet mismatch");
        softAssert.assertEquals(pediatricVisitPage.getInch(), testDataForChild.getInch(), "Height inch mismatch");
        softAssert.assertEquals(pediatricVisitPage.getWeight(), testDataForChild.getWeight(), "Weight mismatch");
        softAssert.assertEquals(pediatricVisitPage.getGender(), testDataForChild.getGender(), "Gender mismatch");
        Assert.assertTrue(pediatricVisitPage.clickNextButton(), "Failed to proceed after demographics");
        ExtentReportManager.getTest().log(Status.PASS, "Demographics validated and continued");
        softAssert.assertAll();
    }
    @Test(priority = 8, dependsOnMethods = {"testVerifyDemographics"})
    public void testUploadIDForChild() {
        Assert.assertTrue(pediatricVisitPage.uploadId(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))), "Failed to upload ID");
        Assert.assertTrue(pediatricVisitPage.clickNextButton(), "Failed to proceed after ID upload");
        ExtentReportManager.getTest().log(Status.PASS, "Child ID uploaded successfully");
    }
    @Test(priority = 9, dependsOnMethods = {"testUploadIDForChild"})
    public void testSelectPharmacyForChild() {
        Assert.assertTrue(pediatricVisitPage.clickAddPharmacyAndSwitchToListView(), "Failed to switch to pharmacy list");
        Assert.assertTrue(pediatricVisitPage.clickFirstPharmacy(), "Failed to select pharmacy");
        Assert.assertTrue(pediatricVisitPage.clickNextButton(), "Failed to continue after pharmacy");
        ExtentReportManager.getTest().log(Status.PASS, "Pharmacy selected for child");
    }
    @Test(priority = 10, dependsOnMethods = {"testSelectPharmacyForChild"})
    public void testAddSymptomsForChild() {
        Assert.assertTrue(pediatricVisitPage.setGeneralSymptoms(testDataForChild.getGeneralSymptom()), "Failed to set symptoms");
        Assert.assertTrue(pediatricVisitPage.clickNextButton(), "Failed to proceed after symptoms");
        ExtentReportManager.getTest().log(Status.PASS, "General symptoms added");
    }
    @Test(priority = 11, dependsOnMethods = {"testAddSymptomsForChild"})
    public void testSkipAllergyForChild() {
        Assert.assertTrue(pediatricVisitPage.clickNoneButton(),"Failed to click none button in allergy");
        Assert.assertTrue(pediatricVisitPage.clickNextButton(), "Failed to proceed after allergy");
        ExtentReportManager.getTest().log(Status.PASS, "Allergy added for child");
    }

    @Test(priority = 12, dependsOnMethods = {"testSkipAllergyForChild"})
    public void testSkipMedicationForChild() {
        Assert.assertTrue(pediatricVisitPage.clickSearchMedicationButton(), "Failed to click search medication");
        Assert.assertTrue(pediatricVisitPage.clickNoneButton(),"Failed to click None button in medication");
        Assert.assertTrue(pediatricVisitPage.clickNextButton(), "Failed to continue after medication");

        if (testDataForChild.getGender().equalsIgnoreCase("Female")) {
            Assert.assertTrue(pediatricVisitPage.setAdditionalMedicalConditionsForFemale("Pregnant"),
                    "Failed to set medical condition for female");
            Assert.assertTrue(pediatricVisitPage.clickNextButton(),
                    "Failed to proceed after setting female condition");
        }
        ExtentReportManager.getTest().log(Status.PASS, "Medication added for child");
    }

    @Test(priority = 13, dependsOnMethods = {"testSkipMedicationForChild"})
    public void testEnterOptionalDetails() {
        Assert.assertTrue(pediatricVisitPage.setOptionalDetails(testDataForChild.getAdditionalText()), "Failed to enter optional details");
        Assert.assertTrue(pediatricVisitPage.clickNextButton(), "Failed to continue after optional details");
        ExtentReportManager.getTest().log(Status.PASS, "Optional details filled");
    }

    @Test(priority = 14, dependsOnMethods = {"testEnterOptionalDetails"})
    public void testAcknowledgeCodeOfConduct() {
        Assert.assertTrue(pediatricVisitPage.acceptAllCodeOfConduct(), "Failed to accept Code of Conduct");
        Assert.assertTrue(pediatricVisitPage.clickNextButton(), "Failed to continue after Code of Conduct");
        ExtentReportManager.getTest().log(Status.PASS, "Code of Conduct accepted");
    }

    @Test(priority = 15, dependsOnMethods = {"testAcknowledgeCodeOfConduct"})
    public void testMakePaymentForChild() throws InterruptedException {
        Assert.assertTrue(pediatricVisitPage.paymentByCard(
                ConfigReader.getProperty("testCardNumber"),
                ConfigReader.getProperty("testCardExpiryDate"),
                ConfigReader.getProperty("testCardCVV")
        ), "Failed to process payment");
        Thread.sleep(2000);
        Assert.assertTrue(pediatricVisitPage.clickNextButton(), "Failed to proceed after payment");
        ExtentReportManager.getTest().log(Status.PASS, "Payment processed successfully for child");
    }

    @Test(priority = 16, dependsOnMethods = {"testMakePaymentForChild"})
    public void testSubmitVisitForChild() {
        try {
            boolean isEnabled = pediatricVisitPage.isSubmitForEvaluationEnabled();
            softAssert.assertTrue(isEnabled, "Submit for Evaluation button should be enabled");

            if (isEnabled) {
                pediatricVisitPage.clickSubmitForEvaluation();
                ExtentReportManager.getTest().log(Status.PASS, "Submit for Evaluation button clicked");
            } else {
                ExtentReportManager.getTest().log(Status.FAIL, "Submit for Evaluation button was disabled");
            }
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Error during submission check: " + e.getMessage());
        }
        softAssert.assertAll();
    }
    @Test(priority = 17, dependsOnMethods = {"testSubmitVisitForChild"})
    public void testVisitValidationInPatientPortal() {
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Validation in Patient Portal");
        // Visit submission verification
        boolean isVisitSubmitted = pediatricVisitPage.isVisitSubmitted();
        try {
            softAssert.assertTrue(isVisitSubmitted, "Visit submission verification failed");
            ExtentReportManager.getTest().log(Status.PASS, "Visit submission verification successful: Visit was submitted successfully");
        } catch (AssertionError e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Visit submission verification failed: Visit was not submitted as expected");
        }
        softAssert.assertEquals(selectedProviderName,pediatricVisitPage.getProviderNameInTheVisitSubmittedPage(),
                "Provider name in the visit submitted page is not matching with the selected provider name");
        pediatricVisitPage.clickGoToMyVisitsButton();
        softAssert.assertAll();
    }
    @Test(priority = 18)
    public void logPediatricVisitDetails() {
        String additionalMedicalConditions = "";
        if (testDataForChild.getGender().equalsIgnoreCase("Female")) {
            additionalMedicalConditions = "Additional Medical Condition for Female: Pregnant<br>";
        }

        String visitDetailsHtml =
                "<b>Account Holder Details:</b><br>" +
                        "First Name: " + testDataForAccountHolder.getFname() + "<br>" +
                        "Last Name: " + testDataForAccountHolder.getLname() + "<br>" +
                        "Email: " + testDataForAccountHolder.getEmail() + "<br>" +
                        "Mobile Number: " + testDataForAccountHolder.getMobileNumber() + "<br>" +
                        "Zipcode: " + testDataForAccountHolder.getZipCode() + "<br><br>" +

                        "<b>Child (Patient) Details:</b><br>" +
                        "First Name: " + testDataForChild.getFname() + "<br>" +
                        "Last Name: " + testDataForChild.getLname() + "<br>" +
                        "Date of Birth: " + testDataForChild.getDobForMinor() + "<br>" +
                        "Height: " + testDataForChild.getFeet() + " feet " + testDataForChild.getInch() + " inches<br>" +
                        "Weight: " + testDataForChild.getWeight() + "<br>" +
                        "Gender: " + testDataForChild.getGender() + "<br>" +
                        "Address Line 1: " + testDataForChild.getStreetAddressOne() + "<br>" +
                        "Address Line 2: " + testDataForChild.getStreetAddressTwo() + "<br>" +
                        "Zipcode: " + testDataForChild.getZipCode() + "<br><br>" +

                        "<b>Insurance Details:</b><br>" +
                        "Primary Insurance Name: AETNA<br>" +
                        "Primary Member Name: " + testDataForChild.getMemberNameForPrimaryInsurance() + "<br>" +
                        "Primary Insurance ID: " + testDataForChild.getMemberIdForPrimaryInsurance() + "<br>" +
                        "Primary Insurance DOB: " + testDataForChild.getMemberDobForPrimaryInsurance() + "<br><br>" +

                        "<b>Visit Details:</b><br>" +
                        "Visit Type: Pediatric Visit<br>" +
                        "Condition Selected: " + testDataForChild.getCondition() + "<br>" +
                        "<b>Provider Name: " + selectedProviderName + "</b><br><br>" +

                        "<b>Symptoms & Medical Info:</b><br>" +
                        "Symptoms: "+testDataForChild.getGeneralSymptom()+"<br>" +
                        "Allergy: None<br>" +
                        "Medication: None<br>" +
                        additionalMedicalConditions +

                        "<b>Other Information:</b><br>" +
                        "Additional Details: " + testDataForChild.getAdditionalText();

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
