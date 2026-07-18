package com.server.api.management.service.impl;

import com.server.api.management.repository.EmployeRepository;
import com.server.api.management.repository.EntrepriseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.server.api.management.domain.entity.Employe;
import com.server.api.management.domain.entity.Entreprise;
import com.server.api.management.domain.enums.ContractType;
import com.server.api.management.exception.ResourceNotFoundException;
import com.server.api.management.service.EmployeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements EmployeService {

    private final EmployeRepository employeRepository;
    private final EntrepriseRepository entrepriseRepository;

    @Override
    @Transactional(readOnly = true)
    public Employe getEmployeById(Long id) {
        log.info("Find employe by id {}", id);
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employe> getEmployesByEntrepriseId(Long entrepriseId) {
        log.info("Find employe by entreprise id {}", entrepriseId);
        return employeRepository.findAllByEntrepriseId(entrepriseId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employe> getAllEmployes() {
        log.info("Get all employees");
        return employeRepository.findAll();
    }

    @Override
    @Transactional
    public Employe createEmploye(Long entrepriseId, Employe employe) {

        log.info("Create new employe By Entreprise id {} , {}", employe, entrepriseId);

        if (employe.getSalary() != null && employe.getSalary().compareTo(BigDecimal.ZERO) < 0) {
            throw new ResourceNotFoundException("Le salaire doit être supérieur à zéro");
        }

        Entreprise entreprise = entrepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise " + entrepriseId + " not found"));
        employe.setEntreprise(entreprise);

        return employeRepository.save(employe);
    }

    @Override
    @Transactional
    public Employe updateEmploye(Long entrepriseId, Employe employeRequest) {

        log.info("Update employe By Entreprise id {} , {}", employeRequest, entrepriseId);

        Employe employe = employeRepository.findById(employeRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("employeId " + employeRequest.getId() + " not found"));

        if (!entrepriseRepository.existsById(entrepriseId)) {
            throw new ResourceNotFoundException("Entreprise " + entrepriseId + " not found");
        }

        // Validation du changement de type de contrat
        if (((employe.getContractType().equals(ContractType.CDD) ||
                employe.getContractType().equals(ContractType.CDI)) &&
                employeRequest.getContractType().equals(ContractType.ALTERNANCE))) {
            throw new ResourceNotFoundException("un employé ne peut pas changer de contrat CDI ou CDD vers alternance");
        }

        // Validation du salaire
        if (employeRequest.getSalary() != null && employeRequest.getSalary().compareTo(BigDecimal.ZERO) < 0) {
            throw new ResourceNotFoundException("Le salaire doit être supérieur à zéro");
        }

        // Mise à jour des champs
        employe.setContractType(employeRequest.getContractType());
        employe.setFirstName(employeRequest.getFirstName());
        employe.setLastName(employeRequest.getLastName());
        employe.setEntreprise(employeRequest.getEntreprise());
        employe.setSocialSecurityNumber(employeRequest.getSocialSecurityNumber());
        employe.setHiringDate(employeRequest.getHiringDate());
        employe.setSalary(employeRequest.getSalary());

        return employeRepository.save(employe);
    }

    @Override
    @Transactional
    public Employe deleteEmploye(Long entrepriseId, Long employeId) {
        log.info("Delete employe By Entreprise id {} , {}", employeId, entrepriseId);
        Employe employe = employeRepository.findByIdAndEntrepriseId(entrepriseId, employeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employe not found with id " + employeId + " and EntrepriseId " + entrepriseId));
        employeRepository.delete(employe);

        return employe;
    }

    @Override
    public BigDecimal getSalaryByEntrepriseIdAndContractType(Long entrepriseId, String contractType, String grille) {
        BigDecimal salary = null;
        if (grille.equals("min")) {
            salary = employeRepository.min(entrepriseId, contractType);
        }
        if (grille.equals("max")) {
            salary = employeRepository.max(entrepriseId, contractType);
        }
        if (grille.equals("moy")) {
            salary = employeRepository.moyen(entrepriseId, contractType);
        }
        return salary;
    }

    @Override
    public List<Employe> filterEmployes(String search) {
        return employeRepository.filterEmployes(search);
    }
}
