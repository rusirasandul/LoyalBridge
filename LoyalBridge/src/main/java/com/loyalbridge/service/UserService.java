package com.loyalbridge.service;

import com.loyalbridge.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    Page<User> getAllUsers(Pageable pageable);
    List<User> searchUsers(String name, String phone, String status);
    void freezeUser(Long id);
    void unfreezeUser(Long id);
    void markAsHighRisk(Long id);
    void markAsVerified(Long id);
    void updatePoints(Long id, Long points);
} 