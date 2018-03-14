Feature: Modify

  Scenario: Modify name ok
    Given The user is on Home Page
    And The user logged in as an admin
    And There is an item with id "1" and name "new name" and location "new location"
    And The user click "Modify" on id "1"
    And The user is redirected to "edit" page
    When Enters name "name 2"
    And Presses "Ok"
    Then The user is redirected to Home Page
    And There is an item with id "1" and name "name 2" and location "new location"
    And There isn't an item with id "1" and name "new name" and location "new location"

  Scenario: Modify location ok
    Given The user is on Home Page
    And The user logged in as an admin
    And There is an item with id "1" and name "new name" and location "new location"
    And The user click "Modify" on id "1"
    And The user is redirected to "edit" page
    When Enters location "location 2"
    And Presses "Ok"
    Then The user is redirected to Home Page
    And There is an item with id "1" and name "new name" and location "location 2"
    And There isn't an item with id "1" and name "new name" and location "new location"

  Scenario: Modify empty name
    Given The user is on Home Page
    And The user logged in as an admin
    And There is an item with id "1" and name "new name" and location "new location"
    And The user click "Modify" on id "1"
    And The user is redirected to "edit" page
    When Enters name ""
    And Presses "Ok"
    Then A message "Empty name" must be shown

  Scenario: Modify empty location
    Given The user is on Home Page
    And The user logged in as an admin
    And There is an item with id "1" and name "new name" and location "new location"
    And The user click "Modify" on id "1"
    And The user is redirected to "edit" page
    When Enters location ""
    And Presses "Ok"
    Then A message "Empty location" must be shown