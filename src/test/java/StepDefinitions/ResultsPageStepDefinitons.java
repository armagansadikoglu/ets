package StepDefinitions;

import Pages.ResultsPage;
import io.cucumber.java.en.And;


import static StepDefinitions.Base.driver;

public class ResultsPageStepDefinitons {

    ResultsPage resultsPage = new ResultsPage(driver);
    @And("selects number {int} hotel from results")
    public void selectsNumberHotelFromResults(int n) {
        resultsPage.selectNthHotel(n);
    }

}
