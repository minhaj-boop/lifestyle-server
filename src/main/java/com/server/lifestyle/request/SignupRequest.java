package com.server.lifestyle.request;

import lombok.Data;

@Data
public class SignupRequest {
    private String fullName;
    private String email;
    private String otp;
}
