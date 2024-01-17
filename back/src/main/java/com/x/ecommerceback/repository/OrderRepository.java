package com.x.ecommerceback.repository;

import com.x.ecommerceback.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.user.id =:userId")
    List<Order> getAllOrderByUserUserId(@Param(value = "userId") Long userId);
}
