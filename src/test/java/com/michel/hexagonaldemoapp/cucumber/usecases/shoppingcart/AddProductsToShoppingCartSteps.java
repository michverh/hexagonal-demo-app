package com.michel.hexagonaldemoapp.cucumber.usecases.shoppingcart;

import com.michel.hexagonaldemoapp.application.port.in.shoppingcart.command.AddProductsToShoppingCartCommand;
import com.michel.hexagonaldemoapp.application.port.in.shoppingcart.AddProductsToShoppingCartUseCase;
import com.michel.hexagonaldemoapp.application.port.out.product.FindProductsPort;
import com.michel.hexagonaldemoapp.application.port.out.shoppingcart.GetShoppingCartPort;
import com.michel.hexagonaldemoapp.application.port.out.shoppingcart.UpdateShoppingCartPort;
import com.michel.hexagonaldemoapp.application.service.shoppingcart.AddProductsToShoppingCartService;
import com.michel.hexagonaldemoapp.cucumber.SharedState;
import com.michel.hexagonaldemoapp.domain.Product;
import com.michel.hexagonaldemoapp.domain.ShoppingCart;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AddProductsToShoppingCartSteps {

    private final SharedState sharedState;

    public AddProductsToShoppingCartSteps(SharedState sharedState) {
        this.sharedState = sharedState;
    }

    private class FakeFindProductsAdapter implements FindProductsPort {

        @Override
        public List<Product> findAllByUUIDs(List<UUID> uuids) {
            return sharedState.getProducts();
        }
    }

    private class FakeGetShoppingCartPort implements GetShoppingCartPort {
        @Override
        public ShoppingCart getShoppingCartByUserID(long userID) {
            return sharedState.getShoppingCart();
        }
    }

    private class FakeUpdateShoppingCartAdapter implements UpdateShoppingCartPort {
        @Override
        public void update(ShoppingCart cart) {
            sharedState.setShoppingCart(cart);
        }
    }

    long userId = 1234L;
    Exception exception;

    GetShoppingCartPort getShoppingCartPort = new FakeGetShoppingCartPort();
    FindProductsPort findProductsPort = new FakeFindProductsAdapter();
    UpdateShoppingCartPort updateShoppingCartPort = new FakeUpdateShoppingCartAdapter();
    AddProductsToShoppingCartUseCase addProductsToShoppingCartUseCase =
            new AddProductsToShoppingCartService(getShoppingCartPort, findProductsPort, updateShoppingCartPort);

    @When("I add them to my Shopping Cart")
    public void iAddThemToMyShoppingCart() {
        final Map<UUID, Integer> productsUUIDandQuantity = new HashMap<>();
        productsUUIDandQuantity.put(sharedState.getProductId(), 1);
        final AddProductsToShoppingCartCommand command = new AddProductsToShoppingCartCommand(userId, productsUUIDandQuantity);

        try {
            addProductsToShoppingCartUseCase.addProducts(command);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("I receive an error specifying the Item is out of stock")
    public void iReceiveAnErrorSpecifyingTheItemIsOutOfStock() {
        if(exception instanceof IllegalArgumentException) {
            assertEquals("Insufficient stock for product: " + sharedState.getProducts().get(0).getName(), exception.getMessage());
        } else {
            fail();
        }
    }

}
