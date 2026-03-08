Feature: Create booking

  Scenario Outline: Create booking with different user data
    Given a payload with booking details "<firstname>" "<lastname>" <price> "<deposit>" "<checkin>" "<checkout>" "<needs>"
    When the user calls "createBooking" with "POST" request
    Then the response status should be <status>
    And the booking id is stored

    Examples:
      | firstname | lastname | price | deposit | checkin     | checkout    | needs     | status |
      | John      | Smith    | 120   | true    | 2024-01-01  | 2024-01-05 | Breakfast | 200    |

  Scenario Outline: Generate authorization token for updation
    Given there are credentials "<username>" and "<password>"
    When the user calls the authAPI "createTokenAPI" with "POST" request
    And the token generated is stored

    Examples:
      | username    | password   |
      | admin       | password123|


  Scenario Outline: update the booking and verify changes in the booker API
    Given the user updates the booking with "<firstname>" "<lastname>" <price> "<deposit>" "<checkin>" "<checkout>" "<needs>" using the generated token
    When calls "updateBooking" with "PUT" request
    Then the response status should be 200
    When the user calls "bookingById" with "GET" request
    Then the response field "firstname" should be "<firstname>"

    Examples:
      | firstname | lastname | price | deposit | checkin | checkout | needs |
      | Alex | Johnson | 150 |true|2024-01-01 | 2024-01-05 | Breakfast |
#| Sarah | Parker | 180 |false | 2024-02-10 | 2024-02-15 | Lunch |

  Scenario Outline: Partially update booking details in the booker API
    Given the user prepares partial update payload with "<lastname>" and "<additionalneeds>"
    When the user calls "partialUpdateBooking" with "PATCH" request
    Then the response status should be 200
    When the user calls "bookingById" with "GET" request
    Then the response fields should be    "<lastname>" "<additionalneeds>"
    Examples:
      | lastname | additionalneeds |
      | Taylor   | Dinner          |
      | Wilson   | Lunch           |
