package tests.InvitePatients;

import Utils.TestData;
import base.BaseTest;
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

public class TC_IP013AddChildWithInsuranceDetails extends BaseTest {
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
    public TestData testDataForChild;
    public SoftAssert softAssert;
    @BeforeClass
    public void setUp() throws IOException {
        //Loading config File
        loadPropFile();
        driver.get(property.getProperty("ProviderPortalUrl"));

        //Test data for account holder and provider
        testDataForAccountHolder = new TestData();
        testDataForChild = new TestData();

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
    private void testAddChildAndInsuranceDetails() throws IOException, InterruptedException {
        //implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        invitePatientPage = new InvitePatientPage(driver);
        invitePatientPage.setFirstNameAs(testDataForAccountHolder.getFname());
        invitePatientPage.setLastNameAs(testDataForAccountHolder.getLname());
        invitePatientPage.setEmailAs(testDataForAccountHolder.getEmail());
        invitePatientPage.setMobileAs(testDataForAccountHolder.getMobileNumber());
        invitePatientPage.setZipcodeAs(testDataForAccountHolder.getZipCode());
        invitePatientPage.selectProviderNameAs(testDataForAccountHolder.getProviderName());

        // Add Child fields
        invitePatientPage.clickAddAdditionalPatientBtnForPatientOne();
        invitePatientPage.selectPatientTypeForPatientOne("Child");
        invitePatientPage.setFirstNameForPatientOne(testDataForChild.getFname());
        invitePatientPage.setLastNameForPatientOne(testDataForChild.getLname());
        invitePatientPage.setZipCodeForPatientOne(testDataForChild.getZipCode());

        invitePatientPage.checkInsuranceCheckboxForPatientOne();
        invitePatientPage.selectPrimaryInsuranceForPatientOne(testDataForChild.getPrimaryInsurance());
        invitePatientPage.setPrimaryInsuranceMemberName(testDataForChild.getFullName());
        invitePatientPage.setPrimaryInsuranceMemberIdForPatientOne(testDataForChild.getMemberIdForPrimaryInsurance());
        invitePatientPage.setPrimaryInsuranceMemberDOBForPatientOne(testDataForChild.getDobForMinor());
       // invitePatientPage.selectPrimaryInsuranceRelationshipForPatientOne(testDataForChild.getRelationshipForPrimaryInsurance());

        invitePatientPage.checkSecondaryInsuranceForPatientOne();
        invitePatientPage.selectSecondaryInsuranceForPatientOne(testDataForChild.getSecondaryInsurance());
        invitePatientPage.setSecondaryInsuranceMemberNameForPatientOne(testDataForChild.getMemberNameForSecondaryInsurance());
        invitePatientPage.setSecondaryInsuranceMemberIdForPatientOne(testDataForChild.getMemberIdForSecondaryInsurance());
        invitePatientPage.setSecondaryInsuranceMemberDOBForPatientOne(testDataForChild.getMemberDobForSecondaryInsurance());
       // invitePatientPage.selectSecondaryInsuranceRelationshipForPatientOne(testDataForChild.getRelationshipForSecondaryInsurance());

        //add patient
        invitePatientPage.clickAddPatientButton();
    }

    @Test(priority = 2)
    public void testVerifyInsuranceInPatientChart() throws IOException, InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        switchToTab(1);

        //Page navigate to Patient chart
        patientChart = new PatientChart(driver);

        //search for patient
        patientChart.searchPatient(testDataForChild.getFullName());

        //validating primary insurance
        softAssert.assertEquals(testDataForChild.getPrimaryInsurance().toLowerCase(), patientChart.getPrimaryInsurance().toLowerCase(),
                "Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForChild.getMemberNameForPrimaryInsurance(), patientChart.getMemberNameInPrimaryInsurance(),
                "Member Name for Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForChild.getMemberIdForPrimaryInsurance(), patientChart.getMemberIdInPrimaryInsurance(),
                "Member ID for Primary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForChild.getMemberDobForPrimaryInsurance(), patientChart.getMemberDobInPrimaryInsurance(),
                "Member DOB for Primary Insurance mismatch in Patient Chart.");

        //validating Secondary insurance
        softAssert.assertEquals(testDataForChild.getSecondaryInsurance().toLowerCase(), patientChart.getSecondaryInsurance().toLowerCase(),
                "Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForChild.getMemberNameForSecondaryInsurance(), patientChart.getMemberNameInSecondaryInsurance(),
                "Member Name for Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForChild.getMemberIdForSecondaryInsurance(), patientChart.getMemberIdInSecondaryInsurance(),
                "Member ID for Secondary Insurance mismatch in Patient Chart.");
        softAssert.assertEquals(testDataForChild.getMemberDobForSecondaryInsurance(), patientChart.getMemberDobInSecondaryInsurance(),
                "Member DOB for Secondary Insurance mismatch in Patient Chart.");

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
    public void testPatientPortalValidation() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        // Login to Patient Portal
        loginPagePatientPortal = new PatientPortalLoginPage(driver);
        loginPagePatientPortal.login(testDataForAccountHolder.getEmail(), "Welcome@123");

        homePagePatPortal = new PatientPortalHomePage(driver);
        homePagePatPortal.selectDermatologyVisit();
        dermatologyVisitPage = new DermatologyVisitPage(driver);
        dermatologyVisitPage.clickSelectPatient();
        Thread.sleep(1000);
        dermatologyVisitPage.selectPatientAsMyChild();
        Thread.sleep(1000);
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();
        Thread.sleep(1000);
        softAssert.assertEquals(testDataForChild.getFullName(), dermatologyVisitPage.getNameOfTheChildInSelectChild());
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();

        //Validation of Primary insurance
        softAssert.assertEquals(testDataForChild.getPrimaryInsurance(), dermatologyVisitPage.getPrimaryInsuranceName(),"Primary insurance name is mismatched");
        softAssert.assertEquals(testDataForChild.getFullName(), dermatologyVisitPage.getMemberNameInPrimaryInsurance(),"Member name is mismatched In Primary insurance");
        softAssert.assertEquals(testDataForChild.getMemberIdForPrimaryInsurance(), dermatologyVisitPage.getMemberIDInPrimaryInsurance(),"Member ID is mismatched In Primary insurance");
        softAssert.assertEquals(testDataForChild.getMemberDobForPrimaryInsurance(), dermatologyVisitPage.getMemberDobInPrimaryInsurance(),"Member DOB is mismatched In Primary insurance");
        softAssert.assertEquals(testDataForChild.getRelationshipForPrimaryInsurance(), dermatologyVisitPage.getRelationshipInPrimaryInsurance(),"Relationship to patient is mismatched In Primary insurance");

        //Validation of Secondary insurance
        softAssert.assertEquals(testDataForChild.getSecondaryInsurance(), dermatologyVisitPage.getSecondaryInsuranceName(),"Secondary insurance name is mismatched");
        softAssert.assertEquals(testDataForChild.getMemberNameForSecondaryInsurance(), dermatologyVisitPage.getMemberNameInSecondaryInsurance(),"Member name is mismatched In Secondary insurance");
        softAssert.assertEquals(testDataForChild.getMemberIdForSecondaryInsurance(), dermatologyVisitPage.getMemberIDInSecondaryInsurance(),"Member ID is mismatched In Secondary insurance");
        softAssert.assertEquals(testDataForChild.getMemberDobForSecondaryInsurance(), dermatologyVisitPage.getMemberDobInSecondaryInsurance(),"Member DOB is mismatched In Secondary insurance");
        softAssert.assertEquals(testDataForChild.getRelationshipForSecondaryInsurance(), dermatologyVisitPage.getRelationshipInSecondaryInsurance(),"Relationship to patient is mismatched In Secondary insurance");

        softAssert.assertAll();
    }
}
