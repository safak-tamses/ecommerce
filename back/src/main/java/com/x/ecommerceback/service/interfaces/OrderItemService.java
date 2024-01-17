package com.x.ecommerceback.service.interfaces;

import com.x.ecommerceback.model.OrderItem;

import java.util.List;

public interface OrderItemService {
    public void saveAllOrderItems(List<OrderItem> orderItems);
    public List<OrderItem> getAllOrderItemsByOrderId(Long orderId);
    public void removeAllOrderItems(List<OrderItem> orderItems);
}
