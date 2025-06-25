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

public class TC_IP017AddChildWithHealthProfile extends BaseTest {
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
     * Invite an account holder and add a child with Health profile.
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

        ExtentReportManager.getTest().log(Status.INFO, "Adding child's health profile details");
        invitePatientPage.clickHealthProfileCheckboxForPatientOne();
        invitePatientPage.clickAddMedicationButtonForPatientOne();
        invitePatientPage.selectMedicationForPatientOne(testDataForChild.getMedicationOne());

        invitePatientPage.clickAddAllergyButtonForPatientOne();
        invitePatientPage.setAllergySetOneForPatientOne(testDataForChild.getAllergyOne(), testDataForChild.getAllergyReactionOne(), testDataForChild.getDrugAllergyCategory());
        invitePatientPage.clickAddAllergyButtonForPatientOne();
        invitePatientPage.setAllergySetTwoForPatientOne(testDataForChild.getAllergyTwo(), testDataForChild.getAllergyReactionTwo(), testDataForChild.getEnvironmentAllergyCategory());
        invitePatientPage.clickAddPatientButton();
    }
    @Test(priority = 2)
    public void testValidatePatientChartHealthProfile() throws InterruptedException {

        switchToTab(1);
        //Page navigate to Patient chart
        //search for patient
        patientChart.searchPatient(testDataForChild.getFullName());
        Thread.sleep(1000);
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
        myProfilePage.clickHealthProfileOfChild();
        myProfilePage.clickAllergyHealthProfile();

        //validate allergy
        softAssert.assertEquals(testDataForChild.getAllergyOne().toLowerCase(), myProfilePage.getDrugAllergyOneValue().toLowerCase(),
                "Allergy One name in patient portal profile does not match in Health profile of My Profile");
        softAssert.assertEquals(testDataForChild.getAllergyReactionOne().toLowerCase(), myProfilePage.getDrugReactionOneValue().toLowerCase(),
                "Reaction One in patient portal profile does not match in Health profile of My Profile");

        softAssert.assertEquals(testDataForChild.getAllergyTwo().toLowerCase(), myProfilePage.getEnvironmentAllergyOneValue().toLowerCase(),
                "Allergy Two name in patient portal profile does not match in Health profile of My Profile");
        softAssert.assertEquals(testDataForChild.getAllergyReactionTwo().toLowerCase(), myProfilePage.getEnvironmentReactionOneValue().toLowerCase(),
                "Reaction Two name in patient portal profile does not match in Health profile of My Profile");

        myProfilePage.clickBackButtonInHealthProfile();
        //validate medication
        myProfilePage.clickMedicationHealthProfile();
        softAssert.assertEquals(testDataForChild.getMedicationOne().toLowerCase(), myProfilePage.getMedicationOneValue().toLowerCase(),
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
