package com.server.api.management.service;

import com.server.api.management.entity.Entreprise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EntrepriseService {
    Entreprise getEntrepriseById(Long id);
    Page<Entreprise> getAllEntreprises(Pageable pageable);
    Entreprise createEntreprise(Entreprise entreprise);
    Entreprise updateEntreprise(Entreprise entreprise);
    Entreprise deleteEntreprise(Long id);
}
