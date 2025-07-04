package tests.InvitePatients;

import utils.ConfigReader;
import utils.ExtentReportManager;
import utils.TestData;
import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.PatientPortal.*;
import pages.ProviderPortal.DashBoardPage;
import pages.ProviderPortal.InvitePatientPage;
import pages.ProviderPortal.LoginPage;
import pages.ProviderPortal.PatientChart;
import pages.YopMail;

import java.io.IOException;
import java.time.Duration;

public class TC_IP011_AddChildWithAllDetails extends BaseTest {
    public LoginPage loginPage;
    public DashBoardPage dashBoardPage;
    public InvitePatientPage invitePatientPage;
    public PatientChart patientChart;
    public SetPasswordPage setPasswordPage;
    public PatientPortalLoginPage loginPagePatientPortal;
    public PatientPortalHomePage homePagePatPortal;
    public PatientPortalMyProfilePage myProfilePage;
    public DermatologyVisitPage dermatologyVisitPage;
    public YopMail yopMail;
    public TestData testDataForAccountHolder;
    public TestData testDataForChild;
    public TestData testDataForProvider;
    public SoftAssert softAssert;

    @BeforeClass
    public void setUp() throws IOException {
        // Launch Provider Portal
        driver.get(ConfigReader.getProperty("ProviderPortalUrl"));

        // Initialize test data for account holder and child
        testDataForAccountHolder = new TestData();
        testDataForChild = new TestData();
        testDataForProvider = new TestData();

        // Login as Medical Assistant (MA) in Provider Portal
        loginPage = new LoginPage(driver);
        loginPage.setEmailAs(ConfigReader.getProperty("MA_Email"));
        loginPage.setPasswordAs(ConfigReader.getProperty("MA_Password"));
        loginPage.clickLoginButton();
    }
    @BeforeMethod
    public void initializeAsset() throws IOException {
        // Set implicit wait and initialize SoftAssert for each test method
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        softAssert = new SoftAssert();

        // Initialize all page objects before each test
        dashBoardPage = new DashBoardPage(driver);
        invitePatientPage = new InvitePatientPage(driver);
        patientChart = new PatientChart(driver);
        setPasswordPage = new SetPasswordPage(driver);
        loginPagePatientPortal = new PatientPortalLoginPage(driver);
        homePagePatPortal = new PatientPortalHomePage(driver);
        myProfilePage = new PatientPortalMyProfilePage(driver);
        dermatologyVisitPage = new DermatologyVisitPage(driver);
        yopMail = new YopMail(driver);

    }
    /**
     * Invite an account holder and add a child with all details.
     */
    @Test(priority = 1)
    public void testInviteAccountHolderAndChild() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Invite Account Holder and Add Child with Mandatory Details");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        // Navigate to Invite Patient page
        ExtentReportManager.getTest().log(Status.INFO, "Navigating to Invite Patient page");
        dashBoardPage.clickInvitePatientLink();

        // Fill in account holder details
        ExtentReportManager.getTest().log(Status.INFO, "Filling in account holder details");
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());
        invitePatientPage.selectProviderNameAs(testDataForAccountHolder.getProviderName());

        // Add child as dependent with mandatory fields
        ExtentReportManager.getTest().log(Status.INFO, "Adding child as dependent with mandatory details");
        invitePatientPage.clickAddAdditionalPatientBtnForPatientOne();
        invitePatientPage.selectPatientTypeForPatientOne("Child");
        invitePatientPage.setFirstNameForPatientOne(testDataForChild.getFname());
        invitePatientPage.setLastNameForPatientOne(testDataForChild.getLname());
        invitePatientPage.setZipCodeForPatientOne(testDataForChild.getZipCode());

        // Fill the referral section
        ExtentReportManager.getTest().log(Status.INFO, "Filling referral section for child");
        invitePatientPage.clickReferralClinicCheckBoxForPatientOne();
        invitePatientPage.setProviderFirstNameInPatientOneReferralClinic(testDataForProvider.getFname());
        invitePatientPage.setProviderLastNameInPatientOneReferralClinic(testDataForProvider.getLname());
        invitePatientPage.selectClinicStateInPatientOneReferralClinic(testDataForProvider.getReferralClinicState());
        invitePatientPage.selectClinicInPatientOneReferralClinic(testDataForProvider.getReferralClinic());

        // Fill additional information
        ExtentReportManager.getTest().log(Status.INFO, "Filling additional information for child");
        invitePatientPage.clickAdditionalInformationForPatientOne();
        invitePatientPage.setStreetAddressOneForPatientOne(testDataForChild.getStreetAddressOne());
        invitePatientPage.setStreetAddressTwoForPatientOne(testDataForChild.getStreetAddressTwo());
        invitePatientPage.setDobForPatientOne(testDataForChild.getDobForMinor());
        invitePatientPage.selectGenderForPatientOne(testDataForChild.getGender());
        invitePatientPage.setFeetForPatientOne(testDataForChild.getFeet());
        invitePatientPage.setInchForPatientOne(testDataForChild.getInch());
        invitePatientPage.setWeightForPatientOne(testDataForChild.getWeight());

        // Fill primary insurance
        ExtentReportManager.getTest().log(Status.INFO, "Filling primary insurance details for child");
        invitePatientPage.checkInsuranceCheckboxForPatientOne();
        invitePatientPage.selectPrimaryInsuranceForPatientOne(testDataForChild.getPrimaryInsurance());
        invitePatientPage.setPrimaryInsuranceMemberName(testDataForAccountHolder.getFullName()); // adding parent name as primary insurance member name
        invitePatientPage.setPrimaryInsuranceMemberIdForPatientOne(testDataForChild.getMemberIdForPrimaryInsurance());
        Thread.sleep(1000);
        invitePatientPage.setPrimaryInsuranceMemberDOBForPatientOne(testDataForAccountHolder.getMemberDobForPrimaryInsurance());
        Thread.sleep(1000);
        invitePatientPage.selectPrimaryInsuranceRelationshipForPatientOne(testDataForChild.getRelationshipForPrimaryInsurance());

        // Fill secondary insurance
        ExtentReportManager.getTest().log(Status.INFO, "Filling secondary insurance details for child");
        invitePatientPage.checkSecondaryInsuranceForPatientOne();
        invitePatientPage.selectSecondaryInsuranceForPatientOne(testDataForChild.getSecondaryInsurance());
        invitePatientPage.setSecondaryInsuranceMemberNameForPatientOne(testDataForChild.getMemberNameForSecondaryInsurance());
        invitePatientPage.setSecondaryInsuranceMemberIdForPatientOne(testDataForChild.getMemberIdForSecondaryInsurance());
        Thread.sleep(1000);
        invitePatientPage.setSecondaryInsuranceMemberDOBForPatientOne(testDataForChild.getMemberDobForSecondaryInsurance());
        Thread.sleep(1000);
        invitePatientPage.selectSecondaryInsuranceRelationshipForPatientOne(testDataForChild.getRelationshipForSecondaryInsurance());

        // Add health profile details for child
        ExtentReportManager.getTest().log(Status.INFO, "Adding child's health profile details");
        invitePatientPage.clickHealthProfileCheckboxForPatientOne();
        invitePatientPage.clickAddMedicationButtonForPatientOne();
        Thread.sleep(500);
        invitePatientPage.selectMedicationForPatientOne(testDataForChild.getMedicationOne());

        // Add allergies for child
        ExtentReportManager.getTest().log(Status.INFO, "Adding allergies for child");
        invitePatientPage.clickAddAllergyButtonForPatientOne();
        invitePatientPage.setAllergySetOneForPatientOne(testDataForChild.getAllergyOne(), testDataForChild.getAllergyReactionOne(), testDataForChild.getDrugAllergyCategory());
        invitePatientPage.clickAddAllergyButtonForPatientOne();
        invitePatientPage.setAllergySetTwoForPatientOne(testDataForChild.getAllergyTwo(), testDataForChild.getAllergyReactionTwo(), testDataForChild.getEnvironmentAllergyCategory());
        // Submit the invitation
        ExtentReportManager.getTest().log(Status.INFO, "Submitting the invitation for account holder and child");
        invitePatientPage.clickAddPatientButton();
// Log HTML formatted mandatory details
        String mandatoryDetailsHtml = "<b>Entered Account Holder Details:</b><br>" +
                "First Name: " + testDataForAccountHolder.getFname() + "<br>" +
                "Last Name: " + testDataForAccountHolder.getLname() + "<br>" +
                "Email: " + testDataForAccountHolder.getEmail() + "<br>" +
                "Phone: " + testDataForAccountHolder.getMobileNumber() + "<br>" +
                "Zipcode: " + testDataForAccountHolder.getZipCode() + "<br>" +
                "Provider Name: " + testDataForAccountHolder.getProviderName();
        ExtentReportManager.getTest().info(mandatoryDetailsHtml);
        // Log Mandatory Details for Child
        String childMandatoryDetailsHtml = "<b>Entered Child’s Mandatory Details:</b><br>" +
                "First Name: " + testDataForChild.getFname() + "<br>" +
                "Last Name: " + testDataForChild.getLname() + "<br>" +
                "Zipcode: " + testDataForChild.getZipCode() + "<br>";
        ExtentReportManager.getTest().info(childMandatoryDetailsHtml);

// Log Referral Clinic Details for Child
        String childReferralHtml = "<b>Entered Child’s Referral Clinic Details:</b><br>" +
                "Provider First Name: " + testDataForProvider.getFname() + "<br>" +
                "Provider Last Name: " + testDataForProvider.getLname() + "<br>" +
                "Clinic State: " + testDataForProvider.getReferralClinicState() + "<br>" +
                "Clinic: " + testDataForProvider.getReferralClinic();
        ExtentReportManager.getTest().info(childReferralHtml);

// Log Additional Info for Child
        String childAdditionalInfoHtml = "<b>Entered Child’s Additional Information:</b><br>" +
                "Street Address 1: " + testDataForChild.getStreetAddressOne() + "<br>" +
                "Street Address 2: " + testDataForChild.getStreetAddressTwo() + "<br>" +
                "Gender: " + testDataForChild.getGender() + "<br>" +
                "Height: " + testDataForChild.getFeet() + " ft " + testDataForChild.getInch() + " in<br>" +
                "Weight: " + testDataForChild.getWeight() + " kg<br>" +
                "DOB: " + testDataForChild.getDobForMinor();
        ExtentReportManager.getTest().info(childAdditionalInfoHtml);

// Log Primary Insurance for Child
        String childPrimaryInsuranceHtml = "<b>Entered Child’s Primary Insurance Details:</b><br>" +
                "Insurance: " + testDataForChild.getPrimaryInsurance() + "<br>" +
                "Member Name: " + testDataForAccountHolder.getFullName() + "<br>" +
                "Member ID: " + testDataForChild.getMemberIdForPrimaryInsurance() + "<br>" +
                "DOB: " + testDataForAccountHolder.getMemberDobForPrimaryInsurance() + "<br>" +
                "Relationship: " + testDataForChild.getRelationshipForPrimaryInsurance();
        ExtentReportManager.getTest().info(childPrimaryInsuranceHtml);

// Log Secondary Insurance for Child
        String childSecondaryInsuranceHtml = "<b>Entered Child’s Secondary Insurance Details:</b><br>" +
                "Insurance: " + testDataForChild.getSecondaryInsurance() + "<br>" +
                "Member Name: " + testDataForChild.getMemberNameForSecondaryInsurance() + "<br>" +
                "Member ID: " + testDataForChild.getMemberIdForSecondaryInsurance() + "<br>" +
                "DOB: " + testDataForChild.getMemberDobForSecondaryInsurance() + "<br>" +
                "Relationship: " + testDataForChild.getRelationshipForSecondaryInsurance();
        ExtentReportManager.getTest().info(childSecondaryInsuranceHtml);

// Log Health Profile for Child
        String childHealthProfileHtml = "<b>Entered Child’s Health Profile Details:</b><br>" +
                "Medication: " + testDataForChild.getMedicationOne() + "<br>" +
                "Drug Allergy: " + testDataForChild.getAllergyOne() + " (Reaction: " +
                testDataForChild.getAllergyReactionOne() + ", Category: " +
                testDataForChild.getDrugAllergyCategory() + ")<br>" +
                "Environmental Allergy: " + testDataForChild.getAllergyTwo() + " (Reaction: " +
                testDataForChild.getAllergyReactionTwo() + ", Category: " +
                testDataForChild.getEnvironmentAllergyCategory() + ")";
        ExtentReportManager.getTest().info(childHealthProfileHtml);

    }
    @Test(priority = 2)
    public void testPatientChartValidations() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Validate Patient Chart for Account Holder and Child");
        // Switch to the patient chart tab
        ExtentReportManager.getTest().log(Status.INFO, "Switching to Patient Chart tab");
        switchToTab(1);
        Thread.sleep(5000);
        if(!patientChart.isPatientChart()){
            ExtentReportManager.getTest().log(Status.INFO, "Patient chart not visible – test skipped");
            Assert.fail("Patient chart page not loaded.");
        }
        // Validate account holder details in patient chart
        ExtentReportManager.getTest().log(Status.INFO, "Validating account holder details in patient chart");
        softAssert.assertEquals(testDataForAccountHolder.getFullName(), patientChart.getNameInThePatientChart(),
                "Account Holder name mismatch in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getEmail(), patientChart.getEmailInThePatientChart(),
                "Account Holder email mismatch in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), patientChart.getZipcodeInPatientChart(),
                "Account Holder zip code mismatch in Patient Chart");

        // Search and validate child details in patient chart
        ExtentReportManager.getTest().log(Status.INFO, "Searching and validating child details in patient chart");
        patientChart.searchPatient(testDataForChild.getFullName());
        Thread.sleep(2000);
        //Validating Mandatory details
        ExtentReportManager.getTest().log(Status.INFO, "Validating mandatory details in patient chart");
        softAssert.assertEquals(testDataForChild.getFullName(), patientChart.getNameInThePatientChart(),
                "Child name mismatch in Patient Chart");
        softAssert.assertEquals(testDataForChild.getZipCode(), patientChart.getZipcodeInPatientChart(),
                "Child zip code mismatch in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getFullName(), patientChart.getGuardianName(),
                "Guardian name mismatch in Patient Chart");
        //Validating Referral clinic
        ExtentReportManager.getTest().log(Status.INFO, "Validating referral clinic details in patient chart");
        softAssert.assertTrue(patientChart.isRefBatchDisplayed(), "RefBatch (Patient is referred) is not displayed in the Patient Chart.");
        softAssert.assertEquals(testDataForProvider.getFullName(), patientChart.getProviderNameFromReferralSection(),
                "Provider name in the referral section of AH is mismatching");
        softAssert.assertEquals(testDataForProvider.getReferralClinic(), patientChart.getClinicNameFromReferralSection(),
                "Clinic name in the referral section of AH is mismatching");
        //Validating Additional details
        ExtentReportManager.getTest().log(Status.INFO, "Validating additional details in patient chart");
        softAssert.assertTrue(patientChart.getAddress().contains(testDataForChild.getStreetAddressOne()),
                "Account Holder's address one from test data is not found in the Patient Chart address");
        softAssert.assertTrue(patientChart.getAddress().contains(testDataForChild.getStreetAddressTwo()),
                "Account Holder's address two from test data is not found in the Patient Chart address");
        softAssert.assertEquals(testDataForChild.getGender().toLowerCase(), patientChart.getGender().toLowerCase(),
                "Gender mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForChild.getGender().toLowerCase(), patientChart.getGenderAtTopBar().toLowerCase(),
                "Gender from test data does not match Gender in Top Bar");
        softAssert.assertEquals(testDataForChild.getWholeHeight(), patientChart.getHeight(),
                "Height mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForChild.getWeight(), patientChart.getWeight(),
                "Weight mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForChild.getDobForMinor(), patientChart.getDOB(),
                "DOB is mismatch for Account Holder in Patient Chart");
        //validating primary insurance
        ExtentReportManager.getTest().log(Status.INFO, "Validating primary insurance details in patient chart");
        softAssert.assertEquals(testDataForChild.getPrimaryInsurance().toLowerCase(), patientChart.getPrimaryInsurance().toLowerCase(),
                "Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberNameForPrimaryInsurance(), patientChart.getMemberNameInPrimaryInsurance(),
                "Member Name for Primary Insurance mismatch in Patient Chart."); // Account holder name for children primary insurance member name
        softAssert.assertEquals(testDataForChild.getMemberIdForPrimaryInsurance(), patientChart.getMemberIdInPrimaryInsurance(),
                "Member ID for Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberDobForPrimaryInsurance(), patientChart.getMemberDobInPrimaryInsurance(),
                "Member DOB for Primary Insurance mismatch in Patient Chart.");

        //validating Secondary insurance
        ExtentReportManager.getTest().log(Status.INFO, "Validating secondary insurance details in patient chart");
        softAssert.assertEquals(testDataForChild.getSecondaryInsurance().toLowerCase(), patientChart.getSecondaryInsurance().toLowerCase(),
                "Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForChild.getMemberNameForSecondaryInsurance(), patientChart.getMemberNameInSecondaryInsurance(),
                "Member Name for Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForChild.getMemberIdForSecondaryInsurance(), patientChart.getMemberIdInSecondaryInsurance(),
                "Member ID for Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForChild.getMemberDobForSecondaryInsurance(), patientChart.getMemberDobInSecondaryInsurance(),
                "Member DOB for Secondary Insurance mismatch in Patient Chart.");

        //validate health profile
        ExtentReportManager.getTest().log(Status.INFO, "Validating health profile details in patient chart");
        patientChart.clickHealthProfileButton();
        Thread.sleep(1000);
        softAssert.assertEquals(testDataForChild.getAllergyOne().toLowerCase(), patientChart.getFirstDrugAllergyName().toLowerCase(),
                "First allergy name in patient chart does not match in Health profile of Provider Portal");
        softAssert.assertEquals(testDataForChild.getAllergyReactionOne(), patientChart.getFirstDrugReactionName(),
                "First drug allergy reaction in patient chart does not match the value in the Health Profile of the Provider Portal");
        softAssert.assertEquals(testDataForChild.getAllergyTwo().toLowerCase(), patientChart.getFirstEnvironmentDrugAllergyName().toLowerCase(),
                "second allergy name in patient chart does not match in Health profile of Provider Portal");
        softAssert.assertEquals(testDataForChild.getAllergyReactionTwo(), patientChart.getFirstEnvironmentReactionName(),
                "First environmental allergy reaction in patient chart does not match the value in the Health Profile of the Provider Portal");

        softAssert.assertEquals(testDataForChild.getMedicationOne().toLowerCase(), patientChart.getFirstMedicationName().toLowerCase(),
                "medication name in patient chart does not match in Health profile of Provider Portal");
        softAssert.assertAll();
    }
    @Test(priority = 3)
    public void testSetPasswordViaYopMail() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Set Password via YopMail");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        // Open YopMail and set password
        ExtentReportManager.getTest().log(Status.INFO, "Opening YopMail and setting password");
        //newTabAndLaunchYopMail();
        yopMail.clickSetPasswordMail(testDataForAccountHolder.getEmail());
        switchToTab(3);
        setPasswordPage.setPassword("Welcome@123");
    }
    @Test(priority = 4, dependsOnMethods = {"testSetPasswordViaYopMail"})
    public void testPatientPortalMyProfile() {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Patient Portal My Profile Verification");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        // Login to Patient Portal as account holder
        ExtentReportManager.getTest().log(Status.INFO, "Logging in to Patient Portal as account holder");
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");

        // Navigate to My Profile and verify dependent
        ExtentReportManager.getTest().log(Status.INFO, "Navigating to My Profile and verifying dependent");
        homePagePatPortal.clickMyProfile();
        myProfilePage.clickDependents();

        // Validate dependent details
        ExtentReportManager.getTest().log(Status.INFO, "Validating dependent details in My Profile");
        softAssert.assertEquals(testDataForChild.getFullName(), myProfilePage.getDependentOneName(),
                "Dependent's name does not match the expected full name for Child.");
        softAssert.assertEquals("Child", myProfilePage.getDependentOneType(),
                "Dependent's type is not 'Child' as expected.");
        softAssert.assertEquals(testDataForChild.getGender(), myProfilePage.getDependentOneGender(),
                "Child's gender in the profile does not match in My Profile");
        softAssert.assertEquals(testDataForChild.getWeight(), myProfilePage.getDependentOneWeight(),
                "Child's weight in the profile does not match in My Profile");
        softAssert.assertEquals(testDataForChild.getWholeHeight(), myProfilePage.getDependentOneHeight(),
                "Child's height in the profile does not match in My Profile");

        // Validate Allergy
        ExtentReportManager.getTest().log(Status.INFO, "Validating allergy details in My Profile");
        myProfilePage.clickHealthProfileLink();
        myProfilePage.clickHealthProfileOfChild();
        myProfilePage.clickAllergyHealthProfile();

        softAssert.assertEquals(testDataForChild.getAllergyOne().toLowerCase(), myProfilePage.getDrugAllergyOneValue().toLowerCase(),
                "Allergy One name mismatch in Health Profile");
        softAssert.assertEquals(testDataForChild.getAllergyReactionOne().toLowerCase(), myProfilePage.getDrugReactionOneValue().toLowerCase(),
                "Reaction One mismatch in Health Profile");
        softAssert.assertEquals(testDataForChild.getAllergyTwo().toLowerCase(), myProfilePage.getEnvironmentAllergyOneValue().toLowerCase(),
                "Allergy Two name mismatch in Health Profile");
        softAssert.assertEquals(testDataForChild.getAllergyReactionTwo().toLowerCase(), myProfilePage.getEnvironmentReactionOneValue().toLowerCase(),
                "Reaction Two mismatch in Health Profile");

        // Validate Medication
        ExtentReportManager.getTest().log(Status.INFO, "Validating medication details in My Profile");
        myProfilePage.clickBackButtonInHealthProfile();
        myProfilePage.clickMedicationHealthProfile();
        softAssert.assertEquals(testDataForChild.getMedicationOne().toLowerCase(), myProfilePage.getMedicationOneValue().toLowerCase(),
                "MedicationOne name mismatch in Health Profile");

        myProfilePage.clickMyProfileLink();
        softAssert.assertAll();
    }
    @Test(priority = 5, dependsOnMethods = {"testSetPasswordViaYopMail"})
    public void testPatientPortalVisit() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Patient Portal Visit Flow for the Child");

        // Go back to home page and start Dermatology Visit for the child
        ExtentReportManager.getTest().log(Status.INFO, "Starting dermatology visit for child");
        myProfilePage.clickHomePageLink();
        homePagePatPortal.selectDermatologyVisit();

        dermatologyVisitPage.clickSelectPatient();
        Thread.sleep(1000);
        dermatologyVisitPage.selectPatientAsMyChild();
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();
        Thread.sleep(1000);

        // Validate selected patient
        ExtentReportManager.getTest().log(Status.INFO, "Validating selected patient in dermatology visit");
        softAssert.assertEquals(testDataForChild.getFullName(), dermatologyVisitPage.getFirstPatientName());
        dermatologyVisitPage.clickContinueButton();
        Thread.sleep(3000);
        // Validation of Primary insurance
        ExtentReportManager.getTest().log(Status.INFO, "Validating primary insurance in dermatology visit");
        softAssert.assertEquals(testDataForChild.getPrimaryInsurance(), dermatologyVisitPage.getPrimaryInsuranceName(),
                "Primary insurance name is mismatched");
        softAssert.assertEquals(testDataForAccountHolder.getMemberNameForPrimaryInsurance(), dermatologyVisitPage.getMemberNameInPrimaryInsurance(),
                "Member name is mismatched In Primary insurance");
        softAssert.assertEquals(testDataForChild.getMemberIdForPrimaryInsurance(), dermatologyVisitPage.getMemberIDInPrimaryInsurance(),
                "Member ID is mismatched In Primary insurance");
        softAssert.assertEquals(testDataForAccountHolder.getMemberDobForPrimaryInsurance(), dermatologyVisitPage.getMemberDobInPrimaryInsurance(),
                "Member DOB is mismatched In Primary insurance");
        softAssert.assertEquals(testDataForChild.getRelationshipForPrimaryInsurance(), dermatologyVisitPage.getRelationshipInPrimaryInsurance(),
                "Relationship to patient is mismatched In Primary insurance");

        // Validation of Secondary insurance
        ExtentReportManager.getTest().log(Status.INFO, "Validating secondary insurance in dermatology visit");
        softAssert.assertEquals(testDataForChild.getSecondaryInsurance(), dermatologyVisitPage.getSecondaryInsuranceName(),
                "Secondary insurance name is mismatched");
        softAssert.assertEquals(testDataForChild.getMemberNameForSecondaryInsurance(), dermatologyVisitPage.getMemberNameInSecondaryInsurance(),
                "Member name is mismatched In Secondary insurance");
        softAssert.assertEquals(testDataForChild.getMemberIdForSecondaryInsurance(), dermatologyVisitPage.getMemberIDInSecondaryInsurance(),
                "Member ID is mismatched In Secondary insurance");
        softAssert.assertEquals(testDataForChild.getMemberDobForSecondaryInsurance(), dermatologyVisitPage.getMemberDobInSecondaryInsurance(),
                "Member DOB is mismatched In Secondary insurance");
        softAssert.assertEquals(testDataForChild.getRelationshipForSecondaryInsurance(), dermatologyVisitPage.getRelationshipInSecondaryInsurance(),
                "Relationship to patient is mismatched In Secondary insurance");

        // Continue and validate address
        ExtentReportManager.getTest().log(Status.INFO, "Validating address details in dermatology visit");
        Thread.sleep(1000);
        dermatologyVisitPage.selectSelfPay();
        dermatologyVisitPage.clickContinueButtonAfterInsurance();
        Thread.sleep(1000);

        // Validate address and zipcode
        softAssert.assertEquals(testDataForChild.getStreetAddressOne(), dermatologyVisitPage.getAddressLineOneValue(),
                "Street Address Line One mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForChild.getStreetAddressTwo(), dermatologyVisitPage.getAddressLineTwoValue(),
                "Street Address Line Two mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForChild.getZipCode(), dermatologyVisitPage.getZipCodeValue(),
                "Zip Code mismatch on Dermatology Visit page.");

        // Continue and validate basic info
        ExtentReportManager.getTest().log(Status.INFO, "Validating basic info in dermatology visit");
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButton();
        Thread.sleep(1000);

        // Validate Height, Weight, DOB
        softAssert.assertEquals(testDataForChild.getDobForMinor(), dermatologyVisitPage.getDOBValue(),
                "Date of Birth mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForChild.getFeet(), dermatologyVisitPage.getFeetValue(),
                "Height (Feet) mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForChild.getInch(), dermatologyVisitPage.getInchesValue(),
                "Height (Inches) mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForChild.getWeight(), dermatologyVisitPage.getWeightValue(),
                "Weight mismatch on Dermatology Visit page.");

        // Return to home page
        ExtentReportManager.getTest().log(Status.INFO, "Returning to home page after dermatology visit");
        dermatologyVisitPage.clickBackArrowForVisitForm();
        Thread.sleep(1000);
        dermatologyVisitPage.clickBackArrowForHomePage();

        softAssert.assertAll();
    }
    @AfterClass()
    public void cleanUp() throws InterruptedException {
        //navigate to my profile
        homePagePatPortal.clickMyProfile();
        myProfilePage.clickSettingsLink();
        myProfilePage.clickLogoutButton();
        myProfilePage.clickConfirmLogoutButton();
        switchToTab("SkyMD Provider Portal");
        patientChart.clickProfileIcon();
        patientChart.clickLogoutButton();
    }
}
