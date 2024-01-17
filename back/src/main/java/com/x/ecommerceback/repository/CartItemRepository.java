package com.x.ecommerceback.repository;

import com.x.ecommerceback.model.Cart;
import com.x.ecommerceback.model.CartItem;
import com.x.ecommerceback.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT ci FROM CartItem  ci WHERE ci.cart=:cart AND ci.product=:product AND ci.size=:size AND ci.userId=:userId")
    public CartItem isCartItemExist(
            @Param("cart") Cart cart,
            @Param("product") Product product,
            @Param("size") String size,
            @Param("userId") Long userId
    );
    @Query("SELECT ci FROM CartItem ci WHERE ci.userId = :userId AND ci.cart.id = :cartId")
    public List<CartItem> getAllCartByUserId(@Param("userId") Long userId, @Param("cartId") Long cartId);

}
