package com.loyalbridge.repository;

import com.loyalbridge.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    @Query("SELECT t FROM Transaction t WHERE " +
           "(:userId IS NULL OR t.user.id = :userId) AND " +
           "(:partnerId IS NULL OR t.partner.id = :partnerId) AND " +
           "(:startDate IS NULL OR t.createdAt >= :startDate) AND " +
           "(:endDate IS NULL OR t.createdAt <= :endDate)")
    Page<Transaction> findTransactions(
            @Param("userId") Long userId,
            @Param("partnerId") Long partnerId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    @Query("SELECT SUM(t.points) FROM Transaction t WHERE t.status = 'COMPLETED'")
    Long getTotalPoints();

    @Query("SELECT SUM(t.convertedAmount) FROM Transaction t WHERE t.status = 'COMPLETED'")
    Double getTotalConvertedAmount();
} 