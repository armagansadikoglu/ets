package StepDefinitions;

import Pages.HomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import static StepDefinitions.Base.driver;

public class HomePageStepDefinitions {
    HomePage homePage;

    @And("^Home Page is opened$")
    public void openHomePage(){
       homePage = new HomePage(driver);
       homePage.openHomePage();
       homePage.checkIfPageLoaded();

    }

    @When("user clicks on hotels")
    public void userClicksOnHotels() {
        homePage.clickOnHotel();
    }

}
