package com.server.lifestyle.service.impl;

import com.server.lifestyle.config.JwtProvider;
import com.server.lifestyle.model.User;
import com.server.lifestyle.repository.UserRepository;
import com.server.lifestyle.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String token) throws Exception {
        String email = jwtProvider.getEmailFromToken(token);

        return this.findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user = this.userRepository.findByEmail(email);

        if(user == null){
            throw new Exception("User not found with email - " + email);
        }
        return user;
    }
}
