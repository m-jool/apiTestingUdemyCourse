package api_step_def;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import resources.APIresources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;


public class StepDefPlaceValApi extends Utils {
    ResponseSpecification resSpecB;
    RequestSpecification req;
    Response res;
    TestDataBuild testDataBuild = new TestDataBuild();
    public static String placeId;

    @Given("Add Place Payload with {string} {string} {string}")
    public void addPlacePayload(String name, String lang, String address) throws IOException {
        AddPlace addPlace = testDataBuild.addPlacePayload(name, lang, address);
        req = given().spec(requestSepcification()).body(addPlace);
    }

    @When("user calls {string} with {string} HTTP request")
    public void userCallsWithHTTPRequest(String path, String method) throws IOException {
//        res = req.when().post(APIresources.AddPlaceApi.getPath())
//                .then().spec(resSpecB).extract().response();
        if (method.equalsIgnoreCase("POST"))
            res = req.when().post(APIresources.valueOf(path).getPath())
                    .then().spec(responseSpecification()).extract().response();
        else if (method.equalsIgnoreCase("GET"))
            res = req.when().get(APIresources.valueOf(path).getPath())
                    .then().spec(responseSpecification()).extract().response();
    }

    @Then("the API call is successful with status code {int}")
    public void theAPICallIsSuccessfulWithStatusCode(int arg0) {
        assertEquals(200, res.statusCode());
    }

    @And("{string} in response body is {string}")
    public void inResponseBodyIs(String keyValue, String expectedValue) {
        assertEquals(getJsonPath(res, keyValue), expectedValue);
    }

    @And("verify place_Id maps to {string} using {string}")
    public void verifyPlace_IdMapsToUsing(String name, String api) throws IOException {
//        prepare request spec
        placeId = getJsonPath(res, "place_id");
        req = given().spec(requestSepcification()).queryParam("place_id", placeId);
        userCallsWithHTTPRequest(api, "GET");
        String actualName = getJsonPath(res, "name");

        assertEquals(actualName, name);
    }

    @Given("DeletePlace Payload")
    public void deletePlacePayload() throws IOException {
        req = given().spec(requestSepcification()).body(testDataBuild.deletePlacePayload(placeId));
    }
}
