package com.x.ecommerceback.service;

import com.x.ecommerceback.exception.CartItemException;
import com.x.ecommerceback.exception.CustomRuntimeException;
import com.x.ecommerceback.exception.UserException;
import com.x.ecommerceback.exception.UserNotFoundException;
import com.x.ecommerceback.model.Cart;
import com.x.ecommerceback.model.CartItem;
import com.x.ecommerceback.model.Product;
import com.x.ecommerceback.model.User;
import com.x.ecommerceback.repository.CartItemRepository;
import com.x.ecommerceback.repository.CartRepository;
import com.x.ecommerceback.repository.UserRepository;
import com.x.ecommerceback.service.interfaces.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartItemServiceImplementation implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CartRepository cartRepository;

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());

        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(String jwtToken, Long cartId, CartItem cartItem){
        try {
            CartItem item = findCartItemById(cartId);

            User user = jwtService.findUserProfileByJwt(jwtToken);

            if (item.getUserId().equals(user.getId())){

                item.setQuantity(cartItem.getQuantity());
                item.setPrice(item.getQuantity()*item.getProduct().getPrice());
                item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());

            }
            return cartItemRepository.save(item);
        }catch (UserNotFoundException e){
            throw new UserNotFoundException("User not found");
        }
        catch (Exception e){
            throw new UserException("Unknown");
        }

    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size,String color, Long userId) {
        return cartItemRepository.isCartItemExist(cart,product,size,userId);
    }


    @Override
    public Cart removeCartItem(String jwtToken, Long cartItemId){
        try {
            User user = jwtService.findUserProfileByJwt(jwtToken);
            Cart cart = cartRepository.findByUserId(user.getId());
            CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow();
            if(cart.getId().equals(cartItem.getCart().getId())){
                cart.setTotalItem(cart.getTotalItem() - cartItem.getQuantity());
                cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getPrice()));
                cart.setTotalDiscountedPrice(cart.getTotalDiscountedPrice() - (cartItem.getDiscountedPrice()));
                cart.setDiscount(cart.getTotalPrice() - cart.getTotalDiscountedPrice());
                cart.getCartItems().remove(cartItem);

                cartItemRepository.delete(cartItem);
                cartRepository.save(cart);
                return cart;
            }else{
                throw new RuntimeException();
            }


        }catch (Exception e){
            throw new RuntimeException("");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);
        if (opt.isPresent()){
            return opt.get();
        }
        throw new CartItemException("CartItem not found with id : " + cartItemId);
    }

    @Override
    public void increasingTheNumberOfItemsOnTheCard(String jwtToken, Long cartId) {
        try {
            User user = jwtService.findUserProfileByJwt(jwtToken);

            CartItem cartItem = findCartItemById(cartId);
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
            cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());
            cartItemRepository.save(cartItem);

            Cart cart = cartRepository.findByUserId(user.getId());
            cart.setTotalItem(cart.getTotalItem() + 1);
            cart.setTotalPrice(cart.getTotalPrice() + cartItem.getProduct().getPrice());
            cart.setTotalDiscountedPrice(cart.getTotalDiscountedPrice() + cartItem.getProduct().getDiscountedPrice());
            cart.setDiscount(cart.getTotalPrice() - cart.getTotalDiscountedPrice());
            cartRepository.save(cart);


        }catch (Exception e){
            throw new RuntimeException("");
        }
    }

    @Override
    public void reduceTheNumberOfItemsInTheCart(String jwtToken, Long cartId) {
        try {
            User user = jwtService.findUserProfileByJwt(jwtToken);

            CartItem cartItem = findCartItemById(cartId);
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
            cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());
            cartItemRepository.save(cartItem);

            Cart cart = cartRepository.findByUserId(user.getId());
            cart.setTotalItem(cart.getTotalItem() - 1);
            cart.setTotalPrice(cart.getTotalPrice() - cartItem.getProduct().getPrice());
            cart.setTotalDiscountedPrice(cart.getTotalDiscountedPrice() - cartItem.getProduct().getDiscountedPrice());
            cart.setDiscount(cart.getTotalPrice() - cart.getTotalDiscountedPrice());
            cartRepository.save(cart);

        }catch (Exception e){
            throw new RuntimeException("");
        }
    }

    @Override
    public Cart setTheNumberOfItemsInTheCart(String jwtToken, Long cartItemId, Integer quantity) {
        try {
            User user = jwtService.findUserProfileByJwt(jwtToken);

            CartItem cartItem = findCartItemById(cartItemId);
            Cart cart = cartRepository.findByUserId(user.getId());
            System.out.println(cartItem.getCart().getId() + " " + cart.getId());
            if (cartItem.getCart().getId().equals(cart.getId())){
                cart.setTotalItem(cart.getTotalItem() - cartItem.getQuantity() + quantity);

                cart.setTotalPrice(cart.getTotalPrice() - cartItem.getPrice() + (cartItem.getProduct().getPrice() * quantity));
                cart.setTotalDiscountedPrice(cart.getTotalDiscountedPrice() - cartItem.getDiscountedPrice() + (cartItem.getProduct().getDiscountedPrice() * quantity));
                cart.setDiscount(cart.getTotalPrice() - cart.getTotalDiscountedPrice());


                cartItem.setQuantity(quantity);
                cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
                cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());


                cartItemRepository.save(cartItem);
                cartRepository.save(cart);
                return cart;
            }
            else {
                throw new RuntimeException();
            }



        }catch (Exception e){
            throw new CustomRuntimeException("Sepetteki item kullanıcıya ait değil veya başka bişey");
        }
    }

    @Override
    public void removeCartItem(CartItem cartItem) {
        try {
            cartItemRepository.delete(cartItem);
        }catch (Exception e){
            throw new CustomRuntimeException("Sepetteki ürün silinirken bir hata meydana geldi");
        }

    }

    @Override
    public List<CartItem> getAllCartByUserIdAndCartId(Long userId, Long cartId) {
        try {
            return cartItemRepository.getAllCartByUserId(userId,cartId);
        }catch (Exception e){
            throw new CustomRuntimeException("Kullanıcı id sine ve sepet idsine göre sepetteki itemları getiren fonksiyonda bir hata oluştu");
        }
    }


}
