package com.example.LoyalBridge.repository;

import com.LoyalBridge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameContainingIgnoreCaseOrPhoneContaining(String name, String phone);
}
