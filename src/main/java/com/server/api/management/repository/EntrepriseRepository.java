package com.server.api.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.api.management.domain.entity.Entreprise;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
}
