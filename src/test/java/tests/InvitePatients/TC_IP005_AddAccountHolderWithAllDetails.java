package tests.InvitePatients;

import Utils.ExtentReportManager;
import Utils.TestData;
import base.BaseTest;
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
import com.aventstack.extentreports.Status;

import java.io.IOException;
import java.time.Duration;

/**
 * Test Case: TC_IP012
 * Description: Invite an account holder with all possible details (referral, additional info, insurance, health profile),
 *              and verify that all information is correctly displayed in the patient chart and patient portal profile.
 */
public class TC_IP005_AddAccountHolderWithAllDetails extends BaseTest {
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
    public TestData testDataForProvider;
    public SoftAssert softAssert;

    @BeforeClass
    public void setUp() throws IOException {
        //Loading config File
        loadPropFile();
        driver.get(property.getProperty("ProviderPortalUrl"));
        //Test data for account holder
        testDataForAccountHolder = new TestData();
        testDataForProvider = new TestData();

        // Login as MA
        loginPage = new LoginPage(driver);
        loginPage.setEmailAs(property.getProperty("MA_Email"));
        loginPage.setPasswordAs(property.getProperty("MA_Password"));
        loginPage.clickLoginButton();

        // Navigate to Invite Patient
        dashBoardPage = new DashBoardPage(driver);
        dashBoardPage.clickInvitePatientLink();
    }
    @BeforeMethod
    public void initializeAsset() throws IOException {
        softAssert = new SoftAssert();
        loginPage = new LoginPage(driver);
        dashBoardPage = new DashBoardPage(driver);
        invitePatientPage = new InvitePatientPage(driver);
        patientChart = new PatientChart(driver);
        setPasswordPage = new SetPasswordPage(driver);
        loginPagePatientPortal = new PatientPortalLoginPage(driver);
        homePagePatPortal = new PatientPortalHomePage(driver);
        dermatologyVisitPage = new DermatologyVisitPage(driver);
        myProfilePage = new PatientPortalMyProfilePage(driver);
        yopMail = new YopMail(driver);
    }
    @Test(priority = 1)
    public void testInvitePatientWithAllDetails() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Invite Patient With All Details");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        ExtentReportManager.getTest().log(Status.INFO, "Filling invite patient form: Mandatory fields");
        // Mandatory fields
        invitePatientPage = new InvitePatientPage(driver);
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());
        invitePatientPage.selectProviderNameAs(testDataForAccountHolder.getProviderName());

        ExtentReportManager.getTest().log(Status.INFO, "Filling invite patient form: Referral section");
        //Referral section
        invitePatientPage.clickReferralClinicCheckBoxForAccountHolder();
        invitePatientPage.setProviderFirstName(testDataForProvider.getFname());
        invitePatientPage.setProviderLastName(testDataForProvider.getLname());
        invitePatientPage.selectClinicStateForAccountHolder(testDataForProvider.getReferralClinicState());
        invitePatientPage.selectClinicForAccountHolder(testDataForProvider.getReferralClinic());

        ExtentReportManager.getTest().log(Status.INFO, "Filling invite patient form: Additional information");
        // Additional information
        invitePatientPage.clickAdditionalInformationCheckboxForAccountHolder();
        invitePatientPage.setStreetAddressOne(testDataForAccountHolder.getStreetAddressOne());
        invitePatientPage.setStreetAddressTwo(testDataForAccountHolder.getStreetAddressTwo());
        invitePatientPage.selectGender(testDataForAccountHolder.getGender());
        invitePatientPage.setHeightFeet(testDataForAccountHolder.getFeet());
        invitePatientPage.setHeightInches(testDataForAccountHolder.getInch());
        invitePatientPage.setWeight(testDataForAccountHolder.getWeight());
        invitePatientPage.setDOB(testDataForAccountHolder.getDobForMajor());

        ExtentReportManager.getTest().log(Status.INFO, "Filling invite patient form: Primary insurance");
        //Primary insurance
        invitePatientPage.clickPrimaryInsuranceCheckboxForAH();
        invitePatientPage.setPrimaryInsuranceDropdownForAH(testDataForAccountHolder.getPrimaryInsurance());
        invitePatientPage.setMemberNameInPrimaryInsuranceForAh(testDataForAccountHolder.getMemberNameForPrimaryInsurance());
        invitePatientPage.setMemberIdInPrimaryInsuranceForAh(testDataForAccountHolder.getMemberIdForPrimaryInsurance());
        invitePatientPage.setMemberDOBInPrimaryInsuranceForAh(testDataForAccountHolder.getDobForMajor());
        invitePatientPage.setRelationshipInPrimaryInsuranceForAh(testDataForAccountHolder.getRelationshipForPrimaryInsurance());

        ExtentReportManager.getTest().log(Status.INFO, "Filling invite patient form: Secondary insurance");
        //Secondary insurance
        invitePatientPage.clickSecondaryInsuranceCheckbox();
        invitePatientPage.setSecondaryInsuranceDropdownForAH(testDataForAccountHolder.getSecondaryInsurance());
        invitePatientPage.setMemberNameInSecondaryInsurance(testDataForAccountHolder.getMemberNameForSecondaryInsurance());
        invitePatientPage.setMemberIdInSecondaryInsurance(testDataForAccountHolder.getMemberIdForSecondaryInsurance());
        invitePatientPage.setMemberDobInSecondaryInsurance(testDataForAccountHolder.getMemberDobForSecondaryInsurance());
        invitePatientPage.setRelationshipInSecondaryInsurance(testDataForAccountHolder.getRelationshipForSecondaryInsurance());

        ExtentReportManager.getTest().log(Status.INFO, "Filling invite patient form: Add health profile details");
        // Add health profile details
        ExtentReportManager.getTest().log(Status.INFO, "Adding MEDICATION");
        //ADD MEDICATION
        invitePatientPage.clickHealthProfileCheckBoxForAccountHolder();
        invitePatientPage.clickAddMedicationButtonForAccountHolder();
        Thread.sleep(1000);
        invitePatientPage.selectMedicationForAccountHolder(testDataForAccountHolder.getMedicationOne());

        ExtentReportManager.getTest().log(Status.INFO, "Adding Allergy");
        //ADD ALLERGY
        invitePatientPage.clickAddAllergyButtonForAccountHolder();
        invitePatientPage.setDrugAllergySetOne(testDataForAccountHolder.getAllergyOne(), testDataForAccountHolder.getAllergyReactionOne(), testDataForAccountHolder.getDrugAllergyCategory());
        invitePatientPage.clickAddAllergyButtonForAccountHolder();
        invitePatientPage.setEnvironmentAllergySetOne(testDataForAccountHolder.getAllergyTwo(), testDataForAccountHolder.getAllergyReactionTwo(), testDataForAccountHolder.getEnvironmentAllergyCategory());

        ExtentReportManager.getTest().log(Status.INFO, "Submitting invite patient form");
        invitePatientPage.clickAddPatientButton();
        ExtentReportManager.getTest().log(Status.INFO, "Invite patient form submitted successfully");
    }
    @Test(priority = 2)
    public void testValidatePatientChart() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Validating patient chart details in Provider Portal");
        switchToTab(1);
        if(!patientChart.isPatientChart()){
            ExtentReportManager.getTest().log(Status.INFO, "Patient chart not visible – test skipped");
            Assert.fail("Patient chart page not loaded.");
        }
        Thread.sleep(3000);
        ExtentReportManager.getTest().log(Status.INFO, "Validating Mandatory details in Patient Chart");
        //Mandatory details
        softAssert.assertEquals(testDataForAccountHolder.getFullName(), patientChart.getNameInThePatientChart(),
                "Name didn't match in the Patient chart");
        softAssert.assertEquals(testDataForAccountHolder.getEmail().toLowerCase(), patientChart.getEmailInThePatientChart().toLowerCase(),
                "Email didn't match");
        softAssert.assertEquals(testDataForAccountHolder.getFullName().toUpperCase(), patientChart.getNameInTopBar(),
                "Name in the Top Bar is mismatching");
        softAssert.assertEquals(testDataForAccountHolder.getMobileNumber(), patientChart.getMobileInPatientChart(),
                "Mobile Number didn't match in the Patient chart");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), patientChart.getZipcodeInPatientChart(),
                "Zip Code mismatch in Patient Chart.");

        ExtentReportManager.getTest().log(Status.INFO, "Validating Referral details in Patient Chart");
        //referral details
        softAssert.assertEquals(testDataForProvider.getFullName(), patientChart.getProviderNameFromReferralSection(),
                "Provider name in the referral section of AH is mismatching");
        softAssert.assertEquals(testDataForProvider.getReferralClinic(), patientChart.getClinicNameFromReferralSection(),
                "Clinic name in the referral section of AH is mismatching");

        ExtentReportManager.getTest().log(Status.INFO, "Validating Additional details in Patient Chart");
        // Additional details
        softAssert.assertTrue(patientChart.getAddress().contains(testDataForAccountHolder.getStreetAddressOne()),
                "Account Holder's address one from test data is not found in the Patient Chart address");
        softAssert.assertTrue(patientChart.getAddress().contains(testDataForAccountHolder.getStreetAddressTwo()),
                "Account Holder's address two from test data is not found in the Patient Chart address");
        softAssert.assertEquals(testDataForAccountHolder.getGender().toLowerCase(), patientChart.getGender().toLowerCase(),
                "Gender mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getGender().toLowerCase(), patientChart.getGenderAtTopBar().toLowerCase(),
                "Gender from test data does not match Gender in Top Bar");
        softAssert.assertEquals(testDataForAccountHolder.getWholeHeight(), patientChart.getHeight(),
                "Height mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getWeight(), patientChart.getWeight(),
                "Weight mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getDobForMajor(), patientChart.getDOB(),
                "DOB is mismatch for Account Holder in Patient Chart");

        ExtentReportManager.getTest().log(Status.INFO, "Validating Primary insurance in Patient Chart");
        //validating primary insurance
        softAssert.assertEquals(testDataForAccountHolder.getPrimaryInsurance().toLowerCase(), patientChart.getPrimaryInsurance().toLowerCase(),
                "Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberNameForPrimaryInsurance(), patientChart.getMemberNameInPrimaryInsurance(),
                "Member Name for Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberIdForPrimaryInsurance(), patientChart.getMemberIdInPrimaryInsurance(),
                "Member ID for Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getDobForMajor(), patientChart.getMemberDobInPrimaryInsurance(),
                "Member DOB for Primary Insurance mismatch in Patient Chart.");

        ExtentReportManager.getTest().log(Status.INFO, "Validating Secondary insurance in Patient Chart");
        //validate secondary insurance
        softAssert.assertEquals(testDataForAccountHolder.getSecondaryInsurance().toLowerCase(), patientChart.getSecondaryInsurance().toLowerCase(),
                "Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberNameForSecondaryInsurance(), patientChart.getMemberNameInSecondaryInsurance(),
                "Member Name for Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberIdForSecondaryInsurance(), patientChart.getMemberIdInSecondaryInsurance(),
                "Member ID for Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberDobForSecondaryInsurance(), patientChart.getMemberDobInSecondaryInsurance(),
                "Member DOB for Secondary Insurance mismatch in Patient Chart.");

        ExtentReportManager.getTest().log(Status.INFO, "Validating Health profile in Patient Chart");
        //health profile
        patientChart.clickHealthProfileButton();

        ExtentReportManager.getTest().log(Status.INFO, "Validating Allergy section in Health profile");
        //Allergy
        softAssert.assertEquals(testDataForAccountHolder.getAllergyOne().toLowerCase(), patientChart.getFirstDrugAllergyName().toLowerCase(),
                "First allergy name in patient chart does not match in Health profile of Provider Portal");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyReactionOne(), patientChart.getFirstDrugReactionName(),
                "First drug allergy reaction in patient chart does not match the value in the Health Profile of the Provider Portal");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyTwo().toLowerCase(), patientChart.getFirstEnvironmentDrugAllergyName().toLowerCase(),
                "second allergy name in patient chart does not match in Health profile of Provider Portal");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyReactionTwo(), patientChart.getFirstEnvironmentReactionName(),
                "First environmental allergy reaction in patient chart does not match the value in the Health Profile of the Provider Portal");

        ExtentReportManager.getTest().log(Status.INFO, "Validating Medication section in Health profile");
        //medication
        softAssert.assertEquals(testDataForAccountHolder.getMedicationOne().toLowerCase(), patientChart.getFirstMedicationName().toLowerCase(),
                "medication name in patient chart does not match in Health profile of Provider Portal");
        ExtentReportManager.getTest().log(Status.INFO, "Patient chart details validated successfully");
        softAssert.assertAll();
    }
    @Test(priority = 3)
    public void testSetPasswordViaYopMail() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Setting password via YopMail");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //newTabAndLaunchYopMail();
        yopMail.clickSetPasswordMail(testDataForAccountHolder.getEmail());
        ExtentReportManager.getTest().log(Status.INFO, "Clicked set password mail in YopMail");

        switchToTab(3);
        setPasswordPage.setPassword("Welcome@123");
        ExtentReportManager.getTest().log(Status.INFO, "Password set successfully for invited patient");
    }
    @Test(priority = 4, dependsOnMethods = {"testSetPasswordViaYopMail"})
    public void testPatientPortalProfileDetails() {
        ExtentReportManager.getTest().log(Status.INFO, "Validating patient portal profile details");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        ExtentReportManager.getTest().log(Status.INFO, "Logging into Patient Portal");
        // Login to Patient Portal
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");
        ExtentReportManager.getTest().log(Status.INFO, "Logged into Patient Portal");
        // Navigate to homepage and profile
        ExtentReportManager.getTest().log(Status.INFO, "Navigating to My Profile page");
        homePagePatPortal.clickMyProfile();

        ExtentReportManager.getTest().log(Status.INFO, "Validating mandatory details in My Profile");
        //mandatory details validation in My profile
        softAssert.assertEquals(testDataForAccountHolder.getFullName(), myProfilePage.getNameOfAccountHolder(), "Name of the Account Holder is mismatching in My profile");
        softAssert.assertEquals(testDataForAccountHolder.getEmail().toLowerCase(), myProfilePage.getEmailOfAccountHolder().toLowerCase(), "Email of the Account Holder is mismatching in My profile");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), myProfilePage.getZipCodeOfAccountHolder(), "Zipcode of the Account Holder is mismatching in My profile");
        softAssert.assertEquals(testDataForAccountHolder.getMobileNumber(), myProfilePage.getMobileOfAccountHolder(), "Mobile Number of the Account Holder is mismatching in My profile");

        ExtentReportManager.getTest().log(Status.INFO, "Validating additional details in My Profile");
        //additional details validation in My profile
        softAssert.assertEquals(testDataForAccountHolder.getGender(), myProfilePage.getGender(), "Gender mismatch in My Profile page.");
        softAssert.assertEquals(testDataForAccountHolder.getStreetAddressOne(), myProfilePage.getAddressLineOne(), "Street Address Line One mismatch in My Profile page.");
        softAssert.assertTrue(myProfilePage.getAddressLineTwo().contains(testDataForAccountHolder.getStreetAddressTwo()), "Street Address Line Two from test data is not found in My Profile page.");

        ExtentReportManager.getTest().log(Status.INFO, "Validating Health profile in My Profile");
        //Validate health profile in my profile
        myProfilePage.clickHealthProfileLink();
        myProfilePage.clickHealthProfileOfAccountHolder();
        ExtentReportManager.getTest().log(Status.INFO, "Validating Allergy section in Health profile");
        myProfilePage.clickAllergyHealthProfile();

        //validate allergy
        softAssert.assertEquals(testDataForAccountHolder.getAllergyOne().toLowerCase(), myProfilePage.getDrugAllergyOneValue().toLowerCase(),
                "Allergy One name in patient portal profile does not match in Health profile of My Profile");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyReactionOne().toLowerCase(), myProfilePage.getDrugReactionOneValue().toLowerCase(),
                "Reaction One in patient portal profile does not match in Health profile of My Profile");

        softAssert.assertEquals(testDataForAccountHolder.getAllergyTwo().toLowerCase(), myProfilePage.getEnvironmentAllergyOneValue().toLowerCase(),
                "Allergy Two name in patient portal profile does not match in Health profile of My Profile");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyReactionTwo().toLowerCase(), myProfilePage.getEnvironmentReactionOneValue().toLowerCase(),
                "Reaction Two name in patient portal profile does not match in Health profile of My Profile");

        myProfilePage.clickBackButtonInHealthProfile();

        ExtentReportManager.getTest().log(Status.INFO, "Validating Medication section in Health profile");
        //validate medication
        myProfilePage.clickMedicationHealthProfile();
        softAssert.assertEquals(testDataForAccountHolder.getMedicationOne().toLowerCase(), myProfilePage.getMedicationOneValue().toLowerCase(),
                "MedicationOne name in patient portal profile does not match in Health profile of My Profile");
        myProfilePage.clickHomePageLink();
        ExtentReportManager.getTest().log(Status.INFO, "Patient portal profile details validated successfully");
        softAssert.assertAll();
    }
    @Test(priority = 5, dependsOnMethods = "testSetPasswordViaYopMail")
    public void testDermatologyVisitValidation() throws InterruptedException {
        // Visit flow
        ExtentReportManager.getTest().log(Status.INFO, "Starting Dermatology Visit flow");
        homePagePatPortal.selectDermatologyVisit();
        dermatologyVisitPage.clickSelectPatient();
        dermatologyVisitPage.clickSelectPatientAsMySelf();
        ExtentReportManager.getTest().log(Status.INFO, "Selected patient as self for visit");

        // Validate primary insurance details
        ExtentReportManager.getTest().log(Status.INFO, "Validating primary insurance details in Dermatology Visit page");
        softAssert.assertEquals(testDataForAccountHolder.getPrimaryInsurance().toLowerCase(), dermatologyVisitPage.getPrimaryInsuranceName().toLowerCase(),
                "Primary Insurance name mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberNameForPrimaryInsurance(), dermatologyVisitPage.getMemberNameInPrimaryInsurance(),
                "Member Name for Primary Insurance mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberIdForPrimaryInsurance(), dermatologyVisitPage.getMemberIDInPrimaryInsurance(),
                "Member ID for Primary Insurance mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getDobForMajor(), dermatologyVisitPage.getMemberDobInPrimaryInsurance(),
                "Member DOB for Primary Insurance mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getRelationshipForPrimaryInsurance(), dermatologyVisitPage.getRelationshipInPrimaryInsurance(),
                "Relationship for Primary Insurance mismatch in Dermatology Visit page.");

        //secondary insurance validation
        ExtentReportManager.getTest().log(Status.INFO, "Validating secondary insurance details in Dermatology Visit page");
        softAssert.assertEquals(testDataForAccountHolder.getSecondaryInsurance().toLowerCase(), dermatologyVisitPage.getSecondaryInsuranceName().toLowerCase(),
                "Secondary Insurance name mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberNameForSecondaryInsurance(), dermatologyVisitPage.getMemberNameInSecondaryInsurance(),
                "Member Name for Secondary Insurance mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberIdForSecondaryInsurance(), dermatologyVisitPage.getMemberIDInSecondaryInsurance(),
                "Member ID for Secondary Insurance mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberDobForSecondaryInsurance(), dermatologyVisitPage.getMemberDobInSecondaryInsurance(),
                "Member DOB for Secondary Insurance mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getRelationshipForSecondaryInsurance(), dermatologyVisitPage.getRelationshipInSecondaryInsurance(),
                "Relationship for Secondary Insurance mismatch in Dermatology Visit page.");

        dermatologyVisitPage.selectSelfPay();
        dermatologyVisitPage.clickContinueButtonAfterInsurance();

        // Validate address and zipcode on Dermatology Visit page
        ExtentReportManager.getTest().log(Status.INFO, "Validating address and zipcode on Dermatology Visit page");
        softAssert.assertEquals(testDataForAccountHolder.getStreetAddressOne(), dermatologyVisitPage.getAddressLineOneValue(),
                "Street Address Line One mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getStreetAddressTwo(), dermatologyVisitPage.getAddressLineTwoValue(),
                "Street Address Line Two mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), dermatologyVisitPage.getZipCodeValue(),
                "Zip Code mismatch on Dermatology Visit page.");
        Thread.sleep(500);
        dermatologyVisitPage.clickContinueButton();
        Thread.sleep(500);
        // Validate Height, Weight, DOB
        ExtentReportManager.getTest().log(Status.INFO, "Validating Height, Weight, and DOB on Dermatology Visit page");
        softAssert.assertEquals(testDataForAccountHolder.getDobForMajor(), dermatologyVisitPage.getDOBValue(),
                "Date of Birth mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getFeet(), dermatologyVisitPage.getFeetValue(),
                "Height (Feet) mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getInch(), dermatologyVisitPage.getInchesValue(),
                "Height (Inches) mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getWeight(), dermatologyVisitPage.getWeightValue(),
                "Weight mismatch on Dermatology Visit page.");

        dermatologyVisitPage.clickBackArrowForVisitForm();
        dermatologyVisitPage.clickBackArrowForHomePage();

        ExtentReportManager.getTest().log(Status.INFO, "Patient portal profile and visit flow validated successfully");
        softAssert.assertAll();
    }
    @AfterClass
    private void patientAndProviderPortalLogout() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Logging out from Patient and Provider Portals");
        homePagePatPortal.clickMyProfile();
        myProfilePage.clickSettingsLink();
        myProfilePage.clickLogoutButton();
        myProfilePage.clickConfirmLogoutButton();

        switchToTab("SkyMD Provider Portal");
        patientChart.clickProfileIcon();
        patientChart.clickLogoutButton();
        ExtentReportManager.getTest().log(Status.INFO, "Logout completed successfully");
    }
}
