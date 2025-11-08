package com.server.api.management.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.server.api.management.enums.ContractType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.Date;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "employe")
public class Employe extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 5, max = 100, message = "Le nom doit être compris entre 5 et 100 caractères")
    @Column(name = "first_name",nullable = false)
    private String firstName;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 5, max = 100, message = "Le prénom doit être compris entre 5 et 100 caractères")
    @Column(name = "last_name",nullable = false)
    private String lastName;

    @NotBlank(message = "Le numéro de sécurité sociale est obligatoire")
    private String socialSecurityNumber;

    @NotBlank(message = "Le date d’embauche est obligatoire")
    private Date hiringDate;

    @NotBlank(message = "Le type de contrat est obligatoire")
    @Enumerated(EnumType.STRING)
    private ContractType contractType;


    private BigDecimal salary;

    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entreprise_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("entreprise_id")
    private Entreprise entreprise;
}
