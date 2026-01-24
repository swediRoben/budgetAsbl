package com.app.budget.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "compteur_be")
public class CompteurBE {

    @Id
    @Column(name="annee")
    private Integer annee;

    @Column(name="last_number", nullable = false)
    private Long lastNumber;
}

