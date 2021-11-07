package Pages;

import io.cucumber.java.en_old.Ac;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;


public class HotelDetailsPage {
    Logger logger ;
    WebDriver driver;
    public HotelDetailsPage(WebDriver driver){
        this.driver = driver;
        logger = LogManager.getLogger(HotelDetailsPage.class);
        PageFactory.initElements(driver,this);
    }
    final String roomsCss = ".room-checkout-link>i";
    @FindBy(css = roomsCss)
    List<WebElement> rooms;

    public void selectNthRoom(int n){
        Set<String> windowHandles = driver.getWindowHandles();
        Object[] handles = windowHandles.toArray();
        driver.switchTo().window(handles[handles.length-1].toString());
        WebDriverWait wait = new WebDriverWait(driver,15);
        WebElement room = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(roomsCss))).get(n-1);
        wait.until(ExpectedConditions.elementToBeClickable(room)).click();
        String title = driver.getTitle();
        if (!title.equals("https://www.etstur.com/Otel/satin-alma/kisi-bilgileri")){
            wait.until(ExpectedConditions.elementToBeClickable(room));
            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(false);", room);
            ((JavascriptExecutor)driver).executeScript("arguments[0].click();", room);
        }

        logger.log(Level.INFO,this.getClass().getSimpleName()+" : number "+n+" room selected");

    }
}
