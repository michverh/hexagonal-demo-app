package com.michel.hexagonaldemoapp.application.service.order;

import com.michel.hexagonaldemoapp.application.port.in.order.ViewOrderUseCase;
import com.michel.hexagonaldemoapp.application.port.out.order.FindOrderPort;
import com.michel.hexagonaldemoapp.domain.Order;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class ViewOrderService implements ViewOrderUseCase {

    private final FindOrderPort findOrderPort;

    @Override
    public Order findOrder(UUID orderId) {
        return findOrderPort.findOrderById(orderId);
    }
}
