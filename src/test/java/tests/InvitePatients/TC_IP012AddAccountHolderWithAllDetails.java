package tests.InvitePatients;

import Utils.TestData;
import base.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.PatientPortal.PatientPortalHomePage;
import pages.PatientPortal.PatientPortalLoginPage;
import pages.PatientPortal.PatientPortalMyProfilePage;
import pages.PatientPortal.SetPasswordPage;
import pages.ProviderPortal.DashBoardPage;
import pages.ProviderPortal.InvitePatientPage;
import pages.ProviderPortal.LoginPage;
import pages.ProviderPortal.PatientChart;
import pages.YopMail;

import java.io.IOException;
import java.time.Duration;

public class TC_IP012AddAccountHolderWithAllDetails extends BaseTest {
    public LoginPage loginPage;
    public DashBoardPage dashBoardPage;
    public InvitePatientPage invitePatientPage;
    public PatientChart patientChart;
    public SetPasswordPage setPasswordPage;
    public PatientPortalLoginPage loginPagePatientPortal;
    public PatientPortalHomePage homePagePatPortal;
    public PatientPortalMyProfilePage myProfilePage;

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
    }
    @Test(priority = 1)
    public void testInvitePatientWithAllDetails() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        // Fill and submit invite form
        invitePatientPage = new InvitePatientPage(driver);
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());
        invitePatientPage.selectProviderNameAs(testDataForAccountHolder.getProviderName());

        //Referral section
        invitePatientPage.clickReferralClinicCheckBoxForAccountHolder();
        invitePatientPage.setProviderFirstName(testDataForProvider.getFname());
        invitePatientPage.setProviderLastName(testDataForProvider.getLname());
        invitePatientPage.selectClinicStateForAccountHolder(testDataForProvider.getReferralClinicState());
        invitePatientPage.selectClinicForAccountHolder(testDataForProvider.getReferralClinic());

        // Additional information
        invitePatientPage.clickAdditionalInformationCheckboxForAccountHolder();
        invitePatientPage.setStreetAddressOne(testDataForAccountHolder.getStreetAddressOne());
        invitePatientPage.setStreetAddressTwo(testDataForAccountHolder.getStreetAddressTwo());
        invitePatientPage.selectGender(testDataForAccountHolder.getGender());
        invitePatientPage.setHeightFeet(testDataForAccountHolder.getFeet());
        invitePatientPage.setHeightInches(testDataForAccountHolder.getInch());
        invitePatientPage.setWeight(testDataForAccountHolder.getWeight());
        invitePatientPage.setDOB(testDataForAccountHolder.getDobForMajor());

        //Primary insurance
        invitePatientPage.clickPrimaryInsuranceCheckboxForAH();
        invitePatientPage.setPrimaryInsuranceDropdownForAH(testDataForAccountHolder.getPrimaryInsurance());
        invitePatientPage.setMemberNameInPrimaryInsuranceForAh(testDataForAccountHolder.getMemberNameForPrimaryInsurance());
        invitePatientPage.setMemberIdInPrimaryInsuranceForAh(testDataForAccountHolder.getMemberIdForPrimaryInsurance());
        invitePatientPage.setMemberDOBInPrimaryInsuranceForAh(testDataForAccountHolder.getDobForMajor());
        invitePatientPage.setRelationshipInPrimaryInsuranceForAh(testDataForAccountHolder.getRelationshipForPrimaryInsurance());

        //Secondary insurance
        invitePatientPage.clickSecondaryInsuranceCheckbox();
        invitePatientPage.setSecondaryInsuranceDropdownForAH(testDataForAccountHolder.getSecondaryInsurance());
        invitePatientPage.setMemberNameInSecondaryInsurance(testDataForAccountHolder.getMemberNameForSecondaryInsurance());
        invitePatientPage.setMemberIdInSecondaryInsurance(testDataForAccountHolder.getMemberIdForSecondaryInsurance());
        invitePatientPage.setMemberDobInSecondaryInsurance(testDataForAccountHolder.getMemberDobForSecondaryInsurance());
        invitePatientPage.setRelationshipInSecondaryInsurance(testDataForAccountHolder.getRelationshipForSecondaryInsurance());

        // Add health profile details
        //ADD MEDICATION
        invitePatientPage.clickHealthProfileCheckBoxForAccountHolder();
        invitePatientPage.clickAddMedicationButtonForAccountHolder();
        Thread.sleep(1000);
        invitePatientPage.selectMedicationForAccountHolder(testDataForAccountHolder.getMedicationOne());

        //ADD ALLERGY
        invitePatientPage.clickAddAllergyButtonForAccountHolder();
        invitePatientPage.setDrugAllergySetOne(testDataForAccountHolder.getAllergyOne(), testDataForAccountHolder.getAllergyReactionOne(), testDataForAccountHolder.getDrugAllergyCategory());
        invitePatientPage.clickAddAllergyButtonForAccountHolder();
        invitePatientPage.setEnvironmentAllergySetOne(testDataForAccountHolder.getAllergyTwo(), testDataForAccountHolder.getAllergyReactionTwo(), testDataForAccountHolder.getEnvironmentAllergyCategory());

        invitePatientPage.clickAddPatientButton();
    }
    @Test(priority = 2)
    public void testValidatePatientChart() throws InterruptedException {
        switchToTab(1);
        patientChart = new PatientChart(driver);
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

        //referral details
        softAssert.assertEquals(testDataForProvider.getFullName(), patientChart.getProviderNameFromReferralSection(),
                "Provider name in the referral section of AH is mismatching");
        softAssert.assertEquals(testDataForProvider.getReferralClinic(), patientChart.getClinicNameFromReferralSection(),
                "Clinic name in the referral section of AH is mismatching");

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

        //validating primary insurance
        softAssert.assertEquals(testDataForAccountHolder.getPrimaryInsurance().toLowerCase(), patientChart.getPrimaryInsurance().toLowerCase(),
                "Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberNameForPrimaryInsurance(), patientChart.getMemberNameInPrimaryInsurance(),
                "Member Name for Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberIdForPrimaryInsurance(), patientChart.getMemberIdInPrimaryInsurance(),
                "Member ID for Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getDobForMajor(), patientChart.getMemberDobInPrimaryInsurance(),
                "Member DOB for Primary Insurance mismatch in Patient Chart.");

        //validate secondary insurance
        softAssert.assertEquals(testDataForAccountHolder.getSecondaryInsurance().toLowerCase(), patientChart.getSecondaryInsurance().toLowerCase(),
                "Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberNameForSecondaryInsurance(), patientChart.getMemberNameInSecondaryInsurance(),
                "Member Name for Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberIdForSecondaryInsurance(), patientChart.getMemberIdInSecondaryInsurance(),
                "Member ID for Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberDobForSecondaryInsurance(), patientChart.getMemberDobInSecondaryInsurance(),
                "Member DOB for Secondary Insurance mismatch in Patient Chart.");

        //health profile
        patientChart.clickHealthProfileButton();

        //Allergy
        softAssert.assertEquals(testDataForAccountHolder.getAllergyOne().toLowerCase(), patientChart.getFirstDrugAllergyName().toLowerCase(),
                "First allergy name in patient chart does not match in Health profile of Provider Portal");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyReactionOne(), patientChart.getFirstDrugReactionName(),
                "First drug allergy reaction in patient chart does not match the value in the Health Profile of the Provider Portal");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyTwo().toLowerCase(), patientChart.getFirstEnvironmentDrugAllergyName().toLowerCase(),
                "second allergy name in patient chart does not match in Health profile of Provider Portal");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyReactionTwo(), patientChart.getFirstEnvironmentReactionName(),
                "First environmental allergy reaction in patient chart does not match the value in the Health Profile of the Provider Portal");

        //medication
        softAssert.assertEquals(testDataForAccountHolder.getMedicationOne().toLowerCase(), patientChart.getFirstMedicationName().toLowerCase(),
                "medication name in patient chart does not match in Health profile of Provider Portal");
        softAssert.assertAll();
    }
    @Test(priority = 3)
    public void testSetPasswordViaYopMail() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        newTabAndLaunchYopMail();
        YopMail yopMail = new YopMail(driver);
        yopMail.clickSetPasswordMail(testDataForAccountHolder.getEmail());

        switchToTab(3);
        setPasswordPage = new SetPasswordPage(driver);
        setPasswordPage.setPassword("Welcome@123");
    }
    @Test(priority = 4, dependsOnMethods = {"testSetPasswordViaYopMail"})
    public void testPatientPortalProfileDetails() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // Login to Patient Portal
        loginPagePatientPortal = new PatientPortalLoginPage(driver);
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");

        // Navigate to homepage and profile
        homePagePatPortal = new PatientPortalHomePage(driver);
        homePagePatPortal.clickMyProfile();

        myProfilePage = new PatientPortalMyProfilePage(driver);

        //mandatory details validation in My profile
        softAssert.assertEquals(testDataForAccountHolder.getFullName(), myProfilePage.getNameOfAccountHolder(), "Name of the Account Holder is mismatching in My profile");
        softAssert.assertEquals(testDataForAccountHolder.getEmail().toLowerCase(), myProfilePage.getEmailOfAccountHolder().toLowerCase(), "Email of the Account Holder is mismatching in My profile");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), myProfilePage.getZipCodeOfAccountHolder(), "Zipcode of the Account Holder is mismatching in My profile");
        softAssert.assertEquals(testDataForAccountHolder.getMobileNumber(), myProfilePage.getMobileOfAccountHolder(), "Mobile Number of the Account Holder is mismatching in My profile");

        //additional details validation in My profile
        softAssert.assertEquals(testDataForAccountHolder.getGender(), myProfilePage.getGender(), "Gender mismatch in My Profile page.");
        softAssert.assertEquals(testDataForAccountHolder.getStreetAddressOne(), myProfilePage.getAddressLineOne(), "Street Address Line One mismatch in My Profile page.");
        softAssert.assertTrue(myProfilePage.getAddressLineTwo().contains(testDataForAccountHolder.getStreetAddressTwo()), "Street Address Line Two from test data is not found in My Profile page.");

        //Validate health profile in my profile
        myProfilePage.clickHealthProfileLink();
        myProfilePage.clickHealthProfileOfAccountHolder();
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

        //validate medication
        myProfilePage.clickMedicationHealthProfile();
        softAssert.assertEquals(testDataForAccountHolder.getMedicationOne().toLowerCase(), myProfilePage.getMedicationOneValue().toLowerCase(),
                "MedicationOne name in patient portal profile does not match in Health profile of My Profile");
        myProfilePage.clickBackButtonInHealthProfile();
        myProfilePage.clickBackButtonInHealthProfile();
        myProfilePage.clickBackButtonInHealthProfile();
        softAssert.assertAll();
    }
    @AfterClass
    private void patientAndProviderPortalLogout() throws InterruptedException {
        // Navigate to myProfile and logout
//        homePagePatPortal = new PatientPortalHomePage(driver);
//        homePagePatPortal.clickMyProfile();
        myProfilePage = new PatientPortalMyProfilePage(driver);
        myProfilePage.clickSettingsLink();
        myProfilePage.clickLogoutButton();
        myProfilePage.clickConfirmLogoutButton();

        switchToTab("SkyMD Provider Portal");
        patientChart = new PatientChart(driver);
        patientChart.clickProfileIcon();
        patientChart.clickLogoutButton();
    }
}
