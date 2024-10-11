package com.michel.hexagonaldemoapp.cucumber.usecases.shoppingcart;

import com.michel.hexagonaldemoapp.application.port.in.shoppingcart.ApplyDiscountToShoppingCartUseCase;
import com.michel.hexagonaldemoapp.application.port.in.shoppingcart.command.ApplyDiscountCommand;
import com.michel.hexagonaldemoapp.application.port.out.discount.GetDiscountPort;
import com.michel.hexagonaldemoapp.application.port.out.shoppingcart.GetShoppingCartPort;
import com.michel.hexagonaldemoapp.application.port.out.shoppingcart.UpdateShoppingCartPort;
import com.michel.hexagonaldemoapp.application.service.shoppingcart.ApplyDiscountToShoppingCartService;
import com.michel.hexagonaldemoapp.cucumber.SharedState;
import com.michel.hexagonaldemoapp.domain.Discount;
import com.michel.hexagonaldemoapp.domain.ShoppingCart;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ApplyDiscountCodeToShoppingCartSteps {

    private final SharedState sharedState;

    public ApplyDiscountCodeToShoppingCartSteps(SharedState sharedState) {
        this.sharedState = sharedState;
    }

    private class FakeGetShoppingCartAdapter implements GetShoppingCartPort {
        @Override
        public ShoppingCart getShoppingCartByUserID(long userID) {
            return sharedState.getShoppingCart();
        }
    }

    private class FakeUpdateShoppingCartAdapter implements UpdateShoppingCartPort {
        @Override
        public void update(ShoppingCart shoppingCart) {
            updatedShoppingCart = shoppingCart;
        }
    }

    private static class FakeGetDiscountAdapter implements GetDiscountPort {
        @Override
        public Optional<Discount> getDiscount(String discountCode) {
            return switch (discountCode) {
                case "10OFF24" -> Optional.of(new Discount(BigDecimal.TEN, discountCode, OffsetDateTime.now().plusDays(10)));
                case "OldCode" -> Optional.of(new Discount(BigDecimal.TEN, discountCode, OffsetDateTime.now().minusDays(10)));
                default -> Optional.empty();
            };
        }
    }

    ShoppingCart updatedShoppingCart;

    GetShoppingCartPort getShoppingCartPort = new FakeGetShoppingCartAdapter();
    UpdateShoppingCartPort updateShoppingCartPort = new FakeUpdateShoppingCartAdapter();
    GetDiscountPort getDiscountPort = new FakeGetDiscountAdapter();
    ApplyDiscountToShoppingCartUseCase applyDiscountToShoppingCartUseCase = new ApplyDiscountToShoppingCartService(
            getShoppingCartPort, updateShoppingCartPort, getDiscountPort
    );

    @And("I prepare a Shopping Cart")
    public void iPrepareAShoppingCart() {
        sharedState.getProducts().forEach(product ->
                sharedState.getShoppingCart().addItem(product, 1)
        );
    }

    @When("^I apply the following discount code \"([^\"]*)\" to my Shopping Cart$")
    public void i_apply_the_following_discount_code_to_my_Shopping_cart(String discountCode) {
        final ApplyDiscountCommand command = new ApplyDiscountCommand(1L, discountCode);

        try {
            applyDiscountToShoppingCartUseCase.applyDiscount(command);
//            fail("Needs to be uncommented and the usecase implemented!");
        } catch(RuntimeException e) {
            sharedState.setException(e);
        } catch (Exception e) {
            fail("An unexpected exception occurred");
        }
    }

    @Then("The Shopping Cart is updated to")
    public void theShoppingCartIsUpdatedTo(DataTable dataTable) {
        final Map<String, String> row = dataTable.asMaps().get(0);

        assertTrue(updatedShoppingCart.getDiscount().isPresent());
        assertEquals(row.get("total"), updatedShoppingCart.getTotal().toString());
        assertEquals(row.get("subTotal"), updatedShoppingCart.getSubTotal().toString());
        assertEquals(row.get("shipping"), updatedShoppingCart.getShipping().toString());
        assertEquals(row.get("discount"), updatedShoppingCart.getDiscount().get().percentage().toString());
    }

    @Then("^I receive an error specifying the discount code is unknown$")
    public void i_receive_an_error_specifying_the_discount_code_is_unknown() {
        assertEquals("Discount code is unknown.", sharedState.getException().getMessage());
    }

    @Then("^I receive an error specifying the discount code is expired$")
    public void i_receive_an_error_specifying_the_discount_code_is_expired() {
        assertEquals("Discount code is expired.", sharedState.getException().getMessage());
    }

}
