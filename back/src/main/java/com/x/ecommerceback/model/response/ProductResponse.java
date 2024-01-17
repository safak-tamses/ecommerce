package com.x.ecommerceback.model.response;

import com.x.ecommerceback.model.Properties;
import com.x.ecommerceback.model.Rating;
import com.x.ecommerceback.model.Review;

import java.time.OffsetDateTime;
import java.util.List;

public record ProductResponse(
        Long id,
        String title,
        String description,
        Double price,
        Double discountedPrice,
        Integer discountPercent,
        Integer quantity,
        String brand,
        String imageUrl,
        Integer numRatings,
        List<Properties> properties,
        List<Rating> ratings,
        List<Review> reviews,
        OffsetDateTime created,
        OffsetDateTime updated,
        String categoryFirst,
        String categorySecond,
        String categoryThird
        ) {
}
