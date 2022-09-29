package step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

public class StepDefinition {
    @Given("User is on NetBanking landing page")
    public void userIsOnNetBankingLandingPage() {
        //write code here
        System.out.println("hello testing! 1");
    }

//    @When("User login into application with user and password")
//    public void userLoginIntoApplicationWithUserAndPassword() {
//        //write code here
//        System.out.println("hello testing! 1");
//    }

    @When("User login into application with user {string} and password {string}")
    public void userLoginIntoApplicationWithUserAndPassword(String arg0, String arg1) {
        System.out.println("hello testing! " + arg0 + arg1);
    }

//    @Then("Home page is populated")
//    public void homePageIsPopulated() {
//        //write code here
//        System.out.println("hello testing! 1");
//    }

    @Then("Home page {int} is populated")
    public void homePageIsPopulated(int arg0) {
        System.out.println("hello testing! " + arg0);
    }

    @And("Card are displayed")
    public void cardAreDisplayed() {
        //write code here
        System.out.println("hello testing! 1");
    }
}
