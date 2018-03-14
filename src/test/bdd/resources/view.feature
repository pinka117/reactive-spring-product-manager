Feature: View

  Scenario: Admin view
    Given The user is on Home Page
    And The db is not empty
    When The user logged in as an admin
    And The user navigates to "index" page
    Then There is a button "modify"
    And There is a button "remove"

  Scenario: Generic user view
    Given The user is on Home Page
    And The db is not empty
    When The user isn't logged in
    Then There isn't a button "modify"
    And There isn't a button "remove"