package runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.actors.OnStage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import utils.BeforeSuite;
import utils.DataToFeature;
import utils.RunnerPersonalizado;

import java.io.IOException;

@CucumberOptions(
        features = "src/test/resources/features/getUserByID.feature",
        glue = "stepdefinitions",
        snippets = CucumberOptions.SnippetType.CAMELCASE )
@RunWith(RunnerPersonalizado.class)
public class GetUserByIDRunner {

    @BeforeSuite
    public static void test() throws InvalidFormatException, IOException
    {
        DataToFeature.overrideFeatureFiles("src/test/resources/features/getUserByID.feature");
    }

    @BeforeClass
    public static void setStage() {
        OnStage.setTheStage(new Cast());
    }

}
