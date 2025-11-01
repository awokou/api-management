package com.server.api.management.service.impl;

import com.server.api.management.entity.Entreprise;
import com.server.api.management.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.server.api.management.repository.EntrepriseRepository;
import com.server.api.management.service.EntrepriseService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EntrepriseServiceImpl implements EntrepriseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntrepriseServiceImpl.class);

    private final EntrepriseRepository entrepriseRepository;

    public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    @Override
    public Entreprise getEntrepriseById(Long id) {
        LOGGER.info("Find entreprise by id {}", id);
        return entrepriseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise not found with id: " + id));
    }

    @Override
    public List<Entreprise> getAllEntreprises() {
        LOGGER.info("Get all entreprises");
        return entrepriseRepository.findAll();
    }

    @Override
    public Entreprise createEntreprise(Entreprise entreprise) {
        LOGGER.info("Create new entreprise {}", entreprise);
        return entrepriseRepository.save(entreprise);
    }

    @Override
    public Entreprise updateEntreprise(Long id, Entreprise entrepriseRequest) {
        LOGGER.info("Update entreprise {},{}", entrepriseRequest, entrepriseRequest.getId());
        Entreprise entreprise = entrepriseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("entrepriseId " + entrepriseRequest.getId() + " not found"));
        entreprise.setAddress(entrepriseRequest.getAddress());
        entreprise.setSiren(entrepriseRequest.getSiren());
        entreprise.setSiret(entrepriseRequest.getSiret());
        entreprise.setSocialReason(entrepriseRequest.getSocialReason());
        entreprise.setCreatedAt(new Date());
        return entrepriseRepository.save(entreprise);
    }

    @Override
    public Entreprise deleteEntreprise(Long id) {
        LOGGER.info("Delete entreprise {} ", id);
        Entreprise entreprise = entrepriseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("entrepriseId " + id + " not found"));
        entrepriseRepository.delete(entreprise);
        return entreprise;
    }
}
