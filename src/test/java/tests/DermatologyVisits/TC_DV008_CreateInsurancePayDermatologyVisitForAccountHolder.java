package tests.DermatologyVisits;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.checkerframework.checker.units.qual.A;
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

public class TC_DV008_CreateInsurancePayDermatologyVisitForAccountHolder extends BaseTest {
    public PatientPortalLoginPage patientPortalLoginPage;
    public PatientPortalHomePage patientPortalHomePage;
    public DermatologyVisitPage dermatologyVisitPage;
    public PatientPortalMyProfilePage myProfilePage;
    public TestData testDataForAccountHolder;
    public SignInPage signInPage;
    public SoftAssert softAssert;
    public WebDriverWait wait;

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
        myProfilePage = new PatientPortalMyProfilePage(driver);
    }
    @Test(priority = 1)
    public void testSignInAsNewValidPatient() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        ExtentReportManager.getTest().log(Status.INFO, "Test started: Sign in as new valid patient");
        Thread.sleep(3000);
        patientPortalLoginPage.clickSignUpLink();
        Thread.sleep(1000);
        ExtentReportManager.getTest().log(Status.INFO, "Clicked on Sign Up link");
        signInPage.enterEmail(testDataForAccountHolder.getEmail());
        Thread.sleep(500);
        signInPage.clickNextButton();
        Thread.sleep(500);
        ExtentReportManager.getTest().log(Status.INFO, "Entered email and clicked Next");

        signInPage.enterPassword(ConfigReader.getProperty("PatientPortalPassword"));
        ExtentReportManager.getTest().log(Status.INFO, "Entered password");

        signInPage.clickAgeCheckIcon();
        signInPage.clickContinueButton();
        Thread.sleep(2000);
        ExtentReportManager.getTest().log(Status.INFO, "Accepted age and clicked Continue");

        signInPage.enterFirstName(testDataForAccountHolder.getFname());
        signInPage.enterLastName(testDataForAccountHolder.getLname());
        signInPage.enterMobileNumber(testDataForAccountHolder.getMobileNumber());
        signInPage.enterZipCode(testDataForAccountHolder.getZipCode());
        Thread.sleep(2000);

        signInPage.clickSignInButton();
        Thread.sleep(5000);
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
    public void testSelectVisitAndPatient() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Assert.assertTrue(patientPortalHomePage.selectDermatologyVisit(),"Failed to select dermatology visit");
        Thread.sleep(1500);
        dermatologyVisitPage.clickContinueAfterSelectingUploadHealthInsurance();
        Thread.sleep(3000);
        Assert.assertTrue(dermatologyVisitPage.setInsuranceName(testDataForAccountHolder.getPrimaryInsurance()),
                "Failed to set primary insurance");
        softAssert.assertEquals(dermatologyVisitPage.getMemberName(),testDataForAccountHolder.getMemberNameForPrimaryInsurance(),
                "Primary insurance Member name mismatch in Pricing page ");
        Assert.assertTrue(dermatologyVisitPage.setMemberId(testDataForAccountHolder.getMemberIdForPrimaryInsurance()),
                "Failed to set member ID");
        Assert.assertTrue(dermatologyVisitPage.setDOB(testDataForAccountHolder.getMemberDobForPrimaryInsuranceInMMDD()),
                "Failed to set date of birth");
        Assert.assertTrue(dermatologyVisitPage.uploadFrontPage(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))),
                "Failed to upload front page of insurance card");
        Assert.assertTrue(dermatologyVisitPage.uploadBackPage(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))),
                "Failed to upload back page of insurance card");
        Assert.assertTrue(dermatologyVisitPage.clickAddInsuranceButton(),
                "Failed to click add insurance button");
        //Thread.sleep(1000);
        Assert.assertTrue(dermatologyVisitPage.proceedBasisOfProvider("Dr. Janice Fahey"),"Failed to select provier");
        //Thread.sleep(1000);
        Assert.assertTrue(dermatologyVisitPage.clickSelectPatient(),"Failed to click select patient");
        Assert.assertTrue(dermatologyVisitPage.clickSelectPatientAsMySelf(),"Failed to select patient as My Self");
        //Thread.sleep(500);
        Assert.assertTrue(dermatologyVisitPage.clickContinueButtonAfterSelectPatient(),"Failed to click continue after selecting patient");
        //Thread.sleep(1000);
        softAssert.assertEquals(dermatologyVisitPage.getPrimaryInsuranceNameInPricingPage(),testDataForAccountHolder.getPrimaryInsurance(),
                "Primary insurance name in pricing page does not match the entered value");
        softAssert.assertEquals(dermatologyVisitPage.getPrimaryInsuranceMemberNameInPricingPage(), testDataForAccountHolder.getFullName(),
                "Primary insurance name in pricing page does not match the entered value");
       // softAssert.assertEquals(dermatologyVisitPage.getRelationshipInPrimaryInsurance(),testDataForAccountHolder.getRelationshipForPrimaryInsurance(),"Relationship in primary insurance does not match the entered value");
        softAssert.assertEquals(dermatologyVisitPage.getPrimaryInsuranceMemberIdInPricingPage(),testDataForAccountHolder.getMemberIdForPrimaryInsurance(),
                "Primary insurance member ID in pricing page does not match the entered value");
        softAssert.assertEquals(dermatologyVisitPage.getPrimaryInsuranceMemberDOBInPricingPage(),testDataForAccountHolder.getDobForMajorInYYYMMDD(),
                "Primary insurance member DOB in pricing page does not match the entered value");

        Assert.assertTrue(dermatologyVisitPage.clickContinueButtonAfterInsurance(),"Failed to click continue button in pricing page");
        ExtentReportManager.getTest().log(Status.INFO, "Patient selected and insurance added");
        softAssert.assertAll();
    }

    @Test(priority = 3, dependsOnMethods = "testSelectVisitAndPatient")
    public void testAddAddressAndBio() {
        Assert.assertTrue(dermatologyVisitPage.setAddressLineOne(testDataForAccountHolder.getStreetAddressOne()),"Failed to set Address Line One");
        Assert.assertTrue(dermatologyVisitPage.setAddressLineTwo(testDataForAccountHolder.getStreetAddressTwo()), "Failed to set Address Line Two");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after adding address");
        Assert.assertTrue(dermatologyVisitPage.setDOB(testDataForAccountHolder.getDobForMajorInMMDD()), "Failed to set DOB");
        Assert.assertTrue(dermatologyVisitPage.setFeet(testDataForAccountHolder.getFeet()), "Failed to set Feet");
        Assert.assertTrue(dermatologyVisitPage.setInches(testDataForAccountHolder.getInch()), "Failed to set Inches");
        Assert.assertTrue(dermatologyVisitPage.setWeight(testDataForAccountHolder.getWeight()), "Failed to set Weight");
        Assert.assertTrue(dermatologyVisitPage.setGender(testDataForAccountHolder.getGender()), "Failed to set Gender");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(),"Failed to click continue button after adding Bio");
        ExtentReportManager.getTest().log(Status.INFO, "Visit details completed");
    }
    @Test(priority = 4, dependsOnMethods = "testAddAddressAndBio")
    public void testUploadId(){
        Assert.assertTrue(dermatologyVisitPage.uploadId(convertToAbsoluteURL(ConfigReader.getProperty("idPhotoPath"))), "Failed to upload ID photo");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(),"Failed to click continue button after adding id");
        ExtentReportManager.getTest().log(Status.INFO, "Id Uploaded");
    }
    @Test(priority = 5 , dependsOnMethods = "testAddAddressAndBio")
    public void testVisitDetails() throws InterruptedException {
        Assert.assertTrue(dermatologyVisitPage.setDermatologyConcern(testDataForAccountHolder.getConcern()), "Failed to set dermatology concern");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after setting dermatology concern");
        Assert.assertTrue(dermatologyVisitPage.selectAffectedBodyPart(testDataForAccountHolder.getBodyParts()), "Failed to select affected body part");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after selecting affected body part");
        Assert.assertTrue(dermatologyVisitPage.selectStatus(testDataForAccountHolder.getStatus()), "Failed to select status");
        Assert.assertTrue(dermatologyVisitPage.selectCount(testDataForAccountHolder.getSufferingConditionDays()), "Failed to select suffering condition days");
        Assert.assertTrue(dermatologyVisitPage.selectDay(testDataForAccountHolder.getSelectDays()), "Failed to select days");
        Assert.assertTrue(dermatologyVisitPage.selectSeverity(testDataForAccountHolder.getSeverity()), "Failed to select severity");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after providing condition details");
        ExtentReportManager.getTest().log(Status.INFO, "Visit details added");
    }
    @Test(priority = 6, dependsOnMethods = "testVisitDetails")
    public void testAddConditionPhotos() throws InterruptedException {
        Assert.assertTrue(dermatologyVisitPage.clickNoBtnForSmartPhoneUpload(), "Failed to click no button for smartphone upload");
        Thread.sleep(500);
        Assert.assertTrue(dermatologyVisitPage.uploadCloseUpPic(convertToAbsoluteURL(ConfigReader.getProperty("conditionPhotoOnePath"))), "Failed to upload close up photo");
        Thread.sleep(500);
        Assert.assertTrue(dermatologyVisitPage.uploadFarAwayPic(convertToAbsoluteURL(ConfigReader.getProperty("conditionPhotoTwoPath"))), "Failed to upload far away photo");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after uploading photos");
        ExtentReportManager.getTest().log(Status.INFO, "Visit photos added");
    }
    @Test(priority = 7, dependsOnMethods = "testAddConditionPhotos")
    public void testVisitSymptoms() throws InterruptedException {
        Thread.sleep(1000);
        Assert.assertTrue(dermatologyVisitPage.selectSymptoms(testDataForAccountHolder.getSymptomOne()), "Failed to select symptoms");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after selecting symptoms");
        Assert.assertTrue(dermatologyVisitPage.setWorseText(testDataForAccountHolder.getWhatMakesWorse()), "Failed to set what makes worse text");
        Assert.assertTrue(dermatologyVisitPage.setBetterText(testDataForAccountHolder.getWhatMakesBetter()), "Failed to set what makes better text");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after setting texts");
        ExtentReportManager.getTest().log(Status.INFO, "Visit symptoms completed");
    }
    @Test(priority = 8, dependsOnMethods = "testVisitSymptoms")
    public void testVisitMedicalHistory() throws InterruptedException {
        Assert.assertTrue(dermatologyVisitPage.selectLiftStyle(testDataForAccountHolder.getLifeStyleItem()), "Failed to select lifestyle");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after selecting lifestyle");
        ExtentReportManager.getTest().log(Status.INFO, "Lifestyle selected");
        if (testDataForAccountHolder.getGender().equalsIgnoreCase("Female")) {
            Assert.assertTrue(dermatologyVisitPage.setAdditionalMedicalConditionsForFemale(testDataForAccountHolder.getFemaleMedicalCondition()), "Failed to set medical conditions");
            Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed click continue button after adding female medical History");
            ExtentReportManager.getTest().log(Status.INFO, "Female Medical History added");
        }
        //skipping allergy
        Assert.assertTrue(dermatologyVisitPage.clickNoButton(),"Failed to click No button in Allergy");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after adding allergy");
        ExtentReportManager.getTest().log(Status.INFO, "Skipped Allergies");
        Thread.sleep(3000);
        //skipping Medications
        Assert.assertTrue(dermatologyVisitPage.clickNoButton(),"Failed to click No button in Medication");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after adding medication");
        ExtentReportManager.getTest().log(Status.INFO, "Skipped Medications");
        Thread.sleep(3000);
        //skin product
        dermatologyVisitPage.clickYesBtnInSkinCareProduct();
        dermatologyVisitPage.enterSkinCareProduct("test");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button after providing medical history");
        ExtentReportManager.getTest().log(Status.INFO, "Skipped Skin product");
        //Pharmacy
        Assert.assertTrue(dermatologyVisitPage.clickAddPharmacyAndSwitchToListView(), "Failed to click Add Pharmacy and switch to list view");
        Assert.assertTrue(dermatologyVisitPage.clickFirstPharmacy(), "Failed to click first pharmacy");
        Assert.assertTrue(dermatologyVisitPage.clickContinueButton(), "Failed to click continue button");
        ExtentReportManager.getTest().log(Status.INFO, "Pharmacy selected");
        dermatologyVisitPage.setOptionalField(testDataForAccountHolder.getOptionalFieldText());
        dermatologyVisitPage.clickContinueButton();
        ExtentReportManager.getTest().log(Status.INFO, "Added optional field text");
        ExtentReportManager.getTest().log(Status.INFO, "Medical history Added");
    }
    @Test(priority = 9, dependsOnMethods = "testVisitMedicalHistory")
    public void testAddPayment() throws InterruptedException {
        Assert.assertTrue(dermatologyVisitPage.clickNextButtonForTAndC(), "Failed to click next button for Terms and Conditions");
        Assert.assertTrue(dermatologyVisitPage.clickAllAcceptTerms(), "Failed to accept all terms");
        Thread.sleep(1000);
        Assert.assertTrue(dermatologyVisitPage.paymentByCard(
                ConfigReader.getProperty("testCardNumber"),
                ConfigReader.getProperty("testCardExpiryDate"),
                ConfigReader.getProperty("testCardCVV")), "Failed to process card payment");
        ExtentReportManager.getTest().log(Status.INFO, "Visit payment completed");
    }
    @Test(priority = 10, dependsOnMethods = "testAddPayment")
    public void testValidatePayTypeInCheckOutPage() throws InterruptedException {
        Thread.sleep(3000);
        boolean payStatusInCheckOutPage = dermatologyVisitPage.isInsurancePaySelectedInTheCheckOutPage();
        softAssert.assertTrue(payStatusInCheckOutPage,"InsurancePay is not selected in the Checkout page");
        if(payStatusInCheckOutPage){
            ExtentReportManager.getTest().log(Status.PASS,"Insurance Pay is selected");
        }else{
            ExtentReportManager.getTest().log(Status.FAIL,"Insurance Pay is not selected");
            Assert.assertTrue(dermatologyVisitPage.clickInsurancePayInCheckOutPage(),"Failed to select insurance pay in checkout page");
        }
        softAssert.assertAll();
    }
    // the above method will fail, so I made the dependsOnMethods as 9th method
    @Test(priority =11, dependsOnMethods = "testAddPayment")
    public void testStatusOfVisitSubmissionButton(){
        try {
            boolean isEnabled = dermatologyVisitPage.isSubmitForEvaluationEnabled();
            softAssert.assertTrue(isEnabled,"Visit submission button is disabled");
            if (isEnabled) {
                ExtentReportManager.getTest().log(Status.PASS, "Submit for Evaluation button is enabled as expected");
                Assert.assertTrue(dermatologyVisitPage.clickSubmitForEvaluation(), "Failed to click submit for evaluation button");
            } else {
                ExtentReportManager.getTest().log(Status.FAIL, "Submit for Evaluation button is disabled when it should be enabled");
            }
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL,
                    "Failed to verify Submit for Evaluation button state: " + e.getMessage());
        }
        softAssert.assertAll();
    }
    @Test(priority = 12, dependsOnMethods = "testStatusOfVisitSubmissionButton")
    public void testVisitValidationInPatientPortal() throws InterruptedException {
        Thread.sleep(3000);
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
        Assert.assertTrue(dermatologyVisitPage.clickGoToMyVisitsButton(),"Failed to select Go to My Visit Button");
        softAssert.assertAll();
    }
    @Test(priority = 13, description = "Print Account Holder and Visit Details in Extent Report")
    public void printVisitDetailsInReport() {
        String additionMedicalConditions = "";
        if(testDataForAccountHolder.getGender().equalsIgnoreCase("Female")){
            additionMedicalConditions = "Additional Medical Condition for Female : "+testDataForAccountHolder.getFemaleMedicalCondition()+"<br>";
        }
        String accountAndVisitDetailsHtml =
                "<b>Entered Account Holder Details:</b><br>" +
                        "First Name: " + testDataForAccountHolder.getFname() + "<br>" +
                        "Last Name: " + testDataForAccountHolder.getLname() + "<br>" +
                        "Email: " + testDataForAccountHolder.getEmail() + "<br>" +
                        "Mobile Number: " + testDataForAccountHolder.getMobileNumber() + "<br>" +
                        "Zipcode: " + testDataForAccountHolder.getZipCode() + "<br>" +
                        "Provider Name: " + testDataForAccountHolder.getProviderName() + "<br><br>" +

                        "<b>Insurance Details:</b><br>" +
                        "Primary Insurance: " + testDataForAccountHolder.getPrimaryInsurance() + "<br>" +
                        "Member name: " + testDataForAccountHolder.getMemberNameForPrimaryInsurance() + "<br>" +
                        "Member ID: " + testDataForAccountHolder.getMemberIdForPrimaryInsurance() + "<br>" +
                        "Relationship: Self <br>" +
                        "Member DOB: " + testDataForAccountHolder.getMemberDobForPrimaryInsurance() + "<br>" +

                        "<b>Visit Type: Dermatology Visit</b><br>" +
                        "<b>Residence Address and Bio:</b><br>" +
                        "Address Line 1: " + testDataForAccountHolder.getStreetAddressOne() + "<br>" +
                        "Address Line 2: " + testDataForAccountHolder.getStreetAddressTwo() + "<br>" +
                        "Date of Birth: " + testDataForAccountHolder.getDobForMajor() + "<br>" +
                        "Height: " + testDataForAccountHolder.getFeet() + " feet " + testDataForAccountHolder.getInch() + " inches<br>" +
                        "Weight: " + testDataForAccountHolder.getWeight() + "<br>" +
                        "Gender: "+testDataForAccountHolder.getGender()+"<br>" +

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
                        additionMedicalConditions +
                        "Allergies: None <br>" +
                        "Current Medications: None <br>"+
                        "Current Skin Care Products: None <br>" +
                        "Additional Information: " + testDataForAccountHolder.getOptionalFieldText();
        ExtentReportManager.getTest().info(accountAndVisitDetailsHtml);
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
