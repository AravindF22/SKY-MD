package tests.InvitePatients;

import utils.ConfigReader;
import utils.TestData;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ProviderPortal.DashBoardPage;
import pages.ProviderPortal.InvitePatientPage;
import pages.ProviderPortal.LoginPage;
import pages.ProviderPortal.PatientChart;
import pages.YopMail;
import utils.ExtentReportManager;
import com.aventstack.extentreports.Status;

import java.io.IOException;
import java.time.Duration;

/**
 * Test Case: TC_IP009
 * Description: Invite an account holder and add a ward (legal guardian of 18+ years) with an existing referral clinic,
 *              then verify that the referral section in the patient chart displays the correct provider and clinic details for the ward.
 */
public class TC_IP014_AddWardWithExistingReferralClinic extends BaseTest {
    public LoginPage loginPage;
    public DashBoardPage dashBoardPage;
    public InvitePatientPage invitePatientPage;
    public PatientChart patientChart;
    public YopMail yopMail;
    public TestData testDataForAccountHolder;
    public TestData testDataForWard;
    public TestData testDataForProvider;
    public SoftAssert softAssert;

    @BeforeClass
    public void setUp() throws IOException {
        //Loading config File
        driver.get(ConfigReader.getProperty("ProviderPortalUrl"));

        //Test data for account holder and provider
        testDataForAccountHolder = new TestData();
        testDataForProvider = new TestData();
        testDataForWard = new TestData();

        // Login as MA
        loginPage = new LoginPage(driver);
        loginPage.setEmailAs(ConfigReader.getProperty("MA_Email"));
        loginPage.setPasswordAs(ConfigReader.getProperty("MA_Password"));
        loginPage.clickLoginButton();
    }
    @BeforeMethod
    public void initializeAsset() throws IOException {
        softAssert = new SoftAssert();
        loginPage = new LoginPage(driver);
        dashBoardPage = new DashBoardPage(driver);
        invitePatientPage = new InvitePatientPage(driver);
        patientChart = new PatientChart(driver);
        yopMail = new YopMail(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    @Test(priority = 1)
    public void testFillAccountHolderMandatoryDetails(){
        // Log the start of the test
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Fill Account Holder Mandatory Details");

        // Navigate to Invite Patient page
        dashBoardPage.clickInvitePatientLink();
        ExtentReportManager.getTest().log(Status.INFO, "Entering AH mandatory details");
        // Fill mandatory fields for account holder
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());
    }
    @Test(priority = 2)
    public void testAddChildAndFillInvitePatientFormWithReferralClinic() {
        // Log the start of the test
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Add Ward and Fill Invite Patient Form With Referral Clinic");
        // Add ward fields
        invitePatientPage.clickAddAdditionalPatientBtnForPatientOne();
        ExtentReportManager.getTest().log(Status.INFO, "Clicked to add additional patient (Ward)");
        invitePatientPage.selectPatientTypeForPatientOne("Ward (legal guardian of 18+ years)");
        ExtentReportManager.getTest().log(Status.INFO, "Selected patient type: Ward (legal guardian of 18+ years)");
        ExtentReportManager.getTest().log(Status.INFO, "Entering Ward mandatory details");
        invitePatientPage.setFirstNameForPatientOne(testDataForWard.getFname());
        invitePatientPage.setLastNameForPatientOne(testDataForWard.getLname());
        invitePatientPage.setZipCodeForPatientOne(testDataForWard.getZipCode());

        ExtentReportManager.getTest().log(Status.INFO, "Entering Ward Referral details");
        // Fill the referral section for ward
        invitePatientPage.clickReferralClinicCheckBoxForPatientOne();
        invitePatientPage.setProviderFirstNameInPatientOneReferralClinic(testDataForProvider.getFname());
        invitePatientPage.setProviderLastNameInPatientOneReferralClinic(testDataForProvider.getLname());
        invitePatientPage.selectClinicStateInPatientOneReferralClinic(testDataForProvider.getReferralClinicState());
        invitePatientPage.selectClinicInPatientOneReferralClinic(testDataForProvider.getReferralClinic());

        // Submit the form for ward
        invitePatientPage.clickAddPatientButton();
        ExtentReportManager.getTest().log(Status.INFO, "Clicked Add Patient button for ward");
    }
    @Test(priority = 2)
    public void testVerifyReferralSectionInPatientChart() throws InterruptedException {
        // Log the start of the test
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Verify Referral Section In Patient Chart");
        switchToTab(1);
        if(!patientChart.isPatientChart()){
            ExtentReportManager.getTest().log(Status.INFO, "Patient chart not visible – test skipped");
            Assert.fail("Patient chart page not loaded.");
        }
        Thread.sleep(3000);
        // Page navigates to Patient chart and searches for patient
        patientChart.searchPatient(testDataForWard.getFullName());
        ExtentReportManager.getTest().log(Status.INFO, "Searched for ward in patient chart: " + testDataForWard.getFullName());

        // Assert provider name in a referral section
        softAssert.assertEquals(testDataForProvider.getFullName(), patientChart.getProviderNameFromReferralSection(),
                "Provider name in the referral section of AH is mismatching");
        ExtentReportManager.getTest().log(Status.INFO, "Verified provider name in referral section");

        // Assert clinic name in a referral section
        softAssert.assertEquals(testDataForProvider.getReferralClinic(), patientChart.getClinicNameFromReferralSection(),
                "Clinic name in the referral section of AH is mismatching");
        ExtentReportManager.getTest().log(Status.INFO, "Verified clinic name in referral section");
        ExtentReportManager.getTest().log(Status.INFO, "Referral section in patient chart validated successfully");
        softAssert.assertAll();
    }
    //@AfterClass()
    public void cleanUp() throws InterruptedException {
       // switchToTab("SkyMD Provider Portal");
        patientChart.clickProfileIcon();
        patientChart.clickLogoutButton();
    }
}
