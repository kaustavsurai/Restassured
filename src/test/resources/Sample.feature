@Test
Feature: Test Rest Features
  Scenario: Usage of Get and JSon Path
    Given I hit api "http://ergast.com/api/f1/2017/circuits.json" with query parameter "" query parameter value ""  path parameter ""
    Then validate Response code is "200"
    Then validate Total Number of Record is "20"
    Then validate "India" is not present
    Then verify longitude is "144.968" when locality is "Melbourne"
    Then verify circuit id is "albert_park" when locality is "Melbourne"
  

  Scenario: Usage of Query Parameters and verify JSon Schema
    Given I hit api "http://md5.jsontest.com/" with query parameter "text" query parameter value "test"  path parameter ""
    Then validate Response code is "200"
    Then Verify Json Schema "test.json"
    Given I hit api "http://ergast.com/api/f1/{resource}/circuits.json" with query parameter "" query parameter value ""  path parameter "2016"
    Then validate Response code is "200"
    Then Verify Json Schema "circuits.json"

   Scenario: Usage of Basic Authentication
     Given I hit api "http://restapi.demoqa.com/authentication/CheckForAuthentication" with username "" password ""
     Then validate Response code is "401"
     Given I hit api "http://restapi.demoqa.com/authentication/CheckForAuthentication" with username "ToolsQA" password "TestPassword"
     Then validate Response code is "200"
     Then I verify Response Header server is "nginx"


  Scenario: Usage of Oauth2 Token Authentication
    Given I hit api "http://coop.apps.symfonycasts.com/token" with Clientid "KaustavSuraiRestAssured" ClientSecret "4c48cd4f98c3044ac5309b3ea194b852"
    Then validate Response code is "200"
    Then I receive Json Token
    Given I hit api "http://coop.apps.symfonycasts.com/api/{User_ID}/barn-unlock" with token and path parameter "652"
    Then validate Response code is "200"


  Scenario Outline: Usage of Post with Request Body
    Given I run "<selectQuery>"
    When I hit api "http://dummy.restapiexample.com/api/v1/create" post with json body
    Then validate Response code is "200"
  Examples:
      |selectQuery                                                      |
      |select name,salary,age from employee where salary=8500           |
    
    