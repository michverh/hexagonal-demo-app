@GetShoppingCart @ShoppingCart
Feature: Get ShoppingCart

  Scenario: Use Case - View Shopping Cart
    Given I request my Shopping Cart
    Then I receive the following Shopping Cart
      | total | subTotal | shipping | discount |
      | 50    | 50       | 0        | 0        |
    And It contains the following Items
      | productName | category    | price | quantity |
      | LGHDTV      | Electronics | 100   | 1        |

