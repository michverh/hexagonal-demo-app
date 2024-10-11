@PlaceOrder
Feature: Place Order Tests

  Scenario: Use Case - Place Order
    Given I prepare the following Shopping Cart for user "1".
      | total | subTotal | shipping | discount |
      | 0     | 0        | 0        | [null]   |
    And I prepare the following Products
      | productName | category    | price | stockQuantity |
      | LGHDTV      | Electronics | 100   | 1             |
    And I prepare the following Shipping Address
      | streetName    | streetNumber | city      | postalCode | country         |
      | Willemsstraat | 21           | Amsterdam | 1015HW     | The Netherlands |
    And I prepare the following Billing Address
      | streetName  | streetNumber | city    | postalCode | country         |
      | Rozenstraat | 1            | Haarlem | 2011LS     | The Netherlands |
    And I prepare the following Products to be found
      | productName | category    | price | stockQuantity |
      | LGHDTV      | Electronics | 100   | 1             |
    When I place the Order
    Then I receive the confirmation message with the following order details
      | total | status  | date    | discount |
      | 100   | Pending | [today] | [null]   |
    And It contains the following OrderItems
      | productName | category    | price |
      | LGHDTV      | Electronics | 100   |

  Scenario: Use Case - Place Order incorrect address format
    Given I prepare the following Shopping Cart for user "1".
      | total | subTotal | shipping | discount |
      | 0     | 0        | 0        | [null]   |
    And I prepare the following Products
      | productName | category    | price | stockQuantity |
      | LGHDTV      | Electronics | 100   | 1             |
    And I prepare the following Shipping Address
      | streetName    | streetNumber | city      | postalCode | country         |
      | Willemsstraat | 21           | Amsterdam | 1015 HW    | The Netherlands |
    And I prepare the following Products to be found
      | productName | category    | price | stockQuantity |
      | LGHDTV      | Electronics | 100   | 1             |
    Then I receive an error specifying the address is incorrect

  Scenario: Use Case - Place Order with a product that's out of stock
    Given I prepare the following Shopping Cart for user "1".
      | total | subTotal | shipping | discount |
      | 100   | 100      | 0        | [null]   |
    And I prepare the following Products
      | productName | category    | price | stockQuantity |
      | LGHDTV      | Electronics | 100   | 1             |
    And I prepare the following Shipping Address
      | streetName    | streetNumber | city      | postalCode | country         |
      | Willemsstraat | 21           | Amsterdam | 1015HW     | The Netherlands |
    And I prepare the following Billing Address
      | streetName  | streetNumber | city    | postalCode | country         |
      | Rozenstraat | 1            | Haarlem | 2011LS     | The Netherlands |
    And I prepare the following Products to be found
      | productName | category    | price | stockQuantity |
      | LGHDTV      | Electronics | 100   | 0             |
    When I place the Order
    Then I receive an error specifying the Product is out of stock