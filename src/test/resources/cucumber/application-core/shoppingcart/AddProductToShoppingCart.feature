@AddProductsToShoppingCart @ShoppingCart
Feature: Add Products to Shopping Cart

  Scenario: Use Case - Add Products to Shopping Cart
    Given I prepare the following Shopping Cart for user "1".
      | total | subTotal | shipping | discount |
      | 0     | 0        | 0        | [null]   |
    And I prepare the following Products
      | productName | category    | price | stockQuantity |
      | LGHDTV      | Electronics | 100   | 1             |
    When I add them to my Shopping Cart
    Then I receive the following Shopping Cart
      | total | subTotal | shipping | discount |
      | 100   | 100      | 0        | [null]   |
    And It contains the following Items
      | productName | category    | price | quantity |
      | LGHDTV      | Electronics | 100   | 1        |

  Scenario: Use Case - Add Products to Shopping Cart - insufficient stock
    Given I prepare the following Shopping Cart for user "1".
      | total | subTotal | shipping | discount |
      | 0     | 0        | 0        | [null]   |
    And I prepare the following Products
      | productName     | category    | price | stockQuantity |
      | Samsung OLED TV | Electronics | 1000  | 0             |
    When I add them to my Shopping Cart
    Then I receive an error specifying the Item is out of stock