Feature: Application Login

  Scenario: Home page default login
    Given User is on NetBanking landing page
    When User login into application with user "jin" and password "1234"
    Then Home page 1 is populated
    And Card are displayed

  Scenario: Home page default login
    Given User is on NetBanking landing page
    When User login into application with user "john" and password "4321"
    Then Home page 2 is populated
    And Card are displayed