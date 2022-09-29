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
import pojo.Location;

import java.util.ArrayList;
import java.util.List;


public class StepDefPlaceValApi {
    ResponseSpecification resSpecB;
    RequestSpecification req;
    Response res;

    @Given("Add Place Payload")
    public void addPlacePayload() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        AddPlace addPlace = new AddPlace();
        addPlace.setAccuracy(50);
        addPlace.setLanguage("French-IN");
        addPlace.setName("Frontline house");
        addPlace.setPhone_number("(+91) 983 893 3937");
        addPlace.setAddress("29, side layout, cohen 09");
        addPlace.setWebsite("http://google.com");

        List<String> list = new ArrayList<String>();
        list.add("shoe park");
        list.add("shop");
        addPlace.setTypes(list);

        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        addPlace.setLocation(location);

        res = given().queryParam("key", "qaclick123")
                .body(addPlace)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response();

        String resString = res.asString();

        RequestSpecification rsb = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON)
                .build();

        req = given().spec(rsb).body(addPlace).log().all();

        resSpecB = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .build();
    }

    @When("user calls {string} with POST HTTP request")
    public void userCallsWithPOSTHTTPRequest(String arg0) {
        res = req.when().post("/maps/api/place/add/json")
                .then().log().all().spec(resSpecB).extract().response();
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
