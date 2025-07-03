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
 * Test Case: TC_IP010
 * Description: Invite an account holder and add a child with additional details (address, gender, height, weight, DOB),
 *              then verify that all additional information is correctly displayed in the patient chart and patient portal profile for the child.
 */
public class TC_IP009_AddChildWithAdditionalDetails extends BaseTest {
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
    public TestData testDataForChild;
    public SoftAssert softAssert;

    @BeforeClass
    public void setUp() throws IOException {
        driver.get(ConfigReader.getProperty("ProviderPortalUrl"));

        //Test data for account holder and child
        testDataForAccountHolder = new TestData();
        testDataForChild = new TestData();

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
    public void testAddChildAndAdditionalDetails() {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Invite Account Holder and Add Child with Additional Details");
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
        // Add child fields
        invitePatientPage.clickAddAdditionalPatientBtnForPatientOne();
        invitePatientPage.selectPatientTypeForPatientOne("Child");
        invitePatientPage.setFirstNameForPatientOne(testDataForChild.getFname());
        invitePatientPage.setLastNameForPatientOne(testDataForChild.getLname());
        invitePatientPage.setZipCodeForPatientOne(testDataForChild.getZipCode());
        ExtentReportManager.getTest().log(Status.INFO, "Filling child details and additional information");
        invitePatientPage.clickAdditionalInformationForPatientOne();
        invitePatientPage.setStreetAddressOneForPatientOne(testDataForChild.getStreetAddressOne());
        invitePatientPage.setStreetAddressTwoForPatientOne(testDataForChild.getStreetAddressTwo());
        invitePatientPage.setDobForPatientOne(testDataForChild.getDobForMinor());
        invitePatientPage.selectGenderForPatientOne(testDataForChild.getGender());
        invitePatientPage.setFeetForPatientOne(testDataForChild.getFeet());
        invitePatientPage.setInchForPatientOne(testDataForChild.getInch());
        invitePatientPage.setWeightForPatientOne(testDataForChild.getWeight());
        invitePatientPage.clickAddPatientButton();
        ExtentReportManager.getTest().log(Status.INFO, "Submitted invite form for account holder and child");
    }
    @Test(priority = 2)
    public void testPatientChartValidations() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Validating patient chart details in Provider Portal");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        switchToTab(1);
        if(!patientChart.isPatientChart()){
            ExtentReportManager.getTest().log(Status.INFO, "Patient chart not visible – test skipped");
            Assert.fail("Patient chart page not loaded.");
        }
        Thread.sleep(3000);
        // Account Holder validations
        ExtentReportManager.getTest().log(Status.INFO, "Validating AH details in Patient chart");
        softAssert.assertEquals(testDataForAccountHolder.getFullName(), patientChart.getNameInThePatientChart(),
                "Account Holder name mismatch in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getEmail(), patientChart.getEmailInThePatientChart(),
                "Account Holder email mismatch in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), patientChart.getZipcodeInPatientChart(),
                "Account Holder zip code mismatch in Patient Chart");
        ExtentReportManager.getTest().log(Status.INFO, "Search for child");
        // Search for child and validate
        patientChart.searchPatient(testDataForChild.getFullName());
        //validation for child mandatory details
        Thread.sleep(2000);
        ExtentReportManager.getTest().log(Status.INFO, "Validating child's mandatory details in Patient chart");
        softAssert.assertEquals(testDataForChild.getFullName(), patientChart.getNameInThePatientChart(),
                "Child name mismatch in Patient Chart");
        softAssert.assertEquals(testDataForChild.getZipCode(), patientChart.getZipcodeInPatientChart(),
                "Child zip code mismatch in Patient Chart");
        // Additional details
        ExtentReportManager.getTest().log(Status.INFO, "Validating child's additional details in patient chart");
        softAssert.assertTrue(patientChart.getAddress().contains(testDataForChild.getStreetAddressOne()), "Account Holder's address one from test data is not found in the Patient Chart address");
        softAssert.assertTrue(patientChart.getAddress().contains(testDataForChild.getStreetAddressTwo()), "Account Holder's address two from test data is not found in the Patient Chart address");
        softAssert.assertEquals(testDataForChild.getGender().toLowerCase(), patientChart.getGender().toLowerCase(), "Gender mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForChild.getGender().toLowerCase(), patientChart.getGenderAtTopBar().toLowerCase(), "Gender from test data does not match Gender in Top Bar");
        softAssert.assertEquals(testDataForChild.getWholeHeight(), patientChart.getHeight(), "Height mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForChild.getWeight(), patientChart.getWeight(), "Weight mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForChild.getDobForMinor(), patientChart.getDOB(), "DOB is mismatch for Account Holder in Patient Chart");
        ExtentReportManager.getTest().log(Status.INFO, "Patient chart details validated successfully");
        softAssert.assertAll();
    }
    @Test(priority = 3)
    public void testSetPasswordViaYopMail() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Setting password via YopMail");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
       // newTabAndLaunchYopMail();
        yopMail.clickSetPasswordMail(testDataForAccountHolder.getEmail());
        switchToTab(3);
        setPasswordPage.setPassword("Welcome@123");
        ExtentReportManager.getTest().log(Status.INFO, "Password set successfully for invited patient");
    }
    @Test(priority = 4, dependsOnMethods = "testSetPasswordViaYopMail")
    public void testPatientPortalDependentAndVisitFlow() {
        ExtentReportManager.getTest().log(Status.INFO, "Validating patient portal profile details for dependent");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // Login to Patient Portal
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");
        // Navigate to homepage and profile
        homePagePatPortal.clickMyProfile();
        myProfilePage.clickDependents();
        ExtentReportManager.getTest().log(Status.INFO, "Validating Child's detail in My profile");
        softAssert.assertEquals(testDataForChild.getFullName(), myProfilePage.getDependentOneName(),
                "Dependent's name does not match the expected full name for Child in my profile.");
        softAssert.assertEquals("Child", myProfilePage.getDependentOneType(),
                "Dependent's type is not 'Child' as expected in my profile.");
        softAssert.assertEquals(testDataForChild.getGender(), myProfilePage.getDependentOneGender(),
                "Child's gender in the profile does not match in my profile");
        softAssert.assertEquals(testDataForChild.getWeight(), myProfilePage.getDependentOneWeight(),
                "Child's weight in the profile does not match in my profile");
        softAssert.assertEquals(testDataForChild.getWholeHeight(), myProfilePage.getDependentOneHeight(),
                "Child's height in the profile does not match in my profile");
        ExtentReportManager.getTest().log(Status.INFO, "Patient portal dependent profile validated successfully");
        // Navigate to home page and Dermatology Visit
        myProfilePage.clickHomePageLink();
        softAssert.assertAll();
    }
    @Test(priority = 5)
    public void testDermatologyVisitValidation() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting Dermatology Visit validation for Child");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //select a dermatology visit
        homePagePatPortal.selectDermatologyVisit();
        //dermatology Visit Page
        dermatologyVisitPage.clickSelectPatient();
        Thread.sleep(1000);
        dermatologyVisitPage.selectPatientAsMyChild();
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();
        Thread.sleep(1000);
        ExtentReportManager.getTest().log(Status.INFO, "Validating Child's Name in Dermatology visit");
        //validate child name
        softAssert.assertEquals(testDataForChild.getFullName(), dermatologyVisitPage.getFirstPatientName(),
                "Child name doesn't match");
        dermatologyVisitPage.clickContinueButton();
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButtonAfterInsurance();
        Thread.sleep(1000);
        ExtentReportManager.getTest().log(Status.INFO, "Validating Child's Address in Dermatology visit");
        // Validate address and zipcode on Dermatology Visit page
        softAssert.assertEquals(testDataForChild.getStreetAddressOne(), dermatologyVisitPage.getAddressLineOneValue(),
                "Street Address Line One mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForChild.getStreetAddressTwo(), dermatologyVisitPage.getAddressLineTwoValue(),
                "Street Address Line Two mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForChild.getZipCode(), dermatologyVisitPage.getZipCodeValue(),
                "Zip Code mismatch on Dermatology Visit page.");
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButton();
        Thread.sleep(1000);
        ExtentReportManager.getTest().log(Status.INFO, "Validating Child's Height, weight and DOB in Dermatology visit");
        // Validate Height, Weight, DOB
        softAssert.assertEquals(testDataForChild.getDobForMinor(), dermatologyVisitPage.getDOBValue(),
                "Date of Birth mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForChild.getFeet(), dermatologyVisitPage.getFeetValue(),
                "Height (Feet) mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForChild.getInch(), dermatologyVisitPage.getInchesValue(),
                "Height (Inches) mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForChild.getWeight(), dermatologyVisitPage.getWeightValue(),
                "Weight mismatch on Dermatology Visit page.");
        ExtentReportManager.getTest().log(Status.INFO, "Dermatology Visit validation for Child completed successfully");
        dermatologyVisitPage.clickBackArrowForVisitForm();
        Thread.sleep(1000);
        dermatologyVisitPage.clickBackArrowForHomePage();
        softAssert.assertAll();
    }
    //@Test(priority = 6)
    public void testPrimaryCareVisitValidation() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        homePagePatPortal.selectPrimaryCareVisit();

        primaryCareVisitPage = new PrimaryCareVisitPage(driver);

        primaryCareVisitPage.clickPatientAsChild();

        primaryCareVisitPage.clickNextButton();

        //primaryCareVisitPage.clickSelectPatient(testDataForWard.getFullName());
        primaryCareVisitPage.clickNextButton();

        primaryCareVisitPage.clickProceedByBookingButton();

        primaryCareVisitPage.clickSelectFirstCondition();

        primaryCareVisitPage.clickNextButton();

        primaryCareVisitPage.clickSelectFirstDateForSlot();
        primaryCareVisitPage.clickSelectFirstSlot();

        primaryCareVisitPage.clickNextButton();

        softAssert.assertEquals(testDataForChild.getFname(), primaryCareVisitPage.getFirstName(),
                "First name does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForChild.getLname(), primaryCareVisitPage.getLastName(),
                "Last name does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForChild.getDobForMinor(), primaryCareVisitPage.getDOB(),
                "Date of birth does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForChild.getStreetAddressOne(), primaryCareVisitPage.getAddressOne(),
                "Street Address One does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForChild.getStreetAddressTwo(), primaryCareVisitPage.getAddressTwo(),
                "Street Address Two does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForChild.getZipCode(), primaryCareVisitPage.getZipcode(),
                "Zip Code does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForChild.getFeet(), primaryCareVisitPage.getFeet(),
                "Height (feet) does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForChild.getInch(), primaryCareVisitPage.getInch(),
                "Height (inches) does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForChild.getWeight(), primaryCareVisitPage.getWeight(),
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
