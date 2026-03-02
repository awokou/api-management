package com.server.api.management.domain.entity;

import com.server.api.management.domain.enums.Role;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom et Prenom est obligatoire")
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
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled;
}
