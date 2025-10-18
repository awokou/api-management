package com.server.api.management.controller;

import com.server.api.management.entity.Employe;
import com.server.api.management.service.EmployeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeController {

    private final EmployeService employeService;

    public EmployeController(EmployeService employeService) {
        this.employeService = employeService;
    }

    @GetMapping("/employe/{employeId}")
    public ResponseEntity<Employe> getEmployeById(@PathVariable Long employeId) {
        try {
            return new ResponseEntity<>(employeService.getEmployeById(employeId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employe/{entrepriseId}")
    public ResponseEntity<List<Employe>> getEmployesByEntrepriseId(@PathVariable Long entrepriseId) {
        try {
            return new ResponseEntity<>(employeService.getEmployesByEntrepriseId(entrepriseId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employe/all")
    public ResponseEntity<List<Employe>> getAllEmployes() {
        try {
            return new ResponseEntity<>(employeService.getAllEmployes(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/employe/create/{entrepriseId}")
    public ResponseEntity<Employe> createEmploye(@PathVariable Long entrepriseId, @RequestBody Employe employe) throws IOException {
        try {
            return new ResponseEntity<>(employeService.createEmploye(entrepriseId, employe), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/employe/update/{entrepriseId}")
    public ResponseEntity<Employe> updateEmploye(@PathVariable Long entrepriseId, @RequestBody Employe employeRequest) {
        try {
            return new ResponseEntity<>(employeService.updateEmploye(entrepriseId, employeRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/employe/delete/{entrepriseId}/{employeId}")
    public ResponseEntity<Employe> deleteEmploye(@PathVariable Long entrepriseId, @PathVariable Long employeId) {
        try {
            return new ResponseEntity<>(employeService.deleteEmploye(entrepriseId, employeId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employe/salary/{entrepriseId}/{contractType}/{grille}")
    public BigDecimal getSalaryByEntrepriseIdAndContractType(@PathVariable Long entrepriseId, @PathVariable String contractType, @PathVariable String grille) {
        return employeService.getSalaryByEntrepriseIdAndContractType(entrepriseId, contractType, grille);
    }

    @GetMapping("/employe/filter/{search}")
    public ResponseEntity<List<Employe>> filterEmploye(@PathVariable String search) {
        try {
            return new ResponseEntity<>(employeService.filterEmployes(search), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
