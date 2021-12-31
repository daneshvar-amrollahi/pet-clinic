Feature: an owner can be found
  Scenario: a user tries to find an owner with id 85
    Given owner with id 85 exists
    When findOwner is called with argument 85
    Then the owner with id 85 is returned

