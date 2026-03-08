Feature: Booking management in Restful Booker API

  Scenario Outline: Create booking with different user data
    Given a payload with booking details "<firstname>" "<lastname>" <price> "<deposit>" "<checkin>" "<checkout>" "<needs>"
    When the user calls "createBooking" with "POST" request
    Then the response status should be 200
    And the booking id is stored

    Examples:
      | firstname | lastname | price | deposit | checkin | checkout | needs |
      | John | Smith | 120 | true | 2024-01-01 | 2024-01-05 | Breakfast |
    # | Mike | Brown | 200 | false | 2024-02-10 | 2024-02-15 | Lunch |


