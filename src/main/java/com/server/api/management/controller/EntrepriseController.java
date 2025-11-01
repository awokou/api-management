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
            Entreprise entreprise = entrepriseService.getEntrepriseById(entrepriseId);
            if (entreprise == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 si introuvable
            }
            return new ResponseEntity<>(entreprise, HttpStatus.OK); // 200 si trouvé
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // 500 si exception
        }
    }

    @GetMapping("/entreprise/all")
    public ResponseEntity<List<Entreprise>> getAllEntreprises() {
        try {
            List<Entreprise> entrepriseList = entrepriseService.getAllEntreprises();
            return new ResponseEntity<>(entrepriseList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/entreprise/create")
    public ResponseEntity<Entreprise> createPost(@RequestBody Entreprise entreprise) {
        try {
            Entreprise entrepriseCreate = entrepriseService.createEntreprise(entreprise);
            return new ResponseEntity<>(entrepriseCreate, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/entreprise/update/{id}")
    public ResponseEntity<Entreprise> updateEntreprise(@PathVariable Long id, @RequestBody Entreprise entrepriseRequest) {
        try {
            Entreprise entreprise = entrepriseService.updateEntreprise(id, entrepriseRequest);
            return new ResponseEntity<>(entreprise, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/entreprise/delete/{entrepriseId}")
    public ResponseEntity<Entreprise> deleteEntreprise(@PathVariable Long entrepriseId) {
        try {
            Entreprise deleted = entrepriseService.deleteEntreprise(entrepriseId);
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);// returns 500 if exception occurs
        }
    }
}
