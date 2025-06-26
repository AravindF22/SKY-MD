package tests.InvitePatients;

import Utils.ExtentReportManager;
import Utils.TestData;
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

public class TC_IP017_AddWardWithAllDetails extends BaseTest {
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
    public TestData testDataForWard;
    public TestData testDataForProvider;
    public SoftAssert softAssert;

    @BeforeClass
    public void setUp() throws IOException {
        // Load configuration properties file
        loadPropFile();
        // Launch Provider Portal
        driver.get(property.getProperty("ProviderPortalUrl"));

        // Initialize test data for account holder and ward
        testDataForAccountHolder = new TestData();
        testDataForWard = new TestData();
        testDataForProvider = new TestData();

        // Login as Medical Assistant (MA) in Provider Portal
        loginPage = new LoginPage(driver);
        loginPage.setEmailAs(property.getProperty("MA_Email"));
        loginPage.setPasswordAs(property.getProperty("MA_Password"));
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
     * Invite an account holder and add a ward with all details.
     */
    @Test(priority = 1)
    public void testInviteAccountHolderAndWard() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Invite Account Holder and Add Ward with Mandatory Details");
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

        // Add Ward as dependent with mandatory fields
        ExtentReportManager.getTest().log(Status.INFO, "Adding ward as dependent with mandatory details");
        invitePatientPage.clickAddAdditionalPatientBtnForPatientOne();
        invitePatientPage.selectPatientTypeForPatientOne("Ward (legal guardian of 18+ years)");
        invitePatientPage.setFirstNameForPatientOne(testDataForWard.getFname());
        invitePatientPage.setLastNameForPatientOne(testDataForWard.getLname());
        invitePatientPage.setZipCodeForPatientOne(testDataForWard.getZipCode());

        // Fill referral section
        ExtentReportManager.getTest().log(Status.INFO, "Filling referral section for ward");
        invitePatientPage.clickReferralClinicCheckBoxForPatientOne();
        invitePatientPage.setProviderFirstNameInPatientOneReferralClinic(testDataForProvider.getFname());
        invitePatientPage.setProviderLastNameInPatientOneReferralClinic(testDataForProvider.getLname());
        invitePatientPage.selectClinicStateInPatientOneReferralClinic(testDataForProvider.getReferralClinicState());
        invitePatientPage.selectClinicInPatientOneReferralClinic(testDataForProvider.getReferralClinic());

        // Fill additional information
        ExtentReportManager.getTest().log(Status.INFO, "Filling additional information for ward");
        invitePatientPage.clickAdditionalInformationForPatientOne();
        invitePatientPage.setStreetAddressOneForPatientOne(testDataForWard.getStreetAddressOne());
        invitePatientPage.setStreetAddressTwoForPatientOne(testDataForWard.getStreetAddressTwo());
        invitePatientPage.setDobForPatientOne(testDataForWard.getDobForMajor());
        invitePatientPage.selectGenderForPatientOne(testDataForWard.getGender());
        invitePatientPage.setFeetForPatientOne(testDataForWard.getFeet());
        invitePatientPage.setInchForPatientOne(testDataForWard.getInch());
        invitePatientPage.setWeightForPatientOne(testDataForWard.getWeight());

        // Fill primary insurance
        ExtentReportManager.getTest().log(Status.INFO, "Filling primary insurance details for ward");
        invitePatientPage.checkInsuranceCheckboxForPatientOne();
        invitePatientPage.selectPrimaryInsuranceForPatientOne(testDataForWard.getPrimaryInsurance());
        invitePatientPage.setPrimaryInsuranceMemberName(testDataForWard.getFullName());
        invitePatientPage.setPrimaryInsuranceMemberIdForPatientOne(testDataForWard.getMemberIdForPrimaryInsurance());
        invitePatientPage.setPrimaryInsuranceMemberDOBForPatientOne(testDataForWard.getDobForMajor());
        invitePatientPage.selectPrimaryInsuranceRelationshipForPatientOne(testDataForWard.getRelationshipForPrimaryInsurance());

        // Fill secondary insurance
        ExtentReportManager.getTest().log(Status.INFO, "Filling secondary insurance details for ward");
        invitePatientPage.checkSecondaryInsuranceForPatientOne();
        invitePatientPage.selectSecondaryInsuranceForPatientOne(testDataForWard.getSecondaryInsurance());
        invitePatientPage.setSecondaryInsuranceMemberNameForPatientOne(testDataForWard.getMemberNameForSecondaryInsurance());
        invitePatientPage.setSecondaryInsuranceMemberIdForPatientOne(testDataForWard.getMemberIdForSecondaryInsurance());
        invitePatientPage.setSecondaryInsuranceMemberDOBForPatientOne(testDataForWard.getMemberDobForSecondaryInsurance());
        invitePatientPage.selectSecondaryInsuranceRelationshipForPatientOne(testDataForWard.getRelationshipForSecondaryInsurance());

        // Add health profile details for ward
        ExtentReportManager.getTest().log(Status.INFO, "Adding Ward's health profile details");
        invitePatientPage.clickHealthProfileCheckboxForPatientOne();
        invitePatientPage.clickAddMedicationButtonForPatientOne();
        invitePatientPage.selectMedicationForPatientOne(testDataForWard.getMedicationOne());

        // Add allergies for ward
        ExtentReportManager.getTest().log(Status.INFO, "Adding allergies for ward");
        invitePatientPage.clickAddAllergyButtonForPatientOne();
        invitePatientPage.setAllergySetOneForPatientOne(testDataForWard.getAllergyOne(), testDataForWard.getAllergyReactionOne(), testDataForWard.getDrugAllergyCategory());
        invitePatientPage.clickAddAllergyButtonForPatientOne();
        invitePatientPage.setAllergySetTwoForPatientOne(testDataForWard.getAllergyTwo(), testDataForWard.getAllergyReactionTwo(), testDataForWard.getEnvironmentAllergyCategory());
        // Submit the invitation
        invitePatientPage.clickAddPatientButton();
        ExtentReportManager.getTest().log(Status.INFO, "The invitation for account holder and Ward Submitted successfully");

    }
    @Test(priority = 2)
    public void testPatientChartValidations() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Validate Patient Chart for Account Holder and Ward");
        // Switch to the patient chart tab
        ExtentReportManager.getTest().log(Status.INFO, "Switching to Patient Chart tab");
        switchToTab(1);
        if(!patientChart.isPatientChart()){
            ExtentReportManager.getTest().log(Status.INFO, "Patient chart not visible – test skipped");
            Assert.fail("Patient chart page not loaded.");
        }
        Thread.sleep(3000);
        // Search and validate Ward details in patient chart
        ExtentReportManager.getTest().log(Status.INFO, "Searching and validating ward details in patient chart");
        patientChart.searchPatient(testDataForWard.getFullName());
        Thread.sleep(2000);
        //Validating Mandatory details
        ExtentReportManager.getTest().log(Status.INFO, "Validating mandatory details in patient chart");
        softAssert.assertEquals(testDataForWard.getFullName(), patientChart.getNameInThePatientChart(),
                "ward name mismatch in Patient Chart");
        softAssert.assertEquals(testDataForWard.getZipCode(), patientChart.getZipcodeInPatientChart(),
                "ward zip code mismatch in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getFullName(), patientChart.getGuardianName(),
                "Guardian name mismatch in Patient Chart");
        //Validating Referral clinic
        ExtentReportManager.getTest().log(Status.INFO, "Validating referral clinic details in patient chart");
        softAssert.assertEquals(testDataForProvider.getFullName(), patientChart.getProviderNameFromReferralSection(),
                "Provider name in the referral section of AH is mismatching");
        softAssert.assertEquals(testDataForProvider.getReferralClinic(), patientChart.getClinicNameFromReferralSection(),
                "Clinic name in the referral section of AH is mismatching");
        //Validating Additional details
        ExtentReportManager.getTest().log(Status.INFO, "Validating additional details in patient chart");
        softAssert.assertTrue(patientChart.getAddress().contains(testDataForWard.getStreetAddressOne()),
                "Account Holder's address one from test data is not found in the Patient Chart address");
        softAssert.assertTrue(patientChart.getAddress().contains(testDataForWard.getStreetAddressTwo()),
                "Account Holder's address two from test data is not found in the Patient Chart address");
        softAssert.assertEquals(testDataForWard.getGender().toLowerCase(), patientChart.getGender().toLowerCase(),
                "Gender mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForWard.getGender().toLowerCase(), patientChart.getGenderAtTopBar().toLowerCase(),
                "Gender from test data does not match Gender in Top Bar");
        softAssert.assertEquals(testDataForWard.getWholeHeight(), patientChart.getHeight(),
                "Height mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForWard.getWeight(), patientChart.getWeight(),
                "Weight mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForWard.getDobForMajor(), patientChart.getDOB(),
                "DOB is mismatch for Account Holder in Patient Chart");
        //validating primary insurance
        ExtentReportManager.getTest().log(Status.INFO, "Validating primary insurance details in patient chart");
        softAssert.assertEquals(testDataForWard.getPrimaryInsurance().toLowerCase(), patientChart.getPrimaryInsurance().toLowerCase(),
                "Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForWard.getMemberNameForPrimaryInsurance(), patientChart.getMemberNameInPrimaryInsurance(),
                "Member Name for Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForWard.getMemberIdForPrimaryInsurance(), patientChart.getMemberIdInPrimaryInsurance(),
                "Member ID for Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForWard.getMemberDobForPrimaryInsurance(), patientChart.getMemberDobInPrimaryInsurance(),
                "Member DOB for Primary Insurance mismatch in Patient Chart.");

        //validating Secondary insurance
        ExtentReportManager.getTest().log(Status.INFO, "Validating secondary insurance details in patient chart");
        softAssert.assertEquals(testDataForWard.getSecondaryInsurance().toLowerCase(), patientChart.getSecondaryInsurance().toLowerCase(),
                "Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForWard.getMemberNameForSecondaryInsurance(), patientChart.getMemberNameInSecondaryInsurance(),
                "Member Name for Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForWard.getMemberIdForSecondaryInsurance(), patientChart.getMemberIdInSecondaryInsurance(),
                "Member ID for Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForWard.getMemberDobForSecondaryInsurance(), patientChart.getMemberDobInSecondaryInsurance(),
                "Member DOB for Secondary Insurance mismatch in Patient Chart.");

        // Validate health profile
        ExtentReportManager.getTest().log(Status.INFO, "Validating health profile details in patient chart");
        patientChart.clickHealthProfileButton();
        Thread.sleep(1000);
        softAssert.assertEquals(testDataForWard.getAllergyOne().toLowerCase(), patientChart.getFirstDrugAllergyName().toLowerCase(),
                "First allergy name in patient chart does not match in Health profile of Provider Portal");
        softAssert.assertEquals(testDataForWard.getAllergyReactionOne(), patientChart.getFirstDrugReactionName(),
                "First drug allergy reaction in patient chart does not match the value in the Health Profile of the Provider Portal");
        softAssert.assertEquals(testDataForWard.getAllergyTwo().toLowerCase(), patientChart.getFirstEnvironmentDrugAllergyName().toLowerCase(),
                "second allergy name in patient chart does not match in Health profile of Provider Portal");
        softAssert.assertEquals(testDataForWard.getAllergyReactionTwo(), patientChart.getFirstEnvironmentReactionName(),
                "First environmental allergy reaction in patient chart does not match the value in the Health Profile of the Provider Portal");

        softAssert.assertEquals(testDataForWard.getMedicationOne().toLowerCase(), patientChart.getFirstMedicationName().toLowerCase(),
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
    public void testPatientPortalMyProfile() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Patient Portal My Profile Verification");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        // Login to Patient Portal as account holder
        ExtentReportManager.getTest().log(Status.INFO, "Logging in to Patient Portal as account holder");
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");

        // Navigate to My Profile and verify dependent
        ExtentReportManager.getTest().log(Status.INFO, "Navigating to My Profile and verifying dependent");
        homePagePatPortal.clickMyProfile();
        myProfilePage.clickDependents();

        // Validate dependent details
        ExtentReportManager.getTest().log(Status.INFO, "Validating dependent details in My Profile");
        softAssert.assertEquals(testDataForWard.getFullName(), myProfilePage.getDependentOneName(),
                "Dependent's name does not match the expected full name for ward.");
        softAssert.assertEquals("Ward", myProfilePage.getDependentOneType(),
                "Dependent's type is not 'ward' as expected.");
        softAssert.assertEquals(testDataForWard.getGender(), myProfilePage.getDependentOneGender(),
                "Ward's gender in the profile does not match in My Profile");
        softAssert.assertEquals(testDataForWard.getWeight(), myProfilePage.getDependentOneWeight(),
                "Ward's weight in the profile does not match in My Profile");
        softAssert.assertEquals(testDataForWard.getWholeHeight(), myProfilePage.getDependentOneHeight(),
                "Ward's height in the profile does not match in My Profile");

        // Validate Allergy
        ExtentReportManager.getTest().log(Status.INFO, "Validating allergy details in My Profile");
        myProfilePage.clickHealthProfileLink();
        myProfilePage.clickHealthProfileOfWard();
        myProfilePage.clickAllergyHealthProfile();

        softAssert.assertEquals(testDataForWard.getAllergyOne().toLowerCase(), myProfilePage.getDrugAllergyOneValue().toLowerCase(),
                "Allergy One name mismatch in Health Profile");
        softAssert.assertEquals(testDataForWard.getAllergyReactionOne().toLowerCase(), myProfilePage.getDrugReactionOneValue().toLowerCase(),
                "Reaction One mismatch in Health Profile");
        softAssert.assertEquals(testDataForWard.getAllergyTwo().toLowerCase(), myProfilePage.getEnvironmentAllergyOneValue().toLowerCase(),
                "Allergy Two name mismatch in Health Profile");
        softAssert.assertEquals(testDataForWard.getAllergyReactionTwo().toLowerCase(), myProfilePage.getEnvironmentReactionOneValue().toLowerCase(),
                "Reaction Two mismatch in Health Profile");

        // Validate Medication
        ExtentReportManager.getTest().log(Status.INFO, "Validating medication details in My Profile");
        myProfilePage.clickBackButtonInHealthProfile();
        myProfilePage.clickMedicationHealthProfile();
        softAssert.assertEquals(testDataForWard.getMedicationOne().toLowerCase(), myProfilePage.getMedicationOneValue().toLowerCase(),
                "MedicationOne name mismatch in Health Profile");

        myProfilePage.clickMyProfileLink();
        softAssert.assertAll();
    }
    @Test(priority = 5, dependsOnMethods = {"testSetPasswordViaYopMail"})
    public void testPatientPortalVisit() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Patient Portal Dermatology Visit for Ward");

        // Start Dermatology Visit
        ExtentReportManager.getTest().log(Status.INFO, "Starting dermatology visit for ward");
        myProfilePage.clickHomePageLink();
        homePagePatPortal.selectDermatologyVisit();
        dermatologyVisitPage.clickSelectPatient();
        Thread.sleep(1000);
        dermatologyVisitPage.selectPatientAsWard();
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();
        Thread.sleep(1000);

        // Validate selected patient
        ExtentReportManager.getTest().log(Status.INFO, "Validating selected patient in dermatology visit");
        softAssert.assertEquals(testDataForWard.getFullName(), dermatologyVisitPage.getNameOfTheWardInSelectWard(),
                "Name is matched with the selected ward ");
        dermatologyVisitPage.clickContinueButton();

        // Validate Primary Insurance
        ExtentReportManager.getTest().log(Status.INFO, "Validating primary insurance in dermatology visit");
        softAssert.assertEquals(testDataForWard.getPrimaryInsurance(), dermatologyVisitPage.getPrimaryInsuranceName(),
                "Primary insurance name is mismatched");
        softAssert.assertEquals(testDataForWard.getFullName(), dermatologyVisitPage.getMemberNameInPrimaryInsurance(),
                "Member name is mismatched In Primary insurance");
        softAssert.assertEquals(testDataForWard.getMemberIdForPrimaryInsurance(), dermatologyVisitPage.getMemberIDInPrimaryInsurance(),
                "Member ID is mismatched In Primary insurance");
        softAssert.assertEquals(testDataForWard.getMemberDobForPrimaryInsurance(), dermatologyVisitPage.getMemberDobInPrimaryInsurance(),
                "Member DOB is mismatched In Primary insurance");
        softAssert.assertEquals(testDataForWard.getRelationshipForPrimaryInsurance(), dermatologyVisitPage.getRelationshipInPrimaryInsurance(),
                "Relationship to patient is mismatched In Primary insurance");

        // Validate Secondary Insurance
        ExtentReportManager.getTest().log(Status.INFO, "Validating secondary insurance in dermatology visit");
        softAssert.assertEquals(testDataForWard.getSecondaryInsurance(), dermatologyVisitPage.getSecondaryInsuranceName(),
                "Secondary insurance name is mismatched");
        softAssert.assertEquals(testDataForWard.getMemberNameForSecondaryInsurance(), dermatologyVisitPage.getMemberNameInSecondaryInsurance(),
                "Member name is mismatched In Secondary insurance");
        softAssert.assertEquals(testDataForWard.getMemberIdForSecondaryInsurance(), dermatologyVisitPage.getMemberIDInSecondaryInsurance(),
                "Member ID is mismatched In Secondary insurance");
        softAssert.assertEquals(testDataForWard.getMemberDobForSecondaryInsurance(), dermatologyVisitPage.getMemberDobInSecondaryInsurance(),
                "Member DOB is mismatched In Secondary insurance");
        softAssert.assertEquals(testDataForWard.getRelationshipForSecondaryInsurance(), dermatologyVisitPage.getRelationshipInSecondaryInsurance(),
                "Relationship to patient is mismatched In Secondary insurance");

        // Continue and validate address
        ExtentReportManager.getTest().log(Status.INFO, "Validating address details in dermatology visit");
        Thread.sleep(1000);
        dermatologyVisitPage.selectSelfPay();
        dermatologyVisitPage.clickContinueButtonAfterInsurance();
        Thread.sleep(1000);

        softAssert.assertEquals(testDataForWard.getStreetAddressOne(), dermatologyVisitPage.getAddressLineOneValue(),
                "Street Address Line One mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForWard.getStreetAddressTwo(), dermatologyVisitPage.getAddressLineTwoValue(),
                "Street Address Line Two mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForWard.getZipCode(), dermatologyVisitPage.getZipCodeValue(),
                "Zip Code mismatch on Dermatology Visit page.");

        // Validate basic info
        ExtentReportManager.getTest().log(Status.INFO, "Validating basic info in dermatology visit");
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButton();
        Thread.sleep(1000);
        softAssert.assertEquals(testDataForWard.getDobForMajor(), dermatologyVisitPage.getDOBValue(),
                "Date of Birth mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForWard.getFeet(), dermatologyVisitPage.getFeetValue(),
                "Height (Feet) mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForWard.getInch(), dermatologyVisitPage.getInchesValue(),
                "Height (Inches) mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForWard.getWeight(), dermatologyVisitPage.getWeightValue(),
                "Weight mismatch on Dermatology Visit page.");

        // Return to Home
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
