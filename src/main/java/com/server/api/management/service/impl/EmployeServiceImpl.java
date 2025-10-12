package com.server.api.management.service.impl;

import com.server.api.management.entity.Entreprise;
import com.server.api.management.repository.EmployeRepository;
import com.server.api.management.repository.EntrepriseRepository;
import com.server.api.management.utils.FileUploadUtil;
import com.server.api.management.validator.EmployeValidator;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.server.api.management.entity.Employe;
import com.server.api.management.exception.ResourceNotFoundException;
import com.server.api.management.service.EmployeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class EmployeServiceImpl implements EmployeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeServiceImpl.class);

    private final EmployeRepository employeRepository;
    private final EntrepriseRepository entrepriseRepository;
    private final EmployeValidator employeValidator;
    private final FileUploadUtil fileUploadUtil;

    public EmployeServiceImpl(EmployeRepository employeRepository, EntrepriseRepository entrepriseRepository, EmployeValidator employeValidator, FileUploadUtil fileUploadUtil) {
        this.employeRepository = employeRepository;
        this.entrepriseRepository = entrepriseRepository;
        this.employeValidator = employeValidator;
        this.fileUploadUtil = fileUploadUtil;
    }


    @Override
    public Employe getEmployeById(Long id) {
        LOGGER.debug("request to find employe by id {}", id);
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe not found with id: " + id));
    }

    @Override
    public List<Employe> getEmployesByEntrepriseId(Long entrepriseId) {
        LOGGER.debug("request to find employe by entreprise id {}", entrepriseId);
        return employeRepository.findAllByEntrepriseId(entrepriseId);
    }

    @Override
    public List<Employe> getAllEmployes() {
        LOGGER.debug("request to get all employees");
        return employeRepository.findAll();
    }

    @Override
    public Employe createEmploye(Long entrepriseId, Employe employe, MultipartFile file) throws IOException {
        LOGGER.debug("request to create new employe By Entreprise id {} , {}", employe, entrepriseId);
        Entreprise entreprise = entrepriseRepository.findById(entrepriseId).orElseThrow(() -> new ResourceNotFoundException("Entreprise " + entrepriseId + " not found"));
        employe.setEntreprise(entreprise);

        // Vérifier si un fichier est fourni et non vide
        if (file != null && !file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null) {
                String fileName = StringUtils.cleanPath(originalFilename);
                String fileCode = fileUploadUtil.saveFile(fileName, file);
                employe.setFileName(fileCode + "-" + originalFilename);
            }
        }
        employeValidator.beforeSave(employe);
        return employeRepository.save(employe);
    }

    @Override
    public Employe updateEmploye(Long entrepriseId, Employe employeRequest) {
        LOGGER.debug("request to update employe By Entreprise id {} , {}", employeRequest, entrepriseId);
        if (!entrepriseRepository.existsById(entrepriseId)) {
            throw new ResourceNotFoundException("Entreprise " + entrepriseId + " not found");
        }
        Employe employe = employeRepository.findById(employeRequest.getId()).orElseThrow(() -> new ResourceNotFoundException("employeId " + employeRequest.getId() + "not found"));
        employe.setContractType(employeRequest.getContractType());
        employe.setFirstName(employeRequest.getFirstName());
        employe.setLastName(employeRequest.getLastName());
        employe.setEntreprise(employeRequest.getEntreprise());
        employe.setSocialSecurityNumber(employeRequest.getSocialSecurityNumber());
        employe.setHiringDate(employeRequest.getHiringDate());
        employe.setSalary(employeRequest.getSalary());
        employeValidator.beforeUpdate(employeRequest);
        return employeRepository.save(employe);
    }

    @Override
    public Employe deleteEmploye(Long entrepriseId, Long employeId) {
        LOGGER.debug("request to delete employe By Entreprise id {} , {}", employeId, entrepriseId);
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
