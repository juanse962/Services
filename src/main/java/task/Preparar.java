package task;

import interactions.Consumo;
import io.cucumber.datatable.DataTable;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.thucydides.core.util.EnvironmentVariables;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Preparar implements Task {

    String url;
    String body;
    String metodo;
    DataTable datosPeticion;

    EnvironmentVariables environmentVariables;


    public Preparar(DataTable datosPeticion) {
        this.datosPeticion = datosPeticion;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {


        List<List<String>> datos = datosPeticion.asLists(String.class);

        //Preparar url
        url = environmentVariables.getProperty(datos.get(0).get(0)) + datos.get(0).get(1);
        System.out.println(url);//Cambiar por Logger

        //Preparar body
        body = datos.get(0).get(4);

        //Indicar Metodo
        metodo = datos.get(0).get(2);

        //Preparar headers
        Map<String, Object> headers = new HashMap<>();
        for (String pair : datos.get(0).get(3).split(",")) {
            String[] entry = pair.split(":");
            headers.put(entry[0].trim(), entry[1].trim());
        }

        //Preparar parametros
        Map<String, Object> params = new HashMap<>();
        if (datos.get(0).get(5).equals("N/A")) {
            System.out.println("No aplica");
        } else {
            for (String pair : datos.get(0).get(5).split(",")) {
                String[] entry = pair.split(":");
                if (entry[1].trim().equals("vacio"))
                    params.put(entry[0].trim(), "  ");
                else
                    params.put(entry[0].trim(), entry[1].trim());
            }
        }

        actor.attemptsTo(
                Consumo.elApi(metodo, url, headers, body, params)
        );


    }
    public static Preparar losDatos(DataTable datosPeticion) {
        return Instrumented.instanceOf(Preparar.class).withProperties(datosPeticion);
    }

}
