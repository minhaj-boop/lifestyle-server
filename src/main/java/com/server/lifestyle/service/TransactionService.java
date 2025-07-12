package com.server.lifestyle.service;

import com.server.lifestyle.model.Order;
import com.server.lifestyle.model.Seller;
import com.server.lifestyle.model.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Order order);
    List<Transaction> getTransactionsBySellerId(Seller seller);
    List<Transaction> getAllTransactions();
}
