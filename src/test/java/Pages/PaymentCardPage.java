package Pages;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class PaymentCardPage {
    WebDriver driver;
    Logger logger;
    public PaymentCardPage(WebDriver driver){
        this.driver = driver;
        logger = LogManager.getLogger(PaymentInformationPage.class);
        PageFactory.initElements(driver,this);
    }
    @FindBy(id = "creditCardOwnerName")
    WebElement cardOwnerNameField;
    @FindBy(id = "tb-creditCardNumber")
    WebElement cardNumberField;
    @FindBy(name = "creditCardValidMonth")
    WebElement cardValidMonth;
    @FindBy(name = "creditCardValidYear")
    WebElement cardValidYear;
    @FindBy(name = "creditCardCVC")
    WebElement cardCVC;
    String cardOwnerString = "isim soyisim";
    String cardNumberString = "1234123412341234";
    public void fillsCardInformationRandomly() {
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeClickable(cardOwnerNameField));
        cardOwnerNameField.sendKeys(cardOwnerString);
        logger.log(Level.INFO,this.getClass().getSimpleName()+" :Card Owner Name Typed : "+cardOwnerString);
        cardNumberField.sendKeys(cardNumberString);
        logger.log(Level.INFO,this.getClass().getSimpleName()+" :Card Number Typed : "+cardNumberString);


        Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);

        int yearLastTwoDigit = year%100;
        Random random = new Random();
        int randomYear = yearLastTwoDigit+random.nextInt(11);
        logger.log(Level.INFO,this.getClass().getSimpleName()+" :Random year generated for card  : "+randomYear);
        int randomMonth;
        if (randomYear==21){
            randomMonth = month+1+random.nextInt(12-month);
        }else{
            randomMonth = 1+random.nextInt(12);
        }

        String randomMonthString = String.valueOf(randomMonth);
        String randomYearString = String.valueOf(randomYear);
        if (randomMonthString.length() == 1){
            randomMonthString = "0"+randomMonthString;
        }
        logger.log(Level.INFO,this.getClass().getSimpleName()+" :Random month generated for card  : "+randomMonthString);
        Select select = new Select(cardValidMonth);
        select.selectByValue(randomMonthString);
        logger.log(Level.INFO,this.getClass().getSimpleName()+" :Random Month selected");
        select = new Select(cardValidYear);
        select.selectByValue(randomYearString);
        logger.log(Level.INFO,this.getClass().getSimpleName()+" :Random Year selected");

        int randomCVC = 1+random.nextInt(999);
        String randomCVCString = String.valueOf(randomCVC);
        if (randomCVCString.length()!=3){
            while (randomCVCString.length()<3){
                randomCVCString = "0"+randomCVCString;
            }
        }
        logger.log(Level.INFO,this.getClass().getSimpleName()+" :Random CVC generated for card  : "+randomCVCString);
        cardCVC.sendKeys(randomCVCString);
        logger.log(Level.INFO,this.getClass().getSimpleName()+" :Random CVC typed");
    }
}
