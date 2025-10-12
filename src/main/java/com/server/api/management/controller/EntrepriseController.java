package com.server.api.management.controller;

import com.server.api.management.entity.Entreprise;
import com.server.api.management.service.EntrepriseService;
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
    public Entreprise getEntrepriseById(@PathVariable Long entrepriseId) {
        return entrepriseService.getEntrepriseById(entrepriseId);
    }

    @GetMapping("/entreprise/all")
    public List<Entreprise> getAllEntreprises() {
        return entrepriseService.getAllEntreprises();
    }

    @PostMapping("/entreprise/create")
    public Entreprise createPost(@RequestBody Entreprise entreprise) {
        return entrepriseService.createEntreprise(entreprise);
    }

    @PutMapping("/entreprise/update/{id}")
    public Entreprise updateEntreprise(@PathVariable Long id,@RequestBody Entreprise entrepriseRequest) {
        return entrepriseService.updateEntreprise(id,entrepriseRequest);
    }

    @DeleteMapping("/entreprise/delete/{entrepriseId}")
    public Entreprise deleteEntreprise(@PathVariable Long entrepriseId) {
        return entrepriseService.deleteEntreprise(entrepriseId);
    }
}
