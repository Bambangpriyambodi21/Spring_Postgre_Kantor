package com.kantor.kantor.service;

import com.kantor.kantor.entity.UserCredential;
import com.kantor.kantor.model.request.AuthRequest;
import com.kantor.kantor.model.response.UserResponse;

public interface AuthService {
    UserResponse register(AuthRequest request);
    String login(AuthRequest request);
}
