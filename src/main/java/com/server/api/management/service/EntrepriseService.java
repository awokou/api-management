package com.server.api.management.service;

import com.server.api.management.entity.Entreprise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EntrepriseService {
    /**
     *
     * @param id
     * @return
     */
    Entreprise getEntrepriseById(Long id);

    /**
     *
     * @param pageable
     * @return
     */
    Page<Entreprise> getAllEntreprises(Pageable pageable);

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
