package tests.InvitePatients;

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
import Utils.ExtentReportManager;
import com.aventstack.extentreports.Status;

import java.io.IOException;
import java.time.Duration;

/**
 * Test Case: TC_IP013
 * Description: Invite an account holder and add a child with both primary and secondary insurance details,
 *              then verify that all insurance information is correctly displayed in the patient chart and patient portal profile for the child.
 */
public class TC_IP010_AddChildWithInsuranceDetails extends BaseTest {
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
    public SoftAssert softAssert;
    @BeforeClass
    public void setUp() throws IOException {
        //Loading config File
        loadPropFile();
        driver.get(property.getProperty("ProviderPortalUrl"));

        //Test data for account holder and provider
        testDataForAccountHolder = new TestData();
        testDataForChild = new TestData();

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
        // Set implicit wait for element loading
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Add child and insurance details");

        // Fill account holder details
        ExtentReportManager.getTest().log(Status.INFO, "Filling account holder details");
        invitePatientPage = new InvitePatientPage(driver);
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());
        invitePatientPage.selectProviderNameAs(testDataForAccountHolder.getProviderName());

        // Add Child fields
        ExtentReportManager.getTest().log(Status.INFO, "Adding child as dependent");
        invitePatientPage.clickAddAdditionalPatientBtnForPatientOne();
        invitePatientPage.selectPatientTypeForPatientOne("Child");
        invitePatientPage.setFirstNameForPatientOne(testDataForChild.getFname());
        invitePatientPage.setLastNameForPatientOne(testDataForChild.getLname());
        invitePatientPage.setZipCodeForPatientOne(testDataForChild.getZipCode());

        // Add insurance details for child
        ExtentReportManager.getTest().log(Status.INFO, "Adding primary and secondary insurance details for child");
        invitePatientPage.checkInsuranceCheckboxForPatientOne();
        invitePatientPage.selectPrimaryInsuranceForPatientOne(testDataForChild.getPrimaryInsurance());
        invitePatientPage.setPrimaryInsuranceMemberName(testDataForAccountHolder.getFullName());
        invitePatientPage.setPrimaryInsuranceMemberIdForPatientOne(testDataForChild.getMemberIdForPrimaryInsurance());
        invitePatientPage.setPrimaryInsuranceMemberDOBForPatientOne(testDataForAccountHolder.getDobForMajor());
        invitePatientPage.selectPrimaryInsuranceRelationshipForPatientOne(testDataForChild.getRelationshipForPrimaryInsurance());

        invitePatientPage.checkSecondaryInsuranceForPatientOne();
        invitePatientPage.selectSecondaryInsuranceForPatientOne(testDataForChild.getSecondaryInsurance());
        invitePatientPage.setSecondaryInsuranceMemberNameForPatientOne(testDataForChild.getMemberNameForSecondaryInsurance());
        invitePatientPage.setSecondaryInsuranceMemberIdForPatientOne(testDataForChild.getMemberIdForSecondaryInsurance());
        invitePatientPage.setSecondaryInsuranceMemberDOBForPatientOne(testDataForChild.getMemberDobForSecondaryInsurance());
        invitePatientPage.selectSecondaryInsuranceRelationshipForPatientOne(testDataForChild.getRelationshipForSecondaryInsurance());

        // Submit patient invitation
        invitePatientPage.clickAddPatientButton();
        ExtentReportManager.getTest().log(Status.INFO, "Patient invitation submitted successfully");
    }

    @Test(priority = 2)
    public void testVerifyInsuranceInPatientChart() throws IOException, InterruptedException {
        // Set implicit wait for element loading
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Verify insurance details in patient chart");
        switchToTab(1);
        if(!patientChart.isPatientChart()){
            ExtentReportManager.getTest().log(Status.INFO, "Patient chart not visible – test skipped");
            Assert.fail("Patient chart page not loaded.");
        }
        Thread.sleep(3000);
        // Navigate to Patient chart and search for patient
        ExtentReportManager.getTest().log(Status.INFO, "Searching for child in patient chart");
        patientChart.searchPatient(testDataForChild.getFullName());

        // Validate primary insurance
        ExtentReportManager.getTest().log(Status.INFO, "Validating primary insurance details in patient chart");
        softAssert.assertEquals(testDataForChild.getPrimaryInsurance().toLowerCase(), patientChart.getPrimaryInsurance().toLowerCase(),
                "Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberNameForPrimaryInsurance(), patientChart.getMemberNameInPrimaryInsurance(),
                "Member Name for Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForChild.getMemberIdForPrimaryInsurance(), patientChart.getMemberIdInPrimaryInsurance(),
                "Member ID for Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberDobForPrimaryInsurance(), patientChart.getMemberDobInPrimaryInsurance(),
                "Member DOB for Primary Insurance mismatch in Patient Chart.");

        // Validate secondary insurance
        ExtentReportManager.getTest().log(Status.INFO, "Validating secondary insurance details in patient chart");
        softAssert.assertEquals(testDataForChild.getSecondaryInsurance().toLowerCase(), patientChart.getSecondaryInsurance().toLowerCase(),
                "Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForChild.getMemberNameForSecondaryInsurance(), patientChart.getMemberNameInSecondaryInsurance(),
                "Member Name for Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForChild.getMemberIdForSecondaryInsurance(), patientChart.getMemberIdInSecondaryInsurance(),
                "Member ID for Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForChild.getMemberDobForSecondaryInsurance(), patientChart.getMemberDobInSecondaryInsurance(),
                "Member DOB for Secondary Insurance mismatch in Patient Chart.");

        ExtentReportManager.getTest().log(Status.INFO, "Insurance details validated in patient chart");
        softAssert.assertAll();
    }
    @Test(priority = 3)
    public void testSetPasswordViaYopMail() throws InterruptedException {
        // Set implicit wait for element loading
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Set password via YopMail");
        //newTabAndLaunchYopMail();
        yopMail.clickSetPasswordMail(testDataForAccountHolder.getEmail());
        // Switch to set password tab and set password
        ExtentReportManager.getTest().log(Status.INFO, "Switching to set password tab and setting password");
        switchToTab(3);
        setPasswordPage.setPassword("Welcome@123");
        ExtentReportManager.getTest().log(Status.INFO, "Password set successfully via YopMail");
    }
    @Test(priority = 4, dependsOnMethods = "testSetPasswordViaYopMail")
    public void testPatientPortalValidation() throws InterruptedException {
        // Set implicit wait for element loading
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Patient portal validation for child insurance details");

        // Login to Patient Portal
        ExtentReportManager.getTest().log(Status.INFO, "Logging into Patient Portal");
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");

        // Select dermatology visit and child
        ExtentReportManager.getTest().log(Status.INFO, "Selecting dermatology visit and child");
        homePagePatPortal.selectDermatologyVisit();
        dermatologyVisitPage.clickSelectPatient();
        Thread.sleep(1000);
        dermatologyVisitPage.selectPatientAsMyChild();
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();
        Thread.sleep(1000);
        softAssert.assertEquals(testDataForChild.getFullName(), dermatologyVisitPage.getNameOfTheChildInSelectChild());
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();

        // Validate primary insurance in portal
        ExtentReportManager.getTest().log(Status.INFO, "Validating primary insurance details in patient portal");
        softAssert.assertEquals(testDataForChild.getPrimaryInsurance(), dermatologyVisitPage.getPrimaryInsuranceName(),"Primary insurance name is mismatched");
        softAssert.assertEquals(testDataForAccountHolder.getFullName(), dermatologyVisitPage.getMemberNameInPrimaryInsurance(),"Member name is mismatched In Primary insurance");
        softAssert.assertEquals(testDataForChild.getMemberIdForPrimaryInsurance(), dermatologyVisitPage.getMemberIDInPrimaryInsurance(),"Member ID is mismatched In Primary insurance");
        softAssert.assertEquals(testDataForAccountHolder.getMemberDobForPrimaryInsurance(), dermatologyVisitPage.getMemberDobInPrimaryInsurance(),"Member DOB is mismatched In Primary insurance");
        softAssert.assertEquals(testDataForChild.getRelationshipForPrimaryInsurance(), dermatologyVisitPage.getRelationshipInPrimaryInsurance(),"Relationship to patient is mismatched In Primary insurance");

        // Validate secondary insurance in portal
        ExtentReportManager.getTest().log(Status.INFO, "Validating secondary insurance details in patient portal");
        softAssert.assertEquals(testDataForChild.getSecondaryInsurance(), dermatologyVisitPage.getSecondaryInsuranceName(),"Secondary insurance name is mismatched");
        softAssert.assertEquals(testDataForChild.getMemberNameForSecondaryInsurance(), dermatologyVisitPage.getMemberNameInSecondaryInsurance(),"Member name is mismatched In Secondary insurance");
        softAssert.assertEquals(testDataForChild.getMemberIdForSecondaryInsurance(), dermatologyVisitPage.getMemberIDInSecondaryInsurance(),"Member ID is mismatched In Secondary insurance");
        softAssert.assertEquals(testDataForChild.getMemberDobForSecondaryInsurance(), dermatologyVisitPage.getMemberDobInSecondaryInsurance(),"Member DOB is mismatched In Secondary insurance");
        softAssert.assertEquals(testDataForChild.getRelationshipForSecondaryInsurance(), dermatologyVisitPage.getRelationshipInSecondaryInsurance(),"Relationship to patient is mismatched In Secondary insurance");
        //Navigate to Home page
        dermatologyVisitPage.clickBackArrowForVisitForm();
        dermatologyVisitPage.clickBackArrowForVisitForm();
        dermatologyVisitPage.clickBackArrowForHomePage();
        ExtentReportManager.getTest().log(Status.INFO, "Patient portal insurance details validated successfully");
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
