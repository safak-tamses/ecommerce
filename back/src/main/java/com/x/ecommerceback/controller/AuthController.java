package com.x.ecommerceback.controller;

import com.x.ecommerceback.model.Product;
import com.x.ecommerceback.model.request.FindAllProductRequest;
import com.x.ecommerceback.model.request.UserCreateRequest;
import com.x.ecommerceback.model.request.UserLoginRequest;
import com.x.ecommerceback.model.response.AuthResponse;
import com.x.ecommerceback.service.UserService;
import com.x.ecommerceback.service.interfaces.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final ProductService productService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody UserCreateRequest request){
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody UserLoginRequest request){
        return new ResponseEntity<>(userService.loginUser(request),HttpStatus.OK);
    }


}
