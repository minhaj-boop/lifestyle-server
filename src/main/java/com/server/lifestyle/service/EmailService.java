package com.server.lifestyle.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendVerificationOtpEmail(String email, String otp, String subject, String text) throws MessagingException;
}
