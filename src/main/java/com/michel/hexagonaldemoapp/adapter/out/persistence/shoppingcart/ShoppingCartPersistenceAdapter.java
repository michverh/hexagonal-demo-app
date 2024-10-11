package com.michel.hexagonaldemoapp.adapter.out.persistence.shoppingcart;

import com.michel.hexagonaldemoapp.application.port.out.shoppingcart.GetShoppingCartPort;
import com.michel.hexagonaldemoapp.application.port.out.shoppingcart.UpdateShoppingCartPort;
import com.michel.hexagonaldemoapp.domain.ShoppingCart;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartPersistenceAdapter implements GetShoppingCartPort, UpdateShoppingCartPort {

    @Override
    public ShoppingCart getShoppingCartByUserID(long userID) {
        return null;
    }

    @Override
    public void update(ShoppingCart shoppingCart) {

    }
}
