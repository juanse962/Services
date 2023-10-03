package task;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

public class Verificar implements Task {

    String expectedMessage;

    public Verificar(String expectedMessage) {
        this.expectedMessage = expectedMessage;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        String responseBody = SerenityRest.lastResponse().getBody().asString();

        // Determine whether response is a JSON array or a single JSON object
        if (responseBody.startsWith("[")) {
            // Response is a JSON array
            JSONArray jsonArray = new JSONArray(responseBody);
            JSONObject firstObject = jsonArray.getJSONObject(0);
            String message = firstObject.getString("message");
            Ensure.that(message).contains(expectedMessage);
//            OnStage.theActorInTheSpotlight().should(
//                    seeThat("Las cadenas son iguales", actor -> message, Matchers.equalTo(expectedMessage))
//            );
        } else if (responseBody.startsWith("{")) {
            // Response is a single JSON object
            JSONObject jsonObject = new JSONObject(responseBody);
            String message = jsonObject.getString("message");
            Ensure.that(message).contains(expectedMessage);

//            OnStage.theActorInTheSpotlight().should(
//                    seeThat("Las cadenas son iguales", actor -> message, Matchers.equalTo(expectedMessage))
//            );
        } else {
            // Response is not in JSON format
            // Handle this case as appropriate for your application
            throw new JSONException("Invalid JSON format");
        }

    }

    public static Verificar elResponse(String expectedMessage){
        return Instrumented.instanceOf(Verificar.class).withProperties(expectedMessage);
    }
}
