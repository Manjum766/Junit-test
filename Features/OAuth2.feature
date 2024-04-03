Feature: Validating Book API's

  Scenario: User Generates Token for Authoriszation
    Given I am an Authorized User

  @OAuth2.0
  Scenario Outline: Verify if User is able to fetch Course Details using Get Course Details API
    Given Fetch Course Details API
    When User Calls "GetCourseDetailsAPI" with "GET" Http Request
    Then Verify Status Code "<StatusCode>"
    And Verify "url" in Response Body is "rahulshettycademy.com"

    Examples: 
      | StatusCode |
      |        401 |
