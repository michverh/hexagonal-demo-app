package com.michel.hexagonaldemoapp.application.port.in.order.command;

import com.michel.hexagonaldemoapp.domain.Address;
import com.michel.hexagonaldemoapp.domain.ShoppingCart;

public record PlaceOrderCommand(
        Address shippingAddress,
        Address billingAddress,
        ShoppingCart shoppingCart
) {}

