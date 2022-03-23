package app;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import files.Payload;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

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
    }
}
