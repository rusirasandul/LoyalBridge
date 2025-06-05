package com.loyalbridge.service.impl;

import com.loyalbridge.model.Transaction;
import com.loyalbridge.repository.TransactionRepository;
import com.loyalbridge.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction updateTransaction(Long id, Transaction transaction) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        
        existingTransaction.setPoints(transaction.getPoints());
        existingTransaction.setConvertedAmount(transaction.getConvertedAmount());
        existingTransaction.setStatus(transaction.getStatus());
        
        return transactionRepository.save(existingTransaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Transaction> getTransactions(Long userId, Long partnerId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return transactionRepository.findTransactions(userId, partnerId, startDate, endDate, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getTotalPoints() {
        return transactionRepository.getTotalPoints();
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalConvertedAmount() {
        return transactionRepository.getTotalConvertedAmount();
    }

    @Override
    public void updateTransactionStatus(Long id, Transaction.TransactionStatus status) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        transaction.setStatus(status);
        transactionRepository.save(transaction);
    }
} 