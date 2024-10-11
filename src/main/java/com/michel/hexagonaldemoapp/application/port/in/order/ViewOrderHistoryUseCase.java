package com.michel.hexagonaldemoapp.application.port.in.order;

import com.michel.hexagonaldemoapp.domain.Order;

import java.util.List;

public interface ViewOrderHistoryUseCase {
    List<Order> viewOrderHistory(final long userId);
}
