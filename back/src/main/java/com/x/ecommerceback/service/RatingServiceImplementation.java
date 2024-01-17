package com.x.ecommerceback.service;

import com.x.ecommerceback.exception.ProductException;
import com.x.ecommerceback.model.Product;
import com.x.ecommerceback.model.Rating;
import com.x.ecommerceback.model.User;
import com.x.ecommerceback.model.request.RatingRequest;
import com.x.ecommerceback.repository.RatingRepository;
import com.x.ecommerceback.service.interfaces.ProductService;
import com.x.ecommerceback.service.interfaces.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RatingServiceImplementation implements RatingService {
    private final RatingRepository ratingRepository;
    private final ProductService productService;
    @Override
    public Rating createRating(RatingRequest request, User user) throws ProductException {
        Product product = productService.findProductById(request.productId());
        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(request.rating());
        rating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRatingByProductId(Long productId) {
        return ratingRepository.getAllProductsRating(productId);
    }
}
