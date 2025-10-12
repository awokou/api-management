package com.server.api.management.service;

import com.server.api.management.entity.Entreprise;

import java.util.List;

public interface EntrepriseService {
    /**
     *
     * @param id
     * @return
     */
    Entreprise getEntrepriseById(Long id);

    List<Entreprise> getAllEntreprises();

    /**
     *
     * @param entreprise
     * @return
     */
    Entreprise createEntreprise(Entreprise entreprise);

    /**
     *
     * @param id
     * @param entreprise
     * @return
     */
    Entreprise updateEntreprise(Long id, Entreprise entreprise);

    /**
     *
     * @param id
     * @return
     */
    Entreprise deleteEntreprise(Long id);
}
