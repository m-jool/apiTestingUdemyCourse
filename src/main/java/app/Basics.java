package app;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import files.Payload;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.LinkedHashMap;

public class Basics {

    public static void main(String[] args){
        // TODO
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        Response response = given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(Payload.AddPlace())
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.18 (Ubuntu)")
                .extract().response();

        System.out.println(response.asString());
        System.out.println(response.body().asString());
        System.out.println(response.getBody().asString());

        JsonPath js = new JsonPath(response.getBody().asString());
        LinkedHashMap x = JsonPath.from(response.getBody().asString()).get();
        String placeId = js.get("place_id");

        System.out.println(placeId);
        System.out.println(x);
        System.out.println(x.get("place_id"));

        String newAddress = "70 Summer walk, USA";

        given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"place_id\":\"" + placeId + "\",\n" +
                        "    \"address\":\"" + newAddress + "\",\n" +
                        "    \"key\":\"qaclick123\"\n" +
                        "}\n")
                .when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200)
                .body("msg", equalTo("Address successfully updated"));

        given().log().all()
                .queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json")
                .then().assertThat().statusCode(200)
                .body("address", equalTo(newAddress));

        //TestNG assertion
        Assert.assertEquals(newAddress, "blabla", "custom mess");

    }
}
