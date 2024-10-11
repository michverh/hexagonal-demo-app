package com.michel.hexagonaldemoapp.cucumber.usecases.shoppingcart;

import com.michel.hexagonaldemoapp.application.port.in.shoppingcart.GetShoppingCartUseCase;
import com.michel.hexagonaldemoapp.application.port.out.shoppingcart.GetShoppingCartPort;
import com.michel.hexagonaldemoapp.application.service.shoppingcart.GetShoppingCartService;
import com.michel.hexagonaldemoapp.cucumber.SharedState;
import com.michel.hexagonaldemoapp.domain.CartItem;
import com.michel.hexagonaldemoapp.domain.Discount;
import com.michel.hexagonaldemoapp.domain.Product;
import com.michel.hexagonaldemoapp.domain.ShoppingCart;
import io.cucumber.java.en.Given;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GetShoppingCartSteps {

    private final SharedState sharedState;

    public GetShoppingCartSteps(SharedState sharedState) {
        this.sharedState = sharedState;
    }

    private static class FakeGetShoppingCartAdapter implements GetShoppingCartPort {

        @Override
        public ShoppingCart getShoppingCartByUserID(long userID) {
            final Product product = new Product(
                    UUID.randomUUID(),
                    "LGHDTV",
                    "Electronics",
                    BigDecimal.valueOf(100),
                    1
            );
            final CartItem item = new CartItem(1, product);
            final List<CartItem> items = new ArrayList<>();
            items.add(item);
            return new ShoppingCart(
                    1L,
                    BigDecimal.valueOf(50),
                    BigDecimal.valueOf(50),
                    BigDecimal.valueOf(0),
                    items,
                    Optional.of(new Discount(BigDecimal.ZERO, "", OffsetDateTime.now()))
            );
        }
    }

    GetShoppingCartPort getShoppingCartPort = new FakeGetShoppingCartAdapter();
    GetShoppingCartUseCase useCase = new GetShoppingCartService(getShoppingCartPort);

    @Given("^I request my Shopping Cart$")
    public void request_shopping_cart() {
        sharedState.setShoppingCart(useCase.getShoppingCart(1L));
    }
}