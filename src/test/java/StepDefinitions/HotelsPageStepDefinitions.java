package StepDefinitions;

import Pages.HotelsPage;
import io.cucumber.java.en.And;
import static StepDefinitions.Base.driver;
public class HotelsPageStepDefinitions {

    HotelsPage hotelsPage = new HotelsPage(driver);
    @And("types destination to go as {string}")
    public void typesDestinationToGo(String destination) {
        hotelsPage.typeDestination(destination);
    }

    @And("selects dates randomly")
    public void selectsDatesRandomly() {
        hotelsPage.selectRandomDate();
    }

    @And("selects {int} adult {int} child as number of guests")
    public void selectsAsNumberOfGuests(int adult,int child) {
        hotelsPage.selectNumberOfGuests(adult,child);
    }

    @And("clicks on search button")
    public void clickOnSearchButton() {
        hotelsPage.clicksOnSearchButton();
    }


    @And("select children age randomly")
    public void selectChildrenAgeRandomly() {
        hotelsPage.selectChildAgeRandomly();
    }
}
