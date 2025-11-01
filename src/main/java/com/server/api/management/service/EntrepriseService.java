package com.server.api.management.service;

import com.server.api.management.entity.Entreprise;

import java.util.List;

public interface EntrepriseService {

    Entreprise getEntrepriseById(Long id);

    List<Entreprise> getAllEntreprises();

    Entreprise createEntreprise(Entreprise entreprise);

    Entreprise updateEntreprise(Long id, Entreprise entreprise);

    Entreprise deleteEntreprise(Long id);
}
