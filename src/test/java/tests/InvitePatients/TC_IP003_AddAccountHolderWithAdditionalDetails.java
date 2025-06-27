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
 * Test Case: TC_IP004
 * Description: Invite an account holder with additional details (address, gender, height, weight, DOB)
 *              and verify that all additional information is correctly displayed in the patient chart and patient portal profile.
 */
public class TC_IP003_AddAccountHolderWithAdditionalDetails extends BaseTest {
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

        //Test data for account holder
        testDataForAccountHolder = new TestData();
        // Login as MA
        loginPage = new LoginPage(driver);
        loginPage.setEmailAs(property.getProperty("MA_Email"));
        loginPage.setPasswordAs(property.getProperty("MA_Password"));
        loginPage.clickLoginButton();

        dashBoardPage = new DashBoardPage(driver);
        dashBoardPage.clickInvitePatientLink();
    }
    @BeforeMethod
    public void initializeAsset() throws IOException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        softAssert = new SoftAssert();
        //page objects initialization
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
    public void testInvitePatientWithAdditionalDetails() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: Invite Account Holder with Additional Details");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // Filling account holder details
        ExtentReportManager.getTest().log(Status.INFO, "Filling account holder mandatory details");
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());
        invitePatientPage.selectProviderNameAs(testDataForAccountHolder.getProviderName());

        // Additional information
        ExtentReportManager.getTest().log(Status.INFO, "Filling additional information for account holder");
        invitePatientPage.clickAdditionalInformationCheckboxForAccountHolder();
        invitePatientPage.setStreetAddressOne(testDataForAccountHolder.getStreetAddressOne());
        invitePatientPage.setStreetAddressTwo(testDataForAccountHolder.getStreetAddressTwo());
        invitePatientPage.selectGender(testDataForAccountHolder.getGender());
        invitePatientPage.setHeightFeet(testDataForAccountHolder.getFeet());
        invitePatientPage.setHeightInches(testDataForAccountHolder.getInch());
        invitePatientPage.setWeight(testDataForAccountHolder.getWeight());
        invitePatientPage.setDOB(testDataForAccountHolder.getDobForMajor());
        ExtentReportManager.getTest().log(Status.INFO, "Submitting invite for account holder");
        invitePatientPage.clickAddPatientButton();
        ExtentReportManager.getTest().log(Status.INFO, "Invite patient form submitted successfully");
        String patientDetailsHtml = "<b>Entered Patient Details:</b><br>" +
                "First Name: " + testDataForAccountHolder.getFname() + "<br>" +
                "Last Name: " + testDataForAccountHolder.getLname() + "<br>" +
                "Email: " + testDataForAccountHolder.getEmail() + "<br>" +
                "Phone: " + testDataForAccountHolder.getMobileNumber() + "<br>" +
                "Zipcode: " + testDataForAccountHolder.getZipCode() + "<br>" +
                "Provider Name: " + testDataForAccountHolder.getProviderName() + "<br>" +
                "Street Address 1: " + testDataForAccountHolder.getStreetAddressOne() + "<br>" +
                "Street Address 2: " + testDataForAccountHolder.getStreetAddressTwo() + "<br>" +
                "Gender: " + testDataForAccountHolder.getGender() + "<br>" +
                "Height: " + testDataForAccountHolder.getFeet() + " ft " + testDataForAccountHolder.getInch() + " in<br>" +
                "Weight: " + testDataForAccountHolder.getWeight() + " lbs<br>" +
                "DOB: " + testDataForAccountHolder.getDobForMajor();
        ExtentReportManager.getTest().info(patientDetailsHtml);
    }
    @Test(priority = 2)
    public void testValidatePatientChartDetails() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Validating patient chart details in Provider Portal");
        switchToTab(1);
        if(!patientChart.isPatientChart()){
            ExtentReportManager.getTest().log(Status.INFO, "Patient chart not visible – test skipped");
            Assert.fail("Patient chart page not loaded.");
        }
        Thread.sleep(3000);
        // Mandatory details
        ExtentReportManager.getTest().log(Status.INFO, "Validating mandatory details in patient chart");
        softAssert.assertEquals(testDataForAccountHolder.getFullName(), patientChart.getNameInThePatientChart(), "Name didn't match in the Patient chart");
        softAssert.assertEquals(testDataForAccountHolder.getEmail().toLowerCase(), patientChart.getEmailInThePatientChart().toLowerCase(), "Email didn't match");
        softAssert.assertEquals(testDataForAccountHolder.getFullName().toUpperCase(), patientChart.getNameInTopBar(), "Name in the Top Bar is mismatching");
        softAssert.assertEquals(testDataForAccountHolder.getMobileNumber(), patientChart.getMobileInPatientChart(), "Mobile Number didn't match in the Patient chart");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), patientChart.getZipcodeInPatientChart(), "Zip code mismatch for Account Holder in Patient Chart");

        // Additional details
        ExtentReportManager.getTest().log(Status.INFO, "Validating additional details in patient chart");
        softAssert.assertTrue(patientChart.getAddress().contains(testDataForAccountHolder.getStreetAddressOne()), "Account Holder's address one from test data is not found in the Patient Chart address");
        softAssert.assertTrue(patientChart.getAddress().contains(testDataForAccountHolder.getStreetAddressTwo()), "Account Holder's address two from test data is not found in the Patient Chart address");
        softAssert.assertEquals(testDataForAccountHolder.getGender().toLowerCase(), patientChart.getGender().toLowerCase(), "Gender mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getGender().toLowerCase(), patientChart.getGenderAtTopBar().toLowerCase(), "Gender from test data does not match Gender in Top Bar");
        softAssert.assertEquals(testDataForAccountHolder.getWholeHeight(), patientChart.getHeight(), "Height mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getWeight(), patientChart.getWeight(), "Weight mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getDobForMajor(), patientChart.getDOB(), "DOB is mismatch for Account Holder in Patient Chart");
        ExtentReportManager.getTest().log(Status.INFO, "Patient chart details validated successfully");
        softAssert.assertAll();
    }
    @Test(priority = 3)
    public void testSetPasswordViaYopMail() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Setting password for invited account holder via YopMail");
        //newTabAndLaunchYopMail();
        yopMail.clickSetPasswordMail(testDataForAccountHolder.getEmail());
        switchToTab(3);
        setPasswordPage.setPassword("Welcome@123");
        ExtentReportManager.getTest().log(Status.INFO, "Password set successfully for invited account holder");
    }

    @Test(priority = 4)
    public void testPatientPortalProfileAndVisitFlow() throws InterruptedException {
        ExtentReportManager.getTest().log(Status.INFO, "Logging into Patient Portal and validating profile and visit flow");
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");
        homePagePatPortal.clickMyProfile();

        // Mandatory details
        ExtentReportManager.getTest().log(Status.INFO, "Validating mandatory details in My Profile page");
        softAssert.assertEquals(testDataForAccountHolder.getFullName(), myProfilePage.getNameOfAccountHolder(),
                "Name of the Account Holder is mismatching in My profile");
        softAssert.assertEquals(testDataForAccountHolder.getEmail().toLowerCase(), myProfilePage.getEmailOfAccountHolder().toLowerCase(),
                "Email of the Account Holder is mismatching in My profile");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), myProfilePage.getZipCodeOfAccountHolder(),
                "Zipcode of the Account Holder is mismatching in My profile");
        softAssert.assertEquals(testDataForAccountHolder.getMobileNumber(), myProfilePage.getMobileOfAccountHolder(),
                "Mobile Number of the Account Holder is mismatching in My profile");
        // Additional details
        ExtentReportManager.getTest().log(Status.INFO, "Validating additional details in My Profile page");
        softAssert.assertEquals(testDataForAccountHolder.getGender(), myProfilePage.getGender(),
                "Gender mismatch in My Profile page.");
        softAssert.assertEquals(testDataForAccountHolder.getStreetAddressOne(), myProfilePage.getAddressLineOne(),
                "Street Address Line One mismatch in My Profile page.");
        softAssert.assertTrue(myProfilePage.getAddressLineTwo().contains(testDataForAccountHolder.getStreetAddressTwo()),
                "Street Address Line Two from test data is not found in My Profile page.");

        // Visit flow
        ExtentReportManager.getTest().log(Status.INFO, "Starting Dermatology Visit flow");
        myProfilePage.clickHomePageLink();
        homePagePatPortal.selectDermatologyVisit();
        dermatologyVisitPage.clickSelectPatient();
        dermatologyVisitPage.clickSelectPatientAsMySelf();
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();
        Thread.sleep(500);
        dermatologyVisitPage.clickContinueButtonAfterInsurance();

        // Validate address and zipcode on Dermatology Visit page
        ExtentReportManager.getTest().log(Status.INFO, "Validating address and zipcode on Dermatology Visit page");
        softAssert.assertEquals(testDataForAccountHolder.getStreetAddressOne(), dermatologyVisitPage.getAddressLineOneValue(), "Street Address Line One mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getStreetAddressTwo(), dermatologyVisitPage.getAddressLineTwoValue(), "Street Address Line Two mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), dermatologyVisitPage.getZipCodeValue(), "Zip Code mismatch on Dermatology Visit page.");

        dermatologyVisitPage.clickContinueButton();

        // Validate Height, Weight, DOB
        ExtentReportManager.getTest().log(Status.INFO, "Validating Height, Weight, and DOB on Dermatology Visit page");
        softAssert.assertEquals(testDataForAccountHolder.getDobForMajor(), dermatologyVisitPage.getDOBValue(), "Date of Birth mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getFeet(), dermatologyVisitPage.getFeetValue(), "Height (Feet) mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getInch(), dermatologyVisitPage.getInchesValue(), "Height (Inches) mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getWeight(), dermatologyVisitPage.getWeightValue(), "Weight mismatch on Dermatology Visit page.");
      
        dermatologyVisitPage.clickBackArrowForVisitForm();
        dermatologyVisitPage.clickBackArrowForHomePage();

        ExtentReportManager.getTest().log(Status.INFO, "Patient portal profile and visit flow validated successfully");
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
