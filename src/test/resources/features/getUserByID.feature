Feature: Get user by ID

  Scenario Outline: GET user by ID

    Given consumo el api <API> para verificar la peticion <Peticion>, con la siguiente data de prueba:
      | <Enviroment> | <EndPoint> | <METODO> | <HEADERS> | <BODY> | <PARAMS> |
    When verifico el status code <StatusCode>
    Then Verifico los valores esperados en el response <ValoresEsperados>

    Examples:
      | API   | Peticion   | Enviroment | EndPoint | METODO | HEADERS                       | BODY | StatusCode | ValoresEsperados                            | PARAMS |
      | Get 2 | Happy Path | BASE       | /users/1 | Get    | Content-Type:application/json |      | 200        | Successfully! All records has been fetched. | N/A    |
