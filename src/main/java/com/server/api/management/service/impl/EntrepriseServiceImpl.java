package com.server.api.management.service.impl;

import com.server.api.management.domain.entity.Entreprise;
import com.server.api.management.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.server.api.management.repository.EntrepriseRepository;
import com.server.api.management.service.EntrepriseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntrepriseServiceImpl implements EntrepriseService {

    private final EntrepriseRepository entrepriseRepository;

    @Override
    @Transactional(readOnly = true)
    public Entreprise getEntrepriseById(Long id) {
        log.info("Find entreprise by id {}", id);
        return entrepriseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entreprise> getAllEntreprises() {
        log.info("Get all entreprises");
        return entrepriseRepository.findAll();
    }

    @Override
    @Transactional
    public Entreprise createEntreprise(Entreprise entreprise) {
        log.info("Create new entreprise {}", entreprise);
        return entrepriseRepository.save(entreprise);
    }

    @Override
    @Transactional
    public Entreprise updateEntreprise(Long id, Entreprise entrepriseRequest) {
        log.info("Update entreprise {},{}", entrepriseRequest, entrepriseRequest.getId());
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
        log.info("Delete entreprise {} ", id);
        Entreprise entreprise = entrepriseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("entrepriseId " + id + " not found"));
        entrepriseRepository.delete(entreprise);

        return entreprise;
    }
}
