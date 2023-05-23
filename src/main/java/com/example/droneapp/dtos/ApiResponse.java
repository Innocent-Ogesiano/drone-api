package com.example.droneapp.dtos;

public record ApiResponse<T>(int statusCode, String message, T data) {
}
