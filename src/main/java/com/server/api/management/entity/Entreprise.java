package com.server.api.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entreprise")
public class Entreprise extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La raison sociale ne doit pas être vide.")
    @Size(min = 5, max = 100, message = "La raison sociale doit être comprise entre 5 et 100 caractères.")
    private String socialReason;

    @NotBlank(message = "Le siret est obligatoire")
    private String siret;

    @NotBlank(message = "Le siren est obligatoire")
    private String siren;

    @NotBlank(message = "L'adresse est obligatoire")
    @Size(min = 5, max = 100, message = "L'adresse doit être comprise entre 5 et 100 caractères.")
    private String address;
}
