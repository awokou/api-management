package com.server.api.management.service.impl;

import com.server.api.management.entity.Entreprise;
import com.server.api.management.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.server.api.management.repository.EntrepriseRepository;
import com.server.api.management.service.EntrepriseService;
import com.server.api.management.validator.EntrepriseValidator;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

@Service
@Transactional
public class EntrepriseServiceImpl implements EntrepriseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntrepriseServiceImpl.class);

    private final EntrepriseRepository entrepriseRepository;
    private final EntrepriseValidator entrepriseValidator;

    public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository, EntrepriseValidator entrepriseValidator) {
        this.entrepriseRepository = entrepriseRepository;
        this.entrepriseValidator = entrepriseValidator;
    }

    @Override
    public Entreprise getEntrepriseById(Long id) {
        LOGGER.info("request to find entreprise by id {}", id);
        return entrepriseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise not found with id: " + id));
    }

    @Override
    public Page<Entreprise> getAllEntreprises(Pageable pageable) {
        LOGGER.info("request to get all entreprises");
        return entrepriseRepository.findAll(pageable);
    }

    @Override
    public Entreprise createEntreprise(Entreprise entreprise) {
        LOGGER.info("request to create new entreprise {}", entreprise);
        entrepriseValidator.beforeSave(entreprise);
        return entrepriseRepository.save(entreprise);
    }

    @Override
    public Entreprise updateEntreprise(Long id, Entreprise entrepriseRequest) {
        LOGGER.info("request to update entreprise {},{}", entrepriseRequest, entrepriseRequest.getId());
        Entreprise entreprise = entrepriseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("entrepriseId " + entrepriseRequest.getId() + " not found"));
        entreprise.setAddress(entrepriseRequest.getAddress());
        entreprise.setSiren(entrepriseRequest.getSiren());
        entreprise.setSiret(entrepriseRequest.getSiret());
        entreprise.setSocialReason(entrepriseRequest.getSocialReason());
        entreprise.setCreatedAt(new Date());
        entrepriseValidator.beforeUpdate(entrepriseRequest);
        return entrepriseRepository.save(entreprise);
    }

    @Override
    public Entreprise deleteEntreprise(Long id) {
        LOGGER.info("request to delete entreprise {} ", id);
        Entreprise entreprise = entrepriseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("entrepriseId " + id + " not found"));
        entrepriseRepository.delete(entreprise);
        return entreprise;
    }
}
