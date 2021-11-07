package Pages;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    Logger logger ;
    WebDriver driver;
    public HomePage(WebDriver driver){
        this.driver=driver;
        logger = LogManager.getLogger(HomePage.class);
        PageFactory.initElements(driver,this);
    }
    @FindBy(xpath = "//span[contains(text(),'OTEL')]")
    WebElement hotelDropdownBtn;
    @FindBy(id = "tb-autocomplete")
    WebElement homePageDestinationInput;
    @FindBy(css = ".cookie-close")
    WebElement kvkkCookieCloseButton;

    public void openHomePage(){

        driver.get("https://www.etstur.com");
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOf(kvkkCookieCloseButton)).click();
        checkIfPageLoaded();
        logger.log(Level.INFO,this.getClass().getSimpleName()+" : etstur.com opened");
    }
    public void clickOnHotel(){
        hotelDropdownBtn.click();
        logger.log(Level.INFO,this.getClass().getSimpleName()+" : hotel button click");
    }

    public void checkIfPageLoaded() {
        Assert.assertTrue(homePageDestinationInput.isDisplayed());
    }
}
