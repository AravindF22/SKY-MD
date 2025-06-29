package tests.InvitePatients;

import Utils.TestData;
import base.BaseTest;
import org.testng.Assert;
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
import Utils.ExtentReportManager;
import com.aventstack.extentreports.Status;

import java.io.IOException;
import java.time.Duration;

/**
 * Test Case: TC_IP007
 * Description: Invite an account holder with health profile details (medications, allergies),
 *              and verify that all health profile information is correctly displayed in the patient chart and patient portal profile.
 */
public class TC_IP006_AddAccountHolderWithHealthProfile extends BaseTest {
    public LoginPage loginPage;
    public DashBoardPage dashBoardPage;
    public InvitePatientPage invitePatientPage;
    public PatientChart patientChart;
    public SetPasswordPage setPasswordPage;
    public PatientPortalLoginPage loginPagePatientPortal;
    public PatientPortalHomePage homePagePatPortal;
    public PatientPortalMyProfilePage myProfilePage;
    public YopMail yopMail;

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
        loginPage = new LoginPage(driver);
        dashBoardPage = new DashBoardPage(driver);
        invitePatientPage = new InvitePatientPage(driver);
        patientChart = new PatientChart(driver);
        setPasswordPage = new SetPasswordPage(driver);
        loginPagePatientPortal = new PatientPortalLoginPage(driver);
        homePagePatPortal = new PatientPortalHomePage(driver);
        myProfilePage = new PatientPortalMyProfilePage(driver);
        yopMail = new YopMail(driver);
    }

    @Test(priority = 1)
    public void testInviteAccountHolderWithHealthProfile() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Invite Account Holder with Health Profile");
        // Fill basic information
        ExtentReportManager.getTest().log(Status.INFO, "Filling basic information for account holder");
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());

        // Add health profile details
        // ADD MEDICATION
        ExtentReportManager.getTest().log(Status.INFO, "Adding medication to health profile");
        invitePatientPage.clickHealthProfileCheckBoxForAccountHolder();
        invitePatientPage.clickAddMedicationButtonForAccountHolder();
        Thread.sleep(2000);
        invitePatientPage.selectMedicationForAccountHolder(testDataForAccountHolder.getMedicationOne());

        // ADD ALLERGY
        ExtentReportManager.getTest().log(Status.INFO, "Adding allergies to health profile");
        invitePatientPage.clickAddAllergyButtonForAccountHolder();
        invitePatientPage.setDrugAllergySetOne(testDataForAccountHolder.getAllergyOne(), testDataForAccountHolder.getAllergyReactionOne(), testDataForAccountHolder.getDrugAllergyCategory());
        invitePatientPage.clickAddAllergyButtonForAccountHolder();
        invitePatientPage.setEnvironmentAllergySetOne(testDataForAccountHolder.getAllergyTwo(), testDataForAccountHolder.getAllergyReactionTwo(), testDataForAccountHolder.getEnvironmentAllergyCategory());

        ExtentReportManager.getTest().log(Status.INFO, "Submitting invite patient form");
        invitePatientPage.clickAddPatientButton();
        ExtentReportManager.getTest().log(Status.INFO, "Invite patient form submitted successfully");
    }

    @Test(priority = 2)
    public void testValidatePatientChartHealthProfile() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Validate Patient Chart Health Profile");
        switchToTab(1);
        Thread.sleep(5000);
        if(!patientChart.isPatientChart()){
            ExtentReportManager.getTest().log(Status.INFO, "Patient chart not visible – test skipped");
            Assert.fail("Patient chart page not loaded.");
        }
        patientChart.clickHealthProfileButton();

        // Validate allergy and medication details in patient chart
        ExtentReportManager.getTest().log(Status.INFO, "Validating allergy and medication details in patient chart");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyOne().toLowerCase(), patientChart.getFirstDrugAllergyName().toLowerCase(),
                "First allergy name in patient chart does not match in Health profile of Provider Portal");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyReactionOne(), patientChart.getFirstDrugReactionName(),
                "First drug allergy reaction in patient chart does not match the value in the Health Profile of the Provider Portal");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyTwo().toLowerCase(), patientChart.getFirstEnvironmentDrugAllergyName().toLowerCase(),
                "second allergy name in patient chart does not match in Health profile of Provider Portal");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyReactionTwo(), patientChart.getFirstEnvironmentReactionName(),
                "First environmental allergy reaction in patient chart does not match the value in the Health Profile of the Provider Portal");

        softAssert.assertEquals(testDataForAccountHolder.getMedicationOne().toLowerCase(), patientChart.getFirstMedicationName().toLowerCase(),
                "medication name in patient chart does not match in Health profile of Provider Portal");
        ExtentReportManager.getTest().log(Status.INFO, "Patient chart health profile validated successfully");
        softAssert.assertAll();
    }

    @Test(priority = 3)
    public void testSetPasswordAndLoginToPortal() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Set Password and Login to Patient Portal");
        // Open YopMail and set password for invited patient
        //newTabAndLaunchYopMail();
        ExtentReportManager.getTest().log(Status.INFO, "Accessing YopMail to set password");
        yopMail.clickSetPasswordMail(testDataForAccountHolder.getEmail());
        switchToTab(3);
        setPasswordPage.setPassword("Welcome@123");
        ExtentReportManager.getTest().log(Status.INFO, "Password set successfully, logging into Patient Portal");
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");
        ExtentReportManager.getTest().log(Status.INFO, "Logged into Patient Portal successfully");
    }

    @Test(priority = 4, dependsOnMethods = "testSetPasswordAndLoginToPortal")
    public void testPatientPortalHealthProfileValidations() {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Patient Portal Health Profile Validations");
        homePagePatPortal.clickMyProfile();
        myProfilePage.clickHealthProfileLink();
        myProfilePage.clickHealthProfileOfAccountHolder();
        myProfilePage.clickAllergyHealthProfile();

        // Validate allergy details in patient portal profile
        ExtentReportManager.getTest().log(Status.INFO, "Validating allergy details in patient portal profile");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyOne().toLowerCase(), myProfilePage.getDrugAllergyOneValue().toLowerCase(),
                "Allergy One name in patient portal profile does not match in Health profile of My Profile");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyReactionOne().toLowerCase(), myProfilePage.getDrugReactionOneValue().toLowerCase(),
                "Reaction One in patient portal profile does not match in Health profile of My Profile");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyTwo().toLowerCase(), myProfilePage.getEnvironmentAllergyOneValue().toLowerCase(),
                "Allergy Two name in patient portal profile does not match in Health profile of My Profile");
        softAssert.assertEquals(testDataForAccountHolder.getAllergyReactionTwo().toLowerCase(), myProfilePage.getEnvironmentReactionOneValue().toLowerCase(),
                "Reaction Two name in patient portal profile does not match in Health profile of My Profile");

        myProfilePage.clickBackButtonInHealthProfile();
        // Validate medication details in patient portal profile
        myProfilePage.clickMedicationHealthProfile();
        ExtentReportManager.getTest().log(Status.INFO, "Validating medication details in patient portal profile");
        softAssert.assertEquals(testDataForAccountHolder.getMedicationOne().toLowerCase(), myProfilePage.getMedicationOneValue().toLowerCase(),
                "MedicationOne name in patient portal profile does not match in Health profile of My Profile");
        myProfilePage.clickBackButtonInHealthProfile();
        myProfilePage.clickBackButtonInHealthProfile();
        myProfilePage.clickBackButtonInHealthProfile();
        ExtentReportManager.getTest().log(Status.INFO, "Patient portal health profile validated successfully");
        softAssert.assertAll();
    }
    @AfterClass()
    public void cleanUp() throws InterruptedException {
        myProfilePage.clickSettingsLink();
        myProfilePage.clickLogoutButton();
        myProfilePage.clickConfirmLogoutButton();
        switchToTab("SkyMD Provider Portal");
        patientChart.clickProfileIcon();
        patientChart.clickLogoutButton();
    }
}
