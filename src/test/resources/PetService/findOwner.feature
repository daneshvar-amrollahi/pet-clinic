Feature: an owner can be found
  Scenario: findOwner(85) is called
    Given owner with id 85 exists
    When findOwner is called with argument 85
    Then the owner with id 85 is returned

