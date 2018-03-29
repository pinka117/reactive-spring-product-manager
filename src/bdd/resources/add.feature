Feature: Add

  Scenario: Add ok
    Given The user is on Home Page
    And The user logged in as an admin
    When The user navigates to "new" page
    And Enters item name "new item"
    And Enters location "new location"
    And Presses "Add"
    Then The user is redirected to Home Page
    And A table must show an item with name "new item", location "new location" and id is positive

  Scenario: Add empty name
    Given The user is on Home Page
    And The user logged in as an admin
    When The user navigates to "new" page
    And Enters location "new location"
    And Presses "Add"
    Then A message "Empty name" must be shown

  Scenario: Add empty location
    Given The user is on Home Page
    And The user logged in as an admin
    When The user navigates to "new" page
    And Enters item name "new item"
    And Presses "Add"
    Then A message "Empty location" must be shown