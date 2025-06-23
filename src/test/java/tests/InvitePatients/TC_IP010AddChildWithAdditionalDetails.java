package tests.InvitePatients;

import Utils.TestData;
import base.BaseTest;
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

import java.io.IOException;
import java.time.Duration;

/**
 * Test Case: TC_IP010
 * Description: Invite an account holder and add a child with additional details (address, gender, height, weight, DOB),
 *              then verify that all additional information is correctly displayed in the patient chart and patient portal profile for the child.
 */
public class TC_IP010AddChildWithAdditionalDetails extends BaseTest {
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
        //Loading config File
        loadPropFile();
        driver.get(property.getProperty("ProviderPortalUrl"));

        //Test data for account holder and child
        testDataForAccountHolder = new TestData();
        testDataForChild = new TestData();

        // Login as MA
        loginPage = new LoginPage(driver);
        loginPage.setEmailAs(property.getProperty("MA_Email"));
        loginPage.setPasswordAs(property.getProperty("MA_Password"));
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
    public void testAddChildAndAdditionalDetails() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        dashBoardPage.clickInvitePatientLink();
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());
        invitePatientPage.selectProviderNameAs(testDataForAccountHolder.getProviderName());

        // Add child fields
        invitePatientPage.clickAddAdditionalPatientBtnForPatientOne();
        invitePatientPage.selectPatientTypeForPatientOne("Child");
        invitePatientPage.setFirstNameForPatientOne(testDataForChild.getFname());
        invitePatientPage.setLastNameForPatientOne(testDataForChild.getLname());
        invitePatientPage.setZipCodeForPatientOne(testDataForChild.getZipCode());

        invitePatientPage.clickAdditionalInformationForPatientOne();
        invitePatientPage.setStreetAddressOneForPatientOne(testDataForChild.getStreetAddressOne());
        invitePatientPage.setStreetAddressTwoForPatientOne(testDataForChild.getStreetAddressTwo());
        invitePatientPage.setDobForPatientOne(testDataForChild.getDobForMinor());
        invitePatientPage.selectGenderForPatientOne(testDataForChild.getGender());
        invitePatientPage.setFeetForPatientOne(testDataForChild.getFeet());
        invitePatientPage.setInchForPatientOne(testDataForChild.getInch());
        invitePatientPage.setWeightForPatientOne(testDataForChild.getWeight());
        invitePatientPage.clickAddPatientButton();
    }
    @Test(priority = 2)
    public void testPatientChartValidations() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        switchToTab(1);
        // patientChart already initialized in @BeforeMethod
        // Account Holder validations
        softAssert.assertEquals(testDataForAccountHolder.getFullName(), patientChart.getNameInThePatientChart(),
                "Account Holder name mismatch in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getEmail(), patientChart.getEmailInThePatientChart(),
                "Account Holder email mismatch in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), patientChart.getZipcodeInPatientChart(),
                "Account Holder zip code mismatch in Patient Chart");

        // Search for child and validate
        patientChart.searchPatient(testDataForChild.getFullName());

        //validation for child mandatory details
        Thread.sleep(2000);
        softAssert.assertEquals(testDataForChild.getFullName(), patientChart.getNameInThePatientChart(),
                "Child name mismatch in Patient Chart");
        softAssert.assertEquals(testDataForChild.getZipCode(), patientChart.getZipcodeInPatientChart(),
                "Child zip code mismatch in Patient Chart");

        // Additional details
        softAssert.assertTrue(patientChart.getAddress().contains(testDataForChild.getStreetAddressOne()), "Account Holder's address one from test data is not found in the Patient Chart address");
        softAssert.assertTrue(patientChart.getAddress().contains(testDataForChild.getStreetAddressTwo()), "Account Holder's address two from test data is not found in the Patient Chart address");
        softAssert.assertEquals(testDataForChild.getGender().toLowerCase(), patientChart.getGender().toLowerCase(), "Gender mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForChild.getGender().toLowerCase(), patientChart.getGenderAtTopBar().toLowerCase(), "Gender from test data does not match Gender in Top Bar");
        softAssert.assertEquals(testDataForChild.getWholeHeight(), patientChart.getHeight(), "Height mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForChild.getWeight(), patientChart.getWeight(), "Weight mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForChild.getDobForMinor(), patientChart.getDOB(), "DOB is mismatch for Account Holder in Patient Chart");
        softAssert.assertAll();
    }
    @Test(priority = 3)
    public void testSetPasswordViaYopMail() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        newTabAndLaunchYopMail();
        yopMail.clickSetPasswordMail(testDataForAccountHolder.getEmail());
        switchToTab(3);
        setPasswordPage.setPassword("Welcome@123");
    }
    @Test(priority = 4, dependsOnMethods = "testSetPasswordViaYopMail")
    public void testPatientPortalDependentAndVisitFlow() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // Login to Patient Portal
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");

        // Navigate to homepage and profile
        homePagePatPortal.clickMyProfile();
        myProfilePage.clickDependents();
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

        // Navigate to home page and Dermatology Visit
        myProfilePage.clickHomePageLink();

        softAssert.assertAll();
    }
    @Test(priority = 5)
    public void testDermatologyVisitValidation() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //select dermatology visit
        homePagePatPortal.selectDermatologyVisit();
        //dermatology Visit Page
        dermatologyVisitPage.clickSelectPatient();
        Thread.sleep(1000);
        dermatologyVisitPage.selectPatientAsMyChild();
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();
        Thread.sleep(1000);
        //validate child name
        softAssert.assertEquals(testDataForChild.getFullName(), dermatologyVisitPage.getNameOfTheChildInSelectChild());
        dermatologyVisitPage.clickContinueButton();
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButtonAfterInsurance();
        Thread.sleep(1000);
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
        // Validate Height, Weight, DOB
        softAssert.assertEquals(testDataForChild.getDobForMinor(), dermatologyVisitPage.getDOBValue(),
                "Date of Birth mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForChild.getFeet(), dermatologyVisitPage.getFeetValue(),
                "Height (Feet) mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForChild.getInch(), dermatologyVisitPage.getInchesValue(),
                "Height (Inches) mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForChild.getWeight(), dermatologyVisitPage.getWeightValue(),
                "Weight mismatch on Dermatology Visit page.");

        dermatologyVisitPage.clickBackArrowForVisitForm();
        Thread.sleep(1000);
        dermatologyVisitPage.clickBackArrowForHomePage();

    }
    //@Test(priority = 6)
    public void testPrimaryCareVisitValidation() throws InterruptedException {
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
