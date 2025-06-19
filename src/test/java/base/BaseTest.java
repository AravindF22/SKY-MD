package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;


public class BaseTest {
    // there is only one driver instance
    protected static WebDriver driver;
    public Properties property;

    @BeforeClass()
    @Parameters("browser")
    public void setup(String browser) throws IOException {
        browser = browser.toLowerCase();
        loadPropFile();
        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();

                // Notification preferences
                Map<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("profile.default_content_setting_values.notifications", 1);
                chromeOptions.setExperimentalOption("prefs", chromePrefs);

                // Headless mode setup
               if(property.getProperty("headless").equals("true")){
                   chromeOptions.addArguments("--headless=new"); // For Chrome 109+
                   chromeOptions.addArguments("window-size=1920,1080");
               }
                driver = new ChromeDriver(chromeOptions);
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if(property.getProperty("headless").equals("true")) {
                    edgeOptions.addArguments("--headless=new");
                    edgeOptions.addArguments("window-size=1920,1080");
                }
                driver = new EdgeDriver(edgeOptions);
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if(property.getProperty("headless").equals("true")) {
                    firefoxOptions.addArguments("--headless");
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.manage().deleteAllCookies();
        if (property.getProperty("headless").equals("false")) {
            driver.manage().window().maximize();
        }
    }
    @AfterClass()
    public void tearDown() {
       driver.quit();
    }
    //for loading config file
    public void loadPropFile() throws IOException {
        FileInputStream file = new FileInputStream("./src//test//resources//config.properties");
        property = new Properties();
        property.load(file);
    }
    public void switchToTab( int tab) throws InterruptedException {
        try {
            // Wait until the desired number of tabs are open
            new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(driver -> driver.getWindowHandles().size() > tab);

            Set<String> ids = driver.getWindowHandles();
            ArrayList<String> idList = new ArrayList<>(ids);

            if (tab < idList.size()) {
                driver.switchTo().window(idList.get(tab));
            } else {
                throw new IllegalStateException("Requested tab index " + tab +
                        " but only " + idList.size() + " tabs are open.");
            }

        } catch (Exception e) {
            System.err.println("Error while switching to tab " + tab + ": " + e.getMessage());
        }
    }
    public void switchToTab(String title) throws InterruptedException {
        Thread.sleep(2000);
        Set<String> ids = driver.getWindowHandles();
        for(String id : ids ){
            String pageTitle  = driver.switchTo().window(id).getTitle();
            if(driver.getTitle().equals(pageTitle))
                break;
        }
    }
    public void newTab(){
        driver.switchTo().newWindow(WindowType.TAB);
    }
    public void newTabAndLaunchYopMail(){
        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to("https://yopmail.com/en/");
    }
    public static String captureScreenshot(String screenshotName) {
        try{
            if (driver == null) {
                System.out.println("Driver is null. Cannot take screenshot.");
                return null;
            }
            String timestamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
            TakesScreenshot ts = (TakesScreenshot) driver;
            File sourceFile = ts.getScreenshotAs(OutputType.FILE);
            String targetFileLocation = "screenshots/" + screenshotName + "_" + timestamp + ".png";
            File targetFile = new File(targetFileLocation);
            sourceFile.renameTo(targetFile);

            String relativePath = "../screenshots/" + screenshotName + "_" + timestamp + ".png";
            return relativePath;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
