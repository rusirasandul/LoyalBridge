package com.loyalbridge.service;

import com.loyalbridge.dto.AuthResponse;
import com.loyalbridge.dto.LoginRequest;
import com.loyalbridge.model.AdminUser;
import com.loyalbridge.model.User;
import com.loyalbridge.repository.AdminUserRepository;
import com.loyalbridge.repository.UserRepository;
import com.loyalbridge.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired private AdminUserRepository adminRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;

    public AuthResponse authenticate(LoginRequest req) {
        // Try to find admin user first
        AdminUser adminUser = adminRepo.findByEmail(req.getEmail()).orElse(null);
        if (adminUser != null) {
            if (!passwordEncoder.matches(req.getPassword(), adminUser.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }
            String token = jwtUtil.generateToken(adminUser.getEmail(), adminUser.getRole());
            return new AuthResponse(token, adminUser.getRole().name());
        }

        // If not admin, try regular user
        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new AuthResponse(token, user.getRole().name());
    }
} 