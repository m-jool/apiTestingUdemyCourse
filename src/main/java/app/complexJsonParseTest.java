package app;

import files.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class complexJsonParseTest {
    public static void main(String[] args){
        JsonPath js = new JsonPath(Payload.CoursePrice());

        /*
        In Java, int is a primitive data type while Integer is a Wrapper class.
        int, being a primitive data type has got less flexibility.
        We can only store the binary value of an integer in it.
        Since Integer is a wrapper class for int data type,
        it gives us more flexibility in storing, converting and manipulating an int data.
         */
        int coursesSize = js.getInt("courses.size()");
        System.out.println(coursesSize);

        System.out.println(js.getString("courses[0].title"));

        for (int i = 0; i < coursesSize; i++) {
            String courseTitle = js.getString("courses[" + i + "].title");
            if(courseTitle.equalsIgnoreCase("rpa")){
                System.out.println(courseTitle);
                System.out.println(js.getString("courses[" + i + "].price"));
                System.out.println("copies sold: " + js.getString("courses[" + i + "].copies"));

                //break in order not to waist iteration anymore
                break;
            }
        }

        int total = 0;
        for (int i = 0; i < coursesSize; i++) {
            total += js.getInt("courses[" + i + "].price") * js.getInt("courses[" + i + "].copies");
        }

        System.out.println("total: " + total);
        System.out.println("purchaseAmount: " + js.getInt("dashboard.purchaseAmount"));
        Assert.assertEquals(total, js.getInt("dashboard.purchaseAmount"), "Total shoul match purchaseAmount");
    }
}
