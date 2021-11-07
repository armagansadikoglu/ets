package StepDefinitions;

import Pages.PaymentCardPage;
import Pages.PaymentInformationPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import static StepDefinitions.Base.driver;

public class PaymentCardStepDefinitions {
    PaymentCardPage paymentCardPage = new PaymentCardPage(driver);


    @Then("fills card information")
    public void fillsCardInformation() {
        paymentCardPage.fillsCardInformationRandomly();
    }

}
