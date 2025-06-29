package tests.SignIn;

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
import pages.PatientPortal.SignInPage;

import java.io.IOException;
import java.time.Duration;
/**
 * Test Case: TC_SI002
 * Description: Attempt to sign up with an already registered email
 *              and validate that the proper error toast message is displayed.
 */
public class TC_SI002_SignInAsExistingCustomer extends BaseTest {
    public PatientPortalLoginPage patientPortalLoginPage;
    public SignInPage signInPage;
    public TestData testDataForAccountHolder;
    public SoftAssert softAssert;
    public WebDriverWait wait;
    @BeforeClass
    public void setUp() throws IOException {
        driver.get(ConfigReader.getProperty("PatientPortalLoginUrl"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    @BeforeMethod
    public void initializeAsset() throws IOException {
        softAssert = new SoftAssert();
    }
    @Test(priority = 1)
    public void testSignUpWithExistingEmailShowsToast() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        ExtentReportManager.getTest().log(Status.INFO, "Navigating to Sign Up page");

        patientPortalLoginPage = new PatientPortalLoginPage(driver);
        patientPortalLoginPage.clickSignUpLink();
        ExtentReportManager.getTest().log(Status.INFO, "Clicked on Sign Up link");

        signInPage = new SignInPage(driver);
        signInPage.enterEmail(ConfigReader.getProperty("ExistingPatientEmail"));
        ExtentReportManager.getTest().log(Status.INFO, "Entered existing email");

        signInPage.clickNextButton();
        ExtentReportManager.getTest().log(Status.INFO, "Clicked Next button");

        boolean isHome = signInPage.isEmailAlreadyExistsToastPresent();
        ExtentReportManager.getTest().log(Status.INFO, "Checked for 'email already exists' toast");

        softAssert.assertTrue(isHome, "Email already Exists error toast message isn't displayed");

        if (isHome) {
            ExtentReportManager.getTest().log(Status.INFO, "Toast for existing email displayed successfully");
        } else {
            ExtentReportManager.getTest().log(Status.INFO, "Toast for existing email NOT displayed");
        }
        softAssert.assertAll();
    }
}
