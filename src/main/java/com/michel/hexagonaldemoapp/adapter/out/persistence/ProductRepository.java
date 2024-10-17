package com.michel.hexagonaldemoapp.adapter.out.persistence;

import com.michel.hexagonaldemoapp.adapter.out.persistence.schema.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductJpaEntity, UUID> {

    @Query(value =
        """
        SELECT p FROM ProductJpaEntity p
        WHERE p.name = ?1 
        AND p.price = ?2
        AND p.category = ?3
        """
    )
    List<ProductJpaEntity> findAllByQuery(String name, BigDecimal price, String category);
}
