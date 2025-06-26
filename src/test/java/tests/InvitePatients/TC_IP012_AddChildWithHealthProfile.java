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

public class TC_IP012_AddChildWithHealthProfile extends BaseTest {
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
        // Load configuration properties file
        loadPropFile();
        // Launch Provider Portal
        driver.get(property.getProperty("ProviderPortalUrl"));

        // Initialize test data for account holder and child
        testDataForAccountHolder = new TestData();
        testDataForChild = new TestData();
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
     * Test: Invite an account holder and add a child with Health profile.
     * Steps:
     * 1. Navigate to Invite Patient page
     * 2. Fill in account holder details
     * 3. Add child as dependent with mandatory and health profile fields
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
        ExtentReportManager.getTest().log(Status.INFO, "Adding child's mandatory details");
        invitePatientPage.clickAddAdditionalPatientBtnForPatientOne();
        invitePatientPage.selectPatientTypeForPatientOne("Child");
        invitePatientPage.setFirstNameForPatientOne(testDataForChild.getFname());
        invitePatientPage.setLastNameForPatientOne(testDataForChild.getLname());
        invitePatientPage.setZipCodeForPatientOne(testDataForChild.getZipCode());

        // Add health profile details for child
        ExtentReportManager.getTest().log(Status.INFO, "Adding child's health profile details");
        invitePatientPage.clickHealthProfileCheckboxForPatientOne();
        ExtentReportManager.getTest().log(Status.INFO, "Adding medication for child");
        invitePatientPage.clickAddMedicationButtonForPatientOne();
        invitePatientPage.selectMedicationForPatientOne(testDataForChild.getMedicationOne());

        // Add allergies for child
        ExtentReportManager.getTest().log(Status.INFO, "Adding first allergy for child");
        invitePatientPage.clickAddAllergyButtonForPatientOne();
        invitePatientPage.setAllergySetOneForPatientOne(testDataForChild.getAllergyOne(), testDataForChild.getAllergyReactionOne(), testDataForChild.getDrugAllergyCategory());
        ExtentReportManager.getTest().log(Status.INFO, "Adding second allergy for child");
        invitePatientPage.clickAddAllergyButtonForPatientOne();
        invitePatientPage.setAllergySetTwoForPatientOne(testDataForChild.getAllergyTwo(), testDataForChild.getAllergyReactionTwo(), testDataForChild.getEnvironmentAllergyCategory());
        ExtentReportManager.getTest().log(Status.INFO, "Submitting patient details");
        invitePatientPage.clickAddPatientButton();
    }
    /*
     * Test: Validate the health profile of the invited child in the Provider Portal's patient chart.
     * Steps:
     * 1. Switch to patient chart tab
     * 2. Search for the child
     * 3. Validate allergy and medication details
     */
    @Test(priority = 2)
    public void testValidatePatientChartHealthProfile() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Switching to patient chart tab");
        switchToTab(1);
        if(!patientChart.isPatientChart()){
            ExtentReportManager.getTest().log(Status.INFO, "Patient chart not visible – test skipped");
            Assert.fail("Patient chart page not loaded.");
        }
        Thread.sleep(3000);
        //Page navigate to Patient chart
        //search for patient
        ExtentReportManager.getTest().log(Status.INFO, "Searching for child in patient chart");
        patientChart.searchPatient(testDataForChild.getFullName());
        Thread.sleep(1000);
        ExtentReportManager.getTest().log(Status.INFO, "Opening Health Profile in patient chart");
        patientChart.clickHealthProfileButton();
        Thread.sleep(1000);
        // Validate first allergy details
        ExtentReportManager.getTest().log(Status.INFO, "Validating first allergy details in patient chart");
        softAssert.assertEquals(testDataForChild.getAllergyOne().toLowerCase(), patientChart.getFirstDrugAllergyName().toLowerCase(),
                "First allergy name in patient chart does not match in Health profile of Provider Portal");
        softAssert.assertEquals(testDataForChild.getAllergyReactionOne(), patientChart.getFirstDrugReactionName(),
                "First drug allergy reaction in patient chart does not match the value in the Health Profile of the Provider Portal");
        // Validate second allergy details
        ExtentReportManager.getTest().log(Status.INFO, "Validating second allergy details in patient chart");
        softAssert.assertEquals(testDataForChild.getAllergyTwo().toLowerCase(), patientChart.getFirstEnvironmentDrugAllergyName().toLowerCase(),
                "second allergy name in patient chart does not match in Health profile of Provider Portal");
        softAssert.assertEquals(testDataForChild.getAllergyReactionTwo(), patientChart.getFirstEnvironmentReactionName(),
                "First environmental allergy reaction in patient chart does not match the value in the Health Profile of the Provider Portal");

        // Validate medication details
        ExtentReportManager.getTest().log(Status.INFO, "Validating medication details in patient chart");
        softAssert.assertEquals(testDataForChild.getMedicationOne().toLowerCase(), patientChart.getFirstMedicationName().toLowerCase(),
                "medication name in patient chart does not match in Health profile of Provider Portal");
        softAssert.assertAll();
    }
    /*
     * Test: Set password for invited account holder and login to Patient Portal.
     * Steps:
     * 1. Open YopMail and set password
     * 2. Login to Patient Portal with new credentials
     */
    @Test(priority = 3)
    public void testSetPasswordAndLoginToPortal() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        ExtentReportManager.getTest().log(Status.INFO, "Switching to YopMail to set password");
        String email = testDataForAccountHolder.getEmail();
        // newTabAndLaunchYopMail();
        yopMail.clickSetPasswordMail(email);
        switchToTab(3);
        Thread.sleep(1000);
        setPasswordPage = new SetPasswordPage(driver);
        setPasswordPage.setPassword("Welcome@123");
        ExtentReportManager.getTest().log(Status.INFO, "Password set successfully from YopMail");
    }
    /*
     * Test: Validate health profile details in Patient Portal for the child.
     * Steps:
     * 1. Navigate to My Profile > Health Profile > Child
     * 2. Validate allergy and medication details
     */
    @Test(priority = 4, dependsOnMethods = "testSetPasswordAndLoginToPortal")
    public void testPatientPortalHealthProfileValidations() {
        ExtentReportManager.getTest().log(Status.INFO, "Navigating to My Profile in Patient Portal");
        homePagePatPortal.clickMyProfile(); // Go to My Profile
        ExtentReportManager.getTest().log(Status.INFO, "Opening Health Profile section");
        myProfilePage.clickHealthProfileLink(); // Open Health Profile
        ExtentReportManager.getTest().log(Status.INFO, "Selecting child's health profile");
        myProfilePage.clickHealthProfileOfChild(); // Select child's health profile
        ExtentReportManager.getTest().log(Status.INFO, "Opening allergy section in health profile");
        myProfilePage.clickAllergyHealthProfile(); // Open allergy section

        //validate allergy
        ExtentReportManager.getTest().log(Status.INFO, "Validating first allergy in patient portal profile");
        softAssert.assertEquals(testDataForChild.getAllergyOne().toLowerCase(), myProfilePage.getDrugAllergyOneValue().toLowerCase(),
                "Allergy One name in patient portal profile does not match in Health profile of My Profile");
        ExtentReportManager.getTest().log(Status.INFO, "Validating first reaction in patient portal profile");
        softAssert.assertEquals(testDataForChild.getAllergyReactionOne().toLowerCase(), myProfilePage.getDrugReactionOneValue().toLowerCase(),
                "Reaction One in patient portal profile does not match in Health profile of My Profile");

        ExtentReportManager.getTest().log(Status.INFO, "Validating second allergy in patient portal profile");
        softAssert.assertEquals(testDataForChild.getAllergyTwo().toLowerCase(), myProfilePage.getEnvironmentAllergyOneValue().toLowerCase(),
                "Allergy Two name in patient portal profile does not match in Health profile of My Profile");
        ExtentReportManager.getTest().log(Status.INFO, "Validating second reaction in patient portal profile");
        softAssert.assertEquals(testDataForChild.getAllergyReactionTwo().toLowerCase(), myProfilePage.getEnvironmentReactionOneValue().toLowerCase(),
                "Reaction Two name in patient portal profile does not match in Health profile of My Profile");

        myProfilePage.clickBackButtonInHealthProfile(); // Go back from allergy section
        ExtentReportManager.getTest().log(Status.INFO, "Opening medication section in health profile");
        //validate medication
        myProfilePage.clickMedicationHealthProfile(); // Open medication section
        ExtentReportManager.getTest().log(Status.INFO, "Validating medication in patient portal profile");
        softAssert.assertEquals(testDataForChild.getMedicationOne().toLowerCase(), myProfilePage.getMedicationOneValue().toLowerCase(),
                "MedicationOne name in patient portal profile does not match in Health profile of My Profile");
        myProfilePage.clickMyProfileLink(); // Return to My Profile
        softAssert.assertAll();
    }
    @AfterClass()
    public void cleanUp() throws InterruptedException {
        // Logout from Patient Portal and Provider Portal after tests
        myProfilePage.clickSettingsLink();
        myProfilePage.clickLogoutButton();
        myProfilePage.clickConfirmLogoutButton();
        switchToTab("SkyMD Provider Portal");
        patientChart.clickProfileIcon();
        patientChart.clickLogoutButton();
    }
}
