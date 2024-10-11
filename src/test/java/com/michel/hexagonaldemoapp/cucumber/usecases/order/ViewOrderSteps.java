package com.michel.hexagonaldemoapp.cucumber.usecases.order;

import com.michel.hexagonaldemoapp.application.port.in.order.ViewOrderUseCase;
import com.michel.hexagonaldemoapp.application.port.out.order.FindOrderPort;
import com.michel.hexagonaldemoapp.application.service.order.ViewOrderService;
import com.michel.hexagonaldemoapp.cucumber.SharedState;
import com.michel.hexagonaldemoapp.domain.Discount;
import com.michel.hexagonaldemoapp.domain.Order;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewOrderSteps {

    private final SharedState sharedState;

    public ViewOrderSteps(SharedState sharedState) {
        this.sharedState = sharedState;
    }

    private class FakeFindOrderAdapter implements FindOrderPort {
        @Override
        public Order findOrderById(UUID orderId) {
            return sharedState.getOrder();
        }
    }

    UUID orderId;
    Order retrievedOrder;

    FindOrderPort findOrderPort = new FakeFindOrderAdapter();
    ViewOrderUseCase viewOrderUseCase = new ViewOrderService(findOrderPort);

    @Given("I request a specific Order")
    public void iRequestASpecificOrder() {
        orderId = UUID.randomUUID();
        retrievedOrder = viewOrderUseCase.findOrder(orderId);
    }

    @Then("I receive the following Order")
    public void iReceiveTheFollowingOrder(DataTable dataTable) {
        final Map<String, String> row = dataTable.asMaps().get(0);

        LocalDate localDate = "[today]".equals(row.get("date"))
                ? LocalDate.now()
                : LocalDate.parse(row.get("date"));

        String discountPercentage = "[null]".equals(row.get("discount"))
                ? "0"
                : row.get("discount");

        assertEquals(row.get("total"), sharedState.getOrder().getTotalAmount().toString());
        assertEquals(row.get("status"), sharedState.getOrder().getStatus().toString());
        assertEquals(localDate, sharedState.getOrder().getOrderDate());
        assertEquals(discountPercentage, sharedState.getOrder().getDiscount()
                .orElse(new Discount(BigDecimal.ZERO, "", OffsetDateTime.now())).percentage().toString());
    }

    @And("It contains the following Shipping Address")
    public void itContainsTheFollowingShippingAddress(DataTable dataTable) {
        final Map<String, String> row = dataTable.asMaps().get(0);

        assertEquals(row.get("streetName"), retrievedOrder.getShippingAddress().getStreetName());
        assertEquals(row.get("streetNumber"), retrievedOrder.getShippingAddress().getStreetNumber());
        assertEquals(row.get("city"), retrievedOrder.getShippingAddress().getCity());
        assertEquals(row.get("postalCode"), retrievedOrder.getShippingAddress().getPostalCode());
        assertEquals(row.get("country"), retrievedOrder.getShippingAddress().getCountry());
    }

    @And("It contains the following Billing Address")
    public void itContainsTheFollowingBillingAddress(DataTable dataTable) {
        final Map<String, String> row = dataTable.asMaps().get(0);

        assertEquals(row.get("streetName"), retrievedOrder.getBillingAddress().getStreetName());
        assertEquals(row.get("streetNumber"), retrievedOrder.getBillingAddress().getStreetNumber());
        assertEquals(row.get("city"), retrievedOrder.getBillingAddress().getCity());
        assertEquals(row.get("postalCode"), retrievedOrder.getBillingAddress().getPostalCode());
        assertEquals(row.get("country"), retrievedOrder.getBillingAddress().getCountry());
    }
}
