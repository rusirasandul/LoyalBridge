package com.example.LoyalBridge.repository;


import com.example.LoyalBridge.model.ConversionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ConversionLogRepository extends JpaRepository<ConversionLog, Long> {
    List<ConversionLog> findByUserIdOrPartnerId(Long userId, Long partnerId);
}
