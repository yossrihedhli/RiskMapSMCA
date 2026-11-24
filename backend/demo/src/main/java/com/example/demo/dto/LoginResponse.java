package com.example.demo.dto;

public record LoginResponse(String token, UserResponse user) {
}