package com.x.ecommerceback.repository;

import com.x.ecommerceback.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(
            "SELECT p FROM Product p " +
                    "WHERE (:minPrice IS NULL OR p.price >= :minPrice) " +
                    "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
                    "AND (:minDiscount IS NULL OR p.discountedPrice >= :minDiscount) " +
                    "ORDER BY " +
                        "CASE WHEN :sort = 'price_low' THEN p.discountedPrice ELSE NULL END ASC, " +
                        "CASE WHEN :sort = 'price_high' THEN p.discountedPrice ELSE NULL END DESC"
    )
    List<Product> filterProduct(
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("minDiscount") Integer minDiscount,
            @Param("sort") String sort
    );



}
