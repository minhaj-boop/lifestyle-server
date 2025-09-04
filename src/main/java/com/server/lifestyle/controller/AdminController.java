package com.server.lifestyle.controller;

import com.server.lifestyle.domain.AccountStatus;
import com.server.lifestyle.domain.USER_ROLE;
import com.server.lifestyle.model.Seller;
import com.server.lifestyle.model.User;
import com.server.lifestyle.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final SellerService sellerService;

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update/seller/{id}/status/{status}")
    public ResponseEntity<Seller> updateSellerStatus(@PathVariable Long id, @PathVariable AccountStatus status) throws Exception {
        Seller updatedSeller = sellerService.updateSellerAccountStatus(id, status);
        return ResponseEntity.ok(updatedSeller);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/all/sellers")
    public ResponseEntity<List<Seller>> getAllSellers( @RequestParam(required = false)AccountStatus status) throws Exception {
        List<Seller> sellers = sellerService.getAllSellers(status);
        return new ResponseEntity<>(sellers, HttpStatus.OK);
    }
}
