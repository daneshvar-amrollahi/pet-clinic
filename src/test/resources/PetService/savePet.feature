Feature: owner can save information for his/her pet
  Scenario: owner tries to save pet information
    Given owner with id 85 and pet with id 90 exist
    When owner 85 saves pet 90 information
    Then pet is added to owner pets
