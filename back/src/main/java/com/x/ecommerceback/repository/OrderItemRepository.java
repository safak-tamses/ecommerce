package com.x.ecommerceback.repository;

import com.x.ecommerceback.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    List<OrderItem> getAllByOrderId(Long orderId);

}
