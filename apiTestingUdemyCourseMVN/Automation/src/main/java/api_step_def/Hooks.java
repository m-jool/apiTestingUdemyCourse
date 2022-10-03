package api_step_def;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {
    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        //write code to give you place ID
        //execute this code only when place ID is null

        StepDefPlaceValApi m = new StepDefPlaceValApi();
        if (StepDefPlaceValApi.placeId == null) {
            m.addPlacePayload("shetty", "french", "asia");
            m.userCallsWithHTTPRequest("AddPlaceApi", "POST");
            m.verifyPlace_IdMapsToUsing("shetty", "GetPlaceApi");
        }
    }
}
