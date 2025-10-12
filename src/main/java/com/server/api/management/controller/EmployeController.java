package com.server.api.management.controller;

import com.server.api.management.entity.Employe;
import com.server.api.management.service.EmployeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public Employe getEmployeById(@PathVariable Long employeId) {
        return employeService.getEmployeById(employeId);
    }

    @GetMapping("/employe/{entrepriseId}")
    public List<Employe> getEmployesByEntrepriseId(@PathVariable Long entrepriseId) {
        return employeService.getEmployesByEntrepriseId(entrepriseId);
    }

    @GetMapping("/employe/all")
    public List<Employe> getAllEmployes() {
        return employeService.getAllEmployes();
    }

    @PostMapping(value = "/employe/create/{entrepriseId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Employe createEmploye(@PathVariable Long entrepriseId, @RequestBody Employe employe, @RequestParam("file") MultipartFile file) throws IOException {
        return employeService.createEmploye(entrepriseId, employe, file);
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
    public List<Employe> filterEmploye(@PathVariable String search) {
        return employeService.filterEmployes(search);
    }
}
