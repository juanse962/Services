Feature: Prueba de Apis POST

  Scenario Outline: POST

    Given consumo el api <API> para verificar la peticion <Peticion>, con la siguiente data de prueba:
      | <Enviroment> | <EndPoint> | <METODO> | <HEADERS> | <BODY> | <PARAMS> |
    When verifico el status code <StatusCode>
    Then Verifico los valores esperados en el response <ValoresEsperados>

    Examples:
      | API  | Peticion   | Enviroment | EndPoint | METODO       | HEADERS                       | BODY                                      | StatusCode | ValoresEsperados        | PARAMS |
      ##@externaldata@src/test/resources/datadriven/Prueba.xlsx@Post
   |POST   |Success   |BASE   |/users   |PostBodyJson   |Content-Type:application/json   |{ "name": "Juan", "email": "Juan@gmail.com" }   |201   |User added successfully   |N/A|
