package tests.InvitePatients;

import utils.ConfigReader;
import utils.TestData;
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
import utils.ExtentReportManager;
import com.aventstack.extentreports.Status;

import java.io.IOException;
import java.time.Duration;

/**
 * Test Case: TC_IP011
 * Description: Invite an account holder and add a ward (legal guardian of 18+ years) with additional details (address, gender, height, weight, DOB),
 *              then verify that all additional information is correctly displayed in the patient chart and patient portal profile for the ward.
 */
public class TC_IP015_AddWardWithAdditionalDetails extends BaseTest {
    public LoginPage loginPage;
    public DashBoardPage dashBoardPage;
    public InvitePatientPage invitePatientPage;
    public PatientChart patientChart;
    public SetPasswordPage setPasswordPage;
    public PatientPortalLoginPage loginPagePatientPortal;
    public PatientPortalHomePage homePagePatPortal;
    public PatientPortalMyProfilePage myProfilePage;
    public DermatologyVisitPage dermatologyVisitPage;
    public PrimaryCareVisitPage primaryCareVisitPage;
    public YopMail yopMail;

    public TestData testDataForAccountHolder;
    public TestData testDataForWard;
    public SoftAssert softAssert;

    @BeforeClass
    public void setUp() throws IOException {
        driver.get(ConfigReader.getProperty("ProviderPortalUrl"));

        //Test data for account holder and Ward
        testDataForAccountHolder = new TestData();
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
        setPasswordPage = new SetPasswordPage(driver);
        loginPagePatientPortal = new PatientPortalLoginPage(driver);
        homePagePatPortal = new PatientPortalHomePage(driver);
        myProfilePage = new PatientPortalMyProfilePage(driver);
        dermatologyVisitPage = new DermatologyVisitPage(driver);
        primaryCareVisitPage = new PrimaryCareVisitPage(driver);
        yopMail = new YopMail(driver);
    }
    @Test(priority = 1)
    public void testAddWardAndAdditionalDetails() {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Invite Account Holder and Add Ward with Additional Details");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        dashBoardPage.clickInvitePatientLink();
        ExtentReportManager.getTest().log(Status.INFO, "Navigating to Invite Patient page");
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());
        invitePatientPage.selectProviderNameAs(testDataForAccountHolder.getProviderName());
        ExtentReportManager.getTest().log(Status.INFO, "Filling account holder details");
        // Add Ward fields
        invitePatientPage.clickAddAdditionalPatientBtnForPatientOne();
        invitePatientPage.selectPatientTypeForPatientOne("Ward (legal guardian of 18+ years)");
        invitePatientPage.setFirstNameForPatientOne(testDataForWard.getFname());
        invitePatientPage.setLastNameForPatientOne(testDataForWard.getLname());
        invitePatientPage.setZipCodeForPatientOne(testDataForWard.getZipCode());
        ExtentReportManager.getTest().log(Status.INFO, "Filling ward details and additional information");
        invitePatientPage.clickAdditionalInformationForPatientOne();
        invitePatientPage.setStreetAddressOneForPatientOne(testDataForWard.getStreetAddressOne());
        invitePatientPage.setStreetAddressTwoForPatientOne(testDataForWard.getStreetAddressTwo());
        invitePatientPage.setDobForPatientOne(testDataForWard.getDobForMajor());
        invitePatientPage.selectGenderForPatientOne(testDataForWard.getGender());
        invitePatientPage.setFeetForPatientOne(testDataForWard.getFeet());
        invitePatientPage.setInchForPatientOne(testDataForWard.getInch());
        invitePatientPage.setWeightForPatientOne(testDataForWard.getWeight());
        invitePatientPage.clickAddPatientButton();
        ExtentReportManager.getTest().log(Status.INFO, "Submitted invite form for account holder and ward");
    }
    @Test(priority = 2)
    public void testPatientChartValidations() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Validating patient chart details in Provider Portal");
        switchToTab(1);
        if(!patientChart.isPatientChart()){
            ExtentReportManager.getTest().log(Status.INFO, "Patient chart not visible – test skipped");
            Assert.fail("Patient chart page not loaded.");
        }
        Thread.sleep(3000);
        // Account Holder validations
        ExtentReportManager.getTest().log(Status.INFO, "Validating account holder details in patient chart");
        softAssert.assertEquals(testDataForAccountHolder.getFullName(), patientChart.getNameInThePatientChart(),
                "Account Holder name mismatch in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getEmail(), patientChart.getEmailInThePatientChart(),
                "Account Holder email mismatch in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), patientChart.getZipcodeInPatientChart(),
                "Account Holder zip code mismatch in Patient Chart");
        // Search for Ward and validate
        patientChart.searchPatient(testDataForWard.getFullName());
        // Validation for Ward mandatory details
        Thread.sleep(2000);
        ExtentReportManager.getTest().log(Status.INFO, "Validating ward mandatory and additional details in patient chart");
        softAssert.assertEquals(testDataForWard.getFullName(), patientChart.getNameInThePatientChart(),
                "Ward name mismatch in Patient Chart");
        softAssert.assertEquals(testDataForWard.getZipCode(), patientChart.getZipcodeInPatientChart(),
                "Ward zip code mismatch in Patient Chart");
        // Additional details
        softAssert.assertTrue(patientChart.getAddress().contains(testDataForWard.getStreetAddressOne()),
                "Account Holder's address one from test data is not found in the Patient Chart address");
        softAssert.assertTrue(patientChart.getAddress().contains(testDataForWard.getStreetAddressTwo()),
                "Account Holder's address two from test data is not found in the Patient Chart address");
        softAssert.assertEquals(testDataForWard.getGender().toLowerCase(), patientChart.getGender().toLowerCase(),
                "Gender mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForWard.getGender().toLowerCase(), patientChart.getGenderAtTopBar().toLowerCase(),
                "Gender from test data does not match Gender in Top Bar");
        softAssert.assertEquals(testDataForWard.getWholeHeight(), patientChart.getHeight(),
                "Height mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForWard.getWeight(), patientChart.getWeight(),
                "Weight mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForWard.getDobForMajor(), patientChart.getDOB(),
                "DOB is mismatch for Account Holder in Patient Chart");
        ExtentReportManager.getTest().log(Status.INFO, "Patient chart details validated successfully");
        softAssert.assertAll();
    }
    @Test(priority = 3)
    public void testSetPasswordViaYopMail() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Setting password via YopMail");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
       //newTabAndLaunchYopMail();
        yopMail.clickSetPasswordMail(testDataForAccountHolder.getEmail());
        switchToTab(3);
        setPasswordPage.setPassword("Welcome@123");
        ExtentReportManager.getTest().log(Status.INFO, "Password set successfully for invited patient");
    }
    @Test(priority = 4, dependsOnMethods = "testSetPasswordViaYopMail")
    public void testPatientPortalDependentValidation() {
        ExtentReportManager.getTest().log(Status.INFO, "Validating patient portal profile details for dependent");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");
        homePagePatPortal.clickMyProfile();
        myProfilePage.clickDependents();
        ExtentReportManager.getTest().log(Status.INFO, "Validating Ward details in My profile");
        softAssert.assertEquals(testDataForWard.getFullName(), myProfilePage.getDependentOneName(),
                "Dependent's name does not match the expected full name for Ward in my profile.");
        softAssert.assertEquals("Ward", myProfilePage.getDependentOneType(),
                "Dependent's type is not 'Ward' as expected in my profile.");
        softAssert.assertEquals(testDataForWard.getGender(), myProfilePage.getDependentOneGender(),
                "Ward's gender in the profile does not match in my profile");
        softAssert.assertEquals(testDataForWard.getWeight(), myProfilePage.getDependentOneWeight(),
                "Ward's weight in the profile does not match in my profile");
        softAssert.assertEquals(testDataForWard.getWholeHeight(), myProfilePage.getDependentOneHeight(),
                "Ward's height in the profile does not match in my profile");
        // Navigate to home page and Dermatology Visit
        myProfilePage.clickHomePageLink();
        ExtentReportManager.getTest().log(Status.INFO, "Patient portal dependent profile validated successfully");
        softAssert.assertAll();
    }
    @Test(priority = 5)
    public void testDermatologyVisitValidation() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting Dermatology Visit validation for Ward");
        homePagePatPortal.selectDermatologyVisit();
        dermatologyVisitPage.clickSelectPatient();
        Thread.sleep(1000);
        dermatologyVisitPage.selectPatientAsWard();
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();
        Thread.sleep(1000);
        ExtentReportManager.getTest().log(Status.INFO, "validating Ward name in Dermatology visit");
        // Validate Ward name
        softAssert.assertEquals(testDataForWard.getFullName(), dermatologyVisitPage.getNameOfTheWardInSelectWard(),
                "Ward name is mismatching in Dermatology visit");
        dermatologyVisitPage.clickContinueButton();
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButtonAfterInsurance();
        Thread.sleep(1000);
        ExtentReportManager.getTest().log(Status.INFO, "validating Ward's address in Dermatology visit");
        // Validate address and zipcode on Dermatology Visit page
        softAssert.assertEquals(testDataForWard.getStreetAddressOne(), dermatologyVisitPage.getAddressLineOneValue(),
                "Street Address Line One mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForWard.getStreetAddressTwo(), dermatologyVisitPage.getAddressLineTwoValue(),
                "Street Address Line Two mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForWard.getZipCode(), dermatologyVisitPage.getZipCodeValue(),
                "Zip Code mismatch on Dermatology Visit page.");
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButton();
        Thread.sleep(1000);
        ExtentReportManager.getTest().log(Status.INFO, "validating Ward's Height, Weight and DOB in Dermatology visit");
        // Validate Height, Weight, DOB
        softAssert.assertEquals(testDataForWard.getDobForMajor(), dermatologyVisitPage.getDOBValue(),
                "Date of Birth mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForWard.getFeet(), dermatologyVisitPage.getFeetValue(),
                "Height (Feet) mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForWard.getInch(), dermatologyVisitPage.getInchesValue(),
                "Height (Inches) mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForWard.getWeight(), dermatologyVisitPage.getWeightValue(),
                "Weight mismatch on Dermatology Visit page.");
        dermatologyVisitPage.clickBackArrowForVisitForm();
        Thread.sleep(1000);
        dermatologyVisitPage.clickBackArrowForHomePage();
        ExtentReportManager.getTest().log(Status.INFO, "Dermatology Visit validation for Ward completed successfully");
        softAssert.assertAll();
    }
    //@Test(priority = 6)
    public void testPrimaryCareVisitValidation() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        homePagePatPortal = new PatientPortalHomePage(driver);
        homePagePatPortal.selectPrimaryCareVisit();

        primaryCareVisitPage = new PrimaryCareVisitPage(driver);
        primaryCareVisitPage.clickPatientAsWard();
        primaryCareVisitPage.clickNextButton();

        //primaryCareVisitPage.clickSelectPatient(testDataForWard.getFullName());
        primaryCareVisitPage.clickNextButton();

        primaryCareVisitPage.clickProceedByBookingButton();

        primaryCareVisitPage.clickSelectFirstCondition();

        primaryCareVisitPage.clickNextButton();

        primaryCareVisitPage.clickSelectFirstDateForSlot();
        primaryCareVisitPage.clickSelectFirstSlot();

        primaryCareVisitPage.clickNextButton();

        softAssert.assertEquals(testDataForWard.getFname(), primaryCareVisitPage.getFirstName(),
                "First name does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForWard.getLname(), primaryCareVisitPage.getLastName(),
                "Last name does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForWard.getDobForMajor(), primaryCareVisitPage.getDOB(),
                "Date of birth does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForWard.getStreetAddressOne(), primaryCareVisitPage.getAddressOne(),
                "Street Address One does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForWard.getStreetAddressTwo(), primaryCareVisitPage.getAddressTwo(),
                "Street Address Two does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForWard.getZipCode(), primaryCareVisitPage.getZipcode(),
                "Zip Code does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForWard.getFeet(), primaryCareVisitPage.getFeet(),
                "Height (feet) does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForWard.getInch(), primaryCareVisitPage.getInch(),
                "Height (inches) does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForWard.getWeight(), primaryCareVisitPage.getWeight(),
                "Weight does not match for Ward on Primary Care Visit page.");
        primaryCareVisitPage.clickBackArrowToHomePage();
        softAssert.assertAll();
    }
    @AfterClass()
    public void cleanUp() throws InterruptedException {
        //navigate to my profile
        homePagePatPortal.clickMyProfile();
        myProfilePage.clickSettingsLink();
        myProfilePage.clickLogoutButton();
        myProfilePage.clickConfirmLogoutButton();
        switchToTab("SkyMD Provider Portal");
        patientChart.clickProfileIcon();
        patientChart.clickLogoutButton();
    }
}
