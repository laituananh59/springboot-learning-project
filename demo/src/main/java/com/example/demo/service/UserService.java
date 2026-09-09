package com.example.demo.service;

import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.UserResponse;

public interface UserService {
    UserResponse register(RegisterRequest request);
}
