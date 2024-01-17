package com.x.ecommerceback.repository;

import com.x.ecommerceback.model.Product;
import com.x.ecommerceback.model.Properties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertiesRepository extends JpaRepository<Properties, Long> {
    @Query("SELECT p FROM Properties p WHERE p.product.id = :productId AND p.color = :color")
    List<Properties> findByProductIdAndColor(@Param("productId") Long productId, @Param("color") String color);

    @Query("SELECT p FROM Properties p WHERE p.product.id = :productId AND p.color = :color AND p.quantity = :quantity AND p.size = :size")
    List<Properties> findByProductIdColorQuantityAndSize(
            @Param("productId") Long productId,
            @Param("color") String color,
            @Param("quantity") int quantity,
            @Param("size") String size
    );
}
