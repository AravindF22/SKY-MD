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

public class TC_IP005AddAccountHolderWithBothInsuranceDetails extends BaseTest {
    public LoginPage loginPage;
    public DashBoardPage dashBoardPage;
    public InvitePatientPage invitePatientPage;
    public PatientChart patientChart;
    public SetPasswordPage setPasswordPage;
    public PatientPortalLoginPage loginPagePatientPortal;
    public PatientPortalHomePage homePagePatPortal;
    public PatientPortalMyProfilePage myProfilePage;
    public DermatologyVisitPage dermatologyVisitPage;

    public TestData testDataForAccountHolder;
    public SoftAssert softAssert;
    @BeforeClass
    public void setUp() throws IOException {
        //Loading config File
        loadPropFile();
        driver.get(property.getProperty("ProviderPortalUrl"));

        //Test data for account holder and provider
        testDataForAccountHolder = new TestData();

        // Login as MA
        loginPage = new LoginPage(driver);
        loginPage.setEmailAs(property.getProperty("MA_Email"));
        loginPage.setPasswordAs(property.getProperty("MA_Password"));
        loginPage.clickLoginButton();

        //Navigate to Invite Patient
        dashBoardPage = new DashBoardPage(driver);
        dashBoardPage.clickInvitePatientLink();
    }
    @BeforeMethod
    public void initializeAsset() throws IOException {
        softAssert = new SoftAssert();
    }
    @Test(priority = 1)
    private void testInvitePatientWithInsurance() throws IOException, InterruptedException {
        //implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        invitePatientPage = new InvitePatientPage(driver);
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());
        invitePatientPage.selectProviderNameAs(testDataForAccountHolder.getProviderName());

        //Primary insurance
        invitePatientPage.clickPrimaryInsuranceCheckboxForAH();
        invitePatientPage.setPrimaryInsuranceDropdownForAH(testDataForAccountHolder.getPrimaryInsurance());
        invitePatientPage.setMemberNameInPrimaryInsuranceForAh(testDataForAccountHolder.getMemberNameForPrimaryInsurance());
        invitePatientPage.setMemberIdInPrimaryInsuranceForAh(testDataForAccountHolder.getMemberIdForPrimaryInsurance());
        invitePatientPage.setMemberDOBInPrimaryInsuranceForAh(testDataForAccountHolder.getDobForMajor());
        invitePatientPage.setRelationshipInPrimaryInsuranceForAh(testDataForAccountHolder.getRelationshipForPrimaryInsurance());

        //Secondary insurance
        invitePatientPage.clickSecondaryInsuranceCheckbox();
        invitePatientPage.setSecondaryInsuranceDropdownForAH(testDataForAccountHolder.getSecondaryInsurance());
        invitePatientPage.setMemberNameInSecondaryInsurance(testDataForAccountHolder.getMemberNameForSecondaryInsurance());
        invitePatientPage.setMemberIdInSecondaryInsurance(testDataForAccountHolder.getMemberIdForSecondaryInsurance());
        invitePatientPage.setMemberDobInSecondaryInsurance(testDataForAccountHolder.getMemberDobForSecondaryInsurance());
        invitePatientPage.setRelationshipInSecondaryInsurance(testDataForAccountHolder.getRelationshipForSecondaryInsurance());

        //add patient
        invitePatientPage.clickAddPatientButton();
    }
    @Test(priority = 2)
    private void testValidatePatientAndInsuranceInChart() throws InterruptedException {
        Thread.sleep(2000);
        switchToTab(1);

        //Page navigate to Patient chart
        patientChart = new PatientChart(driver);

        //validating mandatory details
        softAssert.assertEquals((testDataForAccountHolder.getFullName()), patientChart.getNameInThePatientChart(),
                "Name didn't matched in the Patient chart");
        softAssert.assertEquals(testDataForAccountHolder.getEmail().toLowerCase(), patientChart.getEmailInThePatientChart().toLowerCase(),
                "Email didn't matched");
        softAssert.assertEquals((testDataForAccountHolder.getFullName()).toUpperCase(), patientChart.getNameInTopBar(),
                "Name in the Top Bar is mismatching");
        softAssert.assertEquals(testDataForAccountHolder.getMobileNumber(), patientChart.getMobileInPatientChart(),
                "Mobile Number didn't matched in the Patient chart");
        softAssert.assertEquals(testDataForAccountHolder.getZipCode(), patientChart.getZipcodeInPatientChart(),
                "Zip Code mismatch in Patient Chart.");

        //validating primary insurance
        softAssert.assertEquals(testDataForAccountHolder.getPrimaryInsurance().toLowerCase(), patientChart.getPrimaryInsurance().toLowerCase(),
                "Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberNameForPrimaryInsurance(), patientChart.getMemberNameInPrimaryInsurance(),
                "Member Name for Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberIdForPrimaryInsurance(), patientChart.getMemberIdInPrimaryInsurance(),
                "Member ID for Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getDobForMajor(), patientChart.getMemberDobInPrimaryInsurance(),
                "Member DOB for Primary Insurance mismatch in Patient Chart.");

        //validate secondary insurance
        softAssert.assertEquals(testDataForAccountHolder.getSecondaryInsurance().toLowerCase(), patientChart.getSecondaryInsurance().toLowerCase(),
                "Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberNameForSecondaryInsurance(), patientChart.getMemberNameInSecondaryInsurance(),
                "Member Name for Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberIdForSecondaryInsurance(), patientChart.getMemberIdInSecondaryInsurance(),
                "Member ID for Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberDobForSecondaryInsurance(), patientChart.getMemberDobInSecondaryInsurance(),
                "Member DOB for Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertAll();
    }
    @Test(priority = 3)
    private void testSetPasswordViaYopMail() throws InterruptedException {
        //Navigate to YopMail
        newTabAndLaunchYopMail();
        YopMail yopMail = new YopMail(driver);
        yopMail.clickSetPasswordMail(testDataForAccountHolder.getEmail());

        //Navigate to setPassword page
        switchToTab(3);
        setPasswordPage = new SetPasswordPage(driver);
        setPasswordPage.setPassword("Welcome@123");
    }
    @Test(priority = 4)
    public void testPatientPortalValidations(){
        //navigate to login page
        loginPagePatientPortal = new PatientPortalLoginPage(driver);
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");

        homePagePatPortal = new PatientPortalHomePage(driver);
        homePagePatPortal.selectDermatologyVisit();

        dermatologyVisitPage = new DermatologyVisitPage(driver);
        dermatologyVisitPage.clickSelectPatient();
        dermatologyVisitPage.clickSelectPatientAsMySelf();

        softAssert.assertEquals(testDataForAccountHolder.getPrimaryInsurance().toLowerCase(), dermatologyVisitPage.getPrimaryInsuranceName().toLowerCase(),
                "Primary Insurance name mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberNameForPrimaryInsurance(), dermatologyVisitPage.getMemberNameInPrimaryInsurance(),
                "Member Name for Primary Insurance mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberIdForPrimaryInsurance(), dermatologyVisitPage.getMemberIDInPrimaryInsurance(),
                "Member ID for Primary Insurance mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getDobForMajor(), dermatologyVisitPage.getMemberDobInPrimaryInsurance(),
                "Member DOB for Primary Insurance mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getRelationshipForPrimaryInsurance(), dermatologyVisitPage.getRelationshipInPrimaryInsurance(),
                "Relationship for Primary Insurance mismatch in Dermatology Visit page.");

        //secondary insurance validation
        softAssert.assertEquals(testDataForAccountHolder.getSecondaryInsurance().toLowerCase(), dermatologyVisitPage.getSecondaryInsuranceName().toLowerCase(),
                "Secondary Insurance name mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberNameForSecondaryInsurance(), dermatologyVisitPage.getMemberNameInSecondaryInsurance(),
                "Member Name for Secondary Insurance mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberIdForSecondaryInsurance(), dermatologyVisitPage.getMemberIDInSecondaryInsurance(),
                "Member ID for Secondary Insurance mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getMemberDobForSecondaryInsurance(), dermatologyVisitPage.getMemberDobInSecondaryInsurance(),
                "Member DOB for Secondary Insurance mismatch in Dermatology Visit page.");
        softAssert.assertEquals(testDataForAccountHolder.getRelationshipForSecondaryInsurance(), dermatologyVisitPage.getRelationshipInSecondaryInsurance(),
                "Relationship for Secondary Insurance mismatch in Dermatology Visit page.");
        softAssert.assertAll();

        //Navigate to Home page
        dermatologyVisitPage.clickBackArrowForVisitForm();
        dermatologyVisitPage.clickBackArrowForVisitForm();
        dermatologyVisitPage.clickBackArrowForHomePage();
//        dermatologyVisitPage.clickDeleteVisitButton();
//        dermatologyVisitPage.setConfirmForDeleteVisit();
    }

    @AfterClass
    private void patientAndProviderPortalLogout() throws InterruptedException {
        // Navigate to myProfile and logout
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
