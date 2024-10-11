package com.michel.hexagonaldemoapp.application.port.in.shoppingcart;

import com.michel.hexagonaldemoapp.domain.ShoppingCart;

public interface GetShoppingCartUseCase {
    ShoppingCart getShoppingCart(final long userID);
}
