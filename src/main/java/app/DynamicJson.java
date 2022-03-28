package app;

import files.Payload;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DynamicJson {

    @Test(dataProvider = "booksData")
    public void addBook(String isbn, String aisle){
        RestAssured.baseURI = "http://216.10.245.166";
        Response response = given().header("Content-Type", "application/json")
                .body(Payload.addBook(aisle, isbn))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response();

        JsonPath js = new JsonPath(response.getBody().asString());

        String ID = js.get("ID");
        System.out.println(ID);
    }

    @DataProvider(name="booksData")
    public Object[][] getData(){
        return new Object[][] {
                {"blabla2", "9827162"},
                {"najdhcta", "18763126875"},
                {"jkdhkkjaiuys", "123987234"}
        };
    }

}
