package tests.InvitePatients;

import Utils.ExtentReportManager;
import Utils.TestData;
import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.PatientPortal.*;
import pages.ProviderPortal.*;
import pages.YopMail;

import java.io.IOException;
import java.time.Duration;

public class TC_IP001AddAccountHolderWithMandatoryDetails extends BaseTest {
    public LoginPage loginPage;
    public DashBoardPage dashBoardPage;
    public InvitePatientPage invitePatientPage;
    public PatientChart patientChart;
    public SetPasswordPage setPasswordPage;
    public PatientPortalLoginPage loginPagePatientPortal;
    public PatientPortalHomePage homePagePatPortal;
    public PatientPortalMyProfilePage myProfilePage;
    public YopMail yopMail;
    public TestData testDataForAccountHolder;
    public SoftAssert softAssert;

    @BeforeClass
    public void setUp() throws IOException {
        loadPropFile();
        driver.get(property.getProperty("ProviderPortalUrl"));
        testDataForAccountHolder = new TestData();

        loginPage = new LoginPage(driver);
        loginPage.setEmailAs(property.getProperty("MA_Email"));
        loginPage.setPasswordAs(property.getProperty("MA_Password"));
        loginPage.clickLoginButton();

        dashBoardPage = new DashBoardPage(driver);
        dashBoardPage.clickInvitePatientLink();
    }

    @BeforeMethod
    public void initializeAsset() {
        invitePatientPage = new InvitePatientPage(driver);
        patientChart = new PatientChart(driver);
        yopMail = new YopMail(driver);
        loginPagePatientPortal = new PatientPortalLoginPage(driver);
        myProfilePage = new PatientPortalMyProfilePage(driver);
        softAssert = new SoftAssert();
    }

    @Test(priority = 1)
    public void testInvitePatientWithMandatoryDetails() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        ExtentReportManager.getTest().log(Status.INFO, "Filling mandatory details in Invite Patient form");

        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());
        invitePatientPage.selectProviderNameAs(testDataForAccountHolder.getProviderName());

        ExtentReportManager.getTest().log(Status.INFO, "Submitting invite form for account holder");
        invitePatientPage.clickAddPatientButton();
    }

    @Test(priority = 2)
    public void testPatientChartDetails() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        switchToTab(1);
        if(!patientChart.isPatientChart()){
            ExtentReportManager.getTest().log(Status.INFO, "Patient chart not visible – test skipped");
            Assert.fail("Patient chart page not loaded.");
        }
        Thread.sleep(3000);
        ExtentReportManager.getTest().log(Status.INFO, "Verifying patient chart details in Provider Portal");

// Patient Name
        String actualName = patientChart.getNameInThePatientChart();
        String expectedName = testDataForAccountHolder.getFullName();
        ExtentReportManager.getTest().log(Status.INFO, "Patient Name - Expected: " + expectedName + ", Actual: " + actualName);
        softAssert.assertEquals(actualName, expectedName, "Patient name mismatch in chart");

// Email
        String actualEmail = patientChart.getEmailInThePatientChart().toLowerCase();
        String expectedEmail = testDataForAccountHolder.getEmail().toLowerCase();
        ExtentReportManager.getTest().log(Status.INFO, "Email - Expected: " + expectedEmail + ", Actual: " + actualEmail);
        softAssert.assertEquals(actualEmail, expectedEmail, "Email mismatch");

// Top Bar Name
        String actualTopBarName = patientChart.getNameInTopBar();
        String expectedTopBarName = testDataForAccountHolder.getFullName().toUpperCase();
        ExtentReportManager.getTest().log(Status.INFO, "Top Bar Name - Expected: " + expectedTopBarName + ", Actual: " + actualTopBarName);
        softAssert.assertEquals(actualTopBarName, expectedTopBarName, "Top bar name mismatch");

// Mobile Number
        String actualMobile = patientChart.getMobileInPatientChart();
        String expectedMobile = testDataForAccountHolder.getMobileNumber();
        ExtentReportManager.getTest().log(Status.INFO, "Mobile Number - Expected: " + expectedMobile + ", Actual: " + actualMobile);
        softAssert.assertEquals(actualMobile, expectedMobile, "Mobile number mismatch");

// Zip Code
        String actualZip = patientChart.getZipcodeInPatientChart();
        String expectedZip = testDataForAccountHolder.getZipCode();
        ExtentReportManager.getTest().log(Status.INFO, "Zip Code - Expected: " + expectedZip + ", Actual: " + actualZip);
        softAssert.assertEquals(actualZip, expectedZip, "Zip code mismatch");
        ExtentReportManager.getTest().log(Status.INFO, "Patient chart validated successfully");
        softAssert.assertAll();
    }

    @Test(priority = 3)
    public void testSetPasswordViaYopMail() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        ExtentReportManager.getTest().log(Status.INFO, "Switching to YopMail to set password");
        String email = testDataForAccountHolder.getEmail();
       // newTabAndLaunchYopMail();
        yopMail.clickSetPasswordMail(email);

        switchToTab(3);
        Thread.sleep(1000);
        setPasswordPage = new SetPasswordPage(driver);
        setPasswordPage.setPassword("Welcome@123");
        ExtentReportManager.getTest().log(Status.INFO, "Password set successfully from YopMail");
    }

    @Test(priority = 4, dependsOnMethods = {"testSetPasswordViaYopMail"})
    public void testPatientPortalProfileDetails() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        ExtentReportManager.getTest().log(Status.INFO, "Logging into Patient Portal");

        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");
        homePagePatPortal = new PatientPortalHomePage(driver);
        homePagePatPortal.clickMyProfile();

        ExtentReportManager.getTest().log(Status.INFO, "Verifying My Profile information");

        softAssert.assertEquals(testDataForAccountHolder.getFullName(), myProfilePage.getNameOfAccountHolder(), "Mismatch in name");
        softAssert.assertEquals(testDataForAccountHolder.getEmail().toLowerCase(), myProfilePage.getEmailOfAccountHolder().toLowerCase(), "Mismatch in email");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), myProfilePage.getZipCodeOfAccountHolderInLineOne(), "Mismatch in zip code");
        softAssert.assertEquals(testDataForAccountHolder.getMobileNumber(), myProfilePage.getMobileOfAccountHolder(), "Mismatch in mobile number");

        ExtentReportManager.getTest().log(Status.INFO, "Profile details successfully verified");
        softAssert.assertAll();
    }

    @AfterClass
    public void cleanUp() throws InterruptedException {
        Thread.sleep(3000);
        ExtentReportManager.getTest().log(Status.INFO, "Logging out from Patient and Provider Portals");

        myProfilePage.clickSettingsLink();
        myProfilePage.clickLogoutButton();
        myProfilePage.clickConfirmLogoutButton();

        switchToTab("SkyMD Provider Portal");
        patientChart.clickProfileIcon();
        patientChart.clickLogoutButton();

        ExtentReportManager.getTest().log(Status.INFO, "Cleanup completed successfully");
    }
}
