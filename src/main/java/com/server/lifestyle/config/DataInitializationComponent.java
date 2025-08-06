package com.server.lifestyle.config;

import com.server.lifestyle.domain.USER_ROLE;
import com.server.lifestyle.model.User;
import com.server.lifestyle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializationComponent implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeAdminUser();
    }

    private void initializeAdminUser() {
        String adminUsername = "imminhajulislam95@gmail.com";

        if(userRepository.findByEmail(adminUsername) == null) {
            User adminUser = new User();

            adminUser.setEmail(adminUsername);
            adminUser.setPassword(passwordEncoder.encode("123456"));
            adminUser.setFullName("Minhaj");
            adminUser.setRole(USER_ROLE.ROLE_ADMIN);

            userRepository.save(adminUser);
        }
    }
}
