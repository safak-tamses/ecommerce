package com.x.ecommerceback.service;

import com.x.ecommerceback.config.JwtProvider;
import com.x.ecommerceback.exception.TokenException;
import com.x.ecommerceback.exception.UserException;
import com.x.ecommerceback.exception.UserNotFoundException;
import com.x.ecommerceback.exception.UserRepositoryException;
import com.x.ecommerceback.model.Role;
import com.x.ecommerceback.model.User;
import com.x.ecommerceback.model.response.UserResponse;
import com.x.ecommerceback.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    public User findUserProfileByJwt(String jwt) {
        String email;
        try {
            email = jwtProvider.getEmailFromToken(jwt);
        } catch (TokenException e) {
            throw new UserException("An error occurred while finding the user profile");
        }

        try {
            User user = userRepository.findUserByEmail(email);
            if (user == null) {
                throw new UserNotFoundException("User not found");
            }
            return user;
        } catch (UserRepositoryException e) {
            throw new UserException("An error occurred while finding the user profile");
        } catch (Exception e) {
            throw new UserException("An unexpected error occurred");
        }
    }

    public UserResponse getUserProfile(String jwt){
        return new UserResponse(findUserProfileByJwt(jwt),"Success");
    }

    public Boolean hasAuthority(String jwt){
        return findUserProfileByJwt(jwt).getRole().equals(Role.ADMIN);
    }

}
