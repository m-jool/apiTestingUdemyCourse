Feature: Validatin Place API's

  Scenario Outline: Verify if Place is being succefully added using AddPlaceAPI
    Given Add Place Payload with "<name>" "<lang>" "<address>"
    When user calls "AddPlaceAPI" with POST HTTP request
    Then the API call is successful with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"

    Examples:
    | name | lang | address |
    | hel | ar | house me house|
    | juju | en | from susi street|