package com.server.api.management.service.impl;

import com.server.api.management.repository.EmployeRepository;
import com.server.api.management.repository.EntrepriseRepository;
import com.server.api.management.validator.EmployeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.server.api.management.entity.Employe;
import com.server.api.management.exception.ResourceNotFoundException;
import com.server.api.management.service.EmployeService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

@Service
public class EmployeServiceImpl implements EmployeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeServiceImpl.class);

    private final EmployeRepository employeRepository;
    private final EntrepriseRepository entrepriseRepository;
    private final EmployeValidator employeValidator;

    public EmployeServiceImpl(EmployeRepository employeRepository, EntrepriseRepository entrepriseRepository, EmployeValidator employeValidator) {
        this.employeRepository = employeRepository;
        this.entrepriseRepository = entrepriseRepository;
        this.employeValidator = employeValidator;
    }


    @Override
    public Employe getEmployeById(Long id) {
        LOGGER.debug("request to find employe by id {}", id);
        return employeRepository.getOne(id);
    }

    @Override
    public Page<Employe> getEmployesByEntrepriseId(Long entrepriseId, Pageable pageable) {
        LOGGER.debug("request to find employe by entreprise id {}", entrepriseId);
        return employeRepository.findAllByEntrepriseId(entrepriseId,pageable);
    }

    @Override
    public Page<Employe> getAllEmployes(Pageable pageable) {
        LOGGER.debug("request to get all employees");
        return employeRepository.findAll(pageable);    }

    @Override
    public Employe createEmploye(Long entrepriseId, Employe employe) {
        LOGGER.debug("request to create new employe By Entreprise id {} , {}", employe, entrepriseId);
        return entrepriseRepository.findById(entrepriseId).map(entreprise -> {
            employe.setEntreprise(entreprise);
            employeValidator.beforeSave(employe);
            return employeRepository.save(employe);
        }).orElseThrow(() -> new ResourceNotFoundException("Entreprise " + entrepriseId + " not found"));
    }

    @Override
    public Employe updateEmploye(Long entrepriseId, Employe employeRequest) {
        LOGGER.debug("request to update employe By Entreprise id {} , {}", employeRequest, entrepriseId);
        if(!entrepriseRepository.existsById(entrepriseId)) {
            throw new ResourceNotFoundException("Entreprise " + entrepriseId + " not found");
        }
        return employeRepository.findById(employeRequest.getId()).map(employe -> {
            employe.setContractType(employeRequest.getContractType());
            employe.setFirstName(employeRequest.getFirstName());
            employe.setLastName(employeRequest.getLastName());
            employe.setEntreprise(employeRequest.getEntreprise());
            employe.setSocialSecurityNumber(employeRequest.getSocialSecurityNumber());
            employe.setHiringDate(employeRequest.getHiringDate());
            employe.setSalary(employeRequest.getSalary());
            employeValidator.beforeUpdate(employeRequest);
            return employeRepository.save(employe);
        }).orElseThrow(() -> new ResourceNotFoundException("employeId " + employeRequest.getId() + "not found"));    }

    @Override
    public Employe deleteEmploye(Long entrepriseId, Long employeId) {
        LOGGER.debug("request to delete employe By Entreprise id {} , {}", employeId, entrepriseId);
        return employeRepository.findByIdAndEntrepriseId(entrepriseId, employeId).map(employe -> {
            employeRepository.delete(employe);
            return employe;
        }).orElseThrow(() -> new ResourceNotFoundException("Employe not found with id " + employeId + " and EntrepriseId " + entrepriseId));
    }

    @Override
    public BigDecimal getSalaryByEntrepriseIdAndContractType(Long entrepriseId, String contractType, String grille) {
        BigDecimal salary = null;
        if(grille.equals("min")){
            salary = employeRepository.min(entrepriseId,contractType);
        }
        if (grille.equals("max")){
            salary = employeRepository.max(entrepriseId,contractType);
        }
        if (grille.equals("moy")){
            salary = employeRepository.moyen(entrepriseId,contractType);
        }
        return salary;
    }

    @Override
    public Page<Employe> filterEmployes(String search, Pageable pageable) {
        return employeRepository.filterEmployes(search,pageable);
    }
}
