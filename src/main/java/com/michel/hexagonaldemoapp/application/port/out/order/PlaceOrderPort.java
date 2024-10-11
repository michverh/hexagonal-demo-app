package com.michel.hexagonaldemoapp.application.port.out.order;

import com.michel.hexagonaldemoapp.domain.Order;

public interface PlaceOrderPort {
    void placeOrder(Order order);
}
