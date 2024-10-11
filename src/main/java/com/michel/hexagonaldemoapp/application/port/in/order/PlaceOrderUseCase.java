package com.michel.hexagonaldemoapp.application.port.in.order;

import com.michel.hexagonaldemoapp.application.port.in.order.command.PlaceOrderCommand;

public interface PlaceOrderUseCase {

    void placeOrder(final PlaceOrderCommand command);
}
