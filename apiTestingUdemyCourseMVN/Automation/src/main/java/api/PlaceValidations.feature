Feature: Validatin Place API's

  Scenario: Verify if Place is being succefully added using AddPlaceAPI
    Given Add Place Payload
    When user calls "AddPlaceAPI" with POST HTTP request
    Then the API call is successful with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"

#  Scenario: Verify if Place is being succefully added using AddPlaceAPI
#    Given Add Place Payload
#    When user calls "DeletePlaceAPI" with POST HTTP request
#    Then the API call is successful with status code 200
#    And status in response body is OK