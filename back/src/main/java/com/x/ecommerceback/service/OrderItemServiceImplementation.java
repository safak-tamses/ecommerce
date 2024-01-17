package com.x.ecommerceback.service;

import com.x.ecommerceback.exception.CustomRuntimeException;
import com.x.ecommerceback.model.OrderItem;
import com.x.ecommerceback.repository.OrderItemRepository;
import com.x.ecommerceback.service.interfaces.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemServiceImplementation implements OrderItemService {
    private final OrderItemRepository orderItemRepository;

    @Override
    public void saveAllOrderItems(List<OrderItem> orderItems) {
        try {
            orderItemRepository.saveAll(orderItems);
        }catch (Exception e){
            throw new CustomRuntimeException("Order itemlar kaydedilirken bir hata meydana geldi");
        }

    }

    @Override
    public List<OrderItem> getAllOrderItemsByOrderId(Long orderId) {
        try {
            return orderItemRepository.getAllByOrderId(orderId);
        }catch (Exception e){
            throw new CustomRuntimeException("Sipariş id si ile sipariş itemları alınırken hata oldu.");
        }
    }

    @Override
    public void removeAllOrderItems(List<OrderItem> orderItems) {
        try {
            orderItemRepository.deleteAll(orderItems);
        }catch (Exception e){
            throw new CustomRuntimeException("Sipariş listesindeki itemler silinirken hata oluştu");
        }
    }
}
