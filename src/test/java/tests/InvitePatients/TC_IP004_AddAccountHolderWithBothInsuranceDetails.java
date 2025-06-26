package tests.InvitePatients;

import Utils.TestData;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;
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
 * Test Case: TC_IP005
 * Description: Invite an account holder with both primary and secondary insurance details
 *              and verify that all insurance information is correctly displayed in the patient chart and patient portal.
 */
public class TC_IP004_AddAccountHolderWithBothInsuranceDetails extends BaseTest {
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
    public SoftAssert softAssert;
    @BeforeClass
    public void setUp() throws IOException {
        //Loading config File
        loadPropFile();
        driver.get(property.getProperty("ProviderPortalUrl"));

        //Test data for account holder and provider
        testDataForAccountHolder = new TestData();

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
    private void testInvitePatientWithInsurance() throws IOException, InterruptedException {
        // Test: Invite patient with both primary and secondary insurance details
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Invite patient with both primary and secondary insurance details");
        //implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        ExtentReportManager.getTest().log(Status.INFO, "Filling patient basic details");
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());
        invitePatientPage.selectProviderNameAs(testDataForAccountHolder.getProviderName());

        //Primary insurance
        ExtentReportManager.getTest().log(Status.INFO, "Filling primary insurance details");
        invitePatientPage.clickPrimaryInsuranceCheckboxForAH();
        invitePatientPage.setPrimaryInsuranceDropdownForAH(testDataForAccountHolder.getPrimaryInsurance());
        invitePatientPage.setMemberNameInPrimaryInsuranceForAh(testDataForAccountHolder.getMemberNameForPrimaryInsurance());
        invitePatientPage.setMemberIdInPrimaryInsuranceForAh(testDataForAccountHolder.getMemberIdForPrimaryInsurance());
        Thread.sleep(1000);
        invitePatientPage.setMemberDOBInPrimaryInsuranceForAh(testDataForAccountHolder.getMemberDobForPrimaryInsurance());
        Thread.sleep(1000);
        invitePatientPage.setRelationshipInPrimaryInsuranceForAh(testDataForAccountHolder.getRelationshipForPrimaryInsurance());

        //Secondary insurance
        ExtentReportManager.getTest().log(Status.INFO, "Filling secondary insurance details");
        invitePatientPage.clickSecondaryInsuranceCheckbox();
        invitePatientPage.setSecondaryInsuranceDropdownForAH(testDataForAccountHolder.getSecondaryInsurance());
        invitePatientPage.setMemberNameInSecondaryInsurance(testDataForAccountHolder.getMemberNameForSecondaryInsurance());
        invitePatientPage.setMemberIdInSecondaryInsurance(testDataForAccountHolder.getMemberIdForSecondaryInsurance());
        Thread.sleep(1000);
        invitePatientPage.setMemberDobInSecondaryInsurance(testDataForAccountHolder.getMemberDobForSecondaryInsurance());
        Thread.sleep(1000);
        invitePatientPage.setRelationshipInSecondaryInsurance(testDataForAccountHolder.getRelationshipForSecondaryInsurance());

        //add patient
        ExtentReportManager.getTest().log(Status.INFO, "Submitting invite for patient with both insurances");
        invitePatientPage.clickAddPatientButton();
        ExtentReportManager.getTest().log(Status.INFO, "Invite patient form submitted successfully");
    }
    @Test(priority = 2)
    private void testValidatePatientAndInsuranceInChart() throws InterruptedException {
        // Test: Validate patient and insurance details in Provider Portal's patient chart
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Validate patient and insurance details in Provider Portal's patient chart");
        Thread.sleep(1000);
        switchToTab(1);
        if(!patientChart.isPatientChart()){
            ExtentReportManager.getTest().log(Status.INFO, "Patient chart not visible – test skipped");
            Assert.fail("Patient chart page not loaded.");
        }
        Thread.sleep(3000);
        //validating mandatory details
        ExtentReportManager.getTest().log(Status.INFO, "Validating mandatory patient details in chart");
        softAssert.assertEquals((testDataForAccountHolder.getFullName()), patientChart.getNameInThePatientChart(),
                "Name didn't matched in the Patient chart");
        softAssert.assertEquals(testDataForAccountHolder.getEmail().toLowerCase(), patientChart.getEmailInThePatientChart().toLowerCase(),
                "Email didn't matched");
        softAssert.assertEquals((testDataForAccountHolder.getFullName()).toUpperCase(), patientChart.getNameInTopBar(),
                "Name in the Top Bar is mismatching");
        softAssert.assertEquals(testDataForAccountHolder.getMobileNumber(), patientChart.getMobileInPatientChart(),
                "Mobile Number didn't matched in the Patient chart");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), patientChart.getZipcodeInPatientChart(),
                "Zip Code mismatch in Patient Chart.");

        //validating primary insurance
        ExtentReportManager.getTest().log(Status.INFO, "Validating primary insurance details in chart");
        softAssert.assertEquals(testDataForAccountHolder.getPrimaryInsurance().toLowerCase(), patientChart.getPrimaryInsurance().toLowerCase(),
                "Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberNameForPrimaryInsurance(), patientChart.getMemberNameInPrimaryInsurance(),
                "Member Name for Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberIdForPrimaryInsurance(), patientChart.getMemberIdInPrimaryInsurance(),
                "Member ID for Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberDobForPrimaryInsurance(), patientChart.getMemberDobInPrimaryInsurance(),
                "Member DOB for Primary Insurance mismatch in Patient Chart.");

        //validate secondary insurance
        ExtentReportManager.getTest().log(Status.INFO, "Validating secondary insurance details in chart");
        softAssert.assertEquals(testDataForAccountHolder.getSecondaryInsurance().toLowerCase(), patientChart.getSecondaryInsurance().toLowerCase(),
                "Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberNameForSecondaryInsurance(), patientChart.getMemberNameInSecondaryInsurance(),
                "Member Name for Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberIdForSecondaryInsurance(), patientChart.getMemberIdInSecondaryInsurance(),
                "Member ID for Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberDobForSecondaryInsurance(), patientChart.getMemberDobInSecondaryInsurance(),
                "Member DOB for Secondary Insurance mismatch in Patient Chart.");
        ExtentReportManager.getTest().log(Status.INFO, "Patient and insurance details validated successfully in Provider Portal's patient chart");
        softAssert.assertAll();
    }
    @Test(priority = 3)
    private void testSetPasswordViaYopMail() throws InterruptedException {
        // Test: Set password for invited patient via YopMail
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Set password for invited patient via YopMail");
        //Navigate to YopMail
        //newTabAndLaunchYopMail();
        ExtentReportManager.getTest().log(Status.INFO, "Accessing YopMail and clicking set password mail");
        yopMail.clickSetPasswordMail(testDataForAccountHolder.getEmail());

        //Navigate to setPassword page
        switchToTab(3);
        setPasswordPage.setPassword("Welcome@123");
        ExtentReportManager.getTest().log(Status.INFO, "Password set successfully for invited patient");
    }
    @Test(priority = 4 , dependsOnMethods = "testSetPasswordViaYopMail")
    public void testPatientPortalValidations(){
        // Test: Validate insurance details in Patient Portal after login
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Validate insurance details in Patient Portal after login");
        //navigate to login page
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");
        ExtentReportManager.getTest().log(Status.INFO, "Logged into Patient Portal");

        homePagePatPortal.selectDermatologyVisit();
        ExtentReportManager.getTest().log(Status.INFO, "Selected Dermatology Visit");

        dermatologyVisitPage.clickSelectPatient();
        dermatologyVisitPage.clickSelectPatientAsMySelf();
        ExtentReportManager.getTest().log(Status.INFO, "Selected patient as self for visit");

        // Validate primary insurance details
        ExtentReportManager.getTest().log(Status.INFO, "Validating primary insurance details in Dermatology Visit page");
        softAssert.assertEquals(testDataForAccountHolder.getPrimaryInsurance().toLowerCase(), dermatologyVisitPage.getPrimaryInsuranceName().toLowerCase(),
                "Primary Insurance name mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberNameForPrimaryInsurance(), dermatologyVisitPage.getMemberNameInPrimaryInsurance(),
                "Member Name for Primary Insurance mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberIdForPrimaryInsurance(), dermatologyVisitPage.getMemberIDInPrimaryInsurance(),
                "Member ID for Primary Insurance mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberDobForPrimaryInsurance(), dermatologyVisitPage.getMemberDobInPrimaryInsurance(),
                "Member DOB for Primary Insurance mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getRelationshipForPrimaryInsurance(), dermatologyVisitPage.getRelationshipInPrimaryInsurance(),
                "Relationship for Primary Insurance mismatch in Dermatology Visit page.");

        //secondary insurance validation
        ExtentReportManager.getTest().log(Status.INFO, "Validating secondary insurance details in Dermatology Visit page");
        softAssert.assertEquals(testDataForAccountHolder.getSecondaryInsurance().toLowerCase(), dermatologyVisitPage.getSecondaryInsuranceName().toLowerCase(),
                "Secondary Insurance name mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberNameForSecondaryInsurance(), dermatologyVisitPage.getMemberNameInSecondaryInsurance(),
                "Member Name for Secondary Insurance mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberIdForSecondaryInsurance(), dermatologyVisitPage.getMemberIDInSecondaryInsurance(),
                "Member ID for Secondary Insurance mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberDobForSecondaryInsurance(), dermatologyVisitPage.getMemberDobInSecondaryInsurance(),
                "Member DOB for Secondary Insurance mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getRelationshipForSecondaryInsurance(), dermatologyVisitPage.getRelationshipInSecondaryInsurance(),
                "Relationship for Secondary Insurance mismatch in Dermatology Visit page.");
        ExtentReportManager.getTest().log(Status.INFO, "Patient portal insurance details validated successfully");
        //Navigate to Home page
        dermatologyVisitPage.clickBackArrowForVisitForm();
        dermatologyVisitPage.clickBackArrowForVisitForm();
        dermatologyVisitPage.clickBackArrowForHomePage();
        softAssert.assertAll();
    }
    @AfterClass
    private void patientAndProviderPortalLogout() throws InterruptedException {
        // Navigate to myProfile and logout
        homePagePatPortal.clickMyProfile();
        myProfilePage.clickSettingsLink();
        myProfilePage.clickLogoutButton();
        myProfilePage.clickConfirmLogoutButton();

        switchToTab("SkyMD Provider Portal");
        patientChart.clickProfileIcon();
        patientChart.clickLogoutButton();
    }
}
