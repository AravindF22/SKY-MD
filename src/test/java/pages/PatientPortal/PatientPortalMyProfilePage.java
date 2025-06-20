package pages.PatientPortal;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;
import java.util.List;

public class PatientPortalMyProfilePage extends BasePage {
    private WebDriverWait wait;
    public PatientPortalMyProfilePage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    private final By home = By.xpath("//ul[@class=\"py-10 space-y-5 \"]//li[1]/a");
    private final By nameOfAccountHolder = By.xpath("//h3[contains(@class,'text-4xl')]");
    private final By emailOfAccountHolder = By.xpath("//h3[text()='Email']/following-sibling::p");
    private final By mobileOfAccountHolder = By.xpath("//h3[text()='Mobile']/following-sibling::p");
    private final By gender = By.xpath("//h3[text()='Gender at Birth']/following-sibling::p");
    private final By dob = By.xpath("//h3[text()='DOB']/following-sibling::p");
    private final By addressLineOne = By.xpath("//h3[text()='Address']/following-sibling::p[1]");
    private final By addressLineTwo = By.xpath("//h3[text()='Address']/following-sibling::p[2]");
    private final By ZipcodeInAddressLineOne = By.xpath("//h3[text()='Address']/following-sibling::p[1]");
    private final By SettingsLink = By.xpath("//div[@class = 'flex justify-end flex-none']");
    private final By logoutButton = By.xpath("//p[@class='text-3xl font-medium pl-9 text-red']");
    private final By confirmLogoutButton = By.xpath("//button[text()='Confirm']");
    private final By dependent = By.xpath("//h3[contains(text(),'Dependents')]");
    private final By dependentOneName =By.xpath("(//div[@class=\"px-10 pb-8\"]//h3[text()='Name']/following-sibling::p)[1]");
    private final By dependentOneType = By.xpath("(//div[@class=\"px-10 pb-8\"]//h3[text()='Type']/following-sibling::p)[1]");
    private final By dependentOneHeight = By.xpath("(//div[@class=\"px-10 pb-8\"]//h3[text()='Height']/following-sibling::p)[1]");
    private final By dependentOneGender = By.xpath("(//div[@class=\"px-10 pb-8\"]//h3[text()='Gender at Birth']/following-sibling::p)[1]");
    private final By dependentOneWeight = By.xpath("(//div[@class=\"px-10 pb-8\"]//h3[text()='Weight']/following-sibling::p)[1]");
    private final By healthProfileLink = By.xpath("//h3[text()='Health Profile']");
    private final By healthProfileOfAccountHolder = By.xpath("//div[@class=\"mt-16\"][1]/div");
    private final By allergyHealthProfile = By.xpath("//p[text()='Allergies']");

    private final By allergyOne = By.xpath("//div[contains(@class,\"block pt-7\")][1]//input");
    private final By allergyTwo = By.xpath("//div[contains(@class,\"block pt-7\")][2]//input");
    private final By allergyThree = By.xpath("//div[contains(@class,\"block pt-7\")][3]//input");

    private final By reactionOne = By.xpath("//div[contains(@class,\"block pt-7\")][1]//select");
    private final By reactionTwo = By.xpath("//div[contains(@class,\"block pt-7\")][2]//select");
    private final By reactionThree = By.xpath("//div[contains(@class,\"block pt-7\")][3]//select");

    private final By medicationHealthProfile = By.xpath("//p[text()='Medications']");
    private final By medicationOne = By.xpath("//div[@class=\"pt-7 pb-9 px-9 bg-alternativeWhite rounded-2xl mt-10\"][1]/div[1]/input");
    private final By backButtonInHealthProfile = By.xpath("//img[@alt=\"back\"]");
    public void clickHomePageLink() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(home)).click();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Home element not found.");
        } catch (Exception e) {
            System.err.println("Error retrieving Home text: " + e.getMessage());
        }
    }
    public String getNameOfAccountHolder() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(nameOfAccountHolder));
            return element.getText().trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Name of Account Holder element not found.");
        } catch (Exception e) {
            System.err.println("Error retrieving Name of Account Holder: " + e.getMessage());
        }
        return null;
    }

    public String getEmailOfAccountHolder() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailOfAccountHolder));
            return element.getText().trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Email of Account Holder element not found.");
        } catch (Exception e) {
            System.err.println("Error retrieving Email of Account Holder: " + e.getMessage());
        }
        return null;
    }

    public String getMobileOfAccountHolder() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileOfAccountHolder));
            return element.getText().replaceAll("[^0-9]", "").trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Mobile of Account Holder element not found.");
        } catch (Exception e) {
            System.err.println("Error retrieving Mobile of Account Holder: " + e.getMessage());
        }
        return null;
    }
    public String getZipCodeOfAccountHolderInLineOne(){
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(addressLineOne));
            String [] address =  element.getText().trim().split("\\s");
            return address[address.length-1];
        } catch (TimeoutException e) {
            System.err.println("Timeout: City and Zip Code element not found.");
        } catch (Exception e) {
            System.err.println("Error retrieving Zip Code of Account Holder: " + e.getMessage());
        }
        return null;
    }
    public String getZipCodeOfAccountHolder() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(addressLineTwo));
            String [] address =  element.getText().trim().split(" ");
            String zipcode = address[address.length-1];
            if(zipcode==null){
                WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(addressLineOne));
                String [] add =  element.getText().trim().split(" ");
                return add[add.length-1];
            }
            return address[address.length-1];
        } catch (TimeoutException e) {
            System.err.println("Timeout: City and Zip Code element not found.");
        } catch (Exception e) {
            System.err.println("Error retrieving Zip Code of Account Holder: " + e.getMessage());
        }
        return null;
    }
    public String getGender() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(gender));
            return element.getText().trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Gender element not found.");
        } catch (Exception e) {
            System.err.println("Error retrieving Gender text: " + e.getMessage());
        }
        return null;
    }

    public String getDOB() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(dob));
            return element.getText().trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: DOB element not found.");
        } catch (Exception e) {
            System.err.println("Error retrieving DOB text: " + e.getMessage());
        }
        return null;
    }

    public String getAddressLineOne() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(addressLineOne));
            return element.getText().replace(",","").trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Address Line One element not found.");
        } catch (Exception e) {
            System.err.println("Error retrieving Address Line One text: " + e.getMessage());
        }
        return null;
    }

    public String getAddressLineTwo() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(addressLineTwo));
            return element.getText().trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Address Line Two element not found.");
        } catch (Exception e) {
            System.err.println("Error retrieving Address Line Two text: " + e.getMessage());
        }
        return null;
    }

    // Click on the "Dependents" header
    public void clickDependents() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(dependent));
            element.click();
        } catch (TimeoutException e) {
            System.err.println("Timeout: 'Dependents' header not clickable.");
        } catch (Exception e) {
            System.err.println("Error clicking 'Dependents' header: " + e.getMessage());
        }
    }

    // Get the name of the first dependent
    public String getDependentOneName() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(dependentOneName));
            return element.getText().trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Dependent one name not visible.");
        } catch (Exception e) {
            System.err.println("Error getting dependent one name: " + e.getMessage());
        }
        return null;
    }

    // Get the type of the first dependent
    public String getDependentOneType() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(dependentOneType));
            return element.getText().trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Dependent one type not visible.");
        } catch (Exception e) {
            System.err.println("Error getting dependent one type: " + e.getMessage());
        }
        return null;
    }

    // Get the gender of the first dependent
    public String getDependentOneGender() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(dependentOneGender));
            return element.getText().trim();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Dependent one gender not visible.");
        } catch (Exception e) {
            System.err.println("Error getting dependent one gender: " + e.getMessage());
        }
        return null;
    }
    public String getDependentOneHeight() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(dependentOneHeight));
            String heightValue = element.getText();
            if (heightValue != null) {
                return heightValue.replace("ft", "").trim();
            }
        } catch (Exception e) {
            System.out.println("Error retrieving dependent one's height: " + e.getMessage());
        }
        return null;
    }

    public String getDependentOneWeight() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(dependentOneWeight));
            String weightValue = element.getText();
            if (weightValue != null) {
                return weightValue.replace(".0 lbs", "").trim();
            }
        } catch (Exception e) {
            System.out.println("Error retrieving dependent one's weight: " + e.getMessage());
        }
        return null;
    }

    public void clickHealthProfileLink() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(healthProfileLink)).click();
        } catch (Exception e) {
            System.out.println("Error getting Health Profile Link text: " + e.getMessage());
        }
    }
    public void clickHealthProfileOfAccountHolder() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(healthProfileOfAccountHolder)).click();
        } catch (Exception e) {
            System.out.println("Error getting Health Profile of Account Holder text: " + e.getMessage());
        }
    }
    public void clickAllergyHealthProfile() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(allergyHealthProfile)).click();
        } catch (Exception e) {
            System.out.println("Error getting Allergy Health Profile text: " + e.getMessage());
        }
    }
    public String getAllergyOneValue() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(allergyOne)).getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting Allergy One value: " + e.getMessage());
            return null;
        }
    }
    public String getAllergyTwoValue() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(allergyTwo)).getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting Allergy One value: " + e.getMessage());
            return null;
        }
    }
    public String getAllergyThreeValue() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(allergyThree)).getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting Allergy One value: " + e.getMessage());
            return null;
        }
    }
    public String getReactionOne() {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(reactionOne));
            Select select = new Select(dropdown);
            return select.getFirstSelectedOption().getText();
        } catch (Exception e) {
            System.out.println("Error getting selected option in Reaction One: " + e.getMessage());
            return null;
        }
    }

    public String getReactionTwo() {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(reactionTwo));
            Select select = new Select(dropdown);
            return select.getFirstSelectedOption().getText();
        } catch (Exception e) {
            System.out.println("Error getting selected option in Reaction Two: " + e.getMessage());
            return null;
        }
    }

    public String getReactionThree() {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(reactionThree));
            Select select = new Select(dropdown);
            return select.getFirstSelectedOption().getText();
        } catch (Exception e) {
            System.out.println("Error getting selected option in Reaction Three: " + e.getMessage());
            return null;
        }
    }
    public String getDrugAllergyOneValue() {
        try {
            List<WebElement> element = driver.findElements(By.xpath("//input[contains(@id,'drug_medication_allergies')]"));
            return element.get(element.size()-1).getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting value for drugAllergyOne: " + e.getMessage());
            return null;
        }
    }

    public String getDrugAllergyTwoValue() {
        try {
            List<WebElement> element = driver.findElements(By.xpath("//input[contains(@id,'drug_medication_allergies')]"));
            return element.get(element.size()-2).getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting value for drugAllergyTwo: " + e.getMessage());
            return null;
        }
    }
    public String getDrugReactionOneValue(){
        try {
            List<WebElement> element = driver.findElements(By.xpath(" //select[contains(@name,'drug_medication_allergies')]"));
            return element.get(element.size()-1).getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting value for drugReactionOne: " + e.getMessage());
            return null;
        }
    }
    public String getDrugReactionTwoValue(){
        try {
            List<WebElement> element = driver.findElements(By.xpath(" //select[contains(@name,'drug_medication_allergies')]"));
            return element.get(element.size()-2).getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting value for drugReactionOne: " + e.getMessage());
            return null;
        }
    }

    public String getEnvironmentAllergyOneValue() {
        try {
            List<WebElement> element = driver.findElements(By.xpath("//input[contains(@id,'environmental_food_allergies')]"));
            return element.get(element.size()-1).getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting value for environmentAllergyOne: " + e.getMessage());
            return null;
        }
    }

    public String getEnvironmentAllergyTwoValue() {
        try {
            List<WebElement> element = driver.findElements(By.xpath("//input[contains(@id,'environmental_food_allergies')]"));
            return element.get(element.size()-2).getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting value for environmentAllergyTwo: " + e.getMessage());
            return null;
        }
    }
    public String getEnvironmentReactionOneValue(){
        try {
            List<WebElement> element = driver.findElements(By.xpath("//select[contains(@id,'environmental_food_allergies')]"));
            return element.get(element.size()-1).getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting value for environmentAllergyTwo: " + e.getMessage());
            return null;
        }
    }

    public String getEnvironmentReactionTwoValue(){
        try {
            List<WebElement> element = driver.findElements(By.xpath("//select[contains(@id,'environmental_food_allergies')]"));
            return element.get(element.size()-2).getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting value for environmentAllergyTwo: " + e.getMessage());
            return null;
        }
    }
    public void clickBackButtonInHealthProfile(){
        try {
            wait.until(ExpectedConditions.elementToBeClickable(backButtonInHealthProfile)).click();
        }
        catch (Exception e){
            System.out.println("Error while clicking Back button to health profile: " + e.getMessage());
        }
    }
    public void clickMedicationHealthProfile() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(medicationHealthProfile)).click();
        } catch (Exception e) {
            System.out.println("Error getting Medication Health Profile text: " + e.getMessage());
        }
    }

    public String getMedicationOneValue() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(medicationOne)).getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting Medication One value: " + e.getMessage());
            return null;
        }
    }
    public void clickSettingsLink() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(SettingsLink));
            element.click();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Settings link not clickable.");
        } catch (Exception e) {
            System.err.println("Error clicking Settings link: " + e.getMessage());
        }
    }

    public void clickLogoutButton() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
            element.click();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Logout button not clickable.");
        } catch (Exception e) {
            System.err.println("Error clicking Logout button: " + e.getMessage());
        }
    }

    public void clickConfirmLogoutButton() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(confirmLogoutButton));
            element.click();
        } catch (TimeoutException e) {
            System.err.println("Timeout: Confirm Logout button not clickable.");
        } catch (Exception e) {
            System.err.println("Error clicking Confirm Logout button: " + e.getMessage());
        }
    }
}
