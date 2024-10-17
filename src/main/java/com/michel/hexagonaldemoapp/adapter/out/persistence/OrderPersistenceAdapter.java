package com.michel.hexagonaldemoapp.adapter.out.persistence;

import com.michel.hexagonaldemoapp.adapter.out.persistence.schema.OrderJpaEntity;
import com.michel.hexagonaldemoapp.application.port.out.order.FindOrderPort;
import com.michel.hexagonaldemoapp.application.port.out.order.FindOrdersPort;
import com.michel.hexagonaldemoapp.domain.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class OrderPersistenceAdapter implements FindOrderPort, FindOrdersPort {

    private final OrderRepository orderRepository;

    @Override
    public Order findOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found for: " + orderId))
                .toDomain();
    }

    @Override
    public List<Order> findOrdersByUserId(long userId) {
        return orderRepository.findAllByUserId(userId)
                .stream().map(OrderJpaEntity::toDomain)
                .toList();
    }
}
