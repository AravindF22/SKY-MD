package tests.InvitePatients;

import Utils.TestData;
import base.BaseTest;
import org.openqa.selenium.interactions.Actions;
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

import javax.swing.*;
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
    public PatientPortalHomePage patientPortalHomePage;
    public DermatologyVisitPage dermatologyVisitPage;
    public TestData testDataForAccountHolder;
    public SoftAssert softAssert;

    @BeforeClass
    public void setUp() throws IOException {

        //Loading config File
        loadPropFile();
        driver.get(property.getProperty("ProviderPortalUrl"));
        //Test data for account holder
        testDataForAccountHolder = new TestData();
        // Login as MA
        loginPage = new LoginPage(driver);
        loginPage.setEmailAs(property.getProperty("MA_Email"));
        loginPage.setPasswordAs(property.getProperty("MA_Password"));
        loginPage.clickLoginButton();
        // Navigate to Invite Patient
        dashBoardPage = new DashBoardPage(driver);
        dashBoardPage.clickInvitePatientLink();

    }
    @BeforeMethod
    public void initializeAsset() throws IOException {
        softAssert = new SoftAssert();
    }

    @Test(priority = 1)
    public void testInvitePatientWithMandatoryDetails() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        // Fill and submit invite form
        invitePatientPage = new InvitePatientPage(driver);
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());
        invitePatientPage.selectProviderNameAs(testDataForAccountHolder.getProviderName());
        invitePatientPage.clickAddPatientButton();
    }
    @Test(priority = 2, dependsOnMethods = "testInvitePatientWithMandatoryDetails")
    public void testPatientChartDetails() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        switchToTab(1);

        patientChart = new PatientChart(driver);

        softAssert.assertEquals(testDataForAccountHolder.getFullName(), patientChart.getNameInThePatientChart(), "Name didn't match in the Patient chart");
        softAssert.assertEquals(testDataForAccountHolder.getEmail().toLowerCase(), patientChart.getEmailInThePatientChart().toLowerCase(), "Email didn't match");
        softAssert.assertEquals(testDataForAccountHolder.getFullName().toUpperCase(), patientChart.getNameInTopBar(), "Name in the Top Bar is mismatching");
        softAssert.assertEquals(testDataForAccountHolder.getMobileNumber(), patientChart.getMobileInPatientChart(), "Mobile Number didn't match in the Patient chart");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), patientChart.getZipcodeInPatientChart(), "Zip Code mismatch in Patient Chart.");
        softAssert.assertAll();
    }
    @Test(priority = 3, dependsOnMethods = "testInvitePatientWithMandatoryDetails")
    public void testSetPasswordViaYopMail() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        // Navigate to Yop mail and set password
        newTabAndLaunchYopMail();
        YopMail yopMail = new YopMail(driver);
        yopMail.clickSetPasswordMail(testDataForAccountHolder.getEmail());

        switchToTab(3);
        Thread.sleep(1000);
        setPasswordPage = new SetPasswordPage(driver);
        setPasswordPage.setPassword("Welcome@123");
    }

    @Test(priority = 4, dependsOnMethods = {"testSetPasswordViaYopMail"})
    public void testPatientPortalProfileDetails() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        // Login to Patient Portal
        loginPagePatientPortal = new PatientPortalLoginPage(driver);
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");

        // Navigate to homepage and profile
        homePagePatPortal = new PatientPortalHomePage(driver);
        homePagePatPortal.clickMyProfile();

        myProfilePage = new PatientPortalMyProfilePage(driver);

        softAssert.assertEquals(testDataForAccountHolder.getFullName(), myProfilePage.getNameOfAccountHolder(),
                "Name of the Account Holder is mismatching in My profile");
        softAssert.assertEquals(testDataForAccountHolder.getEmail().toLowerCase(), myProfilePage.getEmailOfAccountHolder().toLowerCase(),
                "Email of the Account Holder is mismatching in My profile");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), myProfilePage.getZipCodeOfAccountHolderInLineOne(),
                "Zipcode of the Account Holder is mismatching in My profile");
        softAssert.assertEquals(testDataForAccountHolder.getMobileNumber(), myProfilePage.getMobileOfAccountHolder(),
                "Mobile Number of the Account Holder is mismatching in My profile");
        softAssert.assertAll();
    }
    //@AfterClass()
    public void cleanUp() throws InterruptedException {
        myProfilePage = new PatientPortalMyProfilePage(driver);
        myProfilePage.clickSettingsLink();
        myProfilePage.clickLogoutButton();
        myProfilePage.clickConfirmLogoutButton();

        switchToTab("SkyMD Provider Portal");
        patientChart = new PatientChart(driver);
        patientChart.clickProfileIcon();
        patientChart.clickLogoutButton();
    }
}
