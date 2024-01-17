package com.x.ecommerceback.service;

import com.x.ecommerceback.exception.CustomRuntimeException;
import com.x.ecommerceback.exception.OrderException;
import com.x.ecommerceback.model.*;
import com.x.ecommerceback.model.request.OrderRequest;
import com.x.ecommerceback.repository.CartItemRepository;
import com.x.ecommerceback.repository.CartRepository;
import com.x.ecommerceback.repository.OrderItemRepository;
import com.x.ecommerceback.repository.OrderRepository;
import com.x.ecommerceback.service.interfaces.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImplementation implements OrderService {


    private final JwtService jwtService;

    private final OrderRepository orderRepository;

    private final CartService cartService;
    private final OrderItemService orderItemService;
    private final CartItemService cartItemService;
    private final AddressService addressService;


    //TODO product tablosunda ürünün siparişi sonrası stocktan azaltılması gerçekleştirilmedi bunu ayarla
    @Override
    public Order createOrder(String jwt, OrderRequest orderRequest) {
        try {
            User user = jwtService.findUserProfileByJwt(jwt);
            Cart cart = cartService.findCartByUserId(user.getId());
            List<CartItem> cartItems = cartItemService.getAllCartByUserIdAndCartId(user.getId(), cart.getId());


            Order order = new Order();
            order = orderSave(order);


            List<OrderItem> orderItems = new ArrayList<>();

            //Sepetteki ürünlerin order içeriğine aktarılması
            for (CartItem cartItem : cartItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(cartItem.getProduct());

                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(cartItem.getPrice());
                orderItem.setDiscountedPrice(cartItem.getDiscountedPrice());
                orderItem.setUserId(cartItem.getUserId());
                orderItem.setDeliveryDate(LocalDateTime.now().plusDays(7));
                orderItems.add(orderItem);
                orderItem.setOrder(order);

                cartItemService.removeCartItem(cartItem);
            }


            //Requeste uygun adresin oluşturulması
            Address address = new Address();
            address.setFirstName(orderRequest.firstName());
            address.setLastName(orderRequest.lastName());
            address.setStreetAddress(orderRequest.streetAddress());
            address.setCity(orderRequest.city());
            address.setState(orderRequest.state());
            address.setZipCode(orderRequest.zipCode());
            address.setUser(user);
            address.setMobile(orderRequest.mobile());

            addressService.addressSave(address);


            //Todo burada ödeme yöntemiyle ilişkili kısımlar düzenlenecek
            PaymentDetails paymentDetails = new PaymentDetails("test", "test", "test", "test", "test", "test", "test");
            //


            //Siparişin oluşturulması
            order.setUser(user);
            order.setOrderItems(orderItems);
            order.setOrderDate(LocalDateTime.now());
            order.setDeliveryDate(LocalDateTime.now().plusDays(7));

            order.setShippingAddress(address);

            order.setPaymentDetails(paymentDetails);
            order.setTotalPrice(cart.getTotalPrice());
            order.setDiscount(cart.getDiscount());
            order.setOrderStatus(OrderStatus.PENDING);
            order.setTotalItem(cart.getTotalItem());
            order.setCreateAt(LocalDateTime.now());

            //Sepet içindeki ürünlerin sıfırlanması
            cart.setTotalDiscountedPrice(0.0);
            cart.setDiscount(0.0);
            cart.getCartItems().clear();
            cart.setTotalItem(0);
            cart.setTotalPrice(0.0);


            cartService.cartSave(cart);

            orderItemService.saveAllOrderItems(orderItems);


            return order;

        } catch (Exception e) {
            throw new RuntimeException("Sebebi bilinmeyen hata", e);
        }

    }

    @Override
    public Order findOrderById(Long orderId) {
        try {
            return orderRepository.findById(orderId).orElseThrow();
        } catch (Exception e) {
            throw new CustomRuntimeException("Id si verilen sipariş bulunamadı.");
        }
    }

    @Override
    public Order findOrderById(String jwtToken, Long orderId) {
        try {
            User user = jwtService.findUserProfileByJwt(jwtToken);
            Order order = orderRepository.findById(orderId).orElseThrow();
            if (order.getUser().getId().equals(user.getId())) {
                return order;
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw new CustomRuntimeException("Id si verilen sipariş bulunamadı.");
        }
    }

    @Override
    public List<Order> usersOrderHistory(String jwtToken) {
        try {
            return orderRepository.getAllOrderByUserUserId(jwtService.findUserProfileByJwt(jwtToken).getId());
        } catch (Exception e) {
            throw new CustomRuntimeException("Sipariş tablosundan user id ye göre siparişler çekilirken hata oluştu.");
        }
    }

    @Override
    public Order placedOrder(Long orderId) {
        try {
            Order order = orderRepository.findById(orderId).orElseThrow();
            order.setOrderStatus(OrderStatus.PLACED);
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new CustomRuntimeException("Sipariş PLACED ibaresi setlenirken hata oluştu.");
        }
    }

    @Override
    public Order confirmedOrder(Long orderId) {
        try {
            Order order = orderRepository.findById(orderId).orElseThrow();
            order.setOrderStatus(OrderStatus.CONFIRMED);
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new CustomRuntimeException("Sipariş CONFIRMED ibaresi setlenirken hata oluştu.");
        }
    }

    @Override
    public Order shippedOrder(Long orderId) {
        try {
            Order order = orderRepository.findById(orderId).orElseThrow();
            order.setOrderStatus(OrderStatus.SHIPPED);
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new CustomRuntimeException("Sipariş CONFIRMED ibaresi setlenirken hata oluştu.");
        }
    }

    @Override
    public Order deliveredOrder(Long orderId) {
        try {
            Order order = orderRepository.findById(orderId).orElseThrow();
            order.setOrderStatus(OrderStatus.DELIVERED);
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new CustomRuntimeException("Sipariş CONFIRMED ibaresi setlenirken hata oluştu.");
        }
    }

    @Override
    public Order cancelledOrder(String jwtToken, Long orderId) {
        try {
            User user = jwtService.findUserProfileByJwt(jwtToken);
            Order order = orderRepository.findById(orderId).orElseThrow();
            if (user.getId().equals(order.getUser().getId())) {
                order.setOrderStatus(OrderStatus.CANCELLED);
                return orderRepository.save(order);
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw new CustomRuntimeException("Sipariş CONFIRMED ibaresi setlenirken hata oluştu.");
        }
    }

    //Adminin siparişi hatalı duruma getirmesi
    @Override
    public Order errorOrder(Long orderId) {
        try {
            Order order = orderRepository.findById(orderId).orElseThrow();
            order.setOrderStatus(OrderStatus.ERROR);
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new CustomRuntimeException("Sipariş ERROR ibaresi setlenirken hata oluştu.");
        }
    }

    //Admin için tüm siparişleri görüntüleme
    @Override
    public List<Order> getAllOrders() {
        try {
            return orderRepository.findAll();
        } catch (Exception e) {
            throw new CustomRuntimeException("Admin için tüm siparişler çekilirken bir hata oluştu.");
        }
    }

    @Override
    public void deleteOrder(String jwtToken, Long orderId) {
        try {
            User user = jwtService.findUserProfileByJwt(jwtToken);
            Order order = orderRepository.findById(orderId).orElseThrow();
            List<OrderItem> orderItems = orderItemService.getAllOrderItemsByOrderId(orderId);
            if (user.getId().equals(order.getUser().getId())) {
                order.getOrderItems().clear();
                orderItemService.removeAllOrderItems(orderItems);
                orderRepository.delete(order);
            }
        } catch (Exception e) {
            throw new CustomRuntimeException("Sipariş silinirken bişeyler oluyor ne oluyor");
        }
    }

    private Order orderSave(Order order) {
        try {
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new CustomRuntimeException("Sipariş kaydedilirken bir hata meydana geldi");
        }
    }



}
