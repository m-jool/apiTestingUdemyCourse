Feature: Validatin Place API's

  Scenario Outline: Verify if Place is being succefully added using AddPlaceAPI
    Given Add Place Payload with "<name>" "<lang>" "<address>"
    When user calls "AddPlaceApi" with "POST" HTTP request
    Then the API call is successful with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify place_Id maps to "<name>" using "GetPlaceApi"

    Examples:
    | name | lang | address |
    | helf | ar | house dme house|
    | jujus | fr | from susdi street|

  Scenario: Verify Delete functionality is working
    Given DeletePlace Payload
    When user calls "DeletePlaceApi" with "POST" HTTP request
    Then the API call is successful with status code 200
    And "status" in response body is "OK"