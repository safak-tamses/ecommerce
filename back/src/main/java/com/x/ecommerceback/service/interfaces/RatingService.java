package com.x.ecommerceback.service.interfaces;

import com.x.ecommerceback.exception.ProductException;
import com.x.ecommerceback.model.Rating;
import com.x.ecommerceback.model.User;
import com.x.ecommerceback.model.request.RatingRequest;

import java.util.List;

public interface RatingService {
    public Rating createRating(RatingRequest request, User user)throws ProductException;
    public List<Rating> getProductsRatingByProductId(Long productId);
}
