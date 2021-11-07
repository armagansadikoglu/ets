package Pages;


import org.apache.logging.log4j.Level;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class ResultsPage {
    WebDriver driver;
    Logger logger;
    public ResultsPage(WebDriver driver){
        this.driver=driver;
        logger = LogManager.getLogger(ResultsPage.class);
        PageFactory.initElements(driver,this);
    }
    final String hotelResultsCss=".total-room-price";
    @FindBy(css = hotelResultsCss )
    List<WebElement> hotelResults;

    public void selectNthHotel(int n){
        WebDriverWait wait = new WebDriverWait(driver,15);
        List<WebElement> until = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(hotelResultsCss)));
        WebElement webElement  = until.get(n-1);
        wait.until(ExpectedConditions.elementToBeClickable(webElement)).click();
        logger.log(Level.INFO,this.getClass().getSimpleName()+" : clicked number "+n +" hotel");

    }
}
