package tests.BasicFlows;

import Utils.TestData;
import base.BaseTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.PatientPortal.PatientPortalHomePage;
import pages.PatientPortal.PatientPortalLoginPage;
import pages.PatientPortal.SignInPage;

import java.io.IOException;
import java.time.Duration;

public class TC_BF001SignIn extends BaseTest {
    public PatientPortalLoginPage patientPortalLoginPage;
    public SignInPage signInPage;
    public PatientPortalHomePage patientPortalHomePage;
    public TestData testDataForAccountHolder;
    public SoftAssert softAssert;
    public WebDriverWait wait;
    @BeforeClass
    public void setUp() throws IOException {
        loadPropFile();
        driver.get(property.getProperty("PatientPortalLoginUrl"));
        testDataForAccountHolder = new TestData();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    @BeforeMethod
    public void initializeAsset() throws IOException {
        softAssert = new SoftAssert();
    }
    @Test(priority = 1)
    public void testSignIn(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        patientPortalLoginPage = new PatientPortalLoginPage(driver);
        patientPortalLoginPage.clickSignUpLink();

        signInPage = new SignInPage(driver);
        signInPage.enterEmail(testDataForAccountHolder.getEmail());
        signInPage.clickNextButton();
        signInPage.enterPassword("Welcome@123");
        signInPage.clickAgeCheckIcon();
        signInPage.clickContinueButton();

        signInPage.enterFirstName(testDataForAccountHolder.getFname());
        signInPage.enterLastName(testDataForAccountHolder.getLname());
        signInPage.enterMobileNumber(testDataForAccountHolder.getMobileNumber());
        signInPage.enterZipCode(testDataForAccountHolder.getZipCode());
        signInPage.clickSignInButton();


        patientPortalHomePage = new PatientPortalHomePage(driver);
        softAssert.assertTrue(patientPortalHomePage.isHomePage());
        softAssert.assertAll();
    }
}
