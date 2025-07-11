package com.server.lifestyle.service;

import com.server.lifestyle.model.User;

public interface UserService {
    User findUserByJwtToken(String token) throws Exception;
    User findUserByEmail(String email) throws Exception;
}
