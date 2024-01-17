package com.x.ecommerceback.service;

import com.x.ecommerceback.exception.CustomRuntimeException;
import com.x.ecommerceback.model.*;
import com.x.ecommerceback.model.request.AddItemRequest;
import com.x.ecommerceback.repository.CartItemRepository;
import com.x.ecommerceback.repository.CartRepository;
import com.x.ecommerceback.service.interfaces.CartItemService;
import com.x.ecommerceback.service.interfaces.CartService;
import com.x.ecommerceback.service.interfaces.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartServiceImplementation implements CartService {
    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final ProductService productService;
    private final JwtService jwtService;
    private final CartItemRepository cartItemRepository;
    @Override
    public void createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
    }

    @Override
    public Cart addCartItem(String jwt, AddItemRequest request) {
        try {
            Long userId = jwtService.findUserProfileByJwt(jwt).getId();
            Cart cart = cartRepository.findByUserId(userId);
            Product product = productService.findProductById(request.productId());

            if (cart.getCartItems().isEmpty()){
                CartItem cartItem = new CartItem();
                cartItem.setUserId(userId);
                cartItem.setQuantity(1);
                cartItem.setColor(stringToColor(request.color()));
                cartItem.setSize(stringToSize(request.size()));
                cartItem.setProduct(product);
                cartItem.setPrice(product.getPrice());
                cartItem.setDiscountedPrice(product.getDiscountedPrice());
                cartItem.setCart(cart);

                cart.getCartItems().add(cartItem);
                cart.setTotalItem(1);
                cart.setTotalPrice(product.getPrice());
                cart.setTotalDiscountedPrice(product.getDiscountedPrice());
                cart.setDiscount(product.getPrice() - product.getDiscountedPrice());
                cartItemRepository.save(cartItem);
            }else {
                boolean stepper = false;
                int count = 0;
                for (CartItem cartItem: cart.getCartItems()){
                    count ++;
                    if (cartItem.getColor().equals(stringToColor(request.color())) && cartItem.getSize().equals(stringToSize(request.size()))){
                        //cart iteme ürün eklenecek
                        cartItem.setQuantity(cartItem.getQuantity()+1);
                        cartItemRepository.save(cartItem);
                        cart.setTotalItem(cart.getTotalItem()+1);
                        cart.setTotalPrice(cart.getTotalPrice()+cartItem.getPrice());
                        cart.setTotalDiscountedPrice(cart.getTotalDiscountedPrice()+cartItem.getDiscountedPrice());
                        cart.setDiscount(cart.getDiscount()+(cartItem.getPrice()-cartItem.getDiscountedPrice()));
                        stepper = true;
                    }
                    if (!stepper && count == cart.getCartItems().size()){
                        //yeni cart item eklenip cart üzerinde değişiklikler yapılacak
                        CartItem cartItem1 = new CartItem();
                        cartItem1.setQuantity(1);
                        cartItem1.setSize(stringToSize(request.size()));
                        cartItem1.setColor(stringToColor(request.color()));
                        cartItem1.setProduct(product);
                        cartItem1.setPrice(product.getPrice());
                        cartItem1.setDiscountedPrice(product.getDiscountedPrice());
                        cartItem1.setUserId(userId);
                        cartItem1.setCart(cart);
                        cartItemRepository.save(cartItem1);

                        cart.getCartItems().add(cartItem1);
                        cart.setTotalItem(cart.getTotalItem()+1);
                        cart.setTotalPrice(cart.getTotalPrice()+cartItem1.getPrice());
                        cart.setTotalDiscountedPrice(cart.getTotalDiscountedPrice()+cartItem1.getDiscountedPrice());
                        cart.setDiscount(cart.getDiscount()+(cartItem1.getPrice()-cartItem1.getDiscountedPrice()));
                    }
                }

            }
            cartRepository.save(cart);
            return cart;
        } catch (Exception e){
            throw new CustomRuntimeException("Ürün sepete eklenirken hata oldu");
        }

    }

    @Override
    public Cart findUserCart(String jwt) {
        try {
            return cartRepository.findByUserId(jwtService.findUserProfileByJwt(jwt).getId());
        }catch (Exception e){
            throw new RuntimeException("Sepet bulunamadı");
        }
    }

    @Override
    public void cartSave(Cart cart) {
        try {
            cartRepository.save(cart);
        }catch (Exception e){
            throw new CustomRuntimeException("Sepetin kaydedilmesinde hata meydana geldi");
        }
    }

    @Override
    public Cart findCartByUserId(Long userId) {
        try {
            return cartRepository.findByUserId(userId);
        }catch (Exception e){
            throw new CustomRuntimeException("User id ile sepeti bulmaya çalışırken hata meydana geldi");
        }
    }

    private Color stringToColor(String value){
        for (Color c: Color.values()){
            if (c.name().equalsIgnoreCase(value)){
                return c;
            }
        }
        throw new IllegalArgumentException("Geçersiz renk: " + value);
    }
    private Size stringToSize(String value){
        for (Size s: Size.values()){
            if (s.name().equalsIgnoreCase(value)){
                return s;
            }
        }
        throw new IllegalArgumentException("Geçersiz beden: " + value);
    }
}
