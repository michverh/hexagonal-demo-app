package com.michel.hexagonaldemoapp.adapter.in.rest.order.model;

import com.michel.hexagonaldemoapp.domain.Address;
import com.michel.hexagonaldemoapp.domain.ShoppingCart;

public record PlaceOrderRequest(
        ShoppingCart shoppingCart,
        Address shippingAddress,
        Address billingAddress
) {
}
