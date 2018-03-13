Feature: Login
Scenario: Login ok
 Given The user is on Home Page
 When I click on Login
 And I insert a valid admin username and password
 Then A message "Logged in" must be shown