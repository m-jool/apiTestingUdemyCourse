package api_step_def;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.given;

import static org.junit.Assert.*;

import pojo.AddPlace;
import resources.TestDataBuild;
import resources.Utils;

import java.io.IOException;


public class StepDefPlaceValApi extends Utils {
    ResponseSpecification resSpecB;
    RequestSpecification req;
    Response res;
    TestDataBuild testDataBuild = new TestDataBuild();

    @Given("Add Place Payload with {string} {string} {string}")
    public void addPlacePayload(String name, String lang, String address) throws IOException {
        AddPlace addPlace = testDataBuild.addPlacePayload(name, lang, address);
        req = given().spec(requestSepcification()).body(addPlace);
    }

    @When("user calls {string} with POST HTTP request")
    public void userCallsWithPOSTHTTPRequest(String arg0) {
        resSpecB = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .build();

        res = req.when().post("/maps/api/place/add/json")
                .then().spec(resSpecB).extract().response();
    }

    @Then("the API call is successful with status code {int}")
    public void theAPICallIsSuccessfulWithStatusCode(int arg0) {
        assertEquals(200, res.statusCode());
    }

    @And("{string} in response body is {string}")
    public void inResponseBodyIs(String arg0, String arg1) {
        String resString =  res.asString();
        JsonPath js = new JsonPath(resString);
        assertEquals(arg1, js.get(arg0));

        System.out.println(arg1 + " / " + arg0);
    }
}
