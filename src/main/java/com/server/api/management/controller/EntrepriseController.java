package com.server.api.management.controller;

import com.server.api.management.entity.Entreprise;
import com.server.api.management.service.EntrepriseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EntrepriseController {

    private final EntrepriseService entrepriseService;

    public EntrepriseController(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    @GetMapping("/entreprise/{entrepriseId}")
    public ResponseEntity<Entreprise> getEntrepriseById(@PathVariable Long entrepriseId) {
        try {
            return new ResponseEntity<>(entrepriseService.getEntrepriseById(entrepriseId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/entreprise/all")
    public ResponseEntity<List<Entreprise>> getAllEntreprises() {
        try {
            return new ResponseEntity<>(entrepriseService.getAllEntreprises(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/entreprise/create")
    public ResponseEntity<Entreprise> createPost(@RequestBody Entreprise entreprise) {
        try {
            return new ResponseEntity<>(entrepriseService.createEntreprise(entreprise), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/entreprise/update/{id}")
    public ResponseEntity<Entreprise> updateEntreprise(@PathVariable Long id, @RequestBody Entreprise entrepriseRequest) {
        try {
            return new ResponseEntity<>(entrepriseService.updateEntreprise(id, entrepriseRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/entreprise/delete/{entrepriseId}")
    public ResponseEntity<Entreprise> deleteEntreprise(@PathVariable Long entrepriseId) {
        try {
            return new ResponseEntity<>(entrepriseService.deleteEntreprise(entrepriseId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
