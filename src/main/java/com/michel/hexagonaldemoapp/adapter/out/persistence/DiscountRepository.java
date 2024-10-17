package com.michel.hexagonaldemoapp.adapter.out.persistence;

import com.michel.hexagonaldemoapp.adapter.out.persistence.schema.DiscountJpaEntity;
import com.michel.hexagonaldemoapp.domain.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<DiscountJpaEntity, Long> {

    Optional<Discount> findByCode(String discountCode);

}
