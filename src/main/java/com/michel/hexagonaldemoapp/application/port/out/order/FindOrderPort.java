package com.michel.hexagonaldemoapp.application.port.out.order;

import com.michel.hexagonaldemoapp.domain.Order;

import java.util.UUID;

public interface FindOrderPort {
    Order findOrderById(UUID orderId);
}
