package com.server.lifestyle.controller;

import com.server.lifestyle.domain.USER_ROLE;
import com.server.lifestyle.model.VerificationCode;
import com.server.lifestyle.request.LoginOtpRequest;
import com.server.lifestyle.request.LoginRequest;
import com.server.lifestyle.response.ApiResponse;
import com.server.lifestyle.response.AuthResponse;
import com.server.lifestyle.request.SignupRequest;
import com.server.lifestyle.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest signupRequest) throws Exception {

        String jwt = authService.createUser(signupRequest);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwt);
        authResponse.setMessage("Registration successful!");
        authResponse.setRole(USER_ROLE.ROLE_CUSTOMER);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/sent/login-signup-otp")
    public ResponseEntity<ApiResponse> sendOtpHandler(@RequestBody LoginOtpRequest req) throws Exception {

        authService.sendLoginOtp(req.getEmail(), req.getRole());

        ApiResponse res = new ApiResponse();

        res.setMessage("Otp Sent Successfully!");

        return ResponseEntity.ok(res);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req) throws Exception {

       AuthResponse  res = authService.signin(req);

       return ResponseEntity.ok(res);
    }
}
