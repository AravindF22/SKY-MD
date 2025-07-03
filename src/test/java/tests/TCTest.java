package tests;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.PatientPortal.DermatologyVisitPage;
import pages.PatientPortal.PatientPortalHomePage;
import pages.PatientPortal.PatientPortalLoginPage;
import pages.PatientPortal.SignInPage;
import utils.ConfigReader;
import utils.ExtentReportManager;
import utils.TestData;

import java.io.IOException;
import java.time.Duration;

public class TCTest extends BaseTest {
 public PatientPortalLoginPage patientPortalLoginPage;
 public PatientPortalHomePage patientPortalHomePage;
 public DermatologyVisitPage dermatologyVisitPage;
 public TestData testDataForAccountHolder;
 public TestData testDataForChild;
 public SignInPage signInPage;
 public SoftAssert softAssert;
 public WebDriverWait wait;

   @BeforeClass
   public void setUp() throws IOException {
    driver.get(ConfigReader.getProperty("PatientPortalLoginUrl"));
    wait = new WebDriverWait(driver, Duration.ofSeconds(15));
   }
   @BeforeMethod
   public void initializeAsset(){
    softAssert = new SoftAssert();
    signInPage = new SignInPage(driver);
    patientPortalLoginPage = new PatientPortalLoginPage(driver);
    patientPortalHomePage = new PatientPortalHomePage(driver);
    dermatologyVisitPage = new DermatologyVisitPage(driver);
    testDataForAccountHolder = new TestData();
    testDataForChild = new TestData();
   }
   @Test(priority = 1)
   public void testLoginWithValidCredentials()
   {
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    ExtentReportManager.getTest().log(Status.INFO, "Starting login with valid patient credentials");

    patientPortalLoginPage.setEmail(ConfigReader.getProperty("ExistingPatientEmail"));
    ExtentReportManager.getTest().log(Status.INFO, "Entered valid email");

    patientPortalLoginPage.setPassword(ConfigReader.getProperty("PatientPortalPassword"));
    ExtentReportManager.getTest().log(Status.INFO, "Entered valid password");

    patientPortalLoginPage.clickLoginButton();
    ExtentReportManager.getTest().log(Status.INFO, "Clicked on Login button");

    patientPortalHomePage = new PatientPortalHomePage(driver);
    boolean isHome = patientPortalHomePage.isHomePage();

    softAssert.assertTrue(isHome, "Home page did not load properly after login.");

    if (isHome) {
     ExtentReportManager.getTest().log(Status.INFO, "Login successful, home page loaded.");
    } else {
     ExtentReportManager.getTest().log(Status.INFO, "Login failed or home page did not load.");
    }
    softAssert.assertAll();
   }
   @Test(priority = 2)
   public void test_BasicDetails() throws InterruptedException {
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    ExtentReportManager.getTest().log(Status.INFO, "Test started: Basic Details");
    patientPortalHomePage.selectDermatologyVisit();
    dermatologyVisitPage.selectDermProviderSection();
    dermatologyVisitPage.clickContinueAfterSelectingDermProvider();
    Thread.sleep(2000);
    dermatologyVisitPage.proceedBasisOfProvider("Dr. Janice Fahey");
    Thread.sleep(1000);
    dermatologyVisitPage.clickSelectPatient();
    //child
    dermatologyVisitPage.selectPatientAsMyChild();
    dermatologyVisitPage.clickContinueButtonAfterSelectPatient();
    dermatologyVisitPage.clickAddChildButton();
    dermatologyVisitPage.enterChildFirstName(testDataForChild.getFname());
    dermatologyVisitPage.enterChildLastName(testDataForChild.getLname());
    dermatologyVisitPage.clickSaveChildButton();
    softAssert.assertEquals(dermatologyVisitPage.getFirstChildName(), testDataForChild.getFullName(),
            "First child name mismatch in dermatology visit");
    dermatologyVisitPage.clickContinueButtonAfterInsurance();
    dermatologyVisitPage.clickContinueButtonAfterSelectPatient();
    dermatologyVisitPage.clickContinueButtonAfterInsurance();
    ExtentReportManager.getTest().log(Status.INFO, "Basic details completed");
    softAssert.assertAll();
   }

   @Test(priority = 3, dependsOnMethods = "test_BasicDetails")
   public void test_visitDetails() {
    ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Details");
    dermatologyVisitPage.setAddressLineOne(testDataForChild.getStreetAddressOne());
    dermatologyVisitPage.setAddressLineTwo(testDataForChild.getStreetAddressTwo());
    dermatologyVisitPage.clickContinueButton();
    dermatologyVisitPage.setDOB(testDataForChild.getDobForMinor());
    dermatologyVisitPage.setFeet(testDataForChild.getFeet());
    dermatologyVisitPage.setInches(testDataForChild.getInch());
    dermatologyVisitPage.setWeight(testDataForChild.getWeight());
    dermatologyVisitPage.setGender("Male");
    dermatologyVisitPage.clickContinueButton();
    dermatologyVisitPage.uploadId(ConfigReader.getProperty("idPhotoPath"));
    dermatologyVisitPage.clickContinueButton();
    ExtentReportManager.getTest().log(Status.INFO, "Visit details completed");
   }

   @Test(priority = 4, dependsOnMethods = "test_visitDetails")
   public void test_visitPhotos() throws InterruptedException {
    ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Photos");

    dermatologyVisitPage.setDermatologyConcern(testDataForChild.getConcern());
    dermatologyVisitPage.clickContinueButton();
    dermatologyVisitPage.selectAffectedBodyPart(testDataForChild.getBodyParts());
    dermatologyVisitPage.clickContinueButton();
    dermatologyVisitPage.selectStatus(testDataForChild.getStatus());
    dermatologyVisitPage.selectCount(testDataForChild.getSufferingConditionDays());
    dermatologyVisitPage.selectDay(testDataForChild.getSelectDays());
    dermatologyVisitPage.selectSeverity(testDataForChild.getSeverity());
    dermatologyVisitPage.clickContinueButton();
    dermatologyVisitPage.clickNoBtnForSmartPhoneUpload();
    Thread.sleep(1000);
    dermatologyVisitPage.uploadCloseUpPic(ConfigReader.getProperty("conditionPhotoOnePath"));
    Thread.sleep(1000);
    dermatologyVisitPage.uploadFarAwayPic(ConfigReader.getProperty("conditionPhotoTwoPath"));
    dermatologyVisitPage.clickContinueButton();
    ExtentReportManager.getTest().log(Status.INFO, "Visit photos completed");
   }

   @Test(priority = 5, dependsOnMethods = "test_visitPhotos")
   public void test_visitSymptoms() throws InterruptedException {
    ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Symptoms");
    Thread.sleep(1000);
    dermatologyVisitPage.selectSymptoms(testDataForChild.getSymptomOne());
    dermatologyVisitPage.clickContinueButton();
    dermatologyVisitPage.setWorseText("test worse");
    dermatologyVisitPage.setBetterText("test better");
    dermatologyVisitPage.clickContinueButton();
    dermatologyVisitPage.selectLiftStyle("Stress");
    dermatologyVisitPage.clickContinueButton();
    ExtentReportManager.getTest().log(Status.INFO, "Visit symptoms completed");
   }

   @Test(priority = 6, dependsOnMethods = "test_visitSymptoms")
   public void test_visitMedicalHistory() throws InterruptedException {
    ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Medical History");
    dermatologyVisitPage.addAllergy(
            testDataForChild.getAllergyOne(),
            testDataForChild.getAllergyReactionOne(),
            testDataForChild.getDrugAllergyCategory());
    dermatologyVisitPage.clickContinueButton();
    Thread.sleep(3000);
    dermatologyVisitPage.clickYesButtonInMedication();
    Thread.sleep(1000);
    dermatologyVisitPage.clickSearchMedicationButton();
    //Thread.sleep(2000);
    dermatologyVisitPage.addMedication(
            testDataForChild.getMedicationOne(),
            "test",
            "Oil",
            "PRN",
            "PRN");
    dermatologyVisitPage.clickContinueButton();
    dermatologyVisitPage.clickYesBtnInSkinCareProduct();
    dermatologyVisitPage.enterSkinCareProduct("test");
    dermatologyVisitPage.clickContinueButton();
    ExtentReportManager.getTest().log(Status.INFO, "Visit medical history completed");
   }

   @Test(priority = 7, dependsOnMethods = "test_visitMedicalHistory")
   public void test_visitPayment() {
    ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Payment");
    dermatologyVisitPage.clickAddPharmacyAndSwitchToListView();
    dermatologyVisitPage.clickFirstPharmacy();
    dermatologyVisitPage.clickContinueButton();
    dermatologyVisitPage.setOptionalField("test optional...");
    dermatologyVisitPage.clickContinueButton();
    dermatologyVisitPage.clickNextButtonForTAndC();
    dermatologyVisitPage.clickAllAcceptTerms();
    dermatologyVisitPage.paymentByCard(
            ConfigReader.getProperty("testCardNumber"),
            ConfigReader.getProperty("testCardExpiryDate"),
            ConfigReader.getProperty( "testCardCVV"));
    dermatologyVisitPage.clickSubmitForEvaluation();
    ExtentReportManager.getTest().log(Status.INFO, "Visit payment completed");
    softAssert.assertAll();
   }

   @Test(priority = 8, dependsOnMethods = "test_visitPayment")
   public void test_VisitValidationInPatientPortal() {
    ExtentReportManager.getTest().log(Status.INFO, "Test started: Visit Validation in Patient Portal");
    softAssert.assertTrue(dermatologyVisitPage.isVisitSubmitted(), "Visit submission verification failed");
    softAssert.assertEquals(
            testDataForChild.getProviderName(),
            dermatologyVisitPage.getProviderNameInVisitSubmittedPage(),
            "Provider name mismatch in submitted visit");
    dermatologyVisitPage.clickGoToMyVisitsButton();
    ExtentReportManager.getTest().log(Status.INFO, "Visit validation completed");
    softAssert.assertAll();
   }
}
