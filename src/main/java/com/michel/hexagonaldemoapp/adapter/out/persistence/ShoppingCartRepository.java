package com.michel.hexagonaldemoapp.adapter.out.persistence;

import com.michel.hexagonaldemoapp.adapter.out.persistence.schema.ShoppingCartJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartJpaEntity, Long> {
}
