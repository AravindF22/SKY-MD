package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.PatientPortal.DermatologyVisitPage;
import pages.PatientPortal.PatientPortalLoginPage;

import java.time.Duration;

public class TCTest extends BaseTest {
    public PatientPortalLoginPage loginPagePatientPortal;
    public DermatologyVisitPage dermatologyVisitPage;

    @Test
   public void test() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.navigate().to("https://patient.skymdstaging.com/login");
        // Login to Patient Portal
        loginPagePatientPortal = new PatientPortalLoginPage(driver);
        loginPagePatientPortal.login("saulkovacek01@yopmail.com", "Welcome@123");
        driver.findElement(By.xpath("//button[text()='Proceed with Consultation']")).click();
        //driver.findElement(By.xpath("//h3[text()='Patient Bio']/parent::div")).click();
        driver.findElement(By.xpath("//h3[text()='Medications']/parent::div")).click();

        dermatologyVisitPage = new DermatologyVisitPage(driver);
        dermatologyVisitPage.addMedication("zerigo Health Kit","test",
                "Oil", "PRN", "Hour");
        dermatologyVisitPage.clickContinueButton();
        Thread.sleep(2500);

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
