package cucumber_options;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "src/main/java/features",
                "src/main/java/api"
        },
        glue = {
                "step_definitions",
                "api_step_def"
        }
)
public class TestRunner {
}
