package com.server.lifestyle.controller;

import com.server.lifestyle.model.Seller;
import com.server.lifestyle.model.Transaction;
import com.server.lifestyle.service.SellerService;
import com.server.lifestyle.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final SellerService sellerService;

    @GetMapping("/seller")
    public ResponseEntity<List<Transaction>> getTransactionBySeller(@RequestHeader("Authorization") String jwtToken) throws Exception {
        Seller seller = sellerService.getSellerProfile(jwtToken);

        List<Transaction> transactions = transactionService.getTransactionsBySellerId(seller);

        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Transaction>> getAllTransaction() throws Exception {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
}
