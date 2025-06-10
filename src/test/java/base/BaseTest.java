package base;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        options.addArguments("--headless");
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
        Thread.sleep(2000);
        Set<String> ids = driver.getWindowHandles();
        ArrayList<String> idlist = new ArrayList<>(ids);
        driver.switchTo().window(idlist.get(tab));
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
    public void newTabandLaunchYopmail(){
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
