package tests.InvitePatients;
import Utils.TestData;
import base.BaseTest;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.PatientPortal.*;
import pages.ProviderPortal.DashBoardPage;
import pages.ProviderPortal.InvitePatientPage;
import pages.ProviderPortal.LoginPage;
import pages.ProviderPortal.PatientChart;
import pages.YopMail;
import com.aventstack.extentreports.Status;
import Utils.ExtentReportManager;

import java.io.IOException;
import java.time.Duration;

/**
 * Test Case: TC_IP003
 * Description: Invite an account holder and add a child with only mandatory details,
 *              then verify the dependent and visit flow in the Patient Portal,
 *              including validations in the patient chart.
 */
public class TC_IP003AddChildWithMandatoryDetails extends BaseTest{
    public LoginPage loginPage;
    public DashBoardPage dashBoardPage;
    public InvitePatientPage invitePatientPage;
    public PatientChart patientChart;
    public SetPasswordPage setPasswordPage;
    public PatientPortalLoginPage loginPagePatientPortal;
    public PatientPortalHomePage homePagePatPortal;
    public PatientPortalMyProfilePage myProfilePage;
    public DermatologyVisitPage dermatologyVisitPage;

    public TestData testDataForAccountHolder;
    public TestData testDataForChild;
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
    }
    /**
     * Invite an account holder and add a child with only mandatory details.
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

        // Submit the invitation
        ExtentReportManager.getTest().log(Status.INFO, "Submitting the invitation for account holder and child");
        invitePatientPage.clickAddPatientButton();
    }
    /**
     * Validate the patient chart for both account holder and child.
     */
    @Test(priority = 2, dependsOnMethods = "testInviteAccountHolderAndChild")
    public void testPatientChartValidations() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Validate Patient Chart for Account Holder and Child");
        // Switch to the patient chart tab
        ExtentReportManager.getTest().log(Status.INFO, "Switching to Patient Chart tab");
        switchToTab(1);

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
        softAssert.assertEquals(testDataForChild.getFullName(), patientChart.getNameInThePatientChart(),
                "Child name mismatch in Patient Chart");
        softAssert.assertEquals(testDataForChild.getZipCode(), patientChart.getZipcodeInPatientChart(),
                "Child zip code mismatch in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getFullName(), patientChart.getGuardianName(),
                "Guardian name mismatch in Patient Chart");
        softAssert.assertAll();
    }
    /**
     * Set password for the invited account holder using YopMail.
     */
    @Test(priority = 3)
    public void testSetPasswordViaYopMail() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Set Password via YopMail");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        // Open YopMail in a new tab and set password for the account holder
        ExtentReportManager.getTest().log(Status.INFO, "Opening YopMail and setting password for account holder");
        newTabAndLaunchYopMail();
        YopMail yopMail = new YopMail(driver);
        yopMail.clickSetPasswordMail(testDataForAccountHolder.getEmail());

        switchToTab(3);
        setPasswordPage.setPassword("Welcome@123");
    }
    /**
     * Login to Patient Portal, verify dependent, and complete Dermatology Visit flow for the child.
     */
    @Test(priority = 4, dependsOnMethods = {"testSetPasswordViaYopMail"})
    public void testPatientPortalDependentAndVisitFlow() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Patient Portal Dependent and Visit Flow");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        // Login to Patient Portal as account holder
        ExtentReportManager.getTest().log(Status.INFO, "Logging in to Patient Portal as account holder");
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");

        // Navigate to homepage and then to profile to verify dependent
        ExtentReportManager.getTest().log(Status.INFO, "Navigating to My Profile and verifying dependent");
        homePagePatPortal.clickMyProfile();

        myProfilePage.clickDependents();
        softAssert.assertEquals(testDataForChild.getFullName(), myProfilePage.getDependentOneName(),
                "Dependent's name does not match the expected full name for Child.");
        softAssert.assertEquals("Child", myProfilePage.getDependentOneType(),
                "Dependent's type is not 'Child' as expected.");

        // Go back to home page and start Dermatology Visit for the child
        ExtentReportManager.getTest().log(Status.INFO, "Starting Dermatology Visit for the child");
        myProfilePage.clickHomePageLink();
        homePagePatPortal.selectDermatologyVisit();
        dermatologyVisitPage.clickSelectPatient();
        Thread.sleep(1000);
        dermatologyVisitPage.selectPatientAsMyChild();
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();
        Thread.sleep(1000);
        softAssert.assertEquals(testDataForChild.getFullName(), dermatologyVisitPage.getNameOfTheChildInSelectChild());
        softAssert.assertAll();
    }
    /**
     * Clean up: Log out from Patient Portal and Provider Portal, and close tabs.
     */
    @AfterClass()
    public void cleanUp() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        // Navigate back from Dermatology Visit and home page
        dermatologyVisitPage.clickBackArrowForVisitForm();
        dermatologyVisitPage.clickBackArrowForHomePage();

        // Log out from Patient Portal
        homePagePatPortal.clickMyProfile();

        myProfilePage.clickSettingsLink();
        myProfilePage.clickLogoutButton();
        myProfilePage.clickConfirmLogoutButton();

        // Switch back to Provider Portal and log out
        switchToTab("SkyMD Provider Portal");
        patientChart.clickProfileIcon();
        patientChart.clickLogoutButton();
    }
}
