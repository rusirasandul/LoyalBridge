package com.loyalbridge.controller;

import com.loyalbridge.dto.*;
import com.loyalbridge.model.User;
import com.loyalbridge.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login with email and password")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse response = authService.authenticate(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        User registeredUser = authService.register(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout user")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/otp/generate")
    @Operation(summary = "Generate OTP for 2FA")
    public ResponseEntity<?> generateOtp(@RequestParam String email) {
        String otp = authService.generateOtp(email);
        return ResponseEntity.ok(new OtpResponse(otp));
    }

    @PostMapping("/otp/validate")
    @Operation(summary = "Validate OTP")
    public ResponseEntity<?> validateOtp(@Valid @RequestBody OtpValidationRequest request) {
        boolean isValid = authService.validateOtp(request.getEmail(), request.getOtp());
        return ResponseEntity.ok(new OtpValidationResponse(isValid));
    }

    @PostMapping("/change-password")
    @Operation(summary = "Change user password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(request.getEmail(), request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.ok().build();
    }
} 