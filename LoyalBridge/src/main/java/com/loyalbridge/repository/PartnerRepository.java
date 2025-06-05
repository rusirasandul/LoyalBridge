package com.loyalbridge.repository;

import com.loyalbridge.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Optional<Partner> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Partner> findByNameContainingIgnoreCase(String name);
    List<Partner> findByActive(boolean active);
} 