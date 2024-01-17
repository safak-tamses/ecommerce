package com.x.ecommerceback.service.interfaces;

import com.x.ecommerceback.exception.ProductException;
import com.x.ecommerceback.model.Review;
import com.x.ecommerceback.model.User;
import com.x.ecommerceback.model.request.ReviewRequest;

import java.util.List;

public interface ReviewService {
    public Review createReview(ReviewRequest request, User user)throws ProductException;
    public List<Review> getAllReview(Long productId);
}
