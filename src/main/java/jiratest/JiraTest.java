package jiratest;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;

@Test
public class JiraTest {

    public void jiraTest(){
        baseURI = "http://localhost:8080";

        SessionFilter sessionFilter = new SessionFilter();

        // Get session cookie (auth info)
        Response response = given().header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"username\": \"joseph\",\n" +
                        "    \"password\": \"Root@1907#\"\n" +
                        "}")
                .filter(sessionFilter)
                .when().post("/rest/auth/1/session")
                .then().extract().response();

        // Add comment
        String commentResponse = given().pathParam("id", 10000)
                .body("{\n" +
                "    \"body\": \"limpy limpy\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}")
                .header("Content-Type", "application/json")
                .filter(sessionFilter)
                .when().post("/rest/api/2/issue/{id}/comment")
                .then().assertThat().statusCode(201).extract().response().asString();

        // Add attachment
        given().header("X-Atlassian-Token", "no-check")
                .header("Content-Type","multipart/form-data")
                .filter(sessionFilter)
                .pathParam("id", 10000)
                .multiPart("file", new File("jira.txt"))
                .when().post("/rest/api/2/issue/{id}/attachments")
                .then().assertThat().statusCode(200);

        Response issueResponse = given().relaxedHTTPSValidation().filter(sessionFilter).pathParam("id", 10000)
                .queryParam("fields", "comment")
                .when().get("/rest/api/2/issue/{id}")
                .then().log().all().extract().response();

        System.out.println(issueResponse.asString());

        JsonPath jsComment = new JsonPath(commentResponse);
        int commentID = jsComment.getInt("id");
        System.out.println(commentID);

        JsonPath js = new JsonPath(issueResponse.asString());
        int commentsSize = js.get("fields.comment.comments.size()");
        int extractedCommentID = -1;
        for (int i = 0; i < commentsSize; i++) {
            extractedCommentID = js.getInt("fields.comment.comments[" + i + "].id");
            if( extractedCommentID == commentID){
                System.out.println("ID Found");
                Assert.assertEquals(js.getString(
                        "fields.comment.comments[" + i + "].body"),
                        "limpy limpy",
                        "exptected limpy to equal body"
                );
                break;
            }
        }
    }
}
