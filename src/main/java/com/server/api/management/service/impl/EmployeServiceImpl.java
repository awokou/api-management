package com.server.api.management.service.impl;

import com.server.api.management.entity.Entreprise;
import com.server.api.management.enums.ContractType;
import com.server.api.management.exception.ValidationException;
import com.server.api.management.repository.EmployeRepository;
import com.server.api.management.repository.EntrepriseRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.server.api.management.entity.Employe;
import com.server.api.management.exception.ResourceNotFoundException;
import com.server.api.management.service.EmployeService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class EmployeServiceImpl implements EmployeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeServiceImpl.class);

    private final EmployeRepository employeRepository;
    private final EntrepriseRepository entrepriseRepository;

    public EmployeServiceImpl(EmployeRepository employeRepository, EntrepriseRepository entrepriseRepository) {
        this.employeRepository = employeRepository;
        this.entrepriseRepository = entrepriseRepository;
    }


    @Override
    public Employe getEmployeById(Long id) {
        LOGGER.info("request to find employe by id {}", id);
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe not found with id: " + id));
    }

    @Override
    public List<Employe> getEmployesByEntrepriseId(Long entrepriseId) {
        LOGGER.info("request to find employe by entreprise id {}", entrepriseId);
        return employeRepository.findAllByEntrepriseId(entrepriseId);
    }

    @Override
    public List<Employe> getAllEmployes() {
        LOGGER.info("request to get all employees");
        return employeRepository.findAll();
    }

    @Override
    public Employe createEmploye(Long entrepriseId, Employe employe) throws IOException {

        LOGGER.info("request to create new employe By Entreprise id {} , {}", employe, entrepriseId);

        if (employe.getSalary().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Le salaire doit être supérieur à zéro");
        }

        Entreprise entreprise = entrepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise " + entrepriseId + " not found"));
        employe.setEntreprise(entreprise);

        return employeRepository.save(employe);
    }

    @Override
    public Employe updateEmploye(Long entrepriseId, Employe employeRequest) {

        LOGGER.info("request to update employe By Entreprise id {} , {}", employeRequest, entrepriseId);

        Employe employe = employeRepository.findById(employeRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("employeId " + employeRequest.getId() + "not found"));

        Employe oldEmploye = employeRepository.findById(employeRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employé " + employeRequest.getId() + " not found"));

        if (!entrepriseRepository.existsById(entrepriseId)) {
            throw new ResourceNotFoundException("Entreprise " + entrepriseId + " not found");
        }

        // Validation du changement de type de contrat
        if ((oldEmploye.getContractType().equals(ContractType.CDD) ||
                oldEmploye.getContractType().equals(ContractType.CDI) &&
                        employeRequest.getContractType().equals(ContractType.ALTERNANCE))) {
            throw new ValidationException("un employé ne peut pas changer de contrat CDI ou CDD vers alternance");
        }

        // Validation du salaire
        if (employeRequest.getSalary().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Le salaire doit être supérieur à zéro");
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
    public Employe deleteEmploye(Long entrepriseId, Long employeId) {
        LOGGER.info("request to delete employe By Entreprise id {} , {}", employeId, entrepriseId);
        Employe employe = employeRepository.findByIdAndEntrepriseId(entrepriseId, employeId).orElseThrow(() -> new ResourceNotFoundException("Employe not found with id " + employeId + " and EntrepriseId " + entrepriseId));
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
