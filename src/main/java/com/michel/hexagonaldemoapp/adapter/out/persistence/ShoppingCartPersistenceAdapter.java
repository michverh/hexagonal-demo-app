package com.michel.hexagonaldemoapp.adapter.out.persistence;

import com.michel.hexagonaldemoapp.adapter.out.persistence.schema.ShoppingCartJpaEntity;
import com.michel.hexagonaldemoapp.application.port.out.shoppingcart.GetShoppingCartPort;
import com.michel.hexagonaldemoapp.application.port.out.shoppingcart.UpdateShoppingCartPort;
import com.michel.hexagonaldemoapp.domain.ShoppingCart;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShoppingCartPersistenceAdapter implements GetShoppingCartPort, UpdateShoppingCartPort {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper mapper;

    @Override
    public ShoppingCart getShoppingCartByUserID(long userID) {
        return mapper.map(shoppingCartRepository.findById(userID)
                .orElseGet(() -> {
                    var shoppingCart = new ShoppingCartJpaEntity();
                    shoppingCart.setUserId(userID);
                    return shoppingCart;
                }));
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        var entity = ShoppingCartJpaEntity.fromDomain(shoppingCart);
        shoppingCartRepository.save(entity);
    }
}
