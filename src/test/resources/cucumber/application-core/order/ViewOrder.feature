@ViewOrder
Feature: View Order

  Scenario: Use Case - View Order
    Given I prepare the following Products
      | productName | category    | price | stockQuantity |
      | LGHDTV      | Electronics | 100   | 1             |
    And I prepare the following Shipping Address
      | streetName    | streetNumber | city      | postalCode | country         |
      | Willemsstraat | 21           | Amsterdam | 1015HW     | The Netherlands |
    And I prepare the following Billing Address
      | streetName  | streetNumber | city    | postalCode | country         |
      | Rozenstraat | 1            | Haarlem | 2011LS     | The Netherlands |
    And I prepare the following Order
      | total | status    | date       | discount |
      | 100   | Delivered | 2024-04-01 | [null]   |
    When I request a specific Order
    Then I receive the following Order
      | total | status    | date       | discount |
      | 100   | Delivered | 2024-04-01 | [null]   |
    And It contains the following OrderItems
      | productName | category    | price |
      | LGHDTV      | Electronics | 100   |
    And It contains the following Shipping Address
      | streetName    | streetNumber | city      | postalCode | country         |
      | Willemsstraat | 21           | Amsterdam | 1015HW     | The Netherlands |
    And It contains the following Billing Address
      | streetName  | streetNumber | city    | postalCode | country         |
      | Rozenstraat | 1            | Haarlem | 2011LS     | The Netherlands |
