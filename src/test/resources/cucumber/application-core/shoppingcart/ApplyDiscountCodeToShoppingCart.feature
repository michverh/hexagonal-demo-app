@ApplyDiscountCodeToShoppingCart @ShoppingCart
Feature: Apply Discount to Shopping Cart

  Scenario: Use Case - Apply Discount to Shopping Cart
    Given I prepare the following Shopping Cart for user "1".
      | total | subTotal | shipping | discount |
      | 0     | 0        | 0        | [null]   |
    Then I prepare the following Products
      | productName | category    | price | stockQuantity |
      | LGHDTV      | Electronics | 100   | 1             |
    And I prepare a Shopping Cart
    When I apply the following discount code "10OFF24" to my Shopping Cart
    Then The Shopping Cart is updated to
      | total | subTotal | shipping | discount |
      | 90    | 90       | 0        | 10       |

  Scenario: Use Case - Apply Discount to Shopping Cart - Discount Code unknown
    Given I prepare the following Shopping Cart for user "1".
      | total | subTotal | shipping | discount |
      | 0     | 0        | 0        | [null]   |
    Then I prepare the following Products
      | productName | category    | price | stockQuantity |
      | LGHDTV      | Electronics | 100   | 1             |
    And I request my Shopping Cart
    When I apply the following discount code "WrongCode" to my Shopping Cart
    Then I receive an error specifying the discount code is unknown

  Scenario: Use Case - Apply Discount to Shopping Cart - Discount Code expired
    Given I prepare the following Shopping Cart for user "1".
      | total | subTotal | shipping | discount |
      | 0     | 0        | 0        | [null]   |
    Then I prepare the following Products
      | productName | category    | price | stockQuantity |
      | LGHDTV      | Electronics | 100   | 1             |
    And I prepare a Shopping Cart
    When I apply the following discount code "OldCode" to my Shopping Cart
    Then I receive an error specifying the discount code is expired