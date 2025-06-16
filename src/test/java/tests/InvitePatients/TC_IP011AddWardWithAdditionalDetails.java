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

public class TC_IP011AddWardWithAdditionalDetails extends BaseTest {
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

    public TestData testDataForAccountHolder;
    public TestData testDataForWard;
    public SoftAssert softAssert;

    @BeforeClass
    public void setUp() throws IOException {
        //Loading config File
        loadPropFile();
        driver.get(property.getProperty("ProviderPortalUrl"));

        //Test data for account holder and Ward
        testDataForAccountHolder = new TestData();
        testDataForWard = new TestData();

        // Login as MA
        loginPage = new LoginPage(driver);
        loginPage.setEmailAs(property.getProperty("MA_Email"));
        loginPage.setPasswordAs(property.getProperty("MA_Password"));
        loginPage.clickLoginButton();
    }
    @BeforeMethod
    public void initializeAsset() throws IOException {
        softAssert = new SoftAssert();
    }
    @Test(priority = 1)
    public void testAddWardAndAdditionalDetails() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        dashBoardPage = new DashBoardPage(driver);
        dashBoardPage.clickInvitePatientLink();

        invitePatientPage = new InvitePatientPage(driver);
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());
        invitePatientPage.selectProviderNameAs(testDataForAccountHolder.getProviderName());

        // Add Ward fields
        invitePatientPage.clickAddAdditionalPatientBtnForPatientOne();
        invitePatientPage.selectPatientTypeForPatientOne("Ward (legal guardian of 18+ years)");
        invitePatientPage.setFirstNameForPatientOne(testDataForWard.getFname());
        invitePatientPage.setLastNameForPatientOne(testDataForWard.getLname());
        invitePatientPage.setZipCodeForPatientOne(testDataForWard.getZipCode());

        invitePatientPage.clickAdditionalInformationForPatientOne();
        invitePatientPage.setStreetAddressOneForPatientOne(testDataForWard.getStreetAddressOne());
        invitePatientPage.setStreetAddressTwoForPatientOne(testDataForWard.getStreetAddressTwo());
        invitePatientPage.setDobForPatientOne(testDataForWard.getDobForMajor());
        invitePatientPage.selectGenderForPatientOne(testDataForWard.getGender());
        invitePatientPage.setFeetForPatientOne(testDataForWard.getFeet());
        invitePatientPage.setInchForPatientOne(testDataForWard.getInch());
        invitePatientPage.setWeightForPatientOne(testDataForWard.getWeight());
        invitePatientPage.clickAddPatientButton();
    }
    @Test(priority = 2)
    public void testPatientChartValidations() throws InterruptedException {
        switchToTab(1);
        patientChart = new PatientChart(driver);
        // Account Holder validations
        softAssert.assertEquals(testDataForAccountHolder.getFullName(), patientChart.getNameInThePatientChart(),
                "Account Holder name mismatch in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getEmail(), patientChart.getEmailInThePatientChart(),
                "Account Holder email mismatch in Patient Chart");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), patientChart.getZipcodeInPatientChart(),
                "Account Holder zip code mismatch in Patient Chart");

        // Search for Ward and validate
        patientChart.searchPatient(testDataForWard.getFullName());

        //validation for Ward mandatory details
        Thread.sleep(2000);
        softAssert.assertEquals(testDataForWard.getFullName(), patientChart.getNameInThePatientChart(),
                "Ward name mismatch in Patient Chart");
        softAssert.assertEquals(testDataForWard.getZipCode(), patientChart.getZipcodeInPatientChart(),
                "Ward zip code mismatch in Patient Chart");

        // Additional details
        softAssert.assertTrue(patientChart.getAddress().contains(testDataForWard.getStreetAddressOne()), "Account Holder's address one from test data is not found in the Patient Chart address");
        softAssert.assertTrue(patientChart.getAddress().contains(testDataForWard.getStreetAddressTwo()), "Account Holder's address two from test data is not found in the Patient Chart address");
        softAssert.assertEquals(testDataForWard.getGender().toLowerCase(), patientChart.getGender().toLowerCase(), "Gender mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForWard.getGender().toLowerCase(), patientChart.getGenderAtTopBar().toLowerCase(), "Gender from test data does not match Gender in Top Bar");
        softAssert.assertEquals(testDataForWard.getWholeHeight(), patientChart.getHeight(), "Height mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForWard.getWeight(), patientChart.getWeight(), "Weight mismatch for Account Holder in Patient Chart");
        softAssert.assertEquals(testDataForWard.getDobForMajor(), patientChart.getDOB(), "DOB is mismatch for Account Holder in Patient Chart");
        softAssert.assertAll();
    }
    @Test(priority = 3)
    public void testSetPasswordViaYopMail() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        newTabAndLaunchYopMail();
        YopMail yopMail = new YopMail(driver);
        yopMail.clickSetPasswordMail(testDataForAccountHolder.getEmail());

        switchToTab(3);
        setPasswordPage = new SetPasswordPage(driver);
        setPasswordPage.setPassword("Welcome@123");
    }
    @Test(priority = 4)
    public void testPatientPortalDependentValidation() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        // Login to Patient Portal
        loginPagePatientPortal = new PatientPortalLoginPage(driver);
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");

        // Navigate to homepage and profile
        homePagePatPortal = new PatientPortalHomePage(driver);
        homePagePatPortal.clickMyProfile();

        myProfilePage = new PatientPortalMyProfilePage(driver);
        myProfilePage.clickDependents();
        softAssert.assertEquals(testDataForWard.getFullName(), myProfilePage.getDependentOneName(),
                "Dependent's name does not match the expected full name for Ward in my profile.");
        softAssert.assertEquals("Ward", myProfilePage.getDependentOneType(),
                "Dependent's type is not 'Ward' as expected in my profile.");
        softAssert.assertEquals(testDataForWard.getGender(), myProfilePage.getDependentOneGender(),
                "Ward's gender in the profile does not match in my profile");
        softAssert.assertEquals(testDataForWard.getWeight(), myProfilePage.getDependentOneWeight(),
                "Ward's weight in the profile does not match in my profile");
        softAssert.assertEquals(testDataForWard.getWholeHeight(), myProfilePage.getDependentOneHeight(),
                "Ward's height in the profile does not match in my profile");

        // Navigate to home page and Dermatology Visit
        myProfilePage.clickHomePageLink();

        softAssert.assertAll();
    }
    @Test(priority = 5)
    public void testDermatologyVisitValidation() throws InterruptedException {
        homePagePatPortal = new PatientPortalHomePage(driver);
        //select dermatology visit
        homePagePatPortal.selectDermatologyVisit();
        //dermatology Visit Page
        dermatologyVisitPage = new DermatologyVisitPage(driver);
        dermatologyVisitPage.clickSelectPatient();
        Thread.sleep(1000);
        dermatologyVisitPage.selectPatientAsWard();
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();
        Thread.sleep(1000);
        //validate Ward name
        softAssert.assertEquals(testDataForWard.getFullName(), dermatologyVisitPage.getNameOfTheWardInSelectWard());
        dermatologyVisitPage.clickContinueButton();
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButtonAfterInsurance();
        Thread.sleep(1000);
        // Validate address and zipcode on Dermatology Visit page
        softAssert.assertEquals(testDataForWard.getStreetAddressOne(), dermatologyVisitPage.getAddressLineOneValue(),
                "Street Address Line One mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForWard.getStreetAddressTwo(), dermatologyVisitPage.getAddressLineTwoValue(),
                "Street Address Line Two mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForWard.getZipCode(), dermatologyVisitPage.getZipCodeValue(),
                "Zip Code mismatch on Dermatology Visit page.");
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButton();
        Thread.sleep(1000);
        // Validate Height, Weight, DOB
        softAssert.assertEquals(testDataForWard.getDobForMajor(), dermatologyVisitPage.getDOBValue(),
                "Date of Birth mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForWard.getFeet(), dermatologyVisitPage.getFeetValue(),
                "Height (Feet) mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForWard.getInch(), dermatologyVisitPage.getInchesValue(),
                "Height (Inches) mismatch on Dermatology Visit page.");
        softAssert.assertEquals(testDataForWard.getWeight(), dermatologyVisitPage.getWeightValue(),
                "Weight mismatch on Dermatology Visit page.");

        dermatologyVisitPage.clickBackArrowForVisitForm();
        Thread.sleep(1000);
        dermatologyVisitPage.clickBackArrowForHomePage();
        softAssert.assertAll();

    }
    //@Test(priority = 6)
    public void testPrimaryCareVisitValidation() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        homePagePatPortal = new PatientPortalHomePage(driver);
        homePagePatPortal.selectPrimaryCareVisit();

        primaryCareVisitPage = new PrimaryCareVisitPage(driver);
        primaryCareVisitPage.clickPatientAsWard();
        primaryCareVisitPage.clickNextButton();

        //primaryCareVisitPage.clickSelectPatient(testDataForWard.getFullName());
        primaryCareVisitPage.clickNextButton();

        primaryCareVisitPage.clickProceedByBookingButton();

        primaryCareVisitPage.clickSelectFirstCondition();

        primaryCareVisitPage.clickNextButton();

        primaryCareVisitPage.clickSelectFirstDateForSlot();
        primaryCareVisitPage.clickSelectFirstSlot();

        primaryCareVisitPage.clickNextButton();

        softAssert.assertEquals(testDataForWard.getFname(), primaryCareVisitPage.getFirstName(),
                "First name does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForWard.getLname(), primaryCareVisitPage.getLastName(),
                "Last name does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForWard.getDobForMajor(), primaryCareVisitPage.getDOB(),
                "Date of birth does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForWard.getStreetAddressOne(), primaryCareVisitPage.getAddressOne(),
                "Street Address One does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForWard.getStreetAddressTwo(), primaryCareVisitPage.getAddressTwo(),
                "Street Address Two does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForWard.getZipCode(), primaryCareVisitPage.getZipcode(),
                "Zip Code does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForWard.getFeet(), primaryCareVisitPage.getFeet(),
                "Height (feet) does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForWard.getInch(), primaryCareVisitPage.getInch(),
                "Height (inches) does not match for Ward on Primary Care Visit page.");

        softAssert.assertEquals(testDataForWard.getWeight(), primaryCareVisitPage.getWeight(),
                "Weight does not match for Ward on Primary Care Visit page.");
        primaryCareVisitPage.clickBackArrowToHomePage();
        softAssert.assertAll();
    }
    @AfterClass()
    public void cleanUp() throws InterruptedException {

        //navigate to my profile
        homePagePatPortal = new PatientPortalHomePage(driver);
        homePagePatPortal.clickMyProfile();

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
