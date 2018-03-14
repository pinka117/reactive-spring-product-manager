Feature: Login

  Scenario: Login ok
    Given The user is on Home Page
    When The user navigates to "login" page
    And The user insert a valid admin username and password
    Then A message "Logged in" must be shown

  Scenario: Login non present username
    Given The user is on Home Page
    When The user navigates to "login" page
    And The user insert a non present username and a password
    Then A message "Non valid username or password" must be shown

  Scenario: Login wrong password
    Given The user is on Home Page
    When The user navigates to "login" page
    And The user insert a valid username and a wrong password
    Then A message "Non valid username or password" must be shown
