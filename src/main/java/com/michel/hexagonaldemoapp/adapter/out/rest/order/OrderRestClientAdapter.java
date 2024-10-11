package com.michel.hexagonaldemoapp.adapter.out.rest.order;

import com.michel.hexagonaldemoapp.application.port.out.order.PlaceOrderPort;
import com.michel.hexagonaldemoapp.domain.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderRestClientAdapter implements PlaceOrderPort {

    @Override
    public void placeOrder(Order order) {

    }
}
