package com.michel.hexagonaldemoapp.cucumber.usecases;

import com.michel.hexagonaldemoapp.cucumber.SharedState;
import com.michel.hexagonaldemoapp.domain.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenericSteps {

    private final SharedState sharedState;

    public GenericSteps(SharedState sharedState) {
        this.sharedState = sharedState;
    }

    @Given("I prepare the following Shopping Cart for user {string}.")
    public void iPrepareTheFollowingShoppingCart(String userId, DataTable dataTable) {
        final Map<String, String> row = dataTable.asMaps().get(0);

        sharedState.setUserId(Long.parseLong(userId));

        sharedState.setShoppingCart(new ShoppingCart(
                sharedState.getUserId(),
                BigDecimal.valueOf(Long.parseLong(row.get("total"))),
                BigDecimal.valueOf(Long.parseLong(row.get("subTotal"))),
                BigDecimal.valueOf(Long.parseLong(row.get("shipping"))),
                new ArrayList<>(),
                "[null]".equals(row.get("discount"))
                        ? Optional.empty()
                        : Optional.of(new Discount(
                        BigDecimal.valueOf(Long.parseLong(row.get("discount"))),
                        "",
                        OffsetDateTime.now().plusDays(2)
                ))
        ));
    }

    @And("I prepare the following Products")
    public void iPrepareTheFollowingProducts(DataTable dataTable) {
        final List<Map<String, String>> table = dataTable.asMaps();

        table.forEach(row -> {
            sharedState.setProductId(UUID.randomUUID());
            Product product = new Product(
                    sharedState.getProductId(),
                    row.get("productName"),
                    row.get("category"),
                    BigDecimal.valueOf(Long.parseLong(row.get("price"))),
                    Integer.parseInt(row.get("stockQuantity"))
            );
            sharedState.getProducts().add(product);
        });

    }

    @And("^I prepare the following Shipping Address$")
    public void i_prepare_the_following_shipping_address(DataTable dataTable) {
        final Map<String, String> row = dataTable.asMaps().get(0);

        try {
            sharedState.setShippingAddress(new Address(
                    row.get("streetName"),
                    row.get("streetNumber"),
                    row.get("postalCode"),
                    row.get("city"),
                    row.get("country")
            ));
        } catch (IllegalArgumentException e) {
            sharedState.setException(e);
        }
    }

    @And("^I prepare the following Billing Address$")
    public void i_prepare_the_following_billing_address(DataTable dataTable) {
        final Map<String, String> row = dataTable.asMaps().get(0);

        try {
            sharedState.setBillingAddress(new Address(
                    row.get("streetName"),
                    row.get("streetNumber"),
                    row.get("postalCode"),
                    row.get("city"),
                    row.get("country")
            ));
        } catch (IllegalArgumentException e) {
            sharedState.setException(e);
        }
    }

    @Given("I prepare the following Order")
    public void iPrepareTheFollowingOrder(DataTable dataTable) {
        final Map<String, String> row = dataTable.asMaps().get(0);

        List<OrderItem> orderItems = sharedState.getProducts().stream()
                .map(product -> new OrderItem(product.getId(), product.getPrice(), product.getStockQuantity()))
                .toList();

        sharedState.setUserId(1L);

        sharedState.setOrder(Order.withId(
                sharedState.getOrderId(),
                sharedState.getUserId(),
                BigDecimal.valueOf(Long.parseLong(row.get("total"))),
                OrderStatus.valueOf(row.get("status")),
                LocalDate.parse(row.get("date")),
                sharedState.getShippingAddress(),
                sharedState.getBillingAddress(),
                Optional.empty(),
                orderItems
        ));
    }

    @Then("^I receive the following Shopping Cart$")
    public void receive_shopping_cart(DataTable dataTable) {
        final Map<String, String> row = dataTable.asMaps().get(0);

        assertThat(sharedState.getShoppingCart().getTotal().toString()).isEqualTo(row.get("total"));
        assertThat(sharedState.getShoppingCart().getSubTotal().toString()).isEqualTo(row.get("subTotal"));
        assertThat(sharedState.getShoppingCart().getShipping().toString()).isEqualTo(row.get("shipping"));
        if (!"[null]".equals(row.get("discount"))) {
            assertThat(sharedState.getShoppingCart().getDiscount().get().percentage().toString()).isEqualTo(row.get("discount"));
        } else {
            assertTrue(sharedState.getShoppingCart().getDiscount().isEmpty());
        }
    }

    @And("^It contains the following Items$")
    public void contains_the_following_Items(DataTable dataTable) {
        final Map<String, String> row = dataTable.asMaps().get(0);

        assertThat(sharedState.getShoppingCart().getProductsAndQuantities().size()).isEqualTo(dataTable.entries().size());
        assertThat(String.valueOf(sharedState.getShoppingCart().getProductsAndQuantities().get(0).getQuantity())).isEqualTo(row.get("quantity"));
        assertThat(sharedState.getShoppingCart().getProductsAndQuantities().get(0).getProduct().getName()).isEqualTo(row.get("productName"));
        assertThat(sharedState.getShoppingCart().getProductsAndQuantities().get(0).getProduct().getCategory()).isEqualTo(row.get("category"));
        assertThat(sharedState.getShoppingCart().getProductsAndQuantities().get(0).getProduct().getPrice().toString()).isEqualTo(row.get("price"));
    }

    @And("It contains the following OrderItems")
    public void itContainsTheFollowingOrderItems(DataTable dataTable) {
        final List<Map<String, String>> table = dataTable.asMaps();
        int i = 0;

        for (Map<String, String> row : table) {
            assertEquals(row.get("productName"), sharedState.getProducts().get(i).getName());
            assertEquals(row.get("category"), sharedState.getProducts().get(i).getCategory());
            assertEquals(row.get("price"), sharedState.getProducts().get(i).getPrice().toString());
            i++;
        }
    }

}
