package com.michel.hexagonaldemoapp.application.port.in.shoppingcart;

import com.michel.hexagonaldemoapp.application.port.in.shoppingcart.command.AddProductsToShoppingCartCommand;

public interface AddProductsToShoppingCartUseCase {
    void addProducts(final AddProductsToShoppingCartCommand command);
}
