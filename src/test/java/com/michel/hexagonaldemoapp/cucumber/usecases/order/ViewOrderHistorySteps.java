package com.michel.hexagonaldemoapp.cucumber.usecases.order;

import com.michel.hexagonaldemoapp.application.port.in.order.ViewOrderHistoryUseCase;
import com.michel.hexagonaldemoapp.application.port.out.order.FindOrdersPort;
import com.michel.hexagonaldemoapp.application.service.order.ViewOrderHistoryService;
import com.michel.hexagonaldemoapp.cucumber.SharedState;
import com.michel.hexagonaldemoapp.domain.Discount;
import com.michel.hexagonaldemoapp.domain.Order;
import com.michel.hexagonaldemoapp.domain.OrderItem;
import com.michel.hexagonaldemoapp.domain.OrderStatus;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ViewOrderHistorySteps {

    private final SharedState sharedState;

    public ViewOrderHistorySteps(SharedState sharedState) {
        this.sharedState = sharedState;
    }

    private class FakeFindOrdersAdapter implements FindOrdersPort {
        @Override
        public List<Order> findOrdersByUserId(long userId) {
            return orders;
        }
    }

    List<Order> orders = new ArrayList<>();
    List<Order> orderHistory = new ArrayList<>();

    FindOrdersPort findOrdersPort = new FakeFindOrdersAdapter();
    ViewOrderHistoryUseCase viewOrderHistoryUseCase = new ViewOrderHistoryService(findOrdersPort);

    @And("I prepare the following Orders")
    public void iPrepareTheFollowingOrders(DataTable dataTable) {
        List<Map<String, String>> table = dataTable.asMaps();

        List<OrderItem> orderItems = sharedState.getProducts().stream()
                .map(product -> new OrderItem(product.getId(), product.getPrice(), product.getStockQuantity()))
                .toList();

        sharedState.setUserId(1L);

        table.forEach(row -> {
            orders.add(Order.withId(
                    UUID.fromString(row.get("orderId")),
                    sharedState.getUserId(),
                    BigDecimal.valueOf(Long.parseLong(row.get("total"))),
                    OrderStatus.valueOf(row.get("status")),
                    LocalDate.parse(row.get("date")),
                    sharedState.getShippingAddress(),
                    sharedState.getBillingAddress(),
                    Optional.empty(),
                    orderItems
            ));
        });
    }

    @When("^I request the order history for user \"([^\"]*)\"$")
    public void i_request_the_order_history_for_user(String userId) {
        orderHistory = viewOrderHistoryUseCase.viewOrderHistory(Long.parseLong(userId));
    }


    @Then("^I receive the following Orders$")
    public void i_receive_the_following_orders(DataTable dataTable) {
        List<Map<String, String>> table = dataTable.asMaps();

        table.forEach(row -> {
            LocalDate localDate = "[today]".equals(row.get("date"))
                    ? LocalDate.now()
                    : LocalDate.parse(row.get("date"));

            String discountPercentage = "[null]".equals(row.get("discount"))
                    ? "0"
                    : row.get("discount");

            Order matchedOrder = orderHistory.stream().filter(order -> order.getUuid().toString().equals(row.get("orderId")))
                            .findFirst().orElseGet(() -> fail("Order not found in Expected!"));

            assertEquals(row.get("totalAmount"), matchedOrder.getTotalAmount().toString());
            assertEquals(row.get("status"), matchedOrder.getStatus().toString());
            assertEquals(localDate, matchedOrder.getOrderDate());
            assertEquals(discountPercentage, matchedOrder.getDiscount()
                    .orElse(new Discount(BigDecimal.ZERO, "", OffsetDateTime.now())).percentage().toString());
        });

    }
}
