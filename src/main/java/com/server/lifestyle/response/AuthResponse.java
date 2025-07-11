package com.server.lifestyle.response;

import com.server.lifestyle.domain.USER_ROLE;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String token;
    private String message;
    private USER_ROLE role;
}
