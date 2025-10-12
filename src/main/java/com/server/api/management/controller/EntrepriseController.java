package com.server.api.management.controller;

import com.server.api.management.entity.Entreprise;
import com.server.api.management.service.EntrepriseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
    public Page<Entreprise> getAllEntreprises(Pageable pageable) {
        return entrepriseService.getAllEntreprises(pageable);
    }

    @PostMapping("/entreprise/create")
    public Entreprise createPost(@RequestBody Entreprise entreprise) {
        return entrepriseService.createEntreprise(entreprise);
    }

    @PutMapping("/entreprise/update")
    public Entreprise updateEntreprise(@RequestBody Entreprise entrepriseRequest) {
        return entrepriseService.updateEntreprise(entrepriseRequest);
    }

    @DeleteMapping("/entreprise/delete/{entrepriseId}")
    public Entreprise deleteEntreprise(@PathVariable Long entrepriseId) {
        return entrepriseService.deleteEntreprise(entrepriseId);
    }
}
