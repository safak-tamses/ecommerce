package com.x.ecommerceback.model.request;

public record OrderRequest(
        String firstName,
        String lastName,
        String streetAddress,
        String city,
        String state,
        String zipCode,
        String mobile
) {
}
