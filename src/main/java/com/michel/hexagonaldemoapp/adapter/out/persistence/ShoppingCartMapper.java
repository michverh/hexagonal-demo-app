package com.michel.hexagonaldemoapp.adapter.out.persistence;

import com.michel.hexagonaldemoapp.adapter.out.persistence.schema.ProductJpaEntity;
import com.michel.hexagonaldemoapp.adapter.out.persistence.schema.ShoppingCartJpaEntity;
import com.michel.hexagonaldemoapp.domain.ShoppingCart;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ShoppingCartMapper {

    private final ProductRepository productRepository;

    public ShoppingCart map(ShoppingCartJpaEntity shoppingCartJpaEntity) {
        return new ShoppingCart(
                shoppingCartJpaEntity.getUserId(),
                shoppingCartJpaEntity.getTotal(),
                shoppingCartJpaEntity.getSubTotal(),
                shoppingCartJpaEntity.getShipping(),
                shoppingCartJpaEntity.getProductsAndQuantities().stream().map(
                        cartItem -> {
                            ProductJpaEntity productJpaEntity = productRepository.findById(cartItem.getProductId())
                                    .orElseThrow(() -> new RuntimeException("Product could not be found for UUID:"
                                            + cartItem.getProductId()));
                            return cartItem.toDomain(productJpaEntity.toDomain());
                        }
                ).toList(),
                Optional.ofNullable(shoppingCartJpaEntity.getDiscount().toDomain())
        );
    }
}
