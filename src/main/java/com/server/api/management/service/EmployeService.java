package com.server.api.management.service;

import com.server.api.management.entity.Employe;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface EmployeService {
    Employe getEmployeById(Long id);

    List<Employe> getEmployesByEntrepriseId(Long entrepriseId);

    List<Employe> getAllEmployes();

    Employe createEmploye(Long entrepriseId, Employe employe) throws IOException;

    Employe updateEmploye(Long entrepriseId, Employe employe);

    Employe deleteEmploye(Long entrepriseId, Long employeId);

    BigDecimal getSalaryByEntrepriseIdAndContractType(Long entrepriseId, String contractType, String grille);

    List<Employe> filterEmployes(String search);
}
