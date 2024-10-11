@FindProducts
Feature: Application Core Tests

  Scenario: Use Case - Search For Products
    Given I request products with the following attributes
      | productName | category    |
      | LGHDTV      | Electronics |
    Then I receive the following products
      | productName | category    | price |
      | LGHDTV      | Electronics | 100   |