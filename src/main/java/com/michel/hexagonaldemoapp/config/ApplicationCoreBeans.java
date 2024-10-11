package com.michel.hexagonaldemoapp.config;

import com.michel.hexagonaldemoapp.application.port.in.order.PlaceOrderUseCase;
import com.michel.hexagonaldemoapp.application.port.in.order.ViewOrderHistoryUseCase;
import com.michel.hexagonaldemoapp.application.port.in.order.ViewOrderUseCase;
import com.michel.hexagonaldemoapp.application.port.in.product.FindProductsUseCase;
import com.michel.hexagonaldemoapp.application.port.in.shoppingcart.AddProductsToShoppingCartUseCase;
import com.michel.hexagonaldemoapp.application.port.in.shoppingcart.ApplyDiscountToShoppingCartUseCase;
import com.michel.hexagonaldemoapp.application.port.in.shoppingcart.GetShoppingCartUseCase;
import com.michel.hexagonaldemoapp.application.port.out.discount.GetDiscountPort;
import com.michel.hexagonaldemoapp.application.port.out.order.FindOrderPort;
import com.michel.hexagonaldemoapp.application.port.out.order.FindOrdersPort;
import com.michel.hexagonaldemoapp.application.port.out.order.PlaceOrderPort;
import com.michel.hexagonaldemoapp.application.port.out.product.FindProductPort;
import com.michel.hexagonaldemoapp.application.port.out.product.FindProductsPort;
import com.michel.hexagonaldemoapp.application.port.out.product.QueryProductPort;
import com.michel.hexagonaldemoapp.application.port.out.shoppingcart.GetShoppingCartPort;
import com.michel.hexagonaldemoapp.application.port.out.shoppingcart.UpdateShoppingCartPort;
import com.michel.hexagonaldemoapp.application.service.order.PlaceOrderService;
import com.michel.hexagonaldemoapp.application.service.order.ViewOrderHistoryService;
import com.michel.hexagonaldemoapp.application.service.order.ViewOrderService;
import com.michel.hexagonaldemoapp.application.service.product.FindProductsService;
import com.michel.hexagonaldemoapp.application.service.shoppingcart.AddProductsToShoppingCartService;
import com.michel.hexagonaldemoapp.application.service.shoppingcart.ApplyDiscountToShoppingCartService;
import com.michel.hexagonaldemoapp.application.service.shoppingcart.GetShoppingCartService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationCoreBeans {

    @Bean
    public GetShoppingCartUseCase getShoppingCartUseCase(final GetShoppingCartPort getShoppingCartPort) {
        return new GetShoppingCartService(getShoppingCartPort);
    }

    @Bean
    public AddProductsToShoppingCartUseCase getShoppingCartUseCase(
            final GetShoppingCartPort getShoppingCartPort,
            final FindProductsPort findProductsPort,
            final UpdateShoppingCartPort updateShoppingCartPort
    ) {
        return new AddProductsToShoppingCartService(getShoppingCartPort, findProductsPort, updateShoppingCartPort);
    }

    @Bean
    public ApplyDiscountToShoppingCartUseCase applyDiscountToShoppingCartUseCase(
            final GetShoppingCartPort getShoppingCartPort,
            final UpdateShoppingCartPort updateShoppingCartPort,
            final GetDiscountPort getDiscountPort
    ) {
        return new ApplyDiscountToShoppingCartService(getShoppingCartPort, updateShoppingCartPort, getDiscountPort);
    }

    @Bean
    public PlaceOrderUseCase placeOrderUseCase(
            final FindProductPort findProductPort, final PlaceOrderPort placeOrderPort
    ) {
        return new PlaceOrderService(findProductPort, placeOrderPort);
    }

    @Bean
    public ViewOrderUseCase viewOrderUseCase(final FindOrderPort findOrderPort) {
        return new ViewOrderService(findOrderPort);
    }

    @Bean
    public ViewOrderHistoryUseCase viewOrderHistoryUseCase(final FindOrdersPort findOrdersPort) {
        return new ViewOrderHistoryService(findOrdersPort);
    }

    @Bean
    public FindProductsUseCase findProductsUseCase(final QueryProductPort queryProductPort) {
        return new FindProductsService(queryProductPort);
    }

}
