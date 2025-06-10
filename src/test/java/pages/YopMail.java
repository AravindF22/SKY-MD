package pages;

import base.BasePage;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class YopMail extends BasePage {
    public YopMail(WebDriver driver){
        super(driver);
    }

    private By emailTextField =  By.xpath("//input[@class=\"ycptinput\"]");
    private By enterEmailArrow = By.xpath("//div[@id=\"refreshbut\"]/button");
    private By setPasswordMail = By.xpath("//div[@class='m']//div[@class='lms' and text()='Set Your SkyMD Password']/parent::button");
    private By setPasswordLink = By.xpath("//a[text()='SET PASSWORD']");
    public void clickSetPasswordMail(String email){
        driver.findElement(emailTextField).sendKeys(email);
        driver.findElement(enterEmailArrow).click();
        driver.switchTo().frame("ifinbox");
        driver.findElement(setPasswordMail).click();
        driver.switchTo().defaultContent();
        driver.switchTo().frame("ifmail");
        driver.findElement(setPasswordLink).click();
    }
}
