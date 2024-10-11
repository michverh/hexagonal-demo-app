package com.michel.hexagonaldemoapp.application.service.order;

import com.michel.hexagonaldemoapp.application.port.in.order.ViewOrderHistoryUseCase;
import com.michel.hexagonaldemoapp.application.port.out.order.FindOrdersPort;
import com.michel.hexagonaldemoapp.domain.Order;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ViewOrderHistoryService implements ViewOrderHistoryUseCase {

    private final FindOrdersPort findOrdersPort;


    @Override
    public List<Order> viewOrderHistory(final long userId) {
        return findOrdersPort.findOrdersByUserId(userId);
    }
}
