package base;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;


public class BaseTest {

    public WebDriver driver;
    public Properties property;

    @BeforeClass()
    @Parameters("browser")
    public void setup(String browser) throws IOException {

        //Allow , block notification
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 1); // 1 = Allow, 2 block
        ChromeOptions options = new ChromeOptions();
       // options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.setExperimentalOption("prefs", prefs);
        switch(browser) {
            case "chrome" : driver= new ChromeDriver(options); break;
            case "edge"   : driver= new EdgeDriver(); break;
            case "firefox": driver = new FirefoxDriver();break;
            default : System.out.println("Invalid browser"); return;
        }
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }
    @AfterClass()
    public void tearDown() {
      // driver.quit();
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
    public String captureScreenshot(String screenshotName) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedDate = formatter.format(date);

        TakesScreenshot ts = (TakesScreenshot)driver;
        File sourceFile = ts.getScreenshotAs(OutputType.FILE);
        String targetFileLocation = ".\\screenshots\\"+screenshotName+"_"+formattedDate+".png";
        File targetFile = new File(targetFileLocation);
        sourceFile.renameTo(targetFile);
        return targetFileLocation;
    }
}
