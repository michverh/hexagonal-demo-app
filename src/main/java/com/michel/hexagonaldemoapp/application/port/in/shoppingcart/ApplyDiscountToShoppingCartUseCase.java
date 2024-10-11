package com.michel.hexagonaldemoapp.application.port.in.shoppingcart;

import com.michel.hexagonaldemoapp.application.port.in.shoppingcart.command.ApplyDiscountCommand;

public interface ApplyDiscountToShoppingCartUseCase {
    void applyDiscount(ApplyDiscountCommand command);
}
