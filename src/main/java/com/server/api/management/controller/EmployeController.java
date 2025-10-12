package com.server.api.management.controller;

import com.server.api.management.entity.Employe;
import com.server.api.management.service.EmployeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
public class EmployeController {

    private final EmployeService employeService;

    public EmployeController(EmployeService employeService) {
        this.employeService = employeService;
    }

    @GetMapping("/employe/{employeId}")
    public Employe getEmployeById(@PathVariable Long employeId) {
        return employeService.getEmployeById(employeId);
    }

    @GetMapping("/employe/{entrepriseId}")
    public Page<Employe> getEmployesByEntrepriseId(@PathVariable Long entrepriseId, Pageable pageable) {
        return employeService.getEmployesByEntrepriseId(entrepriseId, pageable);
    }

    @GetMapping("/employe/all")
    public Page<Employe> getAllEmployes(Pageable pageable) {
        return employeService.getAllEmployes(pageable);
    }

    @PostMapping("/employe/create/{entrepriseId}")
    public Employe createEmploye(@PathVariable Long entrepriseId, @RequestBody Employe employe) {
        return employeService.createEmploye(entrepriseId, employe);
    }

    @PutMapping("/employe/update/{entrepriseId}")
    public Employe updateEmploye(@PathVariable Long entrepriseId, @RequestBody Employe employeRequest) {
        return employeService.updateEmploye(entrepriseId, employeRequest);
    }

    @DeleteMapping("/employe/delete/{entrepriseId}/{employeId}")
    public Employe deleteEmploye(@PathVariable Long entrepriseId, @PathVariable Long employeId) {
        return employeService.deleteEmploye(entrepriseId, employeId);
    }

    @GetMapping("/employe/salary/{entrepriseId}/{contractType}/{grille}")
    public BigDecimal getSalaryByEntrepriseIdAndContractType(@PathVariable Long entrepriseId, @PathVariable String contractType, @PathVariable String grille) {
        return employeService.getSalaryByEntrepriseIdAndContractType(entrepriseId, contractType, grille);
    }

    @GetMapping("/employe/filter/{search}")
    public Page<Employe> filterEmploye(@PathVariable String search, Pageable pageable) {
        return employeService.filterEmployes(search, pageable);
    }
}
