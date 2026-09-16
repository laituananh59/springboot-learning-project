package com.example.demo.service;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.repository.UserRepository;

public interface AuthService {
    UserResponse login(LoginRequest request);
}
