package tests.InvitePatients;
import Utils.TestData;
import base.BaseTest;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.ProviderPortal.DashBoardPage;
import pages.ProviderPortal.InvitePatientPage;
import pages.ProviderPortal.LoginPage;
import pages.ProviderPortal.PatientChart;

import java.io.IOException;
import java.time.Duration;

public class TC_IP002AddAccountHolderWithExistingReferralClinic extends BaseTest{
    public LoginPage loginPage;
    public DashBoardPage dashBoardPage;
    public TestData testDataForAccountHolder;
    public TestData testDataForProvider;
    public InvitePatientPage invitePatientPage;
    public PatientChart patientChart;
    public SoftAssert softAssert;

    @BeforeClass
    public void setUp() throws IOException {//Loading config File
        loadPropFile();
        driver.get(property.getProperty("ProviderPortalUrl"));

        //Test data for account holder and provider
        testDataForAccountHolder = new TestData();
        testDataForProvider = new TestData();

        // Login as MA
        loginPage = new LoginPage(driver);
        loginPage.setEmailAs(property.getProperty("MA_Email"));
        loginPage.setPasswordAs(property.getProperty("MA_Password"));
        loginPage.clickLoginButton();
    }
    @BeforeMethod
    public void initializeAsset() throws IOException {
        softAssert = new SoftAssert();
    }
    @Test(priority = 1)
    public void testFillInvitePatientFormWithReferralClinic() throws IOException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //Navigate to Invite Patient
        dashBoardPage = new DashBoardPage(driver);
        dashBoardPage.clickInvitePatientLink();

        //Mandatory Fields
        invitePatientPage = new InvitePatientPage(driver);
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());

        //Referral section
        invitePatientPage.clickReferralClinicCheckBoxForAccountHolder();
        invitePatientPage.setProviderFirstName(testDataForProvider.getFname());
        invitePatientPage.setProviderLastName(testDataForProvider.getLname());
        invitePatientPage.selectClinicStateForAccountHolder(testDataForProvider.getReferralClinicState());
        invitePatientPage.selectClinicForAccountHolder(testDataForProvider.getReferralClinic());

        invitePatientPage.clickAddPatientButton();
    }
    @Test(priority = 2)
    public void testVerifyReferralSectionInPatientChart() throws IOException, InterruptedException {

        switchToTab(1);

        //Page navigate to Patient chart
        patientChart = new PatientChart(driver);

        softAssert.assertEquals(testDataForProvider.getFullName(), patientChart.getProviderNameFromReferralSection(),"Provider name in the referral section of AH is mismatching");
        softAssert.assertEquals(testDataForProvider.getReferralClinic(), patientChart.getClinicNameFromReferralSection(),"Clinic name in the referral section of AH is mismatching");
        softAssert.assertAll();
    }
   // @AfterClass()
    public void cleanUp(){
        patientChart = new PatientChart(driver);
        patientChart.clickProfileIcon();
        patientChart.clickLogoutButton();
    }
}
