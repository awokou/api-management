package com.server.api.management.controller;

import com.server.api.management.domain.entity.Employe;
import com.server.api.management.service.EmployeService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final EmployeService employeService;

    @GetMapping("/{id}")
    public ResponseEntity<Employe> getEmployeById(@PathVariable Long id) {
        Employe employe = employeService.getEmployeById(id);
        return new ResponseEntity<>(employe, HttpStatus.OK);
    }

    @GetMapping("/{entrepriseId}")
    public ResponseEntity<List<Employe>> getEmployesByEntrepriseId(@PathVariable Long entrepriseId) {
        List<Employe> employeList = employeService.getEmployesByEntrepriseId(entrepriseId);
        return new ResponseEntity<>(employeList, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Employe>> getAllEmployes() {
        List<Employe> employeList = employeService.getAllEmployes();
        return new ResponseEntity<>(employeList, HttpStatus.OK);
    }

    @PostMapping("/{entrepriseId}")
    public ResponseEntity<Employe> createEmploye(@PathVariable Long entrepriseId, @RequestBody Employe employe) {
        Employe employeCreate = employeService.createEmploye(entrepriseId, employe);
        return new ResponseEntity<>(employeCreate, HttpStatus.CREATED);
    }

    @PutMapping("/{entrepriseId}")
    public ResponseEntity<Employe> updateEmploye(@PathVariable Long entrepriseId, @RequestBody Employe employeRequest) {
        Employe employeUpdate = employeService.updateEmploye(entrepriseId, employeRequest);
        return new ResponseEntity<>(employeUpdate, HttpStatus.OK);
    }

    @DeleteMapping("/{entrepriseId}/{employeId}")
    public ResponseEntity<Employe> deleteEmploye(@PathVariable Long entrepriseId, @PathVariable Long employeId) {
        Employe deleted = employeService.deleteEmploye(entrepriseId, employeId);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @GetMapping("/salary/{entrepriseId}/{contractType}/{grille}")
    public BigDecimal getSalaryByEntrepriseIdAndContractType(@PathVariable Long entrepriseId,
            @PathVariable String contractType, @PathVariable String grille) {
        return employeService.getSalaryByEntrepriseIdAndContractType(entrepriseId, contractType, grille);
    }

    @GetMapping("/filter/{search}")
    public ResponseEntity<List<Employe>> filterEmploye(@PathVariable String search) {
        List<Employe> employeFiltered = employeService.filterEmployes(search);
        return new ResponseEntity<>(employeFiltered, HttpStatus.OK);
    }
}
