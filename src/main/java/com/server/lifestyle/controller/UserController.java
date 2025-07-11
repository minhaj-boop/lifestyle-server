package com.server.lifestyle.controller;

import com.server.lifestyle.domain.USER_ROLE;
import com.server.lifestyle.model.User;
import com.server.lifestyle.request.SignupRequest;
import com.server.lifestyle.response.AuthResponse;
import com.server.lifestyle.service.AuthService;
import com.server.lifestyle.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/users/profile")
    public ResponseEntity<User> getUserHandler(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        return ResponseEntity.ok(user);
    }
}
