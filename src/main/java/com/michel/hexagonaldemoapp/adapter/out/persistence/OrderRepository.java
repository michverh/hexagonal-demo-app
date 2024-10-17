package com.michel.hexagonaldemoapp.adapter.out.persistence;

import com.michel.hexagonaldemoapp.adapter.out.persistence.schema.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderJpaEntity, UUID> {
    List<OrderJpaEntity> findAllByUserId(long userId);
}
