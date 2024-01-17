package com.x.ecommerceback.service.interfaces;

import com.x.ecommerceback.exception.CartItemException;
import com.x.ecommerceback.model.Cart;
import com.x.ecommerceback.model.CartItem;
import com.x.ecommerceback.model.Product;

import java.util.List;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);
    public CartItem updateCartItem(String jwtToken, Long cartId, CartItem cartItem);
    public CartItem isCartItemExist(Cart cart, Product product, String size,String color, Long userId);
    public Cart removeCartItem(String jwtToken, Long cartItemId);
    public CartItem findCartItemById(Long cartItemId) throws CartItemException;

    public void increasingTheNumberOfItemsOnTheCard(String jwtToken, Long cartId);
    public void reduceTheNumberOfItemsInTheCart(String jwtToken, Long cartId);
    public Cart setTheNumberOfItemsInTheCart(String jwtToken, Long cartId, Integer quantity);
    public void removeCartItem(CartItem cartItem);
    public List<CartItem> getAllCartByUserIdAndCartId(Long userId, Long cartId);
}
