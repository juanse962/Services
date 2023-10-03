package stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.actors.OnStage;
import net.thucydides.core.util.EnvironmentVariables;
import questions.LastResponseStatusCode;
import task.Preparar;
import task.Verificar;


import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;
import static utils.Constantes.ACTOR;

public class StepDefinitions extends PageObject {

    private EnvironmentVariables environmentVariables;


    @Given("^consumo el api (.+) para verificar la peticion (.+), con la siguiente data de prueba:$")
    public void consumoElApiParaVerificarLaPeticionConLaSiguienteDataDePrueba(String api, String peticion, DataTable datosPeticion) {
        OnStage.theActorCalled(ACTOR).wasAbleTo(
                Preparar.losDatos(datosPeticion)
        );
    }

    @When("^verifico el status code (\\d+)$")
    public void verificoElStatusCode(int sc) {
        System.out.println(sc);
        OnStage.theActorInTheSpotlight().should(seeThat("El status code: ", LastResponseStatusCode.is(), equalTo(sc)));
    }

    @Then("^Verifico los valores esperados en el response (.+)$")
    public void verificoLosValoresEsperadosEnElResponse(String valoresEsperados) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Verificar.elResponse(valoresEsperados)
        );


    }
}