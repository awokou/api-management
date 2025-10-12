package com.server.api.management.service;

import com.server.api.management.entity.Employe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

public interface EmployeService {
    Employe getEmployeById(Long id);

    Page<Employe> getEmployesByEntrepriseId(Long entrepriseId, Pageable pageable);

    Page<Employe> getAllEmployes(Pageable pageable);

    Employe createEmploye(Long entrepriseId, Employe employe, MultipartFile file) throws IOException;

    Employe updateEmploye(Long entrepriseId, Employe employe);

    Employe deleteEmploye(Long entrepriseId, Long employeId);

    BigDecimal getSalaryByEntrepriseIdAndContractType(Long entrepriseId, String contractType, String grille);

    Page<Employe> filterEmployes(String search, Pageable pageable);
}
