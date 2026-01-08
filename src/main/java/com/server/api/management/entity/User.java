package com.server.api.management.entity;

import com.server.api.management.entity.enums.Role;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom et Prenom est obligatoire")
    @Size(min = 5, max = 100, message = "Le nom et Prenom doit être compris entre 5 et 100 caractères")
    @Column(name = "full_name", nullable = false)
    private String fullName;

    /**
     * User's email address. Must be unique and in a valid email format.
     *
     */
    @NotBlank(message = "Email is required.")
    @Email(message = "Valid email is required.")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * User's password. Must be at least 8 characters long.
     *
     */
    @NotBlank(message = "Password is required.")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Roles assigned to the user. Cannot be null.
     *
     */
    @Column(name = "roles", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role roles;

    /**
     * Indicates whether the user account is enabled.
     */
    @Column(name = "is_enabled")
    private boolean isEnabled;
}
