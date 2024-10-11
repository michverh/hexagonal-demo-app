@ViewOrderHistory
Feature: View Order History

  Scenario: Use Case - View Order History
    Given I prepare the following Products
      | productName | category    | price | stockQuantity |
      | LGHDTV      | Electronics | 100   | 1             |
    And I prepare the following Shipping Address
      | streetName    | streetNumber | city      | postalCode | country         |
      | Willemsstraat | 21           | Amsterdam | 1015HW     | The Netherlands |
    And I prepare the following Billing Address
      | streetName  | streetNumber | city    | postalCode | country         |
      | Rozenstraat | 1            | Haarlem | 2011LS     | The Netherlands |
    And I prepare the following Orders
      | orderId                              | total | status    | date       | discount |
      | 2ddaee5b-8bed-4cf2-97c8-dcf2084d2f97 | 100   | Delivered | 2024-04-01 | [null]   |
      | 2ddaee5b-8bed-4cf2-97c8-dcf2084d2f98 | 100   | Shipped   | 2024-10-17 | [null]   |
    When I request the order history for user "1"
    Then I receive the following Orders
      | orderId                              | totalAmount | status    | date       | discount |
      | 2ddaee5b-8bed-4cf2-97c8-dcf2084d2f97 | 100         | Delivered | 2024-04-01 | [null]   |
      | 2ddaee5b-8bed-4cf2-97c8-dcf2084d2f98 | 100         | Shipped   | 2024-10-17 | [null]   |