package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {

    //shared across all instances of class
    public static RequestSpecification rsb = null;

    public RequestSpecification requestSepcification() throws IOException {
        if(rsb != null){
            return rsb;
        }

        PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
        final Properties prop = getGlobalValues();
        rsb = new RequestSpecBuilder().setBaseUri(prop.getProperty("baseUrl"))
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON)
                .addFilter(RequestLoggingFilter.logRequestTo(log))
                .addFilter(ResponseLoggingFilter.logResponseTo(log))
                .build();

        return rsb;
    }

    public Properties getGlobalValues() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config/global.properties");

        Properties prop = new Properties();
        prop.load(inputStream);

        System.out.println(prop.getProperty("baseUrl"));

        return prop;
    }
}
