package tests.InvitePatients;
import Utils.TestData;
import base.BaseTest;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.ProviderPortal.DashBoardPage;
import pages.ProviderPortal.InvitePatientPage;
import pages.ProviderPortal.LoginPage;
import pages.ProviderPortal.PatientChart;
import Utils.ExtentReportManager;
import com.aventstack.extentreports.Status;

import java.io.IOException;
import java.time.Duration;

/**
 * Test Case: TC_IP002
 * Description: Invite an account holder with an existing referral clinic
 *              and verify that the referral section in the patient chart displays the correct provider and clinic details.
 */
public class TC_IP002AddAccountHolderWithExistingReferralClinic extends BaseTest{
    public LoginPage loginPage;
    public DashBoardPage dashBoardPage;
    public TestData testDataForAccountHolder;
    public TestData testDataForProvider;
    public InvitePatientPage invitePatientPage;
    public PatientChart patientChart;
    public SoftAssert softAssert;

    /**
     * Setup method to load properties and login as MA before any tests run
     */
    @BeforeClass
    public void setUp() throws IOException {
        ExtentReportManager.getTest().log(Status.INFO, "Loading config file and navigating to Provider Portal");
        loadPropFile();
        driver.get(property.getProperty("ProviderPortalUrl"));

        // Initialize test data for account holder and provider
        testDataForAccountHolder = new TestData();
        testDataForProvider = new TestData();

        // Login as MA
        loginPage = new LoginPage(driver);
        loginPage.setEmailAs(property.getProperty("MA_Email"));
        loginPage.setPasswordAs(property.getProperty("MA_Password"));
        loginPage.clickLoginButton();
        ExtentReportManager.getTest().log(Status.INFO, "Logged in as MA");
    }

    /**
     * Initialize page objects and SoftAssert before each test method
     */
    @BeforeMethod
    public void initializeAsset() throws IOException {
        softAssert = new SoftAssert();
        dashBoardPage = new DashBoardPage(driver);
        invitePatientPage = new InvitePatientPage(driver);
        patientChart = new PatientChart(driver);
    }

    /**
     * Test to fill Invite Patient form with referral clinic details and submit
     */
    @Test(priority = 1)
    public void testFillInvitePatientFormWithReferralClinic() throws IOException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Fill Invite Patient Form With Referral Clinic");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Navigate to Invite Patient page
        dashBoardPage.clickInvitePatientLink();

        // Fill mandatory details for account holder
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());
        ExtentReportManager.getTest().log(Status.INFO, "Filling mandatory details in Invite Patient form");

        // Fill referral clinic details
        invitePatientPage.clickReferralClinicCheckBoxForAccountHolder();
        invitePatientPage.setProviderFirstName(testDataForProvider.getFname());
        invitePatientPage.setProviderLastName(testDataForProvider.getLname());
        invitePatientPage.selectClinicStateForAccountHolder(testDataForProvider.getReferralClinicState());
        invitePatientPage.selectClinicForAccountHolder(testDataForProvider.getReferralClinic());
        ExtentReportManager.getTest().log(Status.INFO, "Filling referral clinic details in Invite Patient form");

        // Submit the Invite Patient form
        invitePatientPage.clickAddPatientButton();
        ExtentReportManager.getTest().log(Status.PASS, "Invite Patient form submitted successfully");
    }

    /**
     * Test to verify referral section in the patient chart after inviting
     */
    @Test(priority = 2)
    public void testVerifyReferralSectionInPatientChart() throws IOException, InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Verify Referral Section In Patient Chart");
        switchToTab(1);

        // Navigate to Patient Chart and verify referral details
        patientChart.clickPatientChartLink();
        String expectedProviderName = testDataForProvider.getFullName();
        String actualProviderName = patientChart.getProviderNameFromReferralSection();
        softAssert.assertEquals(expectedProviderName, actualProviderName,"Provider name in the referral section of AH is mismatching");

        String expectedClinicName = testDataForProvider.getReferralClinic();
        String actualClinicName = patientChart.getClinicNameFromReferralSection();
        softAssert.assertEquals(expectedClinicName, actualClinicName,"Clinic name in the referral section of AH is mismatching");

        // Assert all soft assertions
        softAssert.assertAll();
        ExtentReportManager.getTest().log(Status.PASS, "Referral section in Patient Chart verified successfully");
    }

    /**
     * Cleanup method to logout after all tests are run
     */
    @AfterClass()
    public void cleanUp(){
        ExtentReportManager.getTest().log(Status.INFO, "Logging out and cleaning up");
        patientChart.clickProfileIcon();
        patientChart.clickLogoutButton();
        ExtentReportManager.getTest().log(Status.INFO, "Logged out successfully");
    }
}
