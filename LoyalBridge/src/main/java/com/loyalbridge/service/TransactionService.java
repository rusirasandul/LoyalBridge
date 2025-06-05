package com.loyalbridge.service;

import com.loyalbridge.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);
    Transaction updateTransaction(Long id, Transaction transaction);
    void deleteTransaction(Long id);
    Optional<Transaction> getTransactionById(Long id);
    Page<Transaction> getTransactions(Long userId, Long partnerId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Long getTotalPoints();
    Double getTotalConvertedAmount();
    void updateTransactionStatus(Long id, Transaction.TransactionStatus status);
} 