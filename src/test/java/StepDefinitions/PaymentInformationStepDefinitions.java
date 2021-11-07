package StepDefinitions;

import Pages.PaymentInformationPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import static StepDefinitions.Base.driver;

public class PaymentInformationStepDefinitions {
    PaymentInformationPage paymentInformationPage = new PaymentInformationPage(driver);
    @And("fills guest information area as foreigner")
    public void fillsGuestInformationAreaAsForeigner() {
        paymentInformationPage.fillsGuestInformationAreaAsForeignerRandomly();
    }

}
