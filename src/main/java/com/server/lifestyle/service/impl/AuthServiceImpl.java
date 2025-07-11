package com.server.lifestyle.service.impl;

import com.server.lifestyle.config.JwtProvider;
import com.server.lifestyle.domain.USER_ROLE;
import com.server.lifestyle.model.Cart;
import com.server.lifestyle.model.Seller;
import com.server.lifestyle.model.User;
import com.server.lifestyle.model.VerificationCode;
import com.server.lifestyle.repository.CartRepository;
import com.server.lifestyle.repository.SellerRepository;
import com.server.lifestyle.repository.UserRepository;
import com.server.lifestyle.repository.VerificationCodeRepository;
import com.server.lifestyle.request.LoginRequest;
import com.server.lifestyle.request.SignupRequest;
import com.server.lifestyle.response.AuthResponse;
import com.server.lifestyle.service.AuthService;
import com.server.lifestyle.service.EmailService;
import com.server.lifestyle.utils.OtpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final JwtProvider jwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final CustomUserServiceImpl customUserService;
    private final SellerRepository sellerRepository;

    @Override
    public void sendLoginOtp(String email, USER_ROLE role) throws Exception {
        String SIGNIN_PREFIX="signin_";
        
        if(email.startsWith(SIGNIN_PREFIX)){
            email = email.substring(SIGNIN_PREFIX.length());

            if(role.equals(USER_ROLE.ROLE_SELLER)) {
                Seller seller = sellerRepository.findByEmail(email);
                if(seller == null){
                    throw new Exception("Seller does not exist for the provided email");
                }

            } else{
                User user = userRepository.findByEmail(email);
                if(user == null){
                    throw new Exception("Customer does not exist for the provided email!");
                }
            }
        }

        VerificationCode isExist = verificationCodeRepository.findByEmail(email);
        if(isExist != null){
            verificationCodeRepository.delete(isExist);
        }

        String otp = OtpUtils.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);

        verificationCodeRepository.save(verificationCode);

        String subject = "lifestyle login/signup otp";
        String text = "Your login/signup otp is - " + otp;

        emailService.sendVerificationOtpEmail(email, otp, subject, text);
    }

    @Override
    public String createUser(SignupRequest signupRequest) throws Exception {

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(signupRequest.getEmail());

        if(verificationCode == null || !verificationCode.getOtp().equals(signupRequest.getOtp())) {
            throw new Exception("Wrong otp...");
        }

        User user = userRepository.findByEmail(signupRequest.getEmail());

        if (user == null) {

            User createdUser = new User();
            createdUser.setEmail(signupRequest.getEmail());
            createdUser.setFullName(signupRequest.getFullName());
            createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createdUser.setMobile("01626885465");
            createdUser.setPassword(passwordEncoder.encode(signupRequest.getOtp()));

            user = userRepository.save(createdUser);

            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(signupRequest.getEmail(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);
    }

    @Override
    public AuthResponse signin(LoginRequest req) throws Exception {
        String username = req.getEmail();
        String otp = req.getOtp();

        Authentication authentication = authenticate(username, otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setMessage("Login successful!");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName = authorities.isEmpty()? null: authorities.iterator().next().getAuthority();

        authResponse.setRole(USER_ROLE.valueOf(roleName));

        return authResponse;
    }

    private Authentication authenticate(String username, String otp) throws Exception {
        UserDetails userDetails = customUserService.loadUserByUsername(username);

        String SELLER_PREFIX = "seller_";
        if(username.startsWith(SELLER_PREFIX)){
           username = username.substring(SELLER_PREFIX.length());
        }

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(username);

        if (verificationCode == null || !verificationCode.getOtp().equals(otp)) {
            throw new Exception("Wrong otp");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
