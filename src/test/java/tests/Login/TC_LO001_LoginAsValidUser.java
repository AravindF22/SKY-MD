package tests.Login;

import Utils.ExtentReportManager;
import Utils.TestData;
import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.PatientPortal.PatientPortalHomePage;
import pages.PatientPortal.PatientPortalLoginPage;
import pages.PatientPortal.PatientPortalMyProfilePage;

import java.io.IOException;
import java.time.Duration;
/**
 * Test Case: TC_LO001
 * Description: Login as an existing patient with valid credentials
 *              and verify that the home page is displayed successfully.
 */
public class TC_LO001_LoginAsValidUser extends BaseTest {
    public PatientPortalLoginPage patientPortalLoginPage;
    public PatientPortalHomePage patientPortalHomePage;
    public PatientPortalMyProfilePage myProfilePage;
    public TestData testDataForAccountHolder;
    public SoftAssert softAssert;
    public WebDriverWait wait;
    @BeforeClass
    public void setUp() throws IOException {
        loadPropFile();
        driver.get(property.getProperty("PatientPortalLoginUrl"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    @BeforeMethod
    public void initializeAsset(){
        softAssert = new SoftAssert();
    }
    @Test(priority = 1)
    public void testLoginWithValidCredentials()
    {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        ExtentReportManager.getTest().log(Status.INFO, "Starting login with valid patient credentials");

        patientPortalLoginPage = new PatientPortalLoginPage(driver);
        patientPortalLoginPage.setEmail(property.getProperty("ExistingPatientEmail"));
        ExtentReportManager.getTest().log(Status.INFO, "Entered valid email");

        patientPortalLoginPage.setPassword(property.getProperty("PatientPortalPassword"));
        ExtentReportManager.getTest().log(Status.INFO, "Entered valid password");

        patientPortalLoginPage.clickLoginButton();
        ExtentReportManager.getTest().log(Status.INFO, "Clicked on Login button");

        patientPortalHomePage = new PatientPortalHomePage(driver);
        boolean isHome = patientPortalHomePage.isHomePage();

        softAssert.assertTrue(isHome, "Home page did not load properly after login.");

        if (isHome) {
            ExtentReportManager.getTest().log(Status.INFO, "Login successful, home page loaded.");
        } else {
            ExtentReportManager.getTest().log(Status.INFO, "Login failed or home page did not load.");
        }

        softAssert.assertAll();
    }
    @AfterClass
    public void patientAndProviderPortalLogout() throws InterruptedException {
        // Navigate to myProfile and logout
        patientPortalHomePage.clickMyProfile();

        myProfilePage = new PatientPortalMyProfilePage(driver);
        myProfilePage.clickSettingsLink();
        myProfilePage.clickLogoutButton();
        myProfilePage.clickConfirmLogoutButton();
    }
}
