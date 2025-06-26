package tests.InvitePatients;

import Utils.ExtentReportManager;
import Utils.TestData;
import base.BaseTest;
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
import com.aventstack.extentreports.Status;

import java.io.IOException;
import java.time.Duration;

/**
 * Test Case: TC_IP014
 * Description: Invite an account holder and add a ward (legal guardian of 18+ years) with insurance details,
 *              then verify that all insurance information is correctly displayed in the patient chart and patient portal profile for the ward.
 */
public class TC_IP016_AddWardWithInsuranceDetails extends BaseTest {
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
    public SoftAssert softAssert;
    @BeforeClass
    public void setUp() throws IOException {
        //Loading config File
        loadPropFile();
        driver.get(property.getProperty("ProviderPortalUrl"));

        //Test data for account holder and provider
        testDataForAccountHolder = new TestData();
        testDataForWard = new TestData();

        // Login as MA
        loginPage = new LoginPage(driver);
        loginPage.setEmailAs(property.getProperty("MA_Email"));
        loginPage.setPasswordAs(property.getProperty("MA_Password"));
        loginPage.clickLoginButton();

        //Navigate to Invite Patient
        dashBoardPage = new DashBoardPage(driver);
        dashBoardPage.clickInvitePatientLink();
    }
    @BeforeMethod
    public void initializeAsset() throws IOException {
        softAssert = new SoftAssert();
        loginPage = new LoginPage(driver);
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
    @Test(priority = 1)
    private void testAddChildAndInsuranceDetails() throws IOException, InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Add Ward with Insurance Details");
        //implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        // Fill in account holder details
        ExtentReportManager.getTest().log(Status.INFO, "Filling in account holder details");
        invitePatientPage = new InvitePatientPage(driver);
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());
        invitePatientPage.selectProviderNameAs(testDataForAccountHolder.getProviderName());

        // Add Ward as dependent with mandatory fields
        ExtentReportManager.getTest().log(Status.INFO, "Adding Ward as dependent with mandatory details");
        invitePatientPage.clickAddAdditionalPatientBtnForPatientOne();
        invitePatientPage.selectPatientTypeForPatientOne("Ward (legal guardian of 18+ years)");
        invitePatientPage.setFirstNameForPatientOne(testDataForWard.getFname());
        invitePatientPage.setLastNameForPatientOne(testDataForWard.getLname());
        invitePatientPage.setZipCodeForPatientOne(testDataForWard.getZipCode());

        // Fill primary insurance details
        ExtentReportManager.getTest().log(Status.INFO, "Filling primary insurance details for ward");
        invitePatientPage.checkInsuranceCheckboxForPatientOne();
        invitePatientPage.selectPrimaryInsuranceForPatientOne(testDataForWard.getPrimaryInsurance());
        invitePatientPage.setPrimaryInsuranceMemberName(testDataForWard.getFullName());
        invitePatientPage.setPrimaryInsuranceMemberIdForPatientOne(testDataForWard.getMemberIdForPrimaryInsurance());
        Thread.sleep(1000);
        invitePatientPage.setPrimaryInsuranceMemberDOBForPatientOne(testDataForWard.getDobForMajor());
        Thread.sleep(1000);
        invitePatientPage.selectPrimaryInsuranceRelationshipForPatientOne(testDataForWard.getRelationshipForPrimaryInsurance());

        // Fill secondary insurance
        ExtentReportManager.getTest().log(Status.INFO, "Filling secondary insurance details for ward");
        invitePatientPage.checkSecondaryInsuranceForPatientOne();
        invitePatientPage.selectSecondaryInsuranceForPatientOne(testDataForWard.getSecondaryInsurance());
        invitePatientPage.setSecondaryInsuranceMemberNameForPatientOne(testDataForWard.getMemberNameForSecondaryInsurance());
        invitePatientPage.setSecondaryInsuranceMemberIdForPatientOne(testDataForWard.getMemberIdForSecondaryInsurance());
        Thread.sleep(1000);
        invitePatientPage.setSecondaryInsuranceMemberDOBForPatientOne(testDataForWard.getMemberDobForSecondaryInsurance());
        Thread.sleep(1000);
        invitePatientPage.selectSecondaryInsuranceRelationshipForPatientOne(testDataForWard.getRelationshipForSecondaryInsurance());

        // Submit the invitation
        invitePatientPage.clickAddPatientButton();
        ExtentReportManager.getTest().log(Status.INFO, "Invitation submitted for account holder and ward with insurance details");
    }

    @Test(priority = 2)
    public void testVerifyInsuranceInPatientChart() throws IOException, InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Verify Insurance in Patient Chart");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // Switch to patient chart tab
        ExtentReportManager.getTest().log(Status.INFO, "Switching to patient chart tab");
        switchToTab(1);
        if(!patientChart.isPatientChart()){
            ExtentReportManager.getTest().log(Status.INFO, "Patient chart not visible – test skipped");
            Assert.fail("Patient chart page not loaded.");
        }
        Thread.sleep(3000);
        //Page navigate to Patient chart
        //search for patient
        ExtentReportManager.getTest().log(Status.INFO, "Searching for ward in patient chart");
        patientChart.searchPatient(testDataForWard.getFullName());

        //validating primary insurance
        ExtentReportManager.getTest().log(Status.INFO, "Validating primary insurance details in patient chart");
        softAssert.assertEquals(testDataForWard.getPrimaryInsurance().toLowerCase(), patientChart.getPrimaryInsurance().toLowerCase(),
                "Primary Insurance mismatch in Patient Chart.");
        ExtentReportManager.getTest().log(Status.INFO, "Validated: Primary Insurance matches");
        softAssert.assertEquals(testDataForWard.getMemberNameForPrimaryInsurance(), patientChart.getMemberNameInPrimaryInsurance(),
                "Member Name for Primary Insurance mismatch in Patient Chart.");
        ExtentReportManager.getTest().log(Status.INFO, "Validated: Member Name for Primary Insurance matches");
        softAssert.assertEquals(testDataForWard.getMemberIdForPrimaryInsurance(), patientChart.getMemberIdInPrimaryInsurance(),
                "Member ID for Primary Insurance mismatch in Patient Chart.");
        ExtentReportManager.getTest().log(Status.INFO, "Validated: Member ID for Primary Insurance matches");
        softAssert.assertEquals(testDataForWard.getDobForMajor(), patientChart.getMemberDobInPrimaryInsurance(),
                "Member DOB for Primary Insurance mismatch in Patient Chart.");
        ExtentReportManager.getTest().log(Status.INFO, "Validated: Member DOB for Primary Insurance matches");

        softAssert.assertAll();
        ExtentReportManager.getTest().log(Status.INFO, "All insurance details validated successfully in patient chart");
    }
    @Test(priority = 3)
    public void testSetPasswordViaYopMail() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Set Password via YopMail");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        // Open YopMail and set password
        ExtentReportManager.getTest().log(Status.INFO, "Opening YopMail and setting password");
        //newTabAndLaunchYopMail();
        yopMail.clickSetPasswordMail(testDataForAccountHolder.getEmail());
        switchToTab(3);
        setPasswordPage.setPassword("Welcome@123");
        ExtentReportManager.getTest().log(Status.INFO, "Password set successfully via YopMail");
    }
    @Test(priority = 4)
    public void testPatientPortalValidation() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Patient Portal Insurance Validation");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        // Login to Patient Portal
        ExtentReportManager.getTest().log(Status.INFO, "Logging in to Patient Portal");
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");

        // Start dermatology visit for ward
        ExtentReportManager.getTest().log(Status.INFO, "Starting dermatology visit for ward");
        homePagePatPortal.selectDermatologyVisit();
        dermatologyVisitPage.clickSelectPatient();
        Thread.sleep(1000);
        dermatologyVisitPage.selectPatientAsWard();
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();
        Thread.sleep(1000);
        // Validate selected patient
        ExtentReportManager.getTest().log(Status.INFO, "Validating selected patient in dermatology visit");
        softAssert.assertEquals(testDataForWard.getFullName(), dermatologyVisitPage.getNameOfTheWardInSelectWard(),
                "Selected Ward name is wrong");
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();

        //primary insurance validation
        ExtentReportManager.getTest().log(Status.INFO, "Validating primary insurance in dermatology visit");
        softAssert.assertEquals(testDataForWard.getPrimaryInsurance(), dermatologyVisitPage.getPrimaryInsuranceName(),"Primary insurance name is mismatched");
        ExtentReportManager.getTest().log(Status.INFO, "Validated: Primary insurance name matches");
        softAssert.assertEquals(testDataForWard.getFullName(), dermatologyVisitPage.getMemberNameInPrimaryInsurance(),"Member name is mismatched In Primary insurance");
        ExtentReportManager.getTest().log(Status.INFO, "Validated: Member name in primary insurance matches");
        softAssert.assertEquals(testDataForWard.getMemberIdForPrimaryInsurance(), dermatologyVisitPage.getMemberIDInPrimaryInsurance(),"Member ID is mismatched In Primary insurance");
        ExtentReportManager.getTest().log(Status.INFO, "Validated: Member ID in primary insurance matches");
        softAssert.assertEquals(testDataForWard.getMemberDobForPrimaryInsurance(), dermatologyVisitPage.getMemberDobInPrimaryInsurance(),"Member DOB is mismatched In Primary insurance");
        ExtentReportManager.getTest().log(Status.INFO, "Validated: Member DOB in primary insurance matches");
        softAssert.assertEquals(testDataForWard.getRelationshipForPrimaryInsurance(), dermatologyVisitPage.getRelationshipInPrimaryInsurance(),"Relationship to patient is mismatched In Primary insurance");
        ExtentReportManager.getTest().log(Status.INFO, "Validated: Relationship to patient in primary insurance matches");
        //Navigate to Home page
        dermatologyVisitPage.clickBackArrowForVisitForm();
        dermatologyVisitPage.clickBackArrowForVisitForm();
        dermatologyVisitPage.clickBackArrowForHomePage();
        ExtentReportManager.getTest().log(Status.INFO, "All insurance details validated successfully in patient portal");
        softAssert.assertAll();
    }
    @AfterClass()
    public void cleanUp() throws InterruptedException {
        // Logout from Patient Portal and Provider Portal after tests
        homePagePatPortal.clickMyProfile();
        myProfilePage.clickSettingsLink();
        myProfilePage.clickLogoutButton();
        myProfilePage.clickConfirmLogoutButton();
        switchToTab("SkyMD Provider Portal");
        patientChart.clickProfileIcon();
        patientChart.clickLogoutButton();
    }
}
