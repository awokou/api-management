package com.server.api.management.controller;

import com.server.api.management.domain.entity.Entreprise;
import com.server.api.management.service.EntrepriseService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/entreprise")
@RequiredArgsConstructor
public class EntrepriseController {

    private final EntrepriseService entrepriseService;

    @GetMapping("/{id}")
    public ResponseEntity<Entreprise> getEntrepriseById(@PathVariable Long id) {
        Entreprise entreprise = entrepriseService.getEntrepriseById(id);
        if (entreprise == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(entreprise);
    }

    @GetMapping
    public ResponseEntity<List<Entreprise>> getAllEntreprises() {
        List<Entreprise> entrepriseList = entrepriseService.getAllEntreprises();
        return ResponseEntity.status(HttpStatus.OK).body(entrepriseList);
    }

    @PostMapping
    public ResponseEntity<Entreprise> createPost(@RequestBody Entreprise entreprise) {
        Entreprise entrepriseCreate = entrepriseService.createEntreprise(entreprise);
        return ResponseEntity.status(HttpStatus.CREATED).body(entrepriseCreate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entreprise> updateEntreprise(@PathVariable Long id,
            @RequestBody Entreprise entrepriseRequest) {
        Entreprise entreprise = entrepriseService.updateEntreprise(id, entrepriseRequest);
        return ResponseEntity.status(HttpStatus.OK).body(entreprise);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Entreprise> deleteEntreprise(@PathVariable Long id) {
        Entreprise deleted = entrepriseService.deleteEntreprise(id);
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }
}
