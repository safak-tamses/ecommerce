package com.x.ecommerceback.model.request;

public record UserCreateRequest(String email, String password, String firstName, String lastName) {
}
