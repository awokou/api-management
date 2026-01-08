package com.server.api.management.service.impl;

import com.server.api.management.entity.Entreprise;
import com.server.api.management.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.server.api.management.repository.EntrepriseRepository;
import com.server.api.management.service.EntrepriseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntrepriseServiceImpl implements EntrepriseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntrepriseServiceImpl.class);

    private final EntrepriseRepository entrepriseRepository;

    @Override
    @Transactional(readOnly = true)
    public Entreprise getEntrepriseById(Long id) {
        LOGGER.info("Find entreprise by id {}", id);
        return entrepriseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entreprise> getAllEntreprises() {
        LOGGER.info("Get all entreprises");
        return entrepriseRepository.findAll();
    }

    @Override
    @Transactional
    public Entreprise createEntreprise(Entreprise entreprise) {
        LOGGER.info("Create new entreprise {}", entreprise);
        return entrepriseRepository.save(entreprise);
    }

    @Override
    @Transactional
    public Entreprise updateEntreprise(Long id, Entreprise entrepriseRequest) {
        LOGGER.info("Update entreprise {},{}", entrepriseRequest, entrepriseRequest.getId());
        Entreprise entreprise = entrepriseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("entrepriseId " + entrepriseRequest.getId() + " not found"));
        entreprise.setAddress(entrepriseRequest.getAddress());
        entreprise.setSiren(entrepriseRequest.getSiren());
        entreprise.setSiret(entrepriseRequest.getSiret());
        entreprise.setSocialReason(entrepriseRequest.getSocialReason());
        entreprise.setCreatedAt(new Date());
        return entrepriseRepository.save(entreprise);
    }

    @Override
    @Transactional
    public Entreprise deleteEntreprise(Long id) {
        LOGGER.info("Delete entreprise {} ", id);
        Entreprise entreprise = entrepriseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("entrepriseId " + id + " not found"));
        entrepriseRepository.delete(entreprise);
        return entreprise;
    }
}
