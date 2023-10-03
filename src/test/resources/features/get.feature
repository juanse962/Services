Feature: Prueba de Apis GET

  Scenario Outline: GET

    Given consumo el api <API> para verificar la peticion <Peticion>, con la siguiente data de prueba:
      | <Enviroment> | <EndPoint> | <METODO> | <HEADERS> | <BODY> | <PARAMS> |
    When verifico el status code <StatusCode>
    Then Verifico los valores esperados en el response <ValoresEsperados>

    Examples:
      | API | Peticion   | Enviroment | EndPoint | METODO | HEADERS                       | BODY | StatusCode | ValoresEsperados                            | PARAMS |
      ##@externaldata@src/test/resources/datadriven/prueba.xlsx@Get
   |GET   |Success   |BASE   |/users   |Get   |Content-Type:application/json   |   |200   |Successfully! All records has been fetched.   |N/A|
