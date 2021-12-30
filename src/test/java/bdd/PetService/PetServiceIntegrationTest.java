package bdd.PetService;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
	features = "src/test/resources/PetService",
	glue = "bdd"
)

public class PetServiceIntegrationTest {
}
