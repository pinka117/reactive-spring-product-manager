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

  Scenario: Username on Home Page
    Given The user logged in as an admin
    When The user is on Home Page
    Then The correct username is displayed

  Scenario: Already logged in
    Given The user logged in as an admin
    When The user navigates to "login" page
    Then A message "You're already logged in" must be shown

  Scenario: Disconnect user
    Given The user logged in as an admin
    And The user is on Home Page
    When Presses "Diconnect"
    Then The user is disconnected

  Scenario: Labels for non logged in user
    Given The user isn't logged in
    When The user is on Home Page
    Then There is a label "Login"
    And There isn't a label "Disconnect"

