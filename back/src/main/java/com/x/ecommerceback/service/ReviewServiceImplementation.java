package com.x.ecommerceback.service;

import com.x.ecommerceback.exception.ProductException;
import com.x.ecommerceback.model.Product;
import com.x.ecommerceback.model.Review;
import com.x.ecommerceback.model.User;
import com.x.ecommerceback.model.request.ReviewRequest;
import com.x.ecommerceback.repository.ProductRepository;
import com.x.ecommerceback.repository.ReviewRepository;
import com.x.ecommerceback.service.interfaces.ProductService;
import com.x.ecommerceback.service.interfaces.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImplementation implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    @Override
    public Review createReview(ReviewRequest request, User user) throws ProductException {
        Product product = productService.findProductById(request.productId());

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReview(request.review());
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }
}
