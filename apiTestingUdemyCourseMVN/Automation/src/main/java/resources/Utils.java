package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

public class Utils {

    //shared across all instances of class
    public static RequestSpecification rsb = null;
    public static ResponseSpecification respSpec = null;
    private String logFileName = "logging.txt";

    public RequestSpecification requestSepcification() throws IOException {
        if(rsb != null) {
            return rsb;
        }

        PrintStream log = new PrintStream(new FileOutputStream(this.logFileName));
        final Properties prop = getGlobalValues();
        rsb = new RequestSpecBuilder().setBaseUri(prop.getProperty("baseUrl"))
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON)
                .addFilter(RequestLoggingFilter.logRequestTo(log))
                .addFilter(ResponseLoggingFilter.logResponseTo(log))
                .build();

        return rsb;
    }

    public ResponseSpecification responseSpecification() throws IOException {
        if (respSpec != null) {
            return respSpec;
        }

        respSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .build();

        return respSpec;
    }

    public Properties getGlobalValues() throws IOException {
        Properties prop = new Properties();
        prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config/global.properties"));
        return prop;
    }

    public String getJsonPath(Response response, String key) {
        String responseString =  response.asString();
        JsonPath js = new JsonPath(responseString);
        return js.get(key).toString();
    }
}
