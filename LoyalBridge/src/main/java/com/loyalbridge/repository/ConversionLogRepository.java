package com.loyalbridge.repository;

import com.loyalbridge.model.ConversionLog;
import com.loyalbridge.model.ConversionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConversionLogRepository extends JpaRepository<ConversionLog, Long> {
    List<ConversionLog> findByUserId(Long userId);
    List<ConversionLog> findByPartnerId(Long partnerId);
    List<ConversionLog> findByStatus(ConversionStatus status);
    List<ConversionLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<ConversionLog> findByUserIdAndStatus(Long userId, ConversionStatus status);
    List<ConversionLog> findByPartnerIdAndStatus(Long partnerId, ConversionStatus status);
} 