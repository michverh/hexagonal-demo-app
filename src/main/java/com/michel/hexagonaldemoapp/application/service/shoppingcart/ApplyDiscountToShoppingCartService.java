package com.michel.hexagonaldemoapp.application.service.shoppingcart;

import com.michel.hexagonaldemoapp.application.port.in.shoppingcart.ApplyDiscountToShoppingCartUseCase;
import com.michel.hexagonaldemoapp.application.port.in.shoppingcart.command.ApplyDiscountCommand;
import com.michel.hexagonaldemoapp.application.port.out.discount.GetDiscountPort;
import com.michel.hexagonaldemoapp.application.port.out.shoppingcart.GetShoppingCartPort;
import com.michel.hexagonaldemoapp.application.port.out.shoppingcart.UpdateShoppingCartPort;
import com.michel.hexagonaldemoapp.domain.Discount;
import com.michel.hexagonaldemoapp.domain.ShoppingCart;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class ApplyDiscountToShoppingCartService implements ApplyDiscountToShoppingCartUseCase {

    private final GetShoppingCartPort getShoppingCartPort;
    private final UpdateShoppingCartPort updateShoppingCartPort;
    private final GetDiscountPort getDiscountPort;


    @Override
    public void applyDiscount(final ApplyDiscountCommand command) {
        final Optional<Discount> discount = getDiscountPort.getDiscount(command.discountCode());

        discount.ifPresentOrElse(
                Discount::verify,
                () -> { throw new RuntimeException("Discount code is unknown."); }
        );

        final ShoppingCart shoppingCart = getShoppingCartPort.getShoppingCartByUserID(command.userId());

        shoppingCart.applyDiscount(discount);

        updateShoppingCartPort.update(shoppingCart);
    }
}
