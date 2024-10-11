package com.michel.hexagonaldemoapp.application.service.shoppingcart;

import com.michel.hexagonaldemoapp.application.port.in.shoppingcart.GetShoppingCartUseCase;
import com.michel.hexagonaldemoapp.application.port.out.shoppingcart.GetShoppingCartPort;
import com.michel.hexagonaldemoapp.domain.ShoppingCart;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetShoppingCartService implements GetShoppingCartUseCase {

    private final GetShoppingCartPort getShoppingCartPort;

    @Override
    public ShoppingCart getShoppingCart(final long userID) {
        return getShoppingCartPort.getShoppingCartByUserID(userID);
    }
}
