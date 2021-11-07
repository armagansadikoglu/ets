Feature: ETSTur Hotel Search
  Background:
    Given Browser is open
    And Home Page is opened

  Scenario: Search For Hotels Happy Path
    When user clicks on hotels
    And types destination to go as "Antalya"
    And selects dates randomly
    And selects 2 adult 2 child as number of guests
    And select children age randomly
    And clicks on search button
    And selects number 1 hotel from results
    And selects number 1 room from results
    And fills guest information area as foreigner
    Then fills card information
    And close browser

  Scenario: More Than 2 Child
    When user clicks on hotels
    And types destination to go as "Antalya"
    And selects dates randomly
    And selects 2 adult 3 child as number of guests
    And close browser

    Scenario: More Than 6 Adult
      When user clicks on hotels
      And types destination to go as "Antalya"
      And selects dates randomly
      And selects 7 adult 1 child as number of guests
      And close browser