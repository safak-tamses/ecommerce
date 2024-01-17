package com.x.ecommerceback.controller;

import com.x.ecommerceback.model.User;
import com.x.ecommerceback.model.response.UserResponse;
import com.x.ecommerceback.service.JwtService;
import com.x.ecommerceback.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final JwtService jwtService;
    @GetMapping("/profile")
    public ResponseEntity<UserResponse> profile(@RequestHeader("Authorization") String jwtToken){
        return new ResponseEntity<>(jwtService.getUserProfile(jwtToken), HttpStatus.OK);
    }
}
