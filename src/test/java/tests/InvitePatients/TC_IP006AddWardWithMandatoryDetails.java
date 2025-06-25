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
 * Test Case: TC_IP006
 * Description: Invite an account holder and add a ward (legal guardian of 18+ years) with mandatory details,
 *              then verify that all information is correctly displayed in the patient chart and patient portal profile.
 */
public class TC_IP006AddWardWithMandatoryDetails extends BaseTest {
    public LoginPage loginPage;
    public DashBoardPage dashBoardPage;
    public InvitePatientPage invitePatientPage;
    public PatientChart patientChart;
    public SetPasswordPage setPasswordPage;
    public PatientPortalLoginPage loginPagePatientPortal;
    public PatientPortalHomePage homePagePatPortal;
    public PatientPortalMyProfilePage myProfilePage;
    public TestData testDataForAccountHolder;
    public TestData testDataForWard;
    public SoftAssert softAssert;
    public YopMail yopMail;

    @BeforeClass
    public void setUp() throws IOException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        loadPropFile();
        driver.get(property.getProperty("ProviderPortalUrl"));

        testDataForAccountHolder = new TestData();
        testDataForWard = new TestData();

        loginPage = new LoginPage(driver);
        loginPage.setEmailAs(property.getProperty("MA_Email"));
        loginPage.setPasswordAs(property.getProperty("MA_Password"));
        loginPage.clickLoginButton();

        dashBoardPage = new DashBoardPage(driver);
        dashBoardPage.clickInvitePatientLink();
    }
    @BeforeMethod
    public void initializeAssert(){
        softAssert = new SoftAssert();
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
    public void testInviteAccountHolderAndWard() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // Log the start of the test
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Invite Account Holder and Add Ward with Mandatory Details");

        // Fill account holder details
        ExtentReportManager.getTest().log(Status.INFO, "Filling Account Holder details");
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());
        invitePatientPage.selectProviderNameAs(testDataForAccountHolder.getProviderName());

        // Add ward fields
        ExtentReportManager.getTest().log(Status.INFO, "Adding Ward (legal guardian of 18+ years) details");
        invitePatientPage.clickAddAdditionalPatientBtnForPatientOne();
        invitePatientPage.selectPatientTypeForPatientOne("Ward (legal guardian of 18+ years)");
        invitePatientPage.setFirstNameForPatientOne(testDataForWard.getFname());
        invitePatientPage.setLastNameForPatientOne(testDataForWard.getLname());
        invitePatientPage.setZipCodeForPatientOne(testDataForWard.getZipCode());

        // Submit the invite
        ExtentReportManager.getTest().log(Status.INFO, "Submitting invite for Account Holder and Ward");
        invitePatientPage.clickAddPatientButton();
    }

    @Test(priority = 2, dependsOnMethods = "testInviteAccountHolderAndWard")
    public void testPatientChartValidations() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // Switch to patient chart tab
        ExtentReportManager.getTest().log(Status.INFO, "Switching to Patient Chart tab and validating details");
        switchToTab(1);
        if(!patientChart.isPatientChart()){
            ExtentReportManager.getTest().log(Status.INFO, "Patient chart not visible – test skipped");
            Assert.fail("Patient chart page not loaded.");
        }
        // Account Holder validations
        ExtentReportManager.getTest().log(Status.INFO, "Validating Account Holder details in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getFullName(), patientChart.getNameInThePatientChart(),
                "Account Holder name mismatch in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getEmail(), patientChart.getEmailInThePatientChart(),
                "Account Holder email mismatch in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), patientChart.getZipcodeInPatientChart(),
                "Account Holder zip code mismatch in Patient Chart");

        // Search for Ward and validate
        ExtentReportManager.getTest().log(Status.INFO, "Searching and validating Ward details in Patient Chart");
        patientChart.searchPatient(testDataForWard.getFullName());
        Thread.sleep(2000);
        softAssert.assertEquals(testDataForWard.getFullName(), patientChart.getNameInThePatientChart(),
                "Ward name mismatch in Patient Chart");
        softAssert.assertEquals(testDataForWard.getZipCode(), patientChart.getZipcodeInPatientChart(),
                "Ward zip code mismatch in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getFullName(), patientChart.getGuardianName(),
                "Guardian name mismatch in Patient Chart");
        ExtentReportManager.getTest().log(Status.INFO, "Patient Chart validations completed");
        softAssert.assertAll();
    }
    @Test(priority = 3)
    public void testSetPasswordViaYopMail() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Thread.sleep(2000);
        // Open YopMail and set password for invited patient
        ExtentReportManager.getTest().log(Status.INFO, "Opening YopMail to set password for invited Account Holder");
        //newTabAndLaunchYopMail();
        yopMail.clickSetPasswordMail(testDataForAccountHolder.getEmail());
        switchToTab(3);
        setPasswordPage.setPassword("Welcome@123");
        ExtentReportManager.getTest().log(Status.INFO, "Password set successfully for invited Account Holder");
    }

    @Test(priority = 4, dependsOnMethods = "testSetPasswordViaYopMail")
    public void testPatientPortalDependentValidation() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // Log in to Patient Portal and validate dependent
        ExtentReportManager.getTest().log(Status.INFO, "Logging into Patient Portal and validating dependent details");
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");
        homePagePatPortal.clickMyProfile();
        myProfilePage.clickDependents();
        softAssert.assertEquals(testDataForWard.getFullName(), myProfilePage.getDependentOneName(),
                "Dependent's name does not match the expected full name for Ward.");
        softAssert.assertEquals("Ward", myProfilePage.getDependentOneType(),
                "Dependent's type is not 'Ward' as expected.");
        ExtentReportManager.getTest().log(Status.INFO, "Patient Portal dependent validation completed");
        softAssert.assertAll();
    }
    @AfterClass
    private void patientAndProviderPortalLogout() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        myProfilePage.clickSettingsLink();
        myProfilePage.clickLogoutButton();
        myProfilePage.clickConfirmLogoutButton();
        switchToTab("SkyMD Provider Portal");
        patientChart.clickProfileIcon();
        patientChart.clickLogoutButton();
    }
}
