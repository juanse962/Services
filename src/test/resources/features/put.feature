Feature: Prueba de Apis PUT

  Scenario Outline: PUT

    Given consumo el api <API> para verificar la peticion <Peticion>, con la siguiente data de prueba:
      | <Enviroment> | <EndPoint> | <METODO> | <HEADERS> | <BODY> | <PARAMS> |
    When verifico el status code <StatusCode>
    Then Verifico los valores esperados en el response <ValoresEsperados>

    Examples:
      | API  | Peticion   | Enviroment | EndPoint | METODO       | HEADERS                       | BODY                                      | StatusCode | ValoresEsperados        | PARAMS |
      ##@externaldata@src/test/resources/datadriven/Prueba.xlsx@Put
   |PUT   |Success   |BASE   |/users/2   |PutBodyJson   |Content-Type:application/json   |{ "name": "Yuli", "email": "yuli@gmail.com" }   |200   |User updated successfully   |N/A|
