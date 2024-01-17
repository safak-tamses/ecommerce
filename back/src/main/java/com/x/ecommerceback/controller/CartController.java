package com.x.ecommerceback.controller;

import com.x.ecommerceback.model.Cart;
import com.x.ecommerceback.model.CartItem;
import com.x.ecommerceback.model.request.AddItemRequest;
import com.x.ecommerceback.service.interfaces.CartItemService;
import com.x.ecommerceback.service.interfaces.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cart/")
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<Cart> addCartItemToUserCart(
            @RequestHeader("Authorization") String jwtToken,
            @RequestBody AddItemRequest addItemRequest
            ){
        return new ResponseEntity<>(cartService.addCartItem(jwtToken,addItemRequest), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Cart> getCart(@RequestHeader("Authorization") String jwtToken){
        return new ResponseEntity<>(cartService.findUserCart(jwtToken),HttpStatus.OK);
    }
    @GetMapping("inc")
    public ResponseEntity increaseCartItem(@RequestHeader("Authorization") String jwtToken, @RequestParam(value = "cartId") Long cartId){
        cartItemService.increasingTheNumberOfItemsOnTheCard(jwtToken, cartId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("dec")
    public ResponseEntity decreaseCartItem(@RequestHeader("Authorization") String jwtToken, @RequestParam(value = "cartId") Long cartId){
        cartItemService.reduceTheNumberOfItemsInTheCart(jwtToken, cartId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("set")
    public ResponseEntity<Cart> setCartItemCount(@RequestHeader("Authorization") String jwtToken, @RequestParam(value = "cartItemId") Long cartItemId, @RequestParam(value = "quantity") Integer quantity){
        return new ResponseEntity<>(cartItemService.setTheNumberOfItemsInTheCart(jwtToken, cartItemId, quantity),HttpStatus.OK);
    }
    @DeleteMapping("{cartItemId}")
    public ResponseEntity<Cart> removeCartItem(@RequestHeader("Authorization") String jwtToken, @PathVariable Long cartItemId){
        return new ResponseEntity<>(cartItemService.removeCartItem(jwtToken,cartItemId),HttpStatus.OK);
    }


}
