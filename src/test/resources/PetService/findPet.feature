Feature: a pet can be found
  Scenario: A user tries to find a pet with id 90
    Given pet with owner id 1 and type id 1 exists
    When I try to find pet with id 90
    Then pet with id 90 is returned
