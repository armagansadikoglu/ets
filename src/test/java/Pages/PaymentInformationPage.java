package Pages;


import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static Pages.HotelsPage.childAges;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PaymentInformationPage {
    WebDriver driver;
    Logger logger;
    public PaymentInformationPage(WebDriver driver){
        this.driver = driver;
        logger = LogManager.getLogger(PaymentInformationPage.class);
        PageFactory.initElements(driver,this);
    }

    @FindBy(css = ".col-lg-12.gender-area")
    List<WebElement> genders;

    @FindBy(xpath = "//input[contains(@id,'tb-name_0')]")
    List<WebElement> names;

    @FindBy(xpath = "//input[contains(@id,'tb-surname_0')]")
    List<WebElement> surnames;

    @FindBy(xpath = "//input[contains(@id,'tb-birthdate_0')]")
    List<WebElement> birthdates;

    @FindBy(xpath = "//label[contains(@for,'notTcCitizen_0')]")
    List<WebElement> notTcCitizenCheckboxes;

    @FindBy(css = "div[id='modalGuestPassportInfo']>div>div[class='modal-content']>div[class='modal-header']>button")
    WebElement warningCloseButton;

    @FindBy(xpath = "//input[contains(@id,'passport_0')]")
    List<WebElement> passports;
    @FindBy(xpath = "//input[contains(@id,'tb-email_0')]")
    WebElement email;

    @FindBy(id = "btn-preBook")
    WebElement proceedPaymentButton;

    @FindBy(id="contactFormPhoneNumber-g1")
    WebElement phoneNumber;

    String phoneString = "5555555555";
    String emailString = "deneme@deneme.com";
    String passportString = "55555555555555";
    String nameString = "İSİM";
    String surnameString = "SOYİSİM";
    public void fillsGuestInformationAreaAsForeignerRandomly() {
        WebDriverWait wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.visibilityOfAllElements(names));

        for (int i=0; i< genders.size();i++){
            wait.until(ExpectedConditions.elementToBeClickable(names.get(i)));
            names.get(i).click();
            logger.log(Level.INFO,this.getClass().getSimpleName()+" : "+(i+1)+" name field clicked");
            names.get(i).sendKeys(nameString);
            surnames.get(i).sendKeys(surnameString);

            Date date= new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            String month = String.valueOf(cal.get(Calendar.MONTH));
            String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

            if (day.length() == 1){
                day = "0"+day;
            }
            if (month.length() == 1){
                month = "0"+month;
            }

            if (i >= genders.size()-childAges.size()){ // children
                year = year - childAges.get(i-genders.size()+childAges.size());
                logger.log(Level.INFO,this.getClass().getSimpleName()+" :Birthdate generated for children : "+day+month+year);
                birthdates.get(i).sendKeys(day+month+year);
            }else { // adults
                year = year - 30 ;
                birthdates.get(i).sendKeys(day+month+year);
                logger.log(Level.INFO,this.getClass().getSimpleName()+" :Birthdate generated for adult : "+day+month+year);
            }

            if (i%2==0){
                        WebElement element = wait.until(ExpectedConditions.
                        elementToBeClickable(genders.get(i).findElements(By.tagName("div")).get(0)));
                element.click();
                logger.log(Level.INFO,this.getClass().getSimpleName()+" :Gender selected for number "+(i+1)+" guest : "+genders.get(i).findElements(By.tagName("div")).get(0).getText());
            }else {
                WebElement element = wait.until(ExpectedConditions.
                        elementToBeClickable(genders.get(i).findElements(By.tagName("div")).get(1)));
                element.click();
                logger.log(Level.INFO,this.getClass().getSimpleName()+" :Gender selected for number "+(i+1)+" guest : "+genders.get(i).findElements(By.tagName("div")).get(1).getText());
            }

            notTcCitizenCheckboxes.get(i).click();
            logger.log(Level.INFO,this.getClass().getSimpleName()+" :Not TC citizen checked for number "+(i+1)+" guest");
            wait.until(ExpectedConditions.elementToBeClickable(warningCloseButton)).click();
            if (warningCloseButton.isDisplayed()){
                ((JavascriptExecutor)driver).executeScript("arguments[0].click();", warningCloseButton);
            }

            passports.get(i).sendKeys(passportString);
            logger.log(Level.INFO,this.getClass().getSimpleName()+" :Passport typed as "+(i+1)+" guest");

        }
        email.sendKeys(emailString);
        phoneNumber.sendKeys(phoneString);
        //wait.until(ExpectedConditions.elementToBeClickable(proceedPaymentButton)).click();
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", proceedPaymentButton);
        logger.log(Level.INFO,this.getClass().getSimpleName()+" :Proceed payment button clicked");

    }


}
