Feature: an owner can get a new pet from PetService
  Scenario: owner adds a new pet
    Given owner exists
    When owner buys a pet
    Then size of owner pets is increased by one
