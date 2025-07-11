package com.server.lifestyle.service;
import com.server.lifestyle.domain.USER_ROLE;
import com.server.lifestyle.request.LoginRequest;
import com.server.lifestyle.response.AuthResponse;
import com.server.lifestyle.request.SignupRequest;

public interface AuthService {

    void sendLoginOtp(String email, USER_ROLE role) throws Exception;
    String createUser(SignupRequest signupRequest) throws Exception;
    AuthResponse signin(LoginRequest req) throws Exception;
}
