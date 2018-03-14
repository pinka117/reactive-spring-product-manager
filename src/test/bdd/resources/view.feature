Feature: View

  Scenario: Admin view
    Given The user is on Home Page
    And The db is not empty
    When The user logged in as an admin
    And The user is on Home Page
    Then There is a button "Modify"
    And There is a button "Remove"

  Scenario: Generic user view
    Given The user is on Home Page
    And The db is not empty
    When The user isn't logged in
    Then There isn't a button "Modify"
    And There isn't a button "Remove"
    And There isn't a button "Add"