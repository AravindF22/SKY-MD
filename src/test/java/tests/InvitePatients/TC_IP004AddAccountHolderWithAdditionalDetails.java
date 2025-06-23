package tests.InvitePatients;

import Utils.TestData;
import base.BaseTest;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.PatientPortal.*;
import pages.ProviderPortal.DashBoardPage;
import pages.ProviderPortal.InvitePatientPage;
import pages.ProviderPortal.LoginPage;
import pages.ProviderPortal.PatientChart;
import pages.YopMail;

import java.io.IOException;
import java.time.Duration;

/**
 * Test Case: TC_IP004
 * Description: Invite an account holder with additional details (address, gender, height, weight, DOB)
 *              and verify that all additional information is correctly displayed in the patient chart and patient portal profile.
 */
public class TC_IP004AddAccountHolderWithAdditionalDetails extends BaseTest {
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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // filling account holder details
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());
        invitePatientPage.selectProviderNameAs(testDataForAccountHolder.getProviderName());

        // Additional information
        invitePatientPage.clickAdditionalInformationCheckboxForAccountHolder();
        invitePatientPage.setStreetAddressOne(testDataForAccountHolder.getStreetAddressOne());
        invitePatientPage.setStreetAddressTwo(testDataForAccountHolder.getStreetAddressTwo());
        invitePatientPage.selectGender(testDataForAccountHolder.getGender());
        invitePatientPage.setHeightFeet(testDataForAccountHolder.getFeet());
        invitePatientPage.setHeightInches(testDataForAccountHolder.getInch());
        invitePatientPage.setWeight(testDataForAccountHolder.getWeight());
        invitePatientPage.setDOB(testDataForAccountHolder.getDobForMajor());

        invitePatientPage.clickAddPatientButton();
    }
    @Test(priority = 2)
    public void testValidatePatientChartDetails() throws InterruptedException {
        switchToTab(1);
        // Mandatory details
        softAssert.assertEquals(testDataForAccountHolder.getFullName(), patientChart.getNameInThePatientChart(), "Name didn't match in the Patient chart");
        softAssert.assertEquals(testDataForAccountHolder.getEmail().toLowerCase(), patientChart.getEmailInThePatientChart().toLowerCase(), "Email didn't match");
        softAssert.assertEquals(testDataForAccountHolder.getFullName().toUpperCase(), patientChart.getNameInTopBar(), "Name in the Top Bar is mismatching");
        softAssert.assertEquals(testDataForAccountHolder.getMobileNumber(), patientChart.getMobileInPatientChart(), "Mobile Number didn't match in the Patient chart");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), patientChart.getZipcodeInPatientChart(), "Zip code mismatch for Account Holder in Patient Chart");

        // Additional details
        softAssert.assertTrue(patientChart.getAddress().contains(testDataForAccountHolder.getStreetAddressOne()), "Account Holder's address one from test data is not found in the Patient Chart address");
        softAssert.assertTrue(patientChart.getAddress().contains(testDataForAccountHolder.getStreetAddressTwo()), "Account Holder's address two from test data is not found in the Patient Chart address");
        softAssert.assertEquals(testDataForAccountHolder.getGender().toLowerCase(), patientChart.getGender().toLowerCase(), "Gender mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getGender().toLowerCase(), patientChart.getGenderAtTopBar().toLowerCase(), "Gender from test data does not match Gender in Top Bar");
        softAssert.assertEquals(testDataForAccountHolder.getWholeHeight(), patientChart.getHeight(), "Height mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getWeight(), patientChart.getWeight(), "Weight mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getDobForMajor(), patientChart.getDOB(), "DOB is mismatch for Account Holder in Patient Chart");
        softAssert.assertAll();
    }
    @Test(priority = 3)
    public void testSetPasswordViaYopMail() throws InterruptedException {
        newTabAndLaunchYopMail();
        yopMail.clickSetPasswordMail(testDataForAccountHolder.getEmail());
        switchToTab(3);
        setPasswordPage.setPassword("Welcome@123");
    }

    @Test(priority = 4)
    public void testPatientPortalProfileAndVisitFlow() throws InterruptedException {
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");
        homePagePatPortal.clickMyProfile();

        // Mandatory details
        softAssert.assertEquals(testDataForAccountHolder.getFullName(), myProfilePage.getNameOfAccountHolder(), "Name of the Account Holder is mismatching in My profile");
        softAssert.assertEquals(testDataForAccountHolder.getEmail().toLowerCase(), myProfilePage.getEmailOfAccountHolder().toLowerCase(), "Email of the Account Holder is mismatching in My profile");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), myProfilePage.getZipCodeOfAccountHolder(), "Zipcode of the Account Holder is mismatching in My profile");
        softAssert.assertEquals(testDataForAccountHolder.getMobileNumber(), myProfilePage.getMobileOfAccountHolder(), "Mobile Number of the Account Holder is mismatching in My profile");
        // Additional details
        softAssert.assertEquals(testDataForAccountHolder.getGender(), myProfilePage.getGender(), "Gender mismatch in My Profile page.");
        softAssert.assertEquals(testDataForAccountHolder.getStreetAddressOne(), myProfilePage.getAddressLineOne(), "Street Address Line One mismatch in My Profile page.");
        softAssert.assertTrue(myProfilePage.getAddressLineTwo().contains(testDataForAccountHolder.getStreetAddressTwo()), "Street Address Line Two from test data is not found in My Profile page.");

        // Visit flow
        myProfilePage.clickHomePageLink();
        homePagePatPortal.selectDermatologyVisit();
        dermatologyVisitPage.clickSelectPatient();
        dermatologyVisitPage.clickSelectPatientAsMySelf();
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();
        dermatologyVisitPage.clickContinueButtonAfterInsurance();

        // Validate address and zipcode on Dermatology Visit page
        softAssert.assertEquals(testDataForAccountHolder.getStreetAddressOne(), dermatologyVisitPage.getAddressLineOneValue(), "Street Address Line One mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getStreetAddressTwo(), dermatologyVisitPage.getAddressLineTwoValue(), "Street Address Line Two mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), dermatologyVisitPage.getZipCodeValue(), "Zip Code mismatch on Dermatology Visit page.");

        dermatologyVisitPage.clickContinueButton();

        // Validate Height, Weight, DOB
        softAssert.assertEquals(testDataForAccountHolder.getDobForMajor(), dermatologyVisitPage.getDOBValue(), "Date of Birth mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getFeet(), dermatologyVisitPage.getFeetValue(), "Height (Feet) mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getInch(), dermatologyVisitPage.getInchesValue(), "Height (Inches) mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getWeight(), dermatologyVisitPage.getWeightValue(), "Weight mismatch on Dermatology Visit page.");

        //Delete the visit
        dermatologyVisitPage.clickBackArrowForVisitForm();
        dermatologyVisitPage.clickBackArrowForHomePage();

//        dermatologyVisitPage.clickDeleteVisitButton();
//        dermatologyVisitPage.setConfirmForDeleteVisit();
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
