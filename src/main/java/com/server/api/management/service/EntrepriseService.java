package com.server.api.management.service;

import java.util.List;

import com.server.api.management.domain.entity.Entreprise;

public interface EntrepriseService {

    Entreprise getEntrepriseById(Long id);

    List<Entreprise> getAllEntreprises();

    Entreprise createEntreprise(Entreprise entreprise);

    Entreprise updateEntreprise(Long id, Entreprise entreprise);

    Entreprise deleteEntreprise(Long id);
}
