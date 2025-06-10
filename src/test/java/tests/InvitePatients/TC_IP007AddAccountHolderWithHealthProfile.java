package tests.InvitePatients;

import Utils.TestData;
import base.BaseTest;
import org.testng.annotations.*;
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

public class TC_IP007AddAccountHolderWithHealthProfile extends BaseTest {
    public LoginPage loginPage;
    public DashBoardPage dashBoardPage;
    public InvitePatientPage invitePatientPage;
    public PatientChart patientChart;
    public SetPasswordPage setPasswordPage;
    public PatientPortalLoginPage loginPagePatientPortal;
    public PatientPortalHomePage homePagePatPortal;
    public PatientPortalMyProfilePage myProfilePage;

    public TestData testDataForAccountHolder;
    public SoftAssert softAssert;
    @BeforeClass
    public void setUp() throws IOException {
        //Loading config File
        loadPropFile();
        driver.get(property.getProperty("ProviderPortalUrl"));

        // Test data for account holder
        testDataForAccountHolder = new TestData();

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
    public void initializeAsset() {
        softAssert = new SoftAssert();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test(priority = 1)
    public void testInviteAccountHolderWithHealthProfile() throws InterruptedException {
        invitePatientPage = new InvitePatientPage(driver);

        // Fill basic information
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());

        // Add health profile details
        invitePatientPage.clickHealthProfileCheckBoxForAccountHolder();
        invitePatientPage.clickAddMedicationButtonForAccountHolder();
        Thread.sleep(1000);
        invitePatientPage.selectMedicationForAccountHolder(testDataForAccountHolder.getMedicationOne());
        invitePatientPage.clickAddAllergyButtonForAccountHolder();
        invitePatientPage.setAllergySetOne(testDataForAccountHolder.getAllergyOne(),testDataForAccountHolder.getAllergyReactionOne(),testDataForAccountHolder.getAllergyCategoryOne());
        invitePatientPage.clickAddAllergyButtonForAccountHolder();
        invitePatientPage.setAllergySetTwo(testDataForAccountHolder.getAllergyTwo(),testDataForAccountHolder.getAllergyReactionTwo(),testDataForAccountHolder.getAllergyCategoryTwo());
        invitePatientPage.clickAddAllergyButtonForAccountHolder();
        invitePatientPage.setAllergySetThree(testDataForAccountHolder.getAllergyThree(),testDataForAccountHolder.getAllergyReactionThree(),testDataForAccountHolder.getAllergyCategoryThree());

        invitePatientPage.clickAddPatientButton();
    }

    @Test(priority = 2, dependsOnMethods = "testInviteAccountHolderWithHealthProfile")
    public void testValidatePatientChartHealthProfile() throws InterruptedException {

        switchToTab(1);
        patientChart = new PatientChart(driver);
        patientChart.clickHealthProfileButton();

        softAssert.assertEquals(testDataForAccountHolder.getAllergyOne().toLowerCase(), patientChart.getFirstAllergyName().toLowerCase(),
                "First allergy name in patient chart does not match in Health profile of Provider Portal");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyTwo().toLowerCase(), patientChart.getSecondAllergyName().toLowerCase(),
                "second allergy name in patient chart does not match in Health profile of Provider Portal");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyThree().toLowerCase(), patientChart.getThirdAllergyName().toLowerCase(),
                "third allergy name in patient chart does not match in Health profile of Provider Portal");

        softAssert.assertEquals(testDataForAccountHolder.getMedicationOne().toLowerCase(), patientChart.getFirstMedicationName().toLowerCase(),
                "medication name in patient chart does not match in Health profile of Provider Portal");
        softAssert.assertAll();
    }

    @Test(priority = 3, dependsOnMethods = "testValidatePatientChartHealthProfile")
    public void testSetPasswordAndLoginToPortal() throws InterruptedException {
        newTabandLaunchYopmail();
        YopMail yopMail = new YopMail(driver);
        yopMail.clickSetPasswordMail(testDataForAccountHolder.getEmail());

        switchToTab(3);
        setPasswordPage = new SetPasswordPage(driver);
        setPasswordPage.setPassword("Welcome@123");

        loginPagePatientPortal = new PatientPortalLoginPage(driver);
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");
    }

    @Test(priority = 4, dependsOnMethods = "testSetPasswordAndLoginToPortal")
    public void testPatientPortalHealthProfileValidations() {
        homePagePatPortal = new PatientPortalHomePage(driver);
        homePagePatPortal.clickMyProfile();

        myProfilePage = new PatientPortalMyProfilePage(driver);
        myProfilePage.clickHealthProfileLink();

        myProfilePage.clickHealthProfileOfAccountHolder();
        myProfilePage.clickAllergyHealthProfile();

        //validate allergy
        softAssert.assertEquals(testDataForAccountHolder.getAllergyOne().toLowerCase(), myProfilePage.getAllergyOneValue().toLowerCase(),
                "Allergy One name in patient portal profile does not match in Health profile of My Profile");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyReactionOne().toLowerCase(), myProfilePage.getReactionOne().toLowerCase(),
                "Reaction One in patient portal profile does not match in Health profile of My Profile");

        softAssert.assertEquals(testDataForAccountHolder.getAllergyTwo().toLowerCase(), myProfilePage.getAllergyTwoValue().toLowerCase(),
                "Allergy Two name in patient portal profile does not match in Health profile of My Profile");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyReactionTwo().toLowerCase(), myProfilePage.getReactionTwo().toLowerCase(),
                "Reaction Two name in patient portal profile does not match in Health profile of My Profile");

        softAssert.assertEquals(testDataForAccountHolder.getAllergyThree().toLowerCase(), myProfilePage.getAllergyThreeValue().toLowerCase(),
                "Allergy Three name in patient portal profile does not match in Health profile of My Profile");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyReactionThree().toLowerCase(), myProfilePage.getReactionThree().toLowerCase(),
                "Reaction Three name in patient portal profile does not match in Health profile of My Profile");

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

   // @AfterClass()
    public void cleanUp() throws InterruptedException {
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
