package tests.InvitePatients;
import utils.ConfigReader;
import utils.TestData;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.ProviderPortal.DashBoardPage;
import pages.ProviderPortal.InvitePatientPage;
import pages.ProviderPortal.LoginPage;
import pages.ProviderPortal.PatientChart;
import utils.ExtentReportManager;
import com.aventstack.extentreports.Status;

import java.io.IOException;
import java.time.Duration;

/**
 * Test Case: TC_IP002
 * Description: Invite an account holder with an existing referral clinic
 *              and verify that the referral section in the patient chart displays the correct provider and clinic details.
 */
public class TC_IP002_AddAccountHolderWithExistingReferralClinic extends BaseTest{
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
        driver.get(ConfigReader.getProperty("ProviderPortalUrl"));
        // Initialize test data for account holder and provider
        testDataForAccountHolder = new TestData();
        testDataForProvider = new TestData();
        // Login as MA
        loginPage = new LoginPage(driver);
        loginPage.setEmailAs(ConfigReader.getProperty("MA_Email"));
        loginPage.setPasswordAs(ConfigReader.getProperty("MA_Password"));
        loginPage.clickLoginButton();
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
        ExtentReportManager.getTest().log(Status.INFO, "Invite Patient form submitted successfully");
        String accountHolderMandatoryDetailsHtml = "<b>Entered Mandatory Details (Account Holder):</b><br>" +
                "First Name: " + testDataForAccountHolder.getFname() + "<br>" +
                "Last Name: " + testDataForAccountHolder.getLname() + "<br>" +
                "Email: " + testDataForAccountHolder.getEmail() + "<br>" +
                "Mobile: " + testDataForAccountHolder.getMobileNumber() + "<br>" +
                "Zipcode: " + testDataForAccountHolder.getZipCode();
        ExtentReportManager.getTest().info(accountHolderMandatoryDetailsHtml);
        String referralClinicDetailsHtml = "<b>Entered Referral Clinic Details (Account Holder):</b><br>" +
                "Provider First Name: " + testDataForProvider.getFname() + "<br>" +
                "Provider Last Name: " + testDataForProvider.getLname() + "<br>" +
                "Clinic State: " + testDataForProvider.getReferralClinicState() + "<br>" +
                "Clinic: " + testDataForProvider.getReferralClinic();
        ExtentReportManager.getTest().info(referralClinicDetailsHtml);
    }

    /**
     * Test to verify referral section in the patient chart after inviting
     */
    @Test(priority = 2)
    public void testVerifyReferralSectionInPatientChart() throws IOException, InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Verify Referral Section In Patient Chart");
        switchToTab(1);
        Thread.sleep(5000);
        if(!patientChart.isPatientChart()){
            ExtentReportManager.getTest().log(Status.INFO, "Patient chart not visible – test skipped");
            Assert.fail("Patient chart page not loaded.");
        }
        ExtentReportManager.getTest().log(Status.INFO, "Verifying Referral details in Provider Portal");
        // Navigate to Patient Chart and verify referral details
        String expectedProviderName = testDataForProvider.getFullName();
        String actualProviderName = patientChart.getProviderNameFromReferralSection();
        softAssert.assertEquals(expectedProviderName, actualProviderName,"Provider name in the referral section of AH is mismatching");

        String expectedClinicName = testDataForProvider.getReferralClinic();
        String actualClinicName = patientChart.getClinicNameFromReferralSection();
        softAssert.assertEquals(expectedClinicName, actualClinicName,"Clinic name in the referral section of AH is mismatching");

        // Assert all soft assertions
        ExtentReportManager.getTest().log(Status.INFO, "Referral section in Patient Chart verified successfully");
        softAssert.assertAll();
    }

    /**
     * Cleanup method to logout after all tests are run
     */
    @AfterClass()
    public void cleanUp(){
        patientChart.clickProfileIcon();
        patientChart.clickLogoutButton();
    }
}
