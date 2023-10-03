Feature: Delete user by ID

  Scenario Outline: DELETE

    Given consumo el api <API> para verificar la peticion <Peticion>, con la siguiente data de prueba:
      | <Enviroment> | <EndPoint> | <METODO> | <HEADERS> | <BODY> | <PARAMS> |
    When verifico el status code <StatusCode>
    Then Verifico los valores esperados en el response <ValoresEsperados>

    Examples:
      | API    | Peticion   | Enviroment | EndPoint | METODO | HEADERS                       | BODY | StatusCode | ValoresEsperados          | PARAMS |
      ##@externaldata@src/test/resources/datadriven/prueba.xlsx@Delete
   |DELETE   |Success   |BASE   |/users/6   |Delete   |Content-Type:application/json   |   |200   |User deleted successfully   |N/A|
