package com.server.lifestyle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class LifestyleApplication {

	public static void main(String[] args) {
		SpringApplication.run(LifestyleApplication.class, args);
	}

}
