package com.server.lifestyle.controller;

import com.server.lifestyle.config.JwtProvider;
import com.server.lifestyle.domain.AccountStatus;
import com.server.lifestyle.domain.USER_ROLE;
import com.server.lifestyle.exceptions.SellerException;
import com.server.lifestyle.model.Seller;
import com.server.lifestyle.model.SellerReport;
import com.server.lifestyle.model.User;
import com.server.lifestyle.model.VerificationCode;
import com.server.lifestyle.repository.VerificationCodeRepository;
import com.server.lifestyle.request.LoginRequest;
import com.server.lifestyle.response.AuthResponse;
import com.server.lifestyle.service.*;
import com.server.lifestyle.utils.OtpUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")

public class SellerController {
    private final SellerService sellerService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AuthService authService;
    private final EmailService emailService;
    private final JwtProvider jwtProvider;
    private final SellerReportService sellerReportService;
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> sellerLogin(
            @RequestBody LoginRequest req

            ) throws Exception {
        String otp = req.getOtp();
        String email = req.getEmail();

        req.setEmail("seller_"+email);
        AuthResponse authResponse = authService.signin(req);

        return ResponseEntity.ok(authResponse);
    }

    @PatchMapping("/verify/{otp}")
    public ResponseEntity<Seller> verifySellerEmail(@PathVariable String otp) throws Exception {
        VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp);

        if(verificationCode == null || !verificationCode.getOtp().equals(otp)) {
            throw new Exception("Wrong otp!");
        }

        Seller seller = sellerService.verifyEmail(verificationCode.getEmail(), otp);

        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) throws Exception, MessagingException {
        Seller savedSeller = sellerService.createSeller(seller);

        String otp = OtpUtils.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(seller.getEmail());
        verificationCodeRepository.save(verificationCode);

        String subject = "Lifestyle Email Verification code";
        String text = "Welcome to lifestyle, verify your account using this link";
        String frontend_url = "http://localhost:3000/verify-seller/";
        emailService.sendVerificationOtpEmail(seller.getEmail(), verificationCode.getOtp(), subject, text + frontend_url);

        return new ResponseEntity<>(savedSeller, HttpStatus.CREATED);
    }

    @GetMapping("/get/by-id/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws SellerException {
        Seller seller = sellerService.getSellerById(id);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<Seller> getSellerByJwt(@RequestHeader("Authorization") String jwt) throws Exception {
        Seller seller = sellerService.getSellerProfile(jwt);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @GetMapping("/report")
    public ResponseEntity<SellerReport> getSellerReport(@RequestHeader("Authorization") String jwt) throws Exception {
        String email = jwtProvider.getEmailFromToken(jwt);
        Seller seller = sellerService.getSellerByEmail(email);
        SellerReport sellerReport = sellerReportService.getSellerReport(seller);
        return new ResponseEntity<>(sellerReport, HttpStatus.OK);
    }

//    @GetMapping("/get/all")
//    public ResponseEntity<List<Seller>> getAllSellers(@RequestHeader("Authorization") String jwt, @RequestParam(required = false)AccountStatus status) throws Exception {
//
//        User user = userService.findUserByJwtToken(jwt);
//        if(user.getRole().equals(USER_ROLE.ROLE_ADMIN)) {
//            List<Seller> sellers = sellerService.getAllSellers(status);
//            return new ResponseEntity<>(sellers, HttpStatus.OK);
//        }else {
//            throw new Exception("Unauthorized access: only admin can view all sellers");
//        }
//    }

    @PatchMapping("/update")
    public ResponseEntity<Seller> updateSeller(@RequestHeader("Authorization") String jwt, @RequestBody Seller seller)  throws Exception {
        Seller profile  = sellerService.getSellerProfile(jwt);
        Seller updateSeller = sellerService.updateSeller(profile.getId(), seller);
        return new ResponseEntity<>(updateSeller, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Seller> deleteSeller(@PathVariable Long id) throws Exception {

        sellerService.deleteSeller(id);

        return ResponseEntity.noContent().build();
    }

}
