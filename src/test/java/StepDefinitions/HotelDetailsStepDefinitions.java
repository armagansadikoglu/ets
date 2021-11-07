package StepDefinitions;

import Pages.HotelDetailsPage;
import io.cucumber.java.en.And;


import static StepDefinitions.Base.driver;

public class HotelDetailsStepDefinitions {

    HotelDetailsPage hotelDetailsPage = new HotelDetailsPage(driver);
    @And("selects number {int} room from results")
    public void selectsNumberRoomFromResults(int arg0) {
        hotelDetailsPage.selectNthRoom(arg0);
    }


}
