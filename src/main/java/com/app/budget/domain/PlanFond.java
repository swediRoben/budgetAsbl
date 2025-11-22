package com.app.budget.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "PlanFonds")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class PlanFond {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

//    @Column
//    private Long idProjet;
//
//    @Column
//    private Long categorie;
//
//    @Column
//    private Long idExercice;

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "exercice_id")
private Exercice exerciceId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "projet_id")
    private Projet projetId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categorie_id")
    private Categorie categorieId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classe_id")
    private Classe classeId;

    @Column(precision = 10, scale = 2)
    private BigDecimal montant;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
