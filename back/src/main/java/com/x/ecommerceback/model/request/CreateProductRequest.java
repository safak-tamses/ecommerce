package com.x.ecommerceback.model.request;

import java.util.List;
import java.util.Properties;
import java.util.Set;

public record CreateProductRequest(
        String title,
        String description,
        double price,
        double discountedPrice,
        int discountPercent,
        String brand,
        String imageUrl,
        String firstCategory,
        String secondCategory,
        String thirdCategory

        ) {
}
