package tests.InvitePatients;

import Utils.ExtentReportManager;
import Utils.TestData;
import base.BaseTest;
import com.aventstack.extentreports.Status;
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

public class TC_IP018AddWardWithHealthProfile extends BaseTest {
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

        // Initialize test data for account holder and Ward
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
    /*
     * Invite an account holder and add a Ward with Health profile.
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
        ExtentReportManager.getTest().log(Status.INFO, "Adding Ward's mandatory details");
        invitePatientPage.clickAddAdditionalPatientBtnForPatientOne();
        invitePatientPage.selectPatientTypeForPatientOne("Ward (legal guardian of 18+ years)");
        invitePatientPage.setFirstNameForPatientOne(testDataForWard.getFname());
        invitePatientPage.setLastNameForPatientOne(testDataForWard.getLname());
        invitePatientPage.setZipCodeForPatientOne(testDataForWard.getZipCode());

        ExtentReportManager.getTest().log(Status.INFO, "Adding Ward's health profile details");
        invitePatientPage.clickHealthProfileCheckboxForPatientOne();
        invitePatientPage.clickAddMedicationButtonForPatientOne();
        invitePatientPage.selectMedicationForPatientOne(testDataForWard.getMedicationOne());

        invitePatientPage.clickAddAllergyButtonForPatientOne();
        invitePatientPage.setAllergySetOneForPatientOne(testDataForWard.getAllergyOne(), testDataForWard.getAllergyReactionOne(), testDataForWard.getDrugAllergyCategory());
        invitePatientPage.clickAddAllergyButtonForPatientOne();
        invitePatientPage.setAllergySetTwoForPatientOne(testDataForWard.getAllergyTwo(), testDataForWard.getAllergyReactionTwo(), testDataForWard.getEnvironmentAllergyCategory());
        invitePatientPage.clickAddPatientButton();
    }
    @Test(priority = 2)
    public void testValidatePatientChartHealthProfile() throws InterruptedException {

        switchToTab(1);
        //Page navigate to Patient chart
        //search for patient
        patientChart.searchPatient(testDataForWard.getFullName());
        Thread.sleep(1000);
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
    public void testSetPasswordAndLoginToPortal() throws InterruptedException {
        newTabAndLaunchYopMail();
        yopMail.clickSetPasswordMail(testDataForAccountHolder.getEmail());
        switchToTab(3);
        setPasswordPage.setPassword("Welcome@123");
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");
    }
    @Test(priority = 4, dependsOnMethods = "testSetPasswordAndLoginToPortal")
    public void testPatientPortalHealthProfileValidations() {
        homePagePatPortal.clickMyProfile();
        myProfilePage.clickHealthProfileLink();
        myProfilePage.clickHealthProfileOfWard();
        myProfilePage.clickAllergyHealthProfile();

        //validate allergy
        softAssert.assertEquals(testDataForWard.getAllergyOne().toLowerCase(), myProfilePage.getDrugAllergyOneValue().toLowerCase(),
                "Allergy One name in patient portal profile does not match in Health profile of My Profile");
        softAssert.assertEquals(testDataForWard.getAllergyReactionOne().toLowerCase(), myProfilePage.getDrugReactionOneValue().toLowerCase(),
                "Reaction One in patient portal profile does not match in Health profile of My Profile");

        softAssert.assertEquals(testDataForWard.getAllergyTwo().toLowerCase(), myProfilePage.getEnvironmentAllergyOneValue().toLowerCase(),
                "Allergy Two name in patient portal profile does not match in Health profile of My Profile");
        softAssert.assertEquals(testDataForWard.getAllergyReactionTwo().toLowerCase(), myProfilePage.getEnvironmentReactionOneValue().toLowerCase(),
                "Reaction Two name in patient portal profile does not match in Health profile of My Profile");

        myProfilePage.clickBackButtonInHealthProfile();
        //validate medication
        myProfilePage.clickMedicationHealthProfile();
        softAssert.assertEquals(testDataForWard.getMedicationOne().toLowerCase(), myProfilePage.getMedicationOneValue().toLowerCase(),
                "MedicationOne name in patient portal profile does not match in Health profile of My Profile");
        myProfilePage.clickMyProfileLink();
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
