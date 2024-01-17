package com.x.ecommerceback.service.interfaces;

import com.x.ecommerceback.model.Cart;
import com.x.ecommerceback.model.User;
import com.x.ecommerceback.model.request.AddItemRequest;

public interface CartService {
    public void createCart(User user);
    public Cart addCartItem(String jwt, AddItemRequest request);
    public Cart findUserCart(String jwt);
    public void cartSave(Cart cart);
    public Cart findCartByUserId(Long id);
}
