package com.server.api.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.api.management.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
