package tests.Login;

import utils.ConfigReader;
import utils.ExtentReportManager;
import utils.TestData;
import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.PatientPortal.PatientPortalLoginPage;
import pages.PatientPortal.PatientPortalMyProfilePage;

import java.io.IOException;
import java.time.Duration;
/**
 * Test Case: TC_LO002
 * Description: Attempt to log in with a non-registered email and valid password.
 *              Verify that the correct error toast message is displayed.
 */
public class TC_LO002_LoginWithNonRegisteredEmail extends BaseTest {
    public PatientPortalLoginPage patientPortalLoginPage;
    public PatientPortalMyProfilePage myProfilePage;
    public TestData testDataForAccountHolder;
    public SoftAssert softAssert;
    public WebDriverWait wait;
    @BeforeClass
    public void setUp() throws IOException {
        driver.get(ConfigReader.getProperty("PatientPortalLoginUrl"));
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
        patientPortalLoginPage.setEmail(ConfigReader.getProperty("NewPatientEmail"));
        ExtentReportManager.getTest().log(Status.INFO, "Entered Non registered email");

        patientPortalLoginPage.setPassword(ConfigReader.getProperty("PatientPortalMasterPassword"));
        ExtentReportManager.getTest().log(Status.INFO, "Entered valid password");

        patientPortalLoginPage.clickLoginButton();
        ExtentReportManager.getTest().log(Status.INFO, "Clicked on Login button");
        boolean isToastVisible = patientPortalLoginPage.isInvalidEmailPasswordCombinationToastPresent();

        softAssert.assertTrue(isToastVisible,
                "Invalid Email / Password Combination Toast message is not appeared");
        if (isToastVisible) {
            ExtentReportManager.getTest().log(Status.INFO, "Toast message appeared correctly for invalid credentials.");
        } else {
            ExtentReportManager.getTest().log(Status.INFO, "Toast message not shown for invalid credentials.");
        }
        softAssert.assertAll();
    }
}
