package oauth;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import pojo.GetCourse;

import java.util.HashMap;

import static io.restassured.RestAssured.*;

public class Oauth {

    @Test
    public void oauthTest(){

        //Requires Selenium Integration which we will be skipping here
        //Selenium libs should be added in the pom.xml file
        //Google now blocks automation => so the user has to login manually to get the code

        HashMap <String, String> queryParams = new HashMap<String, String>();
        queryParams.put("code", "");
        queryParams.put("client_id" , "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com");
        queryParams.put("client_secret", "erZOWM9g3UtwNRj340YYaK_W");
        queryParams.put("redirect_uri", "https://rahulshettyacademy.com/getCourse.php");
        queryParams.put("grant_type", "https://www.googleapis.com/auth/userinfo.email");

        String accessTokenResponse = given().queryParams(queryParams)
                .when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();

        JsonPath js = new JsonPath(accessTokenResponse);
        String accessToken = js.getString("access_token");

        String response = given().queryParam("access_token", accessToken)
                .when().get("https://rahulshettyacademy.com/getCourse.php").asString();

        //If response header Content-Type is application/json, then you do not need to set defaultParser
        GetCourse responseDeserialized =  given().queryParam("access_token", accessToken)
                .expect().defaultParser(Parser.JSON)
                .when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);

        System.out.println(responseDeserialized.getLinkedIn());

    }
}
