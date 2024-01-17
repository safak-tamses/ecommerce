package com.x.ecommerceback.controller;

import com.x.ecommerceback.model.Order;
import com.x.ecommerceback.model.request.OrderRequest;
import com.x.ecommerceback.service.interfaces.OrderService;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest, @RequestHeader("Authorization") String jwtToken){
        return new ResponseEntity<>(orderService.createOrder(jwtToken,orderRequest),HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderByOrderId(@RequestHeader("Authorization") String jwtToken, @PathVariable Long orderId){
        return new ResponseEntity<>(orderService.findOrderById(jwtToken,orderId),HttpStatus.OK);
    }

    @GetMapping("/myOrders")
    public ResponseEntity<List<Order>> getAllOrderForUser(@RequestHeader("Authorization") String jwtToken){
        return new ResponseEntity<>(orderService.usersOrderHistory(jwtToken),HttpStatus.OK);
    }

    @DeleteMapping("/remove/{orderId}")
    public ResponseEntity<String> removeOrder(@RequestHeader("Authorization") String jwtToken, @PathVariable Long orderId){
        orderService.deleteOrder(jwtToken, orderId);
        return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/cancel/{orderId}")
    public ResponseEntity<Order> cancelOrder(@RequestHeader("Authorization") String jwtToken, @PathVariable Long orderId){
        return new ResponseEntity<>(orderService.cancelledOrder(jwtToken,orderId),HttpStatus.OK);
    }

}
