package com.server.api.management.service.impl;

import com.server.api.management.entity.Entreprise;
import com.server.api.management.exception.ResourceNotFoundException;
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
        return entrepriseRepository.getOne(id);
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
    public Entreprise updateEntreprise(Entreprise entrepriseRequest) {
        LOGGER.info("request to update entreprise {},{}", entrepriseRequest,entrepriseRequest.getId());
        return entrepriseRepository.findById(entrepriseRequest.getId()).map(entreprise -> {
            entreprise.setAddress(entrepriseRequest.getAddress());
            entreprise.setSiren(entrepriseRequest.getSiren());
            entreprise.setSiret(entrepriseRequest.getSiret());
            entreprise.setSocialReason(entrepriseRequest.getSocialReason());
            entreprise.setCreatedAt(new Date());
            entrepriseValidator.beforeUpdate(entrepriseRequest);
            return entrepriseRepository.save(entreprise);
        }).orElseThrow(() -> new ResourceNotFoundException("entrepriseId " + entrepriseRequest.getId() + " not found"));    }

    @Override
    public Entreprise deleteEntreprise(Long id) {
        LOGGER.info("request to delete entreprise {} " , id);
        return entrepriseRepository.findById(id).map(entreprise -> {
            entrepriseRepository.delete(entreprise);
            return entreprise;
        }).orElseThrow(() -> new ResourceNotFoundException("entrepriseId " + id + " not found"));
    }
}
