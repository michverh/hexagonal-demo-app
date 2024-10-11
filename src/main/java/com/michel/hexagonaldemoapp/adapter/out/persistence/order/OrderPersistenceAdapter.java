package com.michel.hexagonaldemoapp.adapter.out.persistence.order;

import com.michel.hexagonaldemoapp.application.port.out.order.FindOrderPort;
import com.michel.hexagonaldemoapp.application.port.out.order.FindOrdersPort;
import com.michel.hexagonaldemoapp.domain.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OrderPersistenceAdapter implements FindOrderPort, FindOrdersPort {
    @Override
    public Order findOrderById(UUID orderId) {
        return null;
    }

    @Override
    public List<Order> findOrdersByUserId(long userId) {
        return List.of();
    }
}
