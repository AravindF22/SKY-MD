package tests.InvitePatients;

import Utils.TestData;
import base.BaseTest;
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

/**
 * Test Case: TC_IP014
 * Description: Invite an account holder and add a ward (legal guardian of 18+ years) with insurance details,
 *              then verify that all insurance information is correctly displayed in the patient chart and patient portal profile for the ward.
 */
public class TC_IP014AddWardWithInsuranceDetails extends BaseTest {
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
        //implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        invitePatientPage = new InvitePatientPage(driver);
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());
        invitePatientPage.selectProviderNameAs(testDataForAccountHolder.getProviderName());

        // Add Child fields
        invitePatientPage.clickAddAdditionalPatientBtnForPatientOne();
        invitePatientPage.selectPatientTypeForPatientOne("Ward (legal guardian of 18+ years)");
        invitePatientPage.setFirstNameForPatientOne(testDataForWard.getFname());
        invitePatientPage.setLastNameForPatientOne(testDataForWard.getLname());
        invitePatientPage.setZipCodeForPatientOne(testDataForWard.getZipCode());

        invitePatientPage.checkInsuranceCheckboxForPatientOne();
        invitePatientPage.selectPrimaryInsuranceForPatientOne(testDataForWard.getPrimaryInsurance());
        invitePatientPage.setPrimaryInsuranceMemberName(testDataForWard.getFullName());
        invitePatientPage.setPrimaryInsuranceMemberIdForPatientOne(testDataForWard.getMemberIdForPrimaryInsurance());
        invitePatientPage.setPrimaryInsuranceMemberDOBForPatientOne(testDataForWard.getDobForMajor());
        invitePatientPage.selectPrimaryInsuranceRelationshipForPatientOne(testDataForWard.getRelationshipForPrimaryInsurance());

        //add patient
        invitePatientPage.clickAddPatientButton();
    }

    @Test(priority = 2)
    public void testVerifyInsuranceInPatientChart() throws IOException, InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        switchToTab(1);

        //Page navigate to Patient chart
        //search for patient
        patientChart.searchPatient(testDataForWard.getFullName());

        //validating primary insurance
        softAssert.assertEquals(testDataForWard.getPrimaryInsurance().toLowerCase(), patientChart.getPrimaryInsurance().toLowerCase(),
                "Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForWard.getMemberNameForPrimaryInsurance(), patientChart.getMemberNameInPrimaryInsurance(),
                "Member Name for Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForWard.getMemberIdForPrimaryInsurance(), patientChart.getMemberIdInPrimaryInsurance(),
                "Member ID for Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForWard.getDobForMajor(), patientChart.getMemberDobInPrimaryInsurance(),
                "Member DOB for Primary Insurance mismatch in Patient Chart.");

        softAssert.assertAll();
    }
    @Test(priority = 3)
    public void testSetPasswordViaYopMail() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        newTabAndLaunchYopMail();
        yopMail.clickSetPasswordMail(testDataForAccountHolder.getEmail());

        switchToTab(3);
        setPasswordPage.setPassword("Welcome@123");
    }
    @Test(priority = 4)
    public void testPatientPortalValidation() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        // Login to Patient Portal
        loginPagePatientPortal = new PatientPortalLoginPage(driver);
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");

        homePagePatPortal.selectDermatologyVisit();
        dermatologyVisitPage.clickSelectPatient();
        Thread.sleep(1000);
        dermatologyVisitPage.selectPatientAsWard();
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();
        softAssert.assertEquals(testDataForWard.getFullName(), dermatologyVisitPage.getNameOfTheWardInSelectWard());
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();

        //primary insurance validation
        softAssert.assertEquals(testDataForWard.getPrimaryInsurance(), dermatologyVisitPage.getPrimaryInsuranceName(),"Primary insurance name is mismatched");
        softAssert.assertEquals(testDataForWard.getFullName(), dermatologyVisitPage.getMemberNameInPrimaryInsurance(),"Member name is mismatched In Primary insurance");
        softAssert.assertEquals(testDataForWard.getMemberIdForPrimaryInsurance(), dermatologyVisitPage.getMemberIDInPrimaryInsurance(),"Member ID is mismatched In Primary insurance");
        softAssert.assertEquals(testDataForWard.getMemberDobForPrimaryInsurance(), dermatologyVisitPage.getMemberDobInPrimaryInsurance(),"Member DOB is mismatched In Primary insurance");
        softAssert.assertEquals(testDataForWard.getRelationshipForPrimaryInsurance(), dermatologyVisitPage.getRelationshipInPrimaryInsurance(),"Relationship to patient is mismatched In Primary insurance");

        softAssert.assertAll();
    }
}
