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
            String token = jwtUtil.generateToken(adminUser.getEmail(), adminUser.getRole().getName());
            return new AuthResponse(token, adminUser.getRole().getName());
        }

        // If not admin, try regular user
        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Get the first role from the user's roles set
        String roleName = user.getRoles().stream()
                .findFirst()
                .map(role -> role.getName())
                .orElse("ROLE_USER");

        String token = jwtUtil.generateToken(user.getEmail(), roleName);
        return new AuthResponse(token, roleName);
    }

    public User register(User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public void logout(String token) {
        // In a stateless JWT implementation, logout is handled client-side
        // by removing the token. This method is kept for future implementation
        // of token blacklisting if needed.
    }

    public String generateOtp(String email) {
        // Generate a 6-digit OTP
        String otp = String.format("%06d", (int) (Math.random() * 1000000));
        // TODO: Store OTP in database with expiration
        // TODO: Send OTP via email
        return otp;
    }

    public boolean validateOtp(String email, String otp) {
        // TODO: Validate OTP from database
        return true;
    }

    public void changePassword(String email, String oldPassword, String newPassword) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Invalid old password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }
} 