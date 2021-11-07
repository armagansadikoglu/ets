package Pages;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HotelsPage {
    Logger logger ;
    WebDriver driver;
    WebDriverWait wait;
    Random rand ;
    String childErrorMessageString = "Web sitemizden en fazla 2 çocuk için rezervasyon yapabilirsin.";
    String adultErrorMessageString = "Web sitemizden en fazla 6 yetişkin için rezervasyon yapabilirsin.";
    static List<Integer> childAges;
    public HotelsPage(WebDriver driver){
        this.driver=driver;
        logger = LogManager.getLogger(HotelsPage.class);
        PageFactory.initElements(driver,this);
    }
    @FindBy(id = "tb-autocomplete")
    WebElement hotelsPageDestinationInput;
    @FindBy(xpath = "//span[text()='Giriş Tarihi']")
    WebElement hotelsPageCheckInDate;
    @FindBy(css = "td.today")
    WebElement today;
    @FindBy(xpath = "//div[@class='drp-calendar col left']//td[string-length(normalize-space(text())) > 0]")
    List<WebElement> leftColumnDates;
    @FindBy(xpath = "//div[@class='drp-calendar col right']//td[string-length(normalize-space(text())) > 0]")
    List<WebElement> rightColumnDates;
    @FindBy(id = "checkIn")
    WebElement checkInDateField;
    @FindBy(css = ".autocomplete-result>ul>li:nth-child(1)")
    WebElement result;
    @FindBy(css = ".sf-guests")
    WebElement guestsButton;

    @FindBy(css = "#quantityAdult")
    WebElement quantityAdult;
    @FindBy(css = "#guestAdultSpinner>span:nth-of-type(1)>button")
    WebElement guestsAdultMinusButton;
    @FindBy(css = "#guestAdultSpinner>span:nth-of-type(2)>button")
    WebElement guestsAdultPlusButton;

    @FindBy(css = "#quantityChild")
    WebElement quantityChild;
    @FindBy(css = "#guestChildSpinner>span:nth-of-type(2)>button")
    WebElement guestsChildPlusButton;
    @FindBy(xpath = "//select[contains(@name,'childAge')]")
    List<WebElement> childrenAges;

    @FindBy(css = ".search-hotel")
    WebElement searchHotelButton;
    @FindBy(css = ".tooltip-warning.child-error-message.hidden")
    WebElement childErrorMessage;
    @FindBy(css = ".tooltip-warning.adult-error-message.hidden")
    WebElement adultErrorMessage;



    public void typeDestination(String destination){
        hotelsPageDestinationInput.sendKeys(destination);
        WebDriverWait wait = new WebDriverWait(driver,2);
        wait.until(ExpectedConditions.visibilityOf(result));
        String resultText = result.getText(); // after click it throws Unable to locate element
        result.click();
        logger.log(Level.INFO,this.getClass().getSimpleName()+" : typed-> "+destination+" selected-> "+ resultText);
    }
    public void selectRandomDate(){
        checkInDateField.click();
        logger.log(Level.INFO,this.getClass().getSimpleName()+" : clicked date field");
        int lastDateOfLeftCol = Integer.parseInt(leftColumnDates.get(leftColumnDates.size()-1).getText());
        logger.log(Level.INFO,this.getClass().getSimpleName()+" : Last Date Of First Month(Left Column): " + lastDateOfLeftCol);
        int lastDateOfRightCol = Integer.parseInt(rightColumnDates.get(rightColumnDates.size()-1).getText());
        logger.log(Level.INFO,this.getClass().getSimpleName()+" : Last Date Of Second Month(Right Column): " + lastDateOfRightCol);
        int todaysDate = Integer.parseInt(today.getText());
        logger.log(Level.INFO,this.getClass().getSimpleName()+" : Todays Date: "+todaysDate);
        int random;

        rand = new Random();
        int checkInDay = rand.nextInt(lastDateOfLeftCol-todaysDate+1) + todaysDate;
        logger.log(Level.INFO,this.getClass().getSimpleName()+" : Random check-in date: "+checkInDay);
        driver.findElement(By.xpath("//div[@class='drp-calendar col left']//td[normalize-space(text())="+checkInDay+"]")).click();
        logger.log(Level.INFO,this.getClass().getSimpleName()+" : Random check-in date selected");
        int checkOutDay;
        if (checkInDay == lastDateOfLeftCol){
            checkOutDay = rand.nextInt(lastDateOfRightCol+1);
            logger.log(Level.INFO,this.getClass().getSimpleName()+" : Random check-out date from right column: "+checkOutDay);
            driver.findElement(By.xpath("//div[@class='drp-calendar col right']//td[normalize-space(text())="+checkOutDay+"]")).click();
            logger.log(Level.INFO,this.getClass().getSimpleName()+" : Random check-out date selected from right column: "+checkOutDay);
        }else if(checkInDay < lastDateOfLeftCol ){
            random = rand.nextInt(2);
            if (random == 0){ // choose checkout from left
                checkOutDay = rand.nextInt(lastDateOfLeftCol-checkInDay+1)+checkInDay;
                logger.log(Level.INFO,this.getClass().getSimpleName()+" : Random check-out date from left column: "+checkOutDay);
                driver.findElement(By.xpath("//div[@class='drp-calendar col left']//td[normalize-space(text())="+checkOutDay+"]")).click();
                logger.log(Level.INFO,this.getClass().getSimpleName()+" : Random check-out date selected from left column: "+checkOutDay);
            }else { // choose checkout from right
                checkOutDay = rand.nextInt(lastDateOfRightCol+1);
                logger.log(Level.INFO,this.getClass().getSimpleName()+" : Random check-out date from right column: "+checkOutDay);
                driver.findElement(By.xpath("//div[@class='drp-calendar col right']//td[normalize-space(text())="+checkOutDay+"]")).click();
                logger.log(Level.INFO,this.getClass().getSimpleName()+" : Random check-out date selected from right column: "+checkOutDay);
            }

        }



    }

    public void selectNumberOfGuests(int userAdultCount, int userChildCount) {
        logger.log(Level.INFO,this.getClass().getSimpleName()+" : User Adult Count: "+userAdultCount+" User Child Count: "+userChildCount);
        guestsButton.click();
        logger.log(Level.INFO,this.getClass().getSimpleName()+" : Clicked guests field");
        int adultCount= Integer.parseInt(quantityAdult.getAttribute("value"));
        logger.log(Level.INFO,this.getClass().getSimpleName()+" : Default number of adults selected : "+adultCount);

        if (userAdultCount > adultCount){
            int diff = userAdultCount-adultCount;
            for (int i=0 ; i<diff;i++){
                guestsAdultPlusButton.click();
                logger.log(Level.INFO,this.getClass().getSimpleName()+" : Adult Guest Plus Button Clicked ");
                if (adultCount+i+1>6){
                    WebDriverWait wait = new WebDriverWait(driver,2);
                    wait.until(ExpectedConditions.visibilityOf(adultErrorMessage));
                    File screenshotAs = adultErrorMessage.getScreenshotAs(OutputType.FILE);
                    logger.log(Level.INFO,this.getClass().getSimpleName()+" : Took screenshot for adult error ");
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    try {
                        FileUtils.copyFile(screenshotAs,new File(System.getProperty("user.dir")+"/screenshots/more_than_6_adult_"+sdf1.format(timestamp)+".jpeg"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Assert.assertTrue(adultErrorMessage.isDisplayed());
                    Assert.assertEquals(adultErrorMessage.getText(),adultErrorMessageString);
                }
            }
        }else if(userAdultCount < adultCount){
            int diff = adultCount - userAdultCount;
            for (int i=0 ; i<diff;i++){
                guestsAdultMinusButton.click();
                logger.log(Level.INFO,this.getClass().getSimpleName()+" : Adult Guest Minus Button Clicked ");
            }
        }

        int childCount = Integer.parseInt(quantityChild.getAttribute("value"));
        logger.log(Level.INFO,this.getClass().getSimpleName()+" : Default number of child selected : "+childCount);
        if (userChildCount > childCount){
            int diff = userChildCount-childCount;
            for (int i=0 ; i<diff;i++){
                logger.log(Level.INFO,this.getClass().getSimpleName()+" : Waiting for adult error message to be invisible ");
                wait = new WebDriverWait(driver,5);
                wait.until(ExpectedConditions.invisibilityOf(adultErrorMessage));
                logger.log(Level.INFO,this.getClass().getSimpleName()+" : Adult error message is invisible ");
                guestsChildPlusButton.click();
                logger.log(Level.INFO,this.getClass().getSimpleName()+" : Child Guest Plus Button Clicked ");
                if (childCount+i+1>2){
                    wait = new WebDriverWait(driver,2);
                    wait.until(ExpectedConditions.visibilityOf(childErrorMessage));
                    File screenshotAs = childErrorMessage.getScreenshotAs(OutputType.FILE);
                    logger.log(Level.INFO,this.getClass().getSimpleName()+" : Took screenshot of child error message");
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    try {
                        FileUtils.copyFile(screenshotAs,new File(System.getProperty("user.dir")+"/screenshots/more_than_2_child_"+sdf1.format(timestamp)+".jpeg"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Assert.assertTrue(childErrorMessage.isDisplayed());
                    Assert.assertEquals(childErrorMessage.getText(),childErrorMessageString);
                }
            }

        }

    }
    public void selectChildAgeRandomly(){
        logger.log(Level.INFO,this.getClass().getSimpleName()+" : Generating random ages for child/children");
        Select select;
        childAges = new ArrayList<>();
        for (WebElement childAge : childrenAges ){
            select = new Select(childAge);
            int generatedAge = rand.nextInt(17);
            childAges.add(generatedAge);
            logger.log(Level.INFO,this.getClass().getSimpleName()+" : Age generated : "+generatedAge);
            select.selectByValue(String.valueOf(generatedAge));
        }
    }

    public void clicksOnSearchButton() {
        searchHotelButton.click();
        logger.log(Level.INFO,this.getClass().getSimpleName()+" : Search hotel button clicked" );
    }
}
