package files;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class DynamicJson {

    @Test
    public void addBook(){
        RestAssured.baseURI = "http://216.10.245.166";
        Response response = given().header("Content-Type", "application/json")
                .body(Payload.addBook())
                .when().post("Library/Addbook.php")
                .then().statusCode(200).extract().response();

        JsonPath js = new JsonPath(response.getBody().asString());

    }

}
