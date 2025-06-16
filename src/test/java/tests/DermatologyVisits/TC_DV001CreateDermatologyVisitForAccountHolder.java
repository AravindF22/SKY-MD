package tests.DermatologyVisits;

import Utils.TestData;
import base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.PatientPortal.DermatologyVisitPage;
import pages.PatientPortal.PatientPortalHomePage;
import pages.PatientPortal.PatientPortalLoginPage;

import java.io.IOException;
import java.time.Duration;

public class TC_DV001CreateDermatologyVisitForAccountHolder extends BaseTest {
    public PatientPortalLoginPage patientPortalLoginPage;
    public PatientPortalHomePage patientPortalHomePage;
    public DermatologyVisitPage dermatologyVisitPage;
    public TestData testDataForAccountHolder;

    @BeforeClass
    public void setUp() throws IOException {
        loadPropFile();
        driver.get(property.getProperty("PatientPortalLoginUrl"));
        testDataForAccountHolder = new TestData();
        patientPortalLoginPage = new PatientPortalLoginPage(driver);
        patientPortalLoginPage.login("elbertwuckert01@yopmail.com", "Welcome@123");
    }
    @Test
    public void createDermatologyVisitForAccountHolder() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        patientPortalHomePage = new PatientPortalHomePage(driver);
        patientPortalHomePage.selectDermatologyVisit();

        dermatologyVisitPage = new DermatologyVisitPage(driver);
        dermatologyVisitPage.clickSelectPatient();

        dermatologyVisitPage.clickSelectPatientAsMySelf();
        dermatologyVisitPage.clickContinueButtonAfterSelectPatient();

        dermatologyVisitPage.clickContinueButtonAfterInsurance();

        dermatologyVisitPage.setAddressLineOne(testDataForAccountHolder.getStreetAddressOne());
        dermatologyVisitPage.setAddressLineTwo(testDataForAccountHolder.getStreetAddressTwo());
        dermatologyVisitPage.clickContinueButton();

        dermatologyVisitPage.setDOB(testDataForAccountHolder.getDobForMajor());
        dermatologyVisitPage.setFeet(testDataForAccountHolder.getFeet());
        dermatologyVisitPage.setInches(testDataForAccountHolder.getInch());
        dermatologyVisitPage.setWeight(testDataForAccountHolder.getWeight());
        dermatologyVisitPage.setGender("Male");
        dermatologyVisitPage.clickContinueButton();

        dermatologyVisitPage.uploadId("C:\\Users\\aravi\\OneDrive\\Pictures\\Ids\\jpg id.jpg");
        dermatologyVisitPage.clickContinueButton();

        dermatologyVisitPage.setDermatologyConcern("Acne");
        dermatologyVisitPage.clickContinueButton();

        dermatologyVisitPage.selectAffectedBodyPart("Abdomen");
        dermatologyVisitPage.clickContinueButton();

        dermatologyVisitPage.selectStatus("Improving");
        dermatologyVisitPage.selectCount("3");
        dermatologyVisitPage.selectDay("Day(s)");
        dermatologyVisitPage.selectSeverity("Mild");
        dermatologyVisitPage.clickContinueButton();

        dermatologyVisitPage.clickNoBtnForSmartPhoneUpload();
        dermatologyVisitPage.uploadCloseUpPic("C:\\Users\\aravi\\OneDrive\\Pictures\\Condition Photos\\cond1.jpg");
        dermatologyVisitPage.uploadFarAwayPic("C:\\Users\\aravi\\OneDrive\\Pictures\\Condition Photos\\cond1.jpg");
        dermatologyVisitPage.clickContinueButton();

        dermatologyVisitPage.selectSymptoms("Arthritis");
        dermatologyVisitPage.clickContinueButton();

        dermatologyVisitPage.setWorseText("test worse");
        dermatologyVisitPage.setBetterText("test better");
        dermatologyVisitPage.clickContinueButton();
        Thread.sleep(1000);
        dermatologyVisitPage.selectLiftStyle("Stress");
        dermatologyVisitPage.clickContinueButton();

        dermatologyVisitPage.addAllergy("Cats", "Anaphylaxis");
        dermatologyVisitPage.clickContinueButton();
        Thread.sleep(2000);

        dermatologyVisitPage.addMedication("testim transdermal Gel","test",
                "Oil", "PRN", "PRN");
        dermatologyVisitPage.clickContinueButton();
        Thread.sleep(1000);
        dermatologyVisitPage.clickYesBtnInSkinCareProduct();
        dermatologyVisitPage.enterSkinCareProduct("test");
        dermatologyVisitPage.clickContinueButton();

        dermatologyVisitPage.clickAddPharmacyAndSwitchToListView();
        dermatologyVisitPage.clickFirstPharmacy();
        dermatologyVisitPage.clickContinueButton();

        dermatologyVisitPage.setOptionalField("test optional...");
        dermatologyVisitPage.clickContinueButton();

        dermatologyVisitPage.clickNextButtonForTAndC();
        dermatologyVisitPage.clickAllAcceptTerms();

        dermatologyVisitPage.paymentByCard("4111111111111111", "09/27","111");
        dermatologyVisitPage.clickSubmitForEvaluation();
    }
}
