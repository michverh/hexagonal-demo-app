package com.michel.hexagonaldemoapp.application.port.out.order;

import com.michel.hexagonaldemoapp.domain.Order;

import java.util.List;

public interface FindOrdersPort {
    List<Order> findOrdersByUserId(final long userId);
}
