package tests.Login;

import Utils.ExtentReportManager;
import Utils.TestData;
import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
 * Test Case: TC_LO003
 * Description: Verify error messages are displayed on the login page
 *              when both email and password fields are left empty.
 *              when invalid email is entered.
 *              when password entered less than minimum value.
 *              when password entered More than Maximum value.
 */
public class TC_LO003_VerifyLoginPageErrorMessages extends BaseTest {
    public PatientPortalLoginPage patientPortalLoginPage;
    public PatientPortalHomePage patientPortalHomePage;
    public PatientPortalMyProfilePage myProfilePage;
    public TestData testDataForAccountHolder;
    public SoftAssert softAssert;
    public WebDriverWait wait;
    public Actions actions;
    @BeforeClass
    public void setUp() throws IOException {
        loadPropFile();
        driver.get(property.getProperty("PatientPortalLoginUrl"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        actions = new Actions(driver);
    }
    @BeforeMethod
    public void initializeAsset(){
        softAssert = new SoftAssert();
        patientPortalLoginPage = new PatientPortalLoginPage(driver);
    }
    @Test(priority = 1)
    public void testErrorMessagesDisplayedWhenEmailAndPasswordAreEmpty()
    {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Verify error messages when email and password are left empty");


        patientPortalLoginPage.setEmail("");
        ExtentReportManager.getTest().log(Status.INFO, "Left email field empty");
        actions.keyDown(Keys.TAB).keyUp(Keys.TAB).perform();
        boolean emailErrorVisible = patientPortalLoginPage.isEmailRequiredErrorDisplayed();
        softAssert.assertTrue(emailErrorVisible, "'Email is required' error message was not displayed");
        ExtentReportManager.getTest().log(Status.INFO, "'Email is required' error validation checked");

        patientPortalLoginPage.setPassword("");
        ExtentReportManager.getTest().log(Status.INFO, "Left password field empty");
        actions.keyDown(Keys.TAB).keyUp(Keys.TAB).perform();
        boolean passwordErrorVisible = patientPortalLoginPage.isPasswordRequiredErrorDisplayed();
        softAssert.assertTrue(passwordErrorVisible, "'Password is required' error message was not displayed");
        ExtentReportManager.getTest().log(Status.INFO, "'Password is required' error validation checked");

        softAssert.assertAll();
    }

    @Test(priority = 2)
    public void testErrorMessageForInvalidEmail() {
        ExtentReportManager.getTest().log(Status.INFO, "Testing error message for invalid email format");

        patientPortalLoginPage.setEmail(property.getProperty("invalidEmailOne"));

        boolean isValidError = patientPortalLoginPage.isEnterValidEmailErrorDisplayed();
        softAssert.assertTrue(isValidError, "'Please enter a valid email address' error was not displayed.");

        ExtentReportManager.getTest().log(Status.INFO, "Verified error message for invalid email");
        softAssert.assertAll();
    }

    @Test(priority = 3)
    public void testErrorMessageForInvalidPassword() {
        ExtentReportManager.getTest().log(Status.INFO, "Testing error message for short password");

        patientPortalLoginPage.setPassword(property.getProperty("invalidPasswordlessThanEight"));
        boolean isMinError = patientPortalLoginPage.isMinEightCharactersErrorDisplayed();
        softAssert.assertTrue(isMinError, "'Password must be at least 8 characters' error was not displayed.");
        ExtentReportManager.getTest().log(Status.PASS, "Verified error for password less than 8 characters");

        ExtentReportManager.getTest().log(Status.INFO, "Testing error message for long password");

        patientPortalLoginPage.setPassword(property.getProperty("invalidPasswordMoreThanTwenty"));
        boolean isMaxError = patientPortalLoginPage.isMaxTwentyCharactersErrorDisplayed();
        softAssert.assertTrue(isMaxError, "'Password cannot exceed 20 characters' error was not displayed.");
        ExtentReportManager.getTest().log(Status.INFO, "Verified error for password more than 20 characters");

        softAssert.assertAll();
    }
}
