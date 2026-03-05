package com.server.api.management.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entreprise")
@EqualsAndHashCode(callSuper = true)
public class Entreprise extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La raison sociale ne doit pas être vide.")
    @Column(name = "social_reason",nullable = false)
    private String socialReason;

    @NotBlank(message = "Le siret est obligatoire")
    @Column(name = "siret",nullable = false)
    private String siret;

    @NotBlank(message = "Le siren est obligatoire")
    @Column(name = "siren",nullable = false)
    private String siren;

    @NotBlank(message = "L'adresse est obligatoire")
    @Column(name = "address",nullable = false)
    private String address;
}
