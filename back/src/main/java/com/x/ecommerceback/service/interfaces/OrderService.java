package com.x.ecommerceback.service.interfaces;

import com.x.ecommerceback.exception.OrderException;
import com.x.ecommerceback.model.Address;
import com.x.ecommerceback.model.CartItem;
import com.x.ecommerceback.model.Order;
import com.x.ecommerceback.model.User;
import com.x.ecommerceback.model.request.OrderRequest;

import java.util.List;

public interface OrderService {
    public Order createOrder(String jwt, OrderRequest orderRequest);
    public Order findOrderById(Long orderId);
    public Order findOrderById(String jwtToken, Long orderId);
    public List<Order> usersOrderHistory(String jwtToken);
    public Order placedOrder(Long orderId);
    public Order confirmedOrder(Long orderId);
    public Order shippedOrder(Long orderId);
    public Order deliveredOrder(Long orderId);
    public Order cancelledOrder(String jwtToken, Long orderId);
    public Order errorOrder(Long orderId);
    public List<Order> getAllOrders();
    public void deleteOrder(String jwtToken,Long orderId);
}
