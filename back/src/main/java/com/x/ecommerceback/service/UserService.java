package com.x.ecommerceback.service;

import com.x.ecommerceback.config.JwtProvider;
import com.x.ecommerceback.exception.*;
import com.x.ecommerceback.model.Role;
import com.x.ecommerceback.model.User;
import com.x.ecommerceback.model.request.UserCreateRequest;
import com.x.ecommerceback.model.request.UserLoginRequest;
import com.x.ecommerceback.model.response.AuthResponse;
import com.x.ecommerceback.repository.UserRepository;
import com.x.ecommerceback.service.interfaces.CartService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserServiceImplementation customUserServiceImplementation;
    private final CartService cartService;



    public AuthResponse createUser(UserCreateRequest request) {
        try {
            if (userRepository.findUserByEmail(request.email()) == null) {
                User u = new User();
                u.setEmail(request.email());
                u.setPassword(passwordEncoder.encode(request.password()));
                u.setFirstName(request.firstName());
                u.setLastName(request.lastName());
                u.setRole(Role.USER);
                u.setCreatedAt(LocalDateTime.now());
                User user = userRepository.save(u);
                cartService.createCart(user);
                Authentication authentication = new UsernamePasswordAuthenticationToken(u.getEmail(),u.getPassword());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String token = jwtProvider.generateToken(authentication);

                return new AuthResponse(token,"Sign up success");
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw new CustomRuntimeException("Email is already used with another account");
        }
    }

    public AuthResponse loginUser(UserLoginRequest request){
        SecurityContextHolder.getContext().setAuthentication(authenticate(request.email(),request.password()));
        return new AuthResponse(jwtProvider.generateToken(authenticate(request.email(),request.password())),"Sing in Success");
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customUserServiceImplementation.loadUserByUsername(email);
        if (userDetails==null){
            throw new BadCredentialsException("Invalid Username");
        }
        if (!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
