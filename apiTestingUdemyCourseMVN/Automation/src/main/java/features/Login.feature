Feature: Application Login

  Scenario: Home page default login
    Given User is on NetBanking landing page
    When User login into application with user and password
    Then Home page is populated
    And Card are displayed