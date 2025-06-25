package tests.InvitePatients;

import Utils.TestData;
import base.BaseTest;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
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
 * Test Case: TC_IP008
 * Description: Invite an account holder and add a child with an existing referral clinic,
 *              then verify that the referral section in the patient chart displays the correct provider and clinic details for the child.
 */
public class TC_IP008AddChildWithExistingReferralClinic extends BaseTest {
    public LoginPage loginPage;
    public DashBoardPage dashBoardPage;
    public InvitePatientPage invitePatientPage;
    public PatientChart patientChart;
    public YopMail yopMail;
    public TestData testDataForAccountHolder;
    public TestData testDataForChild;
    public TestData testDataForProvider;
    public SoftAssert softAssert;

    @BeforeClass
    public void setUp() throws IOException {
        //Loading config File
        loadPropFile();
        driver.get(property.getProperty("ProviderPortalUrl"));

        //Test data for account holder and provider
        testDataForAccountHolder = new TestData();
        testDataForProvider = new TestData();
        testDataForChild = new TestData();

        // Login as MA
        loginPage = new LoginPage(driver);
        loginPage.setEmailAs(property.getProperty("MA_Email"));
        loginPage.setPasswordAs(property.getProperty("MA_Password"));
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
    }
    @Test(priority = 1)
    public void testFillAccountHolderMandatoryDetails(){
        // Log the start of the test section
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Fill Account Holder Mandatory Details");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        dashBoardPage.clickInvitePatientLink();
        // Fill account holder mandatory details
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());
    }
    @Test(priority = 2)
    public void testAddChildAndFillInvitePatientFormWithReferralClinic() throws IOException {
        // Log the start of the test section
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Add Child and Fill Invite Patient Form With Referral Clinic");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        ExtentReportManager.getTest().log(Status.INFO, "Adding child mandatory details");
        // Add child fields
        invitePatientPage.clickAddAdditionalPatientBtnForPatientOne();
        invitePatientPage.selectPatientTypeForPatientOne("Child");
        invitePatientPage.setFirstNameForPatientOne(testDataForChild.getFname());
        invitePatientPage.setLastNameForPatientOne(testDataForChild.getLname());
        invitePatientPage.setZipCodeForPatientOne(testDataForChild.getZipCode());
        ExtentReportManager.getTest().log(Status.INFO, "Adding child referral details");
        // Fill referral clinic section for child
        invitePatientPage.clickReferralClinicCheckBoxForPatientOne();
        invitePatientPage.setProviderFirstNameInPatientOneReferralClinic(testDataForProvider.getFname());
        invitePatientPage.setProviderLastNameInPatientOneReferralClinic(testDataForProvider.getLname());
        invitePatientPage.selectClinicStateInPatientOneReferralClinic(testDataForProvider.getReferralClinicState());
        invitePatientPage.selectClinicInPatientOneReferralClinic(testDataForProvider.getReferralClinic());

        invitePatientPage.clickAddPatientButton();
        ExtentReportManager.getTest().log(Status.INFO, "child added successfully with referral deatils");
    }
    @Test(priority = 2)
    public void testVerifyReferralSectionInPatientChart() throws IOException, InterruptedException {
        // Log the start of the test section
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Verify Referral Section In Patient Chart");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        switchToTab(1);
        if(!patientChart.isPatientChart()){
            ExtentReportManager.getTest().log(Status.INFO, "Patient chart not visible – test skipped");
            Assert.fail("Patient chart page not loaded.");
        }
        // Search for patient and verify referral section
        patientChart.searchPatient(testDataForChild.getFullName());
        softAssert.assertEquals(testDataForProvider.getFullName(), patientChart.getProviderNameFromReferralSection(),
                "Provider name in the referral section of AH is mismatching");
        softAssert.assertEquals(testDataForProvider.getReferralClinic(), patientChart.getClinicNameFromReferralSection(),
                "Clinic name in the referral section of AH is mismatching");
        ExtentReportManager.getTest().log(Status.PASS, "Referral section in patient chart validated successfully");
        softAssert.assertAll();
    }
    @AfterClass()
    public void cleanUp() throws InterruptedException {
        //switchToTab("SkyMD Provider Portal");
        patientChart.clickProfileIcon();
        patientChart.clickLogoutButton();
    }
}
