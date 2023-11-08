package com.decagon.karrigobe.services.login_service;

import com.decagon.karrigobe.payload.request.LoginRequest;
import com.decagon.karrigobe.payload.response.AuthResponse;

public interface LoginService {
    AuthResponse login(LoginRequest loginRequest);
    void logout();
}
