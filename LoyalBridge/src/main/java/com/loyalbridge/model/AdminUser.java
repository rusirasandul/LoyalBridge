package com.loyalbridge.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "admin_users")
public class AdminUser extends User {
    @Enumerated(EnumType.STRING)
    private Role.RoleType role;

    private Boolean otpEnabled;
} 