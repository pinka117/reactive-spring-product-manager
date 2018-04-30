Feature: Delete

  Scenario: Delete name ok
    Given The user is on Home Page
    And The user logged in as an admin
    And There is an item with id "1" and name "new name" and location "new location"
    When The user click "Delete" on id "1"
    Then There isn't an item with id "1" and name "new name" and location "new location"