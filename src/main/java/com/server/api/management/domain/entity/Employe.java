package com.server.api.management.domain.entity;

import com.server.api.management.domain.enums.ContractType;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employe")
@EqualsAndHashCode(callSuper = true)
public class Employe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le prénom est obligatoire")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire")
    @Column(name = "last_name", nullable = false)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entreprise_id", nullable = false)
    private Entreprise entreprise;
}
