package com.michel.hexagonaldemoapp.cucumber.usecases.order;

import com.michel.hexagonaldemoapp.application.port.in.order.command.PlaceOrderCommand;
import com.michel.hexagonaldemoapp.application.port.in.order.PlaceOrderUseCase;
import com.michel.hexagonaldemoapp.application.port.out.product.FindProductPort;
import com.michel.hexagonaldemoapp.application.port.out.order.PlaceOrderPort;
import com.michel.hexagonaldemoapp.application.service.order.PlaceOrderService;
import com.michel.hexagonaldemoapp.cucumber.SharedState;
import com.michel.hexagonaldemoapp.domain.*;
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

public class PlaceOrderSteps {

    private final SharedState sharedState;

    public PlaceOrderSteps(SharedState sharedState) {
        this.sharedState = sharedState;
    }

    private class FakeFindProductAdapter implements FindProductPort {

        @Override
        public Optional<Product> findByUuid(UUID uuid) {
            return foundProducts.stream().filter(product -> product.getId().equals(uuid)).findFirst();
        }
    }

    private class FakePlaceOrderAdapter implements PlaceOrderPort {
        @Override
        public void placeOrder(Order order) {
            sharedState.setOrder(order);
        }
    }

    Order createdOrder;
    List<Product> foundProducts = new ArrayList<>();

    FindProductPort findProductPort = new FakeFindProductAdapter();
    PlaceOrderPort placeOrderPort = new FakePlaceOrderAdapter();
    PlaceOrderUseCase placeOrderUseCase = new PlaceOrderService(findProductPort, placeOrderPort);

    @And("I prepare the following Products to be found")
    public void iPrepareTheFollowingProductsToBeFound(DataTable dataTable) {
        final List<Map<String, String>> table = dataTable.asMaps();

        table.forEach(row -> {
            Optional<Product> product = sharedState.getProducts().stream()
                    .filter(prod -> row.get("productName").equals(prod.getName())).findFirst();
            if(product.isPresent()){
                Product product1 = product.get();
                foundProducts.add(new Product(product1.getId(), product1.getName(), product1.getCategory(),
                        product1.getPrice(), Integer.parseInt(row.get("stockQuantity"))));
            }
        });
    }

    @When("^I place the Order$")
    public void i_Place_the_Order() {
        sharedState.getProducts().forEach(product ->
                sharedState.getShoppingCart().addItem(product, 1)
        );

        PlaceOrderCommand command = new PlaceOrderCommand(sharedState.getShippingAddress(),
                sharedState.getBillingAddress(), sharedState.getShoppingCart());

        try {
            placeOrderUseCase.placeOrder(command);
        } catch (IllegalArgumentException e) {
            sharedState.setException(e);
        }
    }

    @Then("^I receive the confirmation message with the following order details$")
    public void i_receive_the_confirmation_message_with_the_following_order_details(DataTable dataTable) {
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

    @Then("^I receive an error specifying the address is incorrect$")
    public void i_receive_an_error_specifying_the_address_is_incorrect() {
        if(sharedState.getException() instanceof IllegalArgumentException) {
            assertEquals("Invalid postal code: 1015 HW", sharedState.getException().getMessage());
        } else {
            fail();
        }
    }

    @Then("^I receive an error specifying the Product is out of stock$")
    public void i_receive_an_error_specifying_the_Product_is_out_of_stock() {
        if(sharedState.getException() instanceof IllegalArgumentException) {
            assertEquals("Not enough stock for Product: " + sharedState.getProducts().get(0).getName(),
                    sharedState.getException().getMessage());
        } else {
            fail();
        }
    }

}
