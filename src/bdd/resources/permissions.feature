Feature: Permissions

  Scenario: Not logged user try to navigate in new page
    Given The user isn't logged in
    When The user navigates on "new" page
    Then There is a permission error

  Scenario: Not logged user try to navigate in modify page
    Given The user isn't logged in
    When The user navigates on "modify" page
    Then The user is redirected to "login" page

  Scenario: Not logged user try to navigate in remove page
    Given The user isn't logged in
    When The user navigates on "remove" page
    Then The user is redirected to "login" page