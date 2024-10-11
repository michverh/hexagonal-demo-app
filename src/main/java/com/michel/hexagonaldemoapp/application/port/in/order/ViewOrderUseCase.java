package com.michel.hexagonaldemoapp.application.port.in.order;

import com.michel.hexagonaldemoapp.domain.Order;

import java.util.UUID;

public interface ViewOrderUseCase {
    Order findOrder(UUID orderId);
}
