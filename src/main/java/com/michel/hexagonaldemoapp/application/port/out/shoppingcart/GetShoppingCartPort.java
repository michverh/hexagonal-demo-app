package com.michel.hexagonaldemoapp.application.port.out.shoppingcart;

import com.michel.hexagonaldemoapp.domain.ShoppingCart;

public interface GetShoppingCartPort {
    ShoppingCart getShoppingCartByUserID(long userID);
}
